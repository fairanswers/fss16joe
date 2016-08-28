
#3.1
print "3.1"
def print_lyrics():
    print "I'm a lumberjack, and I'm okay."
    print "I sleep all night and I work all day."

def repeat_lyrics():
    print_lyrics()
    print_lyrics()

repeat_lyrics()

#3.2 
print "3.2"
print """
Traceback (most recent call last):
  File "think3.py", line 1, in <module>
    repeat_lyrics()
NameError: name 'repeat_lyrics' is not defined
"""

#3.3
print "3.3"
print "Length of allen is "+str(len("allen") )

#3.4
print "3.4"
def do_twice(f, val):
    f(val)
    f(val)

def print_twice( bitsToPrint):
    print str(bitsToPrint)

do_twice(print_twice, "spam")

def do_four(f):
    for x in xrange(4):
	f()

print "Four times"
do_four(print_lyrics)

#3.5
print "3.5"
#A square is made up of a top row and a left row
#A grid is made up of multiple squares
def make_square_top(len):
    for x in range(len):
        print '+', '- ' * 4,
    print '+'

def make_square_left(len):
    for y in range(4):
        for x in range(len + 1):
            print '/', '  ' * 4,
	print

def make_grid(gridWidth):
    for a in range(gridWidth):
        make_square_top(gridWidth)
        make_square_left(gridWidth)
    make_square_top(gridWidth)

print "2x2"
make_grid(2)
print "4x4"
make_grid(4)
