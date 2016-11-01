
import sys
import random
from problems import bdom, say

class sa:
  def __init__(self, prob):
    print("Init sa")
    kmax = 1000  # Count of attempts
    k = 0  # Current count
    emax = 50  # limit of acceptable energy
    s = prob.generate_one()
    prob.evaluate(s)
    sn = s
    sb = s
    while k<kmax :
      #See if new is better than the best
      if bdom(prob, sn, sb):
        sb=sn
        say("!")
      #If new is better than current move there
      if bdom(prob, sn, s):
        s = sn
        sn = prob.neighbor(s)
        say("+")
      else:
        #Should we jump?
        if (k*1.0)/(kmax*1.0) < random.random():
          s=sn
          sn = prob.generate_one()
          say("?")
        else:
          s=sn
          sn = prob.neighbor(s)
          say(".")
      k = k + 1
      if(k%50==0):
        say("\n")
        say(str(sb.decisions[0])+" - "+ str(sb.objectives[0]) +" - ")

    say("Best is point "+str(sb.decisions)+" energy "+str(sb.objectives) )
      

class mws:
  def __init__(self, prob):
    print("in mws")
    kmax = 1000  # Count of attempts
    k = 0  # Current count
    s = prob.generate_one()
    sb = s
    while k < kmax:
      k = k + 1
      if k % 50 == 0:
        say("\n")
        say(str(sb.decisions[0])+" - "+ str(sb.objectives[0]) +" - ")
      #See if new is better than best
      if bdom(prob, s, sb):
        sb = s
        say("!")
      else:
        #Check for local or jump
        if (k*1.0)/(kmax*1.0) < random.random():
          say("?")
          #Do local and compare
          for i in range(10): #Better programmers would make this a variable
            tmp=prob.neighbor(s, .1)# George says check nearest 10% for SA.
            if bdom(prob, tmp, s): #If it's better, use that
              s = tmp

        else:
          #Do jump
          s=prob.generate_one()
          say(".")





