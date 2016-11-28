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

* * * Collisions are not working properly.  See AgentTest.test2AgentsPaintBallWall.  Agents can briefly go off the map, too.  Probably diff. between checking average and casting float to int.

* * * Random generators have to be fed directly to the shuffle method, or it makes a new one.

Wants
* Explore - Continues to move toward unexplored areas
* Bored - No new locations found in X ticks.  Pick a direction and go for y ticks.

Before

```
Agent [name=a3, loc=Location [63.34, 52.14], dir=21.163216484098093, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=10000, ter=
Terrain:  Seed=0 variance=0.0 covered=26.26%
```

After

```
Agent [name=a3, loc=Location [70.22, 17.40], dir=254.9134515343162, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=10000, ter=
Terrain:  Seed=0 variance=0.0 covered=30.1%
]

```

The difference this made was surprisingly small, even when I increased the bored walking to 100 ticks.  It's probably running into too may things (and changing direction at random) to make any difference.  A different map might solve that problem.


Then I tried a different idea.  Keep going in the same direction when you're bored, don't just knock around into things.

```
Agent [name=a3, loc=Location [54.64, 52.78], dir=137.95832448076862, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=10000, ter=
Terrain:  Seed=0 variance=0.0 covered=35.03%
```

This was better, but if I was already pointing towards the center I'd have to traverse the whole thing to get to a new spot.

Third time's the charm: If bored, turn around and head the other direction.  This is the REO Speedwagon "Roll With The Changes" solution.

```
Agent [name=a3, loc=Location [71.89, 8.15], dir=264.57192723105356, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=10000, ter=
Terrain:  Seed=0 variance=0.0 covered=32.16%
```

Finally, I decided to 
1. Check the local area to make sure I'm not right next to an unknown.   If so, go that way.
2. If not, check larger and larger areas to find the nearest unknowns.  Once I find one, go that way.
3. If I've found all the locations, stop.  

By checking map increments of 25%, I was able to get 53% complete

```
Agent [name=a3, loc=Location [39.65, 77.70], dir=358.7806039038538, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=10000, ter=
Terrain:  Seed=0 variance=0.0 covered=53.74%
```

By checking map increments of 10%, I was able to get 56%, and the 
```
Agent [name=a3, loc=Location [83.30, 80.20], dir=72.56991953868334, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=10000, ter=
Terrain:  Seed=0 variance=0.0 covered=56.71%
```

While setting up for testing multiple times, I found a variable that I was randomly resetting that determined how much the weight to the unknown affects the direction.  I don't remember why I put it in there, but when I took it out my test map coverage went to 76%

```

Agent [name=a3, loc=Location [48.94, 70.96], dir=91.44150746224801, speed=1.0, see=1.0, dirWiggle=30.0, chanceFwd=0.2, tick=10000, ter=
Terrain:  Seed=0 variance=0.0 covered=76.74%
```

With these improvements, I am still able to run a map of 100x100 locations for 10000 ticks a thousand times in a minute.
#Phase 8 - Format the output for analysis
done 11/20
I thought about creating a separate executable with cmd line options for tests.  But I decided to use a Study instead, which will run all the evaluations and do some analysis.  After a lot of work I went back to a Runner, which does single thread, single problems.  Will revisit soon.  Plus, it seems to be a lot slower.  I may need to fix that.


#Phase 9 - More optimizers

Get DE and NSGAII working.

Research bleeding edge options (oversample/down select)

The first big test was for nsgii and de, it ran overnight.  Output looks like this

rank ,         name ,    med   ,  iqr 
----------------------------------------------------
   1 , CoverageDE.cr=0.5_f=0.5_variant=rand/1/bin_pop=30_evals=250_seed9443 ,    -90   ,    2 (         ---  *|  ---         ),-91.76, -91.19, -90.78, -90.00, -89.42
   1 , CoverageDE.cr=0.5_f=0.5_variant=rand/1/bin_pop=30_evals=250_seed7377 ,    -90   ,    0 (        ------ *  -           ),-91.84, -90.79, -90.53, -90.06, -89.80
   1 , CoverageDE.cr=0.5_f=0.5_variant=rand/1/bin_pop=30_evals=250_seed3565 ,    -90   ,    1 (          ---- *  ----        ),-91.47, -90.82, -90.52, -90.06, -89.28
   1 , CoverageDE.cr=0.5_f=0.5_variant=rand/1/bin_pop=30_evals=250_seed1901 ,    -90   ,    2 (        -----  |* ---         ),-91.85, -90.98, -90.42, -90.07, -89.35

