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
Done on 11/12
Terrain is randomly generated based on a seed and a variance factor.  Higher factors are easier to navigate.

Terrain is varied
```
Key:
Space 	Unknown
.		Paved
,		Grass
o		Slope
X		Cliff
```

Terrain:  Seed=1 variance=10.0
T0123456789T
3.........3
2.....X...2
1.X.......1
0........o0
T0123456789T


Terrain:  Seed=1 variance=1.0
T0123456789T
3,.,.o..o.3
2o.,o,X,.o2
1,X..o.ooo1
0...o....X0
T0123456789T

```
Agent [name=a3, loc=Location [54.22, 43.67], dir=35.80822387260736, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=100000, ter=
Terrain:  Seed=0 variance=0.0 covered=58.74%
T0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789T
9                   .X.X....                                                                        9
8                   .o..,..o,,                                                                      8
7                   ..o.,,.oo..         o..o.o                                                      7
6                 o,,,o,.X.,.,.oooX.XX,.,...X.                                                      6
5                .,oo......,..,.X,oo,,o,......                                                      5
4             ....,X..,..,....o....Xo.,,.,...,                                                      4
3            ...,.,o.X.,...oo.,oo,Xoo.,.X,..                                                        3
2            ..o.....X.......X,X..o,,X...o,.o..                                                     2
1           .X,.XX..X,o...Xo.X.oX....,o..,..o.o,                                                    1
0           .X,.,............oX......,,.,...X.o.XX.                                                 0
9           o,..XX,,,...,o....o..X..,,.X..,..,,..X,    ....                                         9
8            o,o,....,X.o....,o.., XooX..X,.o.oo,.oX  ,,...,                                        8
7             ,.,X.,..o,,o,,..o,Xo .Xo..oX.ooXX,o.,.. ....,.                                        7
6            .oooXX..ooo..,..,X..X  o,..oo,oo,,o.oo..XoX....                                        6
5            .o.,,oo..o..X..o..o,, .,...oX..  .,X,o...,o,o,oXo..                                    5
4            .X.,..,.,o,oo...X,o,X..X.o.X..... .,ooo...,.X.....X                                    4
3            ..o.,o.,,.X.X.....XX.oX..,o,,..o,,.,o..,..oooX,...,..o..  ..XX                         3
2               ,o,..,..o,Xo..,Xo.o...o,o.,......o.,..,.X,oX,o.Xoo.....,ooo        ..X              2
1                   ...,,,.XX..,..X,oo,X.......,...X.ooo..,..,.....ooX.,.o.,.,    .,.,,             1
0                     ..,.o.X,..o..,.X...,.,oo..,.X..,X..o,.,..,,,X..,.....,..,o.o.,oo.             0
9                      ..,o    XXo.X.,,.o..,o.,o.,X,.,oX,X.X,,oo,.o..o.o,o...oo,...o.,.             9
8                              ..Xo..o.XX.o.,.,,oo......o.oo..oX..,oo,...,,,o.,,.o.,...             8
7                              ooo,.o..o...,o....o....,XXX........o.Xo.,o,..XXo.oX.o.               7
6                              .X,.,X.o.,,..o.o...oo,.X....X.o,..X.,...,.oX.o.oX,oo..  .o,          6
5                            .....XX.o,...,..o..,o,.,o,...,o,.Xo.o,X..X.X.X...,X..o.o.,...X         5
4                           ....Xo,...X..Xo,,o,.o...,..X..o..,.X.o..o.o,o.oX,..,..o.....,.X         4
3                           .o.,X.....o...o,..o.ooX,..XXXX...,,.X.,oo..,....,,o,.o,XX...,..         3
2                           ,..X....,.,,.o.Xo.,X.Xo..,,...X,.,o..,.,,,oo,.,Xo.oo,Xo.o..,oo,,.       2
1                           .o,,...o.oo......,,.X..o.o..ooX.....,.,.X,..,,.,,.,,.Xoooo ,.oo..       1
0                         o..o.X  .....,,....X.o........ooo..,.X.oo,..,,.,...X.oXoo..o ......       0
9                        ....oX,..oX..X....oXXX...,.....,.,..o...,...o.....o,...o....oo,o,X,.       9
8                        ..X..X..oo......XXo.Xo,.oo.,.....oo..,.Xoo,,...,,X,..,,,.o.,.o,..o..       8
7                        X.,oo...X..,X.ooo,o,.o..X,o.X.,.,.....o......o..X....o,o,.o,oX.,..oo       7
6                        ..,,..,.X,...,.o,o..o.o,o,.XX.,.,ooXo.,......,,,.X....o....o.o.,....       6
5                         ,Xoo.,..,oX,..,...,.ooo....o,o..o,...o..,....,,...o...X,,.,,...,..,       5
4                         X,,.oo,.,.oX....,,.,...,...,.oo..o.,X..o,.,X.o...o..oo,.,,.X,.,,oX,..     4
3                         .X.o...o..X....oo.o.o.X..o...o.X.oo,,.o.X..X.,X,.,.XoX.Xoooo...,..,...    3
2                         ,..X.,.,,.X...oo.o..o.o.,.o...,.X,...o....,o.o,.X,,.,.ooX....oX..Xo.,o    2
1                          o.,,.o.o,X,o,..,.,X,.X,o..o.X..,.Xo,,.oo.,,.o,....o.,X.X,.....oo..ooo    1
0                 ,...    ,..,,.,,oo,.o,,,..o.XoX...,X.,ooo.,....o.,...,.,X.o..o..,X,..o,o,..,..    0
9                 ,.X,oo.o,o,o.,,....X....X..oo..,..oXo.ooooo.Xoo,o,....oo,.o.....,o.......o,.oX.   9
8                 o,,....Xo.,,.,o.,o.....oX..o,..,,,,.Xo...,X....,.ooo..X.X,...,,,,X,.o..,o..,,..,..8
7                 ..Xoo,.,...o,oo...X,,,.o.X..o...,...,,.oXX..o,...,.o.oXX..XoXo.,.oX,.ooXX...o,X.,,7
6                .,.oX.,X.,.o.,.,o..oX..Xo...,.,o,,....X.oX.X.,.,X.o..X..o..o,,.,...,.,,  ,....o,,o.6
5                ,..,.X.,oX,.XX.X...o,..,X,oo..,o.oo...,oX.X.X..,...,.......oXX..,X.o.o.   .,....,,.5
4                ,o.o.X.ooo..o...,..,o.o..,..,o...,,......o.X,o,.o.X....,.,..,o...,o.o.,.   .o,...,,4
3                ..o.,..o...,X.,o...X.o.ooX,....o.o.,,oX.Xoo,.,o,o......,X,o.o,..XX..,..X  o,.,o..o,3
2                .o..Xoo..,oo,oX...o.o,.o.oX.o..,.o.X..o..,,X..,...,..Xo,,.,ooXoo..X.oo....,o.o.,,,,2
1                 oo........,o..,,o.o.....o.,..,..o,.o.oX.X.XX......,.o..,oX.oX,,o...,,,.oX..o.o.X,.1
0                 ...oo.,...o.o,.o.X.,,.oX...o.oo...,Xo....,,.,.....,oo,ooXXoo.o.o.o.......,X,oX.,,o0
9                 o,..o.,.,..o......o.,.,.,...,,.,.,,.oX.....,oo..,...ooX,o..,.X,..o.X,..oX.....o.o 9
8               ,Xo,..oX.X......o.oX.,o,.o...o.,,,...,..,.,ooX...X..,..o,,,..o,,o.ooo..oXo..,o...o  8
7               ,.,..,.,o.o.Xo...X...o.Xo.,.....o..,o,X.X,..X...,.,..,o,.ooX,.o.oo,...ooo..  ...o   7
6           .oo,o,.ooo...o....oo,....X.,o.o..o.o.,,o...o....o.o.....oooo.o....o,.,..,.,oX,.         6
5        .X..X...,,,...XX.o.o,.X,...,.oo,.,oX,...X.oo.o,.oo,,.,.X...o...,.,.,.,,o,.,,o...X.         5
4        ,.......o.,ooXo..o.,...,.o.,,.o.....o.oo...o....,,o..oX...,,.o.o...,,,X..,..o,X,.,         4
3        oo..o..,..X,...,,.,o.X.....X.X...X..,,..,,...Xo.,..o.,,.,.o.,..,..o,.....oXX.....          3
2       o.X,...oooo.oX....,,oo..o.o....o.o,.o..o.....Xo......,...,..,oX....o...,.oo..               2
1       XX...X.,.o.,.,.o,.Xo,,X....,,o..X,,,,..,..o,o...,.o,..,,o,o.,X.XX.,,,,.oX.,o,o.             1
0       X.X,,,o...,,oo,.oo.X.,...,,,,o.o,..X..o..,...X.....XX.o..o,.o.,,.,..X.o.o,.oo,,.            0
9        ,.....o.,.o...o...,.X,,,X,ooo.X,X.o,,,oX,.oo.X...o..X..o.o..oX,.....oXo,.o..o.,            9
8         ,oo....,.....o,X,o..Xoo,,oXooXo.,,.,,X.o.X,,.,.oo.o....,,.oo,.,.oX..X.,,...,.,            8
7        ,oo...,...oo..X.,.o.,...o,o,..oX.,.X,X.....o,..o..,X,XX,.,,..,,.oX,oo.o.o,.ooX             7
6        ,.X..oXX,o,.....XX...,,o,..o...,...,,o.o,,ooX,.,,..,o,.o,.o,,...o.o,o,..oo,.,              6
5        o..,XXoo.o,....,,o,X.,,.,X...,X.Xo....ooo.X,..o..o.,,.....,..,o..,,..X.o.Xoo.              5
4        .X.X.. ..X.,o,..o,...XX...,o.X,.,.o..o....XXX,..o.....X.X..Xo..,,,.oo,,.,....              4
3       oo..    o...Xoo,Xo.o,..o....o...oX.X,o,...,,.o.ooX,.,..X..X..Xo..X.,,.X.o.,,..              3
2     ,ooX,o.,,.X.o,.X..,,oo,oo.o..o..,...,..,..X,o.o.o...o.,.o,.,.Xo....X..o.o,X..,.,..X,o         2
1    .oX..ooX.....X.,,..........,X..o...oo.o.oo.X...X.,..X,..,....o...,...o..,o,.X..oX.....         1
0    o..,..o...o.ooXoX..X.o.o..X..,,,,X,o.,,o....,,X..X.X.o,..o,..,.X...o,.,X,...,,..ooX.o.         0
9    .,oXo.o.o...o.,..oX.o,.,X,.,.o.o.o.oooXoo,,.o...o..o.,.oX,.o..X,.....,X.,,o.....X,.o...X       9
8    o,oXoo.X....,.oo.,o.oX.Xo..o..X.oo,.,,Xo,...o.o,..,...,,.X,.,.,oo,.....oo....o.oo..o,,,.       8
7    Xo.,o.X..,,,o.,..o..o....o,.o,X.o.oo.o......X..,..,.,,,,.,.,Xoo.o.,..o.o,o......o....XX.       7
6    ..,.   X...o.o.o.,..,.Xo.,..,o,o....,...,..,......o,oX..o.oXXo,.......o,.,.,.o.o..,,o..X       6
5           ,.o....,..X,..   o...o,X...,...o.,..,o,o...,oo,.o..,o,.X,.o...,.,....o.X.o.,.,,.,       5
4           o...o....o,oo.   o.o.,..,,.o...o,.,..X.X,.o.,...,o,.Xo.oXX...o..XX,.,X. .o.oX...        4
3           ..,,...,...oX    .o,.o,,.,...o........oo....o.,Xoo..o,.o,..oX.,,...o.o   .,o....        3
2           .oo,,..,.,..   .X.,.ooo,,...X,...o..X.,,.o.X..o..o.X.o.,X.oX.,,o..         ,..X.        2
1           ,.o.,,.,.XX    .,oXX.o..,,oo...o.o,,.,.o..o,X.,....X,.XX.X.,o              oX..,        1
0          o..,...o,...,.o.X,o.,.X.o.o.oo.,,,,.....,.o....,.,X,o.,X,,,                  .X,         0
9       ...ooX,o..XX,o..,.,.o..X,...,,.X,oXo....,.,..,o.o,.,.X,,..o..                               9
8      .X,o.,o..XX.o,.....,o.X   ..,.oX....o..o...Xo...o.oXo..o..,...o                              8
7     .o...,oo.,..X.o.,,.,.      .,....o,ooX.,..,o,X,oX..XX...Xoo..,X,                              7
6    .,....o..,XoX.o.o.....        ,.,o,o.....ooX...Xoo...,..,o.oo.,.o                              6
5    ..o.,,..o..,.,..X..,           ,,,o,,oX,.,.oo,..XoXo.o...o...o.X..                             5
4    X,X.o.........o..,oo           ..oX....oXooo..o,.,,Xo.,X,...oo.o..                             4
3    ,.,....,oX.....,..o.           .,..,.,,.o.,, .....XX.o...,....X.,.                             3
2     oo..,..o.,....,Xo,.             oX....X.o,   .,o,.,ooo.,o.X,..,,,                             2
1      ..,,.....,o.,,o...                          ,....,..                                         1
0      .X.o ,X..X...,o,,o                          XX.o,,                                           0
9      .... .X..,o....,                              ,...                                           9
8   .,X.,oo ...oX..,,.                                                                              8
7  o,oo...X   o..,.,.                                                                               7
6  ,XX..,.o      ....                                                                               6
5  ..,oX.oo      ,o..                                                                               5
4   ,..,..                                                                                          4
3   .....                                                                                           3
2  ,...X,                                                                                           2
1,ooo,..                                                                                            1
0..oXXXo                                                                                            0
T0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789T
]

```

