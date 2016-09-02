from swampy.TurtleWorld import *
from math import pi, sin, cos

world = TurtleWorld()
bob = Turtle()
bob.set_delay(.01)

def square(t, length):
  for x in xrange(4):
    fd(t, length)
    lt(t)

def polygon(t, length, sides):
  ang = 360 - (360 / sides)
  for x in xrange(sides):
    fd(t, length)
    rt(t, ang)

def circle(t, radius):
  #diameter = pi * 2* radius
  #Each side length = diameter / number of sides
  sides = 360
  length = (pi*2*radius)/sides
  polygon(t, length, sides)

def arc(t, radius, angle):
  sides = angle
  ang = 1
  length = (pi*2*radius)/360
  for x in xrange(sides):
    fd(t, length)
    rt(t, ang)

def make_petal(t, radius, petal_angle, width):
  print "start dir = " + str(t.get_heading())
  heading = t.get_heading()
  #pre turn
  lt(t, (petal_angle/2) * 1 )
  #first half
  arc_len = (radius) * 1  #Need trig function here to get length right.
  arc(t, arc_len, petal_angle)
  print "petal tip dir = " + str(t.get_heading())
  #turn around
  lt(t, -180)
  #turn for the petal
  lt(t, petal_angle)
  arc(t, arc_len, petal_angle)
  rt(t, t.get_heading() - heading)
  print "end dir = " + str(t.get_heading())

def flower(t, radius, petals, width):
  start_angle = t.get_heading() 
  petal_angle = 360 / petals #90
  print "petal_angle = " + str(petal_angle)
  for p in range(petals):
     make_petal(t, radius, int(petal_angle * width), width) 
     rt(t, petal_angle )
  t.rt(t.get_heading() - start_angle)  
#flower(bob, 100, 7, 1)
#bob.pu()
#fd(bob, 150)
#bob.pd()
#flower(bob, 80, 10, 2)
#bob.pu()
#fd(bob, 150)
#bob.pd()
#flower(bob, 220, 20, 1)
#bob.pu()
#fd(bob, 150)
#bob.pd()

def make_piece(t, angle, radius):
  print "start angle = "+str(t.get_heading() )
  stupid_radians = pi/180
  start_angle = t.get_heading() 
  base_angle = (180 - angle) / 2
  inner_angle = angle / 2
  print "base angle = "+str(base_angle )
  print "inner angle = "+str(inner_angle )
  base_len = sin( (inner_angle * stupid_radians)/ sin(base_angle * stupid_radians )) * radius * 2
  leg_len = 1/sin(base_angle * stupid_radians ) * radius
  print "angle "+str(angle)
  print "base_len "+str(base_len)
  print "leg_len "+str(leg_len)
  fd(t, leg_len)
  rt(t, 180)
  lt(t, base_angle)
  fd(t, base_len)
  rt(t, 180)
  lt(t, base_angle)
  fd(t, leg_len)
  rt(t, t.get_heading() - start_angle)  
  print "end angle = "+str(t.get_heading() )

def pie(t, sides, radius):
  piece_angle = 360 / sides
  for s in range(sides):
    make_piece(t, piece_angle, radius)
    rt(t, piece_angle)

pie(bob, 10, 100)
bob.pu()
fd(bob, 200)
bob.pd()
pie(bob, 6, 100)
bob.pu()
fd(bob, 200)
bob.pd()
pie(bob, 5, 100)


#bob.rt(bob.get_heading() - bob.get_heading)
#circle(bob, 100)
#arc(bob, 100, 360)
#arc(bob, 10, 180)
#arc(bob, 10, 180)
#arc(bob, 100, 360)

#square(bob, 100)
#polygon(bob, 20, 10)

#circle(bob, 100)
#circle(bob, 10)
#circle(bob, 500)
#circle(bob, 1000)


wait_for_user()
