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

class o(Pretty):
  def __init__(i, **adds): i.__dict__.update(adds)

def kv(d):
  return '('+', '.join(['%s: %s' % (k,d[k])
    for k in sorted(d.keys())
    if k[0] != "_"]) + ')'

if __name__ == '__main__':
  print("sfsg -> %s" % "so far, so good")

# vim:sw=2 ts=2
