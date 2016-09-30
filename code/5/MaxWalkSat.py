#!/usr/bin/python
'''
Modified from Dr. Menzies http://menzies.us/
'''
from __future__ import division,print_function
import sys,random,os
sys.dont_write_bytecode=True

class Pretty(object):
  def __repr__(i):
    return i.__class__.__name__ + kv(i.__dict__)

class O(Pretty):
  def __init__(i, **adds): i.__dict__.update(adds)

def kv(d):
  return '('+', '.join(['%s: %s' % (k,d[k])
    for k in sorted(d.keys())
    if k[0] != "_"]) + ')'


def say(x):
  sys.stdout.write(str(x)); sys.stdout.flush()

def random_value(low, high, decimals=2):
  """Generate a random number between low and high. decimals incidicate number of decimal places"""
  return round(random.uniform(low, high), decimals)

class Decision(O):
  def __init__(self, name='unknown', low=0, high=10):
    O.__init__(self, name=name, low=low, high=high)

  def is_valid(self, v):
    if(v>self.low and v<self.high):
      return True
    else:
      return False

  def make_value(self):
    """Give a random valid value"""
    return random_value(self.low, self.high)


class Point(O):
  """Member of the population.  Holds decision values"""
  def __init__(self, dvals):
    O.__init__(self, dvals=dvals)
    self.ovals = None

  def __eq__(self, other):
    return self.dvals == other.dvals

  def clone(self):
    np = Point(list( self.dvals) )
    np.ovals=list(self.ovals)
    return np

  def is_better_than(self, other):
    my_score = sum(self.ovals)
    other_score = sum(other.ovals)
    if my_score < other_score:
      return True
    else:
      return False

class Objective(O):
  def __init__(self, name, do_minimize=True):
    """
    @param name: Name of the objective
    @param do_minimize: Flag indicating if objective has to be minimized or maximized
    """
    O.__init__(self, name=name, do_minimize=do_minimize)

class Osyczka2(O):
  def __init__(self):
    O.__init__(self,)
    self.local_search_attempts=10  # how many tries during local search
    self.decisions = self.make_decisions()
    self.objectives = [Objective("f1", True),
                       Objective("f2", True)]

  def make_decisions(self):
    x1 = Decision('x1', 0, 10)
    x2 = Decision('x2', 0, 10)
    x3 = Decision('x3', 1, 5)
    x4 = Decision('x4', 1, 5)
    x5 = Decision('x5', 0, 6)
    x6 = Decision('x6', 0, 10)
    decisions = [x1, x2, x3, x4, x5, x6]
    return decisions

  def fix_index(self, tmp_list):
    """Changes zero-based to one-based list, for sanity's sake with  Osyczka2 requirements"""
    tmp_x = [0]
    tmp_x.extend(tmp_list)
    return tmp_x

  def is_valid(self, point):
    '''Must pass all constraints to be a valid point for this problem.  This should probably be parameterized.'''
    if len(self.decisions ) != 6:
      print("Can not validate point "+point+" if decisions no 6.  decisions = "+len(self.decisions))
      return False
    #Prepend zero to the list, so we can use the same indexes as the requirements
    x=self.fix_index(point.dvals)
    #g1
    if 0 > x[1]+x[2] - 2:
      #print("failed g1")
      return False
    #g2
    if 0 > 6-x[1]-x[2]:
      #print("failed g2")
      return False
    #g3
    if 0 > 2-x[2]+x[1]:
      #print("failed g3")
      return False
    #g4
    if 0 > 2-x[1]+3*x[2]:
      #print("failed g4")
      return False
    #g5
    if 0 > 4-pow( (x[3]-3), 2)-x[4]:
      #print("failed g5")
      return False
    #g6
    if 0 > pow( (x[5]-3), 3)+x[6]-4:
      #print("failed g6")
      return False
    return True

  def generate_neighbor(self, p):
    """perturb by one decision"""
    n=p.clone()
    #For a random decision
    d_index=random.randint(0, len(self.decisions)-1 )  #Which decision to test
    #pick a new point
    new_p = self.find_best_point_by_local_search(p, d_index)
    return n

  def find_best_point_by_local_search(self, p, d_index):
    best_p = p.clone()
    tmp_p = p.clone()

    for x in range(self.local_search_attempts + 1): # Try 0% to 90%, plus 100% == attempts plus 1
      d_value = self.find_incremental_value_for_local_search(self.decisions[d_index], x/float(self.local_search_attempts) )
      #Try that value
      tmp_p.dvals[d_index] = d_value
      if self.is_valid(tmp_p):  #Skip if not valid
        self.calc_objectives(tmp_p)
        if tmp_p.is_better_than(best_p):
          best_p = tmp_p
    return best_p

  def find_incremental_value_for_local_search(self, decision, percent):
    val = round(percent * (decision.high - decision.low) + decision.low, 2 )
    return val

  def generate_one(self):
    while True:
      p = Point([d.make_value() for d in self.decisions ])
      #print(p)
      if self.is_valid(p):
        return p

  def calc_objectives(self, p):
    """Sets objectives in p AND returns them"""
    f1= round(self.calcf1(p), 2)
    f2= round(self.calcf2(p), 2)
    p.ovals = [f1, f2]
    return p.ovals

  # It seems like the objective calculations can also be parameterized.
  def calcf1(self,p):
    """Calculates but does NOT set f1"""
    x=self.fix_index(p.dvals)
    #f1=x[0]
    f1= -1 * (25*pow((x[1]-2), 2) )
    f1 = f1 + pow( (x[2] - 2), 2)
    f1 = f1 + pow( (x[3] - 1), 2)*pow( (x[4]-4), 2)
    f1 = f1 + pow( (x[5]-1) , 2)
    return f1

  def calcf2(self, p):
    """Calculates but does NOT set f2"""
    x=self.fix_index(p.dvals)
    f2=pow(x[1], 2)+pow(x[2], 2)+pow(x[3], 2)+pow(x[4], 2)+pow(x[5], 2)+pow(x[6], 2)
    return f2

#EndOfClass

def populate(problem, size):
  population = []
  for _ in range(size):
    population.append(problem.generate_one())
  return population

def runMaxWalkSat(loop_counts = 1000, seed=1):
  random.seed(seed)
  problem = Osyczka2()
  #Set initial point and conditions
  cur_p = problem.generate_one()
  problem.calc_objectives(cur_p)
  best_p = next_p = cur_p
  say("\n "+str(best_p.ovals)+" : ")
  loop_count = 0
  while loop_count < loop_counts:
    #Big jump or small jump?
    if .5 < random.random():
      next_p = problem.generate_neighbor(cur_p)
    else:
      say("!")
      next_p = problem.generate_one()
    problem.calc_objectives(next_p)
    if next_p.is_better_than(cur_p):
      cur_p = next_p
      say("+")
      if next_p.is_better_than(best_p):
        best_p = next_p
    else:
      say(".")
    loop_count += 1
    if loop_count %50 == 0:
      say("\n")
      say(str(best_p.ovals) )
  return best_p

if __name__ == '__main__':
  print("sfsg -> %s" % "so far, so good")
  if len(sys.argv) < 2:
    seed = 1
  else:
    seed = int(sys.argv[1])
  print("SEED # " + str(seed))
  pr = Osyczka2()
  solution = runMaxWalkSat(1000, 22)
  print("\nSolution "+str(solution  ))


# vim:sw=2 ts=2
