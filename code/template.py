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


if __name__ == '__main__':
  print("sfsg -> %s" % "so far, so good")

# vim:sw=2 ts=2