#Phase 6 - More Realistic Terrains
Done 11/14
Be able to save and load agents, maps and terrains.

Get some maps of paint ball arenas. Load them and have the agent run around.

Better visibility (currently hard coded to 1)


# Phase 7 - Abstracting Wants and Needs

Differentiate between Actor and Physical models

* Actor is overall direction
* Physical takes into account terrain 

This may be a case where we are able to use state (FSM) to give precedence to one model over the other.  States could be explore, bored, obstacle following

#Phase 8 - Format the output for analysis

Separate executable with cmd line options for tests.

Review all the variables and make them scriptable from cmd line.

#Phase 9 - More optimizers

Get SA and PSO working 

Research bleeding edge options (oversample/down select)


 
#Future

States

Happy

* Wander - There are unknows nearby, go find them.
* Lazy - Only take the easiest paths
* Board - Haven't seen a new on in a while.  Find the nearest and go to it. (Travel-to-point).  If you can't get there, give up after a while (state=dead) and try again.  Search by quarter, half, and all.


?Add water, which can be flowing.  Look at http://unbox.org/open/trunk/472/14/spring/doc/games.md 

? Point to point navigation?

?Speed variable

?Bomb dog- don't step on the bombs

?MIA Rescue - Find known number MIAs

?Active Shoter - must stay behind hill.  Hear shots to estimate location.  Figure out how to stay behind cover.

?Take friction into account when planning routes.

