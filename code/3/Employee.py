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


class Employee:
  "Class to hold henchmen data"
  def __init__(self, name='unknown', age=-1):
    self.name = name
    self.age = age


  def __repr__(self):
    return kv(self.__dict__)

  def __cmp__(self, other):
    if(self.age == other.age):
      return 0
    if(self.age > other.age):
      return 1
    else:
      return -1

e=Employee()
print(e)

f=Employee("Dandis Moore", 27)
print(f)

g=Employee("Arthur Dent", 22)
print(g)

lot=[e, f, g]
print("Before")
print(lot)
print("After");
print(sorted(lot))

