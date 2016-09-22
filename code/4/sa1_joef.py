#!/usr/bin/python
'''
Modified template from Dr. Menzies http://menzies.us/
'''
from __future__ import division,print_function
import sys,random,os,math
sys.dont_write_bytecode=True
import pdb

class Pretty(object):
  def __repr__(i):
    return i.__class__.__name__ + kv(i.__dict__)

def kv(d):
  return '('+', '.join(['%s: %s' % (k,d[k])
    for k in sorted(d.keys())
    if k[0] != "_"]) + ')'

def items(x):
  if is_instance(x,(list,tuple)):
    for y in x:
      for z in items(y):
        yield z
  else:
    yield x

def say(x): 
  sys.stdout.write(str(x)); sys.stdout.flush()

# Let's start at the very beginning...
random.seed(1)

smin = -10000 #Minimum solution
smax =  10000 # Maximum solution

def energy_schaffer(x):
  f1=x**2
  f2=(x-2)**2
  return f1, f2

def baseline():
  global smin, smax
  emin=sys.maxint
  emax=-sys.maxint
  basemin=0
  basemax=0
  for x in range(10000):
    s=random.randint(smin, smax)
    e = s*s + (s-2)*(s-2)
    if e < emin:
      emin = e
      basemin = s
    if e> emax:
      emax = e
      basemax = s
  print("Emin = %s=%s Emax = %s=%s" %(emin, basemin, emax, basemax) )
  return emin, emax


def P(e, en, kpct):
  return kpct

def neighbor(s):
  global smin, smax
  jump=random.randint(-10, +10)
  if s+jump < -smin or s+jump > smax:
    return s-jump
  return s+jump


def distant_neighbor(s):
  global smin, smax
  jump=random.randint(smin, smax)
  return jump

def E(s):
  "Energy normalized from previous tests"
  # From earlier baseline
  emax = 199920010
  emin = 4
  f1=s*s
  f2=(s-2)*(s-2)
  #return ((f1+f2) - emin) / (emax - emin)
  return f1+f2


# Beginners tests
def testFunction():
  #schaffer_norm1 = schaffer_norm(0,0)
  #print(schaffer_norm1)
  #print("EF = %s %s " % energy_schaffer(4) )
  baseline()

def annealing():
  global smin, smax
  kmax = 1000 # Count of attempts
  #kmax = 5 # Count of attempts
  k  = 0      # Current count
  #  These values come from experiments
  emax = 0 # limit of acceptable energy

  s = random.randint(smin,smax)    # Start Solution
  e = E(s)
  sn = s
  sb = s    # Best Solution ( so far )
  eb = e

  print("Starting annealing")
  #say(str(eb))
  #say("asdf")
  #say(emax)
  say(eb)

  while( k<kmax and e > emax ):
    
    #pdb.set_trace()
    sn = neighbor(s) #  Get new neighbor
    en = E(sn)       #  Get new neighbor's energy
    #say ("solution sn=");say (sn );say (" en=");say (en );say ("\n");say (sb)
    #pdb.set_trace()
    #If new is better than BEST, make new BEST
    if en < eb:
      sb = sn
      se = en
      eb = en
      say("!")
    #If new is better than CURRENT
    if en < e: #Move there
      s = sn
      e = en
      say("+")
    else: 
      #Do we jump?
      if (k/kmax < random.random() ):
        s = random.randint(smin, smax);
        e = E(s)
        say("?")
      else:
        say(".")
    k += 1
    if k%50==0:  say("\n"); say(eb)
  print("Finised annealing.  Solution = %s Energy = %f" % (sb, eb) )
    


if __name__ == '__main__':
  if False:
    testFunction()
    #print(E(1))
    #print(E(100))
    #print(E(1000))
    #print(E(10000))
  else:
    #pdb.set_trace()
    if(len(sys.argv)==2 ):
      random.seed(int(sys.argv[1] ))
      print("Starting with seed %s" % sys.argv[1])
    else:
      random.seed(1)
      print("Starting with seed 1")
    annealing()
