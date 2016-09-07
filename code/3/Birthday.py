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
    for k in sorted(d.keys()) if k[0] != "_"]) + ')'


def has_duplicates(lst):
    d = {}
    if(lst == None):
        return False
    for x in lst:
        if(d.get(x, None) == None):
            d[x] = 1
        else:
            return True;
    return False

#Birthday paradox setup
sampleCount = 100000
sampleSize = 23
random.seed(1)


def make_sample(size):
    sample = []
    #make random samples
    for _ in range(sampleSize):
        sample.append(random.randint(0, 365) ) #leap year
    return sample

yes = 0
for x in range(sampleCount):
    s = make_sample(sampleSize)
    if(has_duplicates(s) ):
        #print("found one")
        yes += 1

print("Found %s duplicates out of %s attempts.  %s percent" % (str(yes), str(sampleCount) , str(yes/sampleCount * 100) ) )

#    print("Same %s = %s" % str(s), str(sample)

# has_duplicates tests
#print("empty = %s" % has_duplicates(None) )
#testList = ['a', 'b']
#print("false = %s" % has_duplicates(testList) )
#testList = ['a', 'b', 'a']
#print("true = %s" % has_duplicates(testList) )
