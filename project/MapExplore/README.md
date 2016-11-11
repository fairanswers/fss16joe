# ASE16 Joe's Final Project
# MapExplore
This project will use Particle Swarm Optimization to cover a map.  

The decisions will include
* Map size
* Number of agents
* Points of Interest (POI)

Objectives:
* Percent map coverage (maximize)
* Time (measured in clock ticks)
* POI found

Interesting Scenarios
* Known POI location vs. unknown POI location.  Bomb dogs and MIA search and rescue
* Starting at random points vs the same point.
* Solar charging - Rain, sunshine.  Affects charge rate, wet terrain slows speed.

Not on agenda
* Agents communicate recent findings.
* Defenders take out agents (loss of data)
* POI are mobile (old data is less reliable)
* Agents have a set rate of failure.


FSM in java
Adapted from python code.  Starts in the Model class.

Example PSO
http://unbox.org/open/trunk/472/14/spring/doc/games.md

Jmetal
http://jmetal.github.io/jMetal/


#Phase 1 - Make a map and an agent.  Get them to move.
Done 11/3.

#Phase 2 - Make the agent marginally smarter.
Done 11/7
* Agent remembers where it has been. 
* Make it move, look, and check boundaries.  
* Decides direction (always 90) and shows movement.
* Maps pretty-print with border for easier counting.
* Terrain is like a map, but each agent remembers where its been.

Sample Finished Terrain (Starts out blank. Dots are where agent 'saw' terrain. )
```
T01234567890123456789012345678901234567890123456789012345678901234567890123456789T
9     .................      .............                      ..........   ....9
8     .....................  .................    ...............................8
7     ....      .................................................................7
6     ...           ............... .............................................6
5    ....               ........... ..... .......................................5
4    ....               ............................     ........................4
3    ....              .................................................... .....3
2    ...               .....................................................  ...2
1.......               ..................      ..................................1
0.......               ..................         ...................   .........0
T01234567890123456789012345678901234567890123456789012345678901234567890123456789T
```
Also removed mapState and World classes (for now).

#Phase 3 - Getting Real (numbers)
Done 11/8
Currently, map spaces are 1x1, and agent moves 1 space.  All values (x, y, dir) are integers.
This phase is about changing all of that to doubles, 

Research - (I use stack overflow 3 times a day in my day job, so that's where google takes me.)

http://stackoverflow.com/questions/611732/what-to-do-with-java-bigdecimal-performance

http://stackoverflow.com/questions/1378044/how-using-bigdecimal-would-affect-application-performance

http://stackoverflow.com/questions/3413448/double-vs-bigdecimal

*Best Answer

A BigDecimal is an exact way of representing numbers. A Double has a certain precision. Working with doubles of various magnitudes (say d1=1000.0 and d2=0.001) could result in the 0.001 being dropped altogether when summing as the difference in magnitude is so large. With BigDecimal this would not happen.

The disadvantage of BigDecimal is that it's slower, and it's a bit more difficult to program algorithms that way (due to + - * and / not being overloaded).

If you are dealing with money, or precision is a must, use BigDecimal. Otherwise Doubles tend to be good enough.

In the javadocs
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 For decimal values, this data type is generally the default choice. 
 
#Phase 4 - Better Directions
Done 11/8

* Change dir from ordinal positions to degrees. Zero is north.  Show a circle.
* Agents get dirWiggle (degrees to wander while going straight).
* Agents get ability to random turn


Example Terrain after circle:

```

Agent [name=a2, loc=Location [.68, .97], speed=1.00, dir=266.40, see=1.00, ter=
T01234567890123456789012345678901234567890123456789012345678901234567890123456789T
9............                                                                    9
8............                                                                    8
7...      ...                                                                    7
6...      ...                                                                    6
5...      ...                                                                    5
4..       ...                                                                    4
3..       ...                                                                    3
2..       ...                                                                    2
1............                                                                    1
0............                                                                    0
T01234567890123456789012345678901234567890123456789012345678901234567890123456789T
]

```
In the interest of science, I ran one with plenty of turns.

* chanceFwd of .6 (only 60% chance of going forward)
* dirWiggle of 10 degrees - Each forward includes a random +/- 10

```

Agent [name=testAgentBumpAndTurnExciting, loc=Location [38.58, 5.92], dir=220.0324612279289, speed=1.0, see=1.0, dirWiggle=10.0, chanceFwd=0.6, tick=0, ter=
T01234567890123456789012345678901234567890123456789012345678901234567890123456789T
9     ......  .........................  ...                                     9
8     ...... ................................                                    8
7     ...... ................................                                    7
6      ......................................                                    6
5    ......................................                                      5
4.........................................                                       4
3.......................................                                         3
2......................................                                          2
1....  ...................... .....                                              1
0....  .............. ....    .....                                              0
T01234567890123456789012345678901234567890123456789012345678901234567890123456789T
```

# Phase 5 - Finish the terrain


# Phase 6 - Abstracting Wants and Needs
Actor Model = Want - desired direction:  Given what we know, generally head that direction

Physical Model = Need - Avoiding obstacles:  Don't get stuck.

This may be a case where we are able to use state to give precedence to one model over the other.
#Future

Terrain is varied
```
Key:
Space 	Unknown
.		Paved
,		Grass
o		Hill
X		Wall
```

Differentiate between Actor and Physical models

* Actor is overall direction
* Physical takes into account terrain 

?Speed variable

?Spaces on the map are 100x100

?Bomb dog- don't step on the bombs

?MIA Rescue - Find known number MIAs

?Active Shoter - must stay behind hill.  Hear shots to estimate location.  Figure out how to stay behind cover.

