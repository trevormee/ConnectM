Overview
______________________________________________________________________
This is a simple program written in java that allows you to play
connect-4 (and connect-5, connect-6,...) against an AI.

Installation (on linux and wsl)
______________________________________________________________________
Run the following commands in the directory containing this readme

to compile the code:

	javac src/introtoai/Project1_CAP4601/*.java

to run the code:

	java -cp src introtoai.Project1_CAP4601.Main

Instructions
______________________________________________________________________

Once the program is running you will be prompted to enter three numbers
seperated by a space.

1. The first number specifies the size of the board.

2. The second number specifies the number of pieces a user needs to connect
   in a line to win the game.

3. The third number specifies if the user or AI
   will go first. 0 means the AI goes first, 1 means the user will go first.

As an example, entering the following:

	5 4 1

will make a 5x5 board that requires the user to connect four pieces to win.
It will also ensure the user makes the first move in the game.
