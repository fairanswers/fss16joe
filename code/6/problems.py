#Represents the problems that we're trying to solve for:
#My code is at the bottom

from __future__ import print_function, division
from math import *
import random, sys
from sys import dont_write_bytecode
dont_write_bytecode=True

#Reused from class

# Utility functions
def say(*lst):
  """
  Print whithout going to new line
  """
  print(*lst, end="")
  sys.stdout.flush()


def random_value(low, high, decimals=2):
  """
  Generate a random number between low and high.
  decimals incidicate number of decimal places
  """
  return round(random.uniform(low, high), decimals)


def gt(a, b): return a > b


def lt(a, b): return a < b


def shuffle(lst):
  """
  Shuffle a list
  """
  random.shuffle(lst)
  return lst


class O:
  """
  Basic Class which
      - Helps dynamic updates
      - Pretty Prints
  """

  def __init__(self, **kwargs):
    self.has().update(**kwargs)

  def has(self):
    return self.__dict__

  def update(self, **kwargs):
    self.has().update(kwargs)
    return self

  def __repr__(self):
    show = [':%s %s' % (k, self.has()[k])
            for k in sorted(self.has().keys())
            if k[0] is not "_"]
    txt = ' '.join(show)
    if len(txt) > 60:
      show = map(lambda x: '\t' + x + '\n', show)
    return '{' + ' '.join(show) + '}'

class Decision(O):
  """
  Class indicating Decision of a problem
  """

  def __init__(self, name, low, high):
    """
    @param name: Name of the decision
    @param low: minimum value
    @param high: maximum value
    """
    O.__init__(self, name=name, low=low, high=high)


class Objective(O):
  """
  Class indicating Objective of a problem
  """

  def __init__(self, name, do_minimize=True, low=0, high=1):
    """
    @param name: Name of the objective
    @param do_minimize: Flag indicating if objective has to be minimized or maximized
    """
    O.__init__(self, name=name, do_minimize=do_minimize, low=low, high=high)

  def normalize(self, val):
    return (val - self.low) / (self.high - self.low)


class Point(O):
  """
  Represents a member of the population
  """

  def __init__(self, decisions):
    O.__init__(self)
    self.decisions = decisions
    self.objectives = []

  def __hash__(self):
    return hash(tuple(self.decisions))

  def __eq__(self, other):
    return self.decisions == other.decisions

  def clone(self):
    new = Point(self.decisions[:])
    new.objectives = self.objectives[:]
    return new


class Problem(O):
  """
  Class representing the cone problem.
  """

  def __init__(self, decisions, objectives):
    """
    Initialize Problem.
    :param decisions -  Metadata for Decisions
    :param objectives - Metadata for Objectives
    """
    O.__init__(self)
    self.decisions = decisions
    self.objectives = objectives

  @staticmethod
  def evaluate(point):
    assert False
    return point.objectives

  @staticmethod
  def is_valid(point):
    return True

  def generate_one(self, retries=20):
    for _ in xrange(retries):
      point = Point([random_value(d.low, d.high) for d in self.decisions])
      if self.is_valid(point):
        point.objectives = self.evaluate(point)
        #print("Generated "+ str(point))
        return point
    raise RuntimeError("Exceeded max runtimes of %d" % 20)

  def any(self, retries=20):
    self.generate_one()

# For GA
def populate(problem, size):
    """
    Create a Point list of length size
    """
    population = []
    for _ in range(size):
      population.append(problem.generate_one())
    return population


def crossover(mom, dad):
  """
  Create a new point which contains decisions from
  the first half of mom and second half of dad
  """
  n = len(mom.decisions)
  return Point(mom.decisions[:n // 2] + dad.decisions[n // 2:])


def mutate(problem, point, mutation_rate=0.01):
  """
  Iterate through all the decisions in the point
  and if the probability is less than mutation rate
  change the decision(randomly set it between its max and min).
  """
  for i, decision in enumerate(problem.decisions):
    if random.random() < mutation_rate:
      point.decisions[i] = random_value(decision.low, decision.high)
  return point


def bdom(problem, one, two):
  """
  Return if one dominates two based
  on binary domintation
  """
  objs_one = problem.evaluate(one)
  objs_two = problem.evaluate(two)
  dominates = False
  for i, obj in enumerate(problem.objectives):
    better = lt if obj.do_minimize else gt
    if better(objs_one[i], objs_two[i]):
      dominates = True
    elif objs_one[i] != objs_two[i]:
      return False
  return dominates


def fitness(problem, population, point, dom_func):
  """
  Evaluate fitness of a point based on the definition in the previous block.
  For example point dominates 5 members of population,
  then fitness of point is 5.
  """
  return len([1 for another in population if dom_func(problem, point, another)])


def elitism(problem, population, retain_size, dom_func):
  """
  Sort the population with respect to the fitness
  of the points and return the top 'retain_size' points of the population
  """
  fitnesses = []
  for point in population:
    fitnesses.append((fitness(problem, population, point, dom_func), point))
  population = [tup[1] for tup in sorted(fitnesses, reverse=True)]
  return population[:retain_size]


#All new good stuff
class Schaffer(Problem):
  def __init__(self):
    print("Init for Schaffer")
    fmin = -10000
    fmax = 10000
    decisions = []
    objectives = []
    names = ["x"]
    lows = [fmin]
    highs = [fmax]
    for i in range(len(names)):
      decisions.append(Decision(names[i], lows[i], highs[i]))
    objectives.append(Objective("Energy", True, 4, 199920010 ) )
    Problem.__init__(self, decisions, objectives)

  def ok(self, obj):
    return True

  def evaluate(self, point):
    #print("Eval point "+str(point) )
    s=point.decisions[0]
    tmp = s*s*(s-2)*(s-2)
    point.objectives= [tmp]
    return point.objectives

  def neighbor(self, point):
    """ Neighbors are withing 1% of each other on each decision"""
    for i in range(len(self.decisions) ):
      d=self.decisions[i]
      neighbor_range = (d.high - d.low) * .01
      point.decisions[i] = point.decisions[i] + random.randrange(-1*neighbor_range, neighbor_range)
      self.evaluate(point)
    return point

class Osyczka2:
  def __init__(self):
    print("Init for Osyczka2")
    return None

class Kursawe:
  def __init__(self):
    print("Init for Kursawe")
    return None



if __name__ == '__main__':
  prob = Schaffer()
  p1=prob.generate_one()
  print(str(p1))
  p2=prob.generate_one()
  print(str(p2))
  if(bdom(prob, p1, p2)):
    print("True")
  else:
    print("False")