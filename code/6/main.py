
import sys, random
from problems import Schaffer,Osyczka2,Kursawe
from optimizers import sa, mws

if __name__ == '__main__':
  if(len(sys.argv)==2 ):
    random.seed(int(sys.argv[1] ))
    print("Starting with seed %s" % sys.argv[1])
  else:
    random.seed(1)
    print("Starting with seed 1")

  #Copy paste from req.
  for model in [Schaffer, Osyczka2, Kursawe]:
    for optimizer in [sa, mws]:
      optimizer(model())
