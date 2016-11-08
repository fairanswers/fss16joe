# ASE16 Joe's Final Project
# MapExplore



Open Questions:
* How should maps and agents interact?  Does each agent need their own map?
* 
FSM in java
https://github.com/hekailiang/squirrel

Example PSO
http://unbox.org/open/trunk/472/14/spring/doc/games.md

#Phase 1 - Make a map and an agent.  Get them to move.
Done 11/3.

#Phase 2 - Make the agent marginally smarter.
Done 11/7
- Agent remembers where it has been. 
- Make it move, look, and check boundaries.  
- Decides direction (always 90) and shows movement.
- Maps pretty-print with border for easier counting.
- Terrain is like a map, but each agent remembers where its been.

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
Currently, map spaces are 1x1, and agent moves 1 space.  All values (x, y, dir) are integers.
This phase is about changing all of that to floats, 

Research - (I use stoack overflow 3 times a day in my day job, so that's where google takes me.)

http://stackoverflow.com/questions/611732/what-to-do-with-java-bigdecimal-performance
http://stackoverflow.com/questions/1378044/how-using-bigdecimal-would-affect-application-performance
http://stackoverflow.com/questions/3413448/double-vs-bigdecimal

*Best Answer

A BigDecimal is an exact way of representing numbers. A Double has a certain precision. Working with doubles of various magnitudes (say d1=1000.0 and d2=0.001) could result in the 0.001 being dropped alltogether when summing as the difference in magnitude is so large. With BigDecimal this would not happen.

The disadvantage of BigDecimal is that it's slower, and it's a bit more difficult to program algorithms that way (due to + - * and / not being overloaded).

If you are dealing with money, or precision is a must, use BigDecimal. Otherwise Doubles tend to be good enough.

In the javadocs
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 For decimal values, this data type is generally the default choice. 
 
#Future

Speed variable

Any direction (360 degrees)

Terrain is varied

Spaces on the map are 100x100
