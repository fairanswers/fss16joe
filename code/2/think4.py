from swampy.TurtleWorld import *

world = TurtleWorld()
bob = Turtle()
bob.set_delay(0.0001)

def square(t, length):
  for x in xrange(4):
    fd(t, 100)
    lt(t)

def polygon(t, length, sides):
  ang = 360 - (360 / sides)
  for x in xrange(sides):
    fd(t, length)
    rt(t, ang)


square(bob, 44)
#polygon(bob, 20, 10)


wait_for_user()
