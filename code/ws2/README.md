+ASE16 Homework Workshop Two

I added a climb_out function that gave machines in the pit a (1-pit_chance) chance to get out if they have any energy.  But each attempt (success or failure) takes half the remaining energy.
 
Also, if two machines are at the same energy, and one is in the pit, they can help the other out of the pit.

Helpouts and climbouts are logged.


[Workshop Code](./fsms.ipynb)

Workshop Output

Seed :  100

2  |  . . . . . . . . . . . . . . . . . . . . . . . . . 7 . . . . . . . . . . . . . . . . . . . . . . . . .

2  |  . . . . . . . . . . . . . . . . . . . . . . . . . 7 . . . . . . . . . . . . . . . . . . . . . . . . .

Helpout machine 1 for machine 2

1  |  . . . . . . . . . . . . . . . . . . . . . . . . . 7 . . . . . . . . . . . . . . . . . . . . . . . . .

1  |  . . . . . . . . . . . . . . . . . . . . . . . . . 6 1 . . . . . . . . . . . . . . . . . . . . . . . .

2  |  . . . . . . . . . . . . . . . . . . . . . . . . . 6 1 . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 2 result True

2  |  . . . . . . . . . . . . 2 . . . . . . . . . . . . 4 1 . . . . . . . . . . . . . . . . . . . . . . . .

4  |  . . . . . . . . . . . . 2 . . . . . . . . . . . . 4 1 . . . . . . . . . . . . . . . . . . . . . . . .

1  |  . . . . . . . . . . . . 2 . . . . . . . . . . . . 4 1 . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 1 result False

4  |  . . . . . . . . . . . . 2 1 . . . . . . . . . . . 4 . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 1 result True

1  |  . . . . . . 1 . . . . . 2 . . . . . . . . . . . . 4 . . . . . . . . . . . . . . . . . . . . . . . . .

1  |  . . . . . . 1 . . . . . 2 . . . . . . . . . . . . 4 . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result True

4  |  . . . . . . 1 . . . . . 6 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

2  |  . . . . . . 1 . . . . . 6 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

4  |  . . . . . . 1 . . . . . 6 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

4  |  . . . . . . 1 . . . . . 6 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result True

4  |  . . . . . . 5 . . . . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

4  |  . . . . . . 5 . . . . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

4  |  . . . . . . 5 . . . . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result False

2  |  . . . 4 . . 1 . . . . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

1  |  . . . 4 . . 1 . . . . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 2 result False

Climbout machine 4 result True

Climbout machine 1 result True

1  |  . 4 . 1 . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result True

1  |  4 . . 1 . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result True

Climbout machine 2 result True

2  |  4 . . 3 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result True

1  |  4 . . 3 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 1 result False

2  |  4 1 . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result False

Helpout machine 2 for machine 4

2  |  6 1 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 1 result False

4  |  7 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 4 result False

2  |  5 . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

2  |  5 . . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 1 result True

Climbout machine 4 result True

2  |  5 . . . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 2 result True

2  |  5 . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

2  |  5 . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

2  |  5 . 2 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

Climbout machine 1 result True

Climbout machine 2 result False

Climbout machine 4 result True