rank ,         name ,    med   ,  iqr 
----------------------------------------------------
   1 ,       NSGIIg ,    -91   ,    1 (       ----  * | ----         ),-92.29, -91.62, -91.31, -90.71, -90.08
   1 ,       NSGIIh ,    -91   ,    1 (       ----  * | ----         ),-92.29, -91.62, -91.31, -90.71, -90.08
   1 ,       NSGIId ,    -91   ,    1 (        ---   *|---           ),-92.11, -91.62, -91.27, -90.89, -90.33
   1 ,       NSGIIb ,    -91   ,    1 (        ---   *|-----         ),-92.22, -91.62, -91.22, -90.86, -90.10
   1 ,       NSGIIc ,    -91   ,    1 (         ---  *|----          ),-92.06, -91.57, -91.22, -90.89, -90.32
   1 ,        NSGII ,    -91   ,    1 (       -----  *| ----         ),-92.26, -91.51, -91.18, -90.79, -90.01
   1 ,       NSGIIa ,    -91   ,    1 (       -----  *| ----         ),-92.26, -91.51, -91.18, -90.79, -90.01
   1 ,       NSGIIe ,    -91   ,    1 (          --  *| ----         ),-91.84, -91.47, -91.17, -90.74, -90.13
   1 ,       NSGIIf ,    -91   ,    1 (          --  *| ----         ),-91.84, -91.47, -91.17, -90.74, -90.13
   
   
Runs were taking in the 2 hour range for DE.  NSGII is still running, but apparently in parallell !?!

C:\apps\ase16\fss16joe\project\MapExplore>ls -lrt *jdat
-rw-rw-rw-   1 user     group         415 Nov 22 02:00 CoverageDE.1479789695038.1479798000460.jdat
-rw-rw-rw-   1 user     group         418 Nov 22 04:20 CoverageDE.1479789695038.1479806441821.jdat
-rw-rw-rw-   1 user     group         417 Nov 22 06:39 CoverageDE.1479789695038.1479814778676.jdat
-rw-rw-rw-   1 user     group         413 Nov 22 09:03 CoverageDE.1479789695038.1479823415500.jdat
-rw-rw-rw-   1 user     group         356 Nov 22 10:12 NSGII.1479789695038.1479796534934.jdat
-rw-rw-rw-   1 user     group         356 Nov 22 10:13 NSGII.1479789695038.1479804979213.jdat
-rw-rw-rw-   1 user     group         354 Nov 22 10:13 NSGII.1479789695038.1479813322938.jdat
-rw-rw-rw-   1 user     group         356 Nov 22 10:13 NSGII.1479789695038.1479821938145.jdat
   
cat CoverageDE.1479789695038.1479798000460.jdat   
CoverageDE.cr=0.5_f=0.5_variant=rand/1/bin_pop=30_evals=250_seed9443 -90.86 -90.77 -91.17 -90.72 -91.26 -91.96 -92.37 -91.05 -89.59 -90.0 -91.76 -89.8 -89.8 -91.44 -89.94 -89.86 -90.45 -89.42 -89.71 -90.36 -90.95 -91.26 -89.5 -90.86 -92.66 -93.18 -91.19 -90.88 -91.26 -91.15 -90.78 -91.63 -90.21 -88.92 -91.41 -90.58 -90.48 -90.32 -89.73 -91.0 -90.1 -89.69 -91.59 -91.12 -89.32 -91.33 -93.52 -89.23 -91.55 -88.39
 
cat NSGII.1479789695038.1479796534934.jdat
NSGIIa -91.86 -92.25 -90.94 -90.97 -91.22 -92.68 -90.69 -90.07 -91.2 -91.67 -91.91 -92.42 -91.92 -91.49 -92.36 -90.71 -90.81 -91.28 -90.25 -91.79 -90.52 -89.81 -90.95 -91.23 -90.22 -90.34 -92.26 -91.32 -91.97 -91.51 -89.8 -92.55 -91.65 -88.72 -90.86 -91.18 -91.07 -91.71 -91.21 -90.03 -91.4 -90.85 -91.26 -90.12 -90.86 -93.01 -91.02 -90.01 -90.79 -90.01

#Phase 10 Energy Consumption
Modify behavior so energy consumption is taken into account.
* Agents track energy consumption.  Done.
* Maps aggregate energy consumption. Done.
* Opts. consider percent complete with energy consumption. Done.
* Display beginning and end Pareto plot. Done
* Better function to minimize energy - Agent variable that decides how lazy to be. Done.
* Consider whether chanceFwd is worthwhile. Can take it out?.
* Run fewer iterations - data too similar
* DE is 10 times decisions - 100-1000 population
* Find scholarly articles.

Once I started doing this, the lazy factor blew up my effiencey.  I went from coving 80-90% of each map to covering 5-25%.

