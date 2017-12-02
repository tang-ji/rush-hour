# RushHour
A Rush Hour Solver

The purpose of this programming code is to write a solver for rush hour puzzles. The goal of a rush hour puzzle is to help the red car to escape the traffic and reach the exit (on the right side). Horizontal cars can be moved left and right and vertical cars can be moved up and down. Cars are not allowed to move through other cars. One move is the displacement of one car to another eligible location.

A solution to a rush hour puzzle is a sequence of moves that allows the red car to exit. An optimal solution is a solution with fewest possible moves.

# Reading a file and instanciate a game. 

A state of the game is a configuration of non-overlapping vehicles which can be of length 2 and 3 and which can be horizontal or vertical.

An initial state of the game will be given by a file of the following format. The first line is the size of the grid (which is square by assumption). The second line gives the number of vehicles.

Then, there is a line for each vehicle: first, we give an integral label to the vehicle, then its orientation h or v for horizontal or vertical, its length (2 or 3) and finally the absissa and the ordinate of its topleft cell. We list columns of the grid from left to right and lines from the grid from top to bottom. We always assume that the vehicle number 1 is the car that needs to exit the traffic (a.k.a the red car).

For instance, the text file encoding the initial state described above is:

6

8 

1 h 2 2 3 

2 h 2 1 1 

3 h 2 5 5 

4 h 3 3 6 

5 v 3 6 1 

6 v 3 1 2 

7 v 3 4 2 

8 v 2 1 5
