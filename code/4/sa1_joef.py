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
  for x in range(100):
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
  "Hard coded to 10.  If out of bounds, bouce back"
  jump=random.randint(-10, +10)
  if s+jump < -10000 or s+jump > 10000:
    return s-jump
  return s+jump

def E(s):
  "Energy normalized from previous tests"
  # From earlier baseline
  emax = 198363364 
  emin = 3874
  f1=s*s
  f2=(s-2)*(s-2)
  return ((f1+f2) - emin) / (emax - emin)


# Beginners tests
def testFunction():
  #schaffer_norm1 = schaffer_norm(0,0)
  #print(schaffer_norm1)
  #print("EF = %s %s " % energy_schaffer(4) )
  baseline()

def annealing():
  global smin, smax
  kmax = 1000 # number of attempts
  k  = 0    # Current count
  #  These values come from experiments
  emax = 0 # maximum acceptable energy

  s = random.randint(smin,smax)    # Start Solution
  e = E(s)
  sn = 0
  sb = 0    # Best Solution ( so far )
  eb = .9

  print("Starting annealing")
  say(eb)
  #say("asdf")
  #say(emax)

  while( k<kmax and e > emax ):
    sn = neighbor(s) #  Get new neighbor
    en = E(sn)       #  Get new neighbor's energy
    #say ("solution sn=");say (sn );say (" en=");say (en );say ("\n");say (sb)
    #pdb.set_trace()
    #If new is better than BEST, make new BEST
    if en < eb:
      sb = sn
      se = en
      say("!")
    #If new is better than CURRENT
    if en < e: #Move there
      s = sn
      e = en
      say("+")
    else: 
      #Do we jump?
      if (P(e, en, k/kmax ) < random.random() ):
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
  if True:
    annealing()