Also, I changed my DE from cr=.5 to .3, and the results were really odd.  Some of the initial points were much better than the final ones, even on 300 evaluations.
```
CoverageDE.cr=0.3_f=0.9_variant=rand/1/bin_pop=20_evals=300_seed3932 68.18 60.584 69.06 65.54 69.17 56.324 69.44 67.188 69.78 67.08 70.16 87.952 70.26 69.828 71.23 73.14 71.23 67.04 71.74 80.819 72.4 79.272 72.66 86.024 72.7 67.524 72.78999999999999 90.272 73.63 56.24 73.68 70.204 73.78 64.032 74.0 82.696 74.23 93.251 74.4 60.596 

PARETO:
Terrain: variance=0.0 covered=0.39%
T0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789T
9                                                                           I          I  I I   I    9
8                                                                                                    8
7                                                                              I                     7
6                                                                                                    6
5                                                                                                    5
4                                                                                                    4
3                                                                          *                         3
2                                                                                                    2
1                                                                                                    1
0                                                                        *                           0
9                                                                                                    9
8                                                                                                    8
7                                                                      *                             7
6                                                                        *                           6
5                                                                                                    5
4                                                                                                    4
3                                                                                                    3
2                                                                          *                         2
1                                                                             I                      1
0                                                                       *                            0
9                                                                        *                           9
8                                                                                                    8
7                                                                                                    7
6                                                                                                    6
5                                                                                                    5
4                                                                                                    4
3                                                                       *                            3
2                                                                                                    2
1                                                                                                    1
0                                                                         *                          0
9                                                                      *                             9
8                                                                                                    8
7                                                                     * **                           7
6                                                                                                    6
5                                                                     *                              5
4                                                                         *                          4
3                                                                        I                           3
2                                                                    I                               2
1                                                                                II                  1
0                                                                    *     *                         0
9                                                                                                    9
8                                                                                                    8
7                                                                                                    7
6                                                                     *   *                          6
5                                                                                                    5
4                                                                                                    4
3                                                                                                    3
2                                                                                                    2
1                                                                                                    1
0                                                                                                    0
9                                                                                                    9
8                                                                                                    8
7                                                                                                    7
6                                                                                                    6
5                                                                                   I                5
4                                                                                                    4
3                                                                                                    3
2                                                                                                    2
1                                                                                                    1
0                                                                                                    0
9                                                                                   I                9
8                                                                                                    8
7                                                                                                    7
6                                                                                                    6
5                                                                                    I    I          5
4                                                                                                    4
3                                                                                         I          3
2                                                                                                    2
1                                                                                                    1
0                                                                                                    0
9                                                                                                    9
8                                                                                                    8
7                                                                                                    7
6                                                                                                I   6
5                                                                                                    5
4                                                                                                    4
3                                                                                                    3
2                                                                                                    2
1                                                                                                    1
0                                                                                                    0
9                                                                                                    9
8                                                                                               I    8
7                                                                                              I     7
6                                                                                                    6
5                                                                                                    5
4                                                                                                    4
3                                                                                                I   3
2                                                                                                    2
1                                                                                                    1
0                                                                                                    0
9                                                                                                    9
8                                                                                                    8
7                                                                                                    7
6                                                                                                    6
5                                                                                                    5
4                                                                                                    4
3                                                                                                    3
2                                                                                                    2
1                                                                                                    1
0                                                                                                    0
T0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789T 

```
#Future


?Increate likelyhood of walls by doing a grid like on unbox, where each value increases the likelyhood of walls.  YOu can do vertical, horizontal, diagonal.

?Add water, which can be flowing.  Look at http://unbox.org/open/trunk/472/14/spring/doc/games.md 

? Point to point navigation?

?Speed variable

?Bomb dog- don't step on the bombs

?MIA Rescue - Find known number MIAs to end simulation early.  Using maps of X by Y and ticks of X*Y, I've only been able to get completion in the mid 80s.

?Active Shoter - must stay behind hill.  Hear shots to estimate location.  Figure out how to stay behind cover.

#ASE Presentation Draft

Tell and show - 
 Tell in Title
 Show in graphic (Picture or graph)

Cropping is good.
Image on Right Side, Text on Left
Make picture whole slide.  Put text on semi-transparent
Text Upper Left, Image lower right, no background image.
Use images with a different style.
No clip art.

Image Ideas
Indiana Jones
Pirates
X marks the spot
Robots from the future
Lost
Asking for directions
Bored
Happy 
Bored to death
Wandering
Agents (Secrect)

Presentation Outline
When I first came to NC State I learned about and drone made from a big model airplane.  It takes off, finds things within an area, and lands, all by itself. That stuck with me, because it's the kind of problem that you can think about on your own, but you can also get luck at, so having experience may not always be an advantage.

My project is to make a map and a agent to drive around on it. It's a land-based dron with different characteristics (lazy, bored, patient) that we can set, then let it run around.  We take the same agent and see how much it explores.

Maps
[Show diagram of map and key for terrains]
The maps are 100 units by 100 units, and each drone has 100 * 100 = 10,000 moves to explore it.  Each map has terrain that has different friction amounts, paved is easy to travel, grass is harder, slopes are harder, and cliffs are impossible.  If you hit a cliff (or an edge) turn right or left 90 degress and start again.

Agents
[Show diagram of early version terrain vs later version]
The agest start a 'run' of experiments at a random location with the same set of variables:  Momentum, Chance Forward, Direction Wiggle, Lazyness.  He can also have states of Happy, Bored, and Lazy.

Happy agents are finding new territory.  That's their goal, to explore the map, so as long as they are doing that, they are happy.  If they don't find anything for a while, they get bored and change their behavior.  They stop what they are doing and think about where they want to go.  They start with the closest 10% of the map and see if there are any unknown places there.  If so, great!  They head that way.  If not, they check the next 10%, and the next, until they check the whole map.  If there are no unknowns, the map is complete.

If they find an unknown but it takes too long to get there, they die of bordem.  This is effectively early termination for an agent that just isn't doing well.

Taboo Search

Results

