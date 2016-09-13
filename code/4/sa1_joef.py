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

#Constants
random.seed(1)



def energy_schaffer(x):
  f1=x**2
  f2=(x-2)**2
  return f1, f2

def baseline():
  local_fmin=100000
  local_fmax=0
  f1=f2=0
  for x in range(100):
    f1, f2 = energy_schaffer(x)
    # check for min
    if f1 < local_fmin:
      local_fmin = f1
    if f2 < local_fmin:
      local_fmin = f2
    # check for max
    if f1> local_fmax:
      local_fmax = f1
    if f2> local_fmax:
      local_fmax = f2
  return local_fmin, local_fmax

def schaffer_norm(f1, f2):
  return ((f1+f2) - fmin) / (fmax - fmin)

# Beginners tests
def testFunction():
  #schaffer_norm1 = schaffer_norm(0,0)
  #print(schaffer_norm1)
  #print("EF = %s %s " % energy_schaffer(4) )
  print("baseline fmin = %s max = %s" %baseline() )

def annealing():
  #  These values come from experiments
  fmax = 9801 # from baseline runs
  fmin = 0 # from baseline runs
  kmax = 1000 # number of attempts
  emax = 10000 # maximum possible energy

  s = 0 
  e = sys.maxint

  for k in xrange(kmax):
    


if __name__ == '__main__':
  if True:
    testFunction()
