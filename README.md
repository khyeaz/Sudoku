Project compiled with Maven; check the pom file for the appropriate JUnit libraries

How to Run:
- Invoke the main() method in Main.java
- Play the sudoku game via the terminal

Program flow:
Main method invokes the core class, SudokuGame. SudokuGame uses the other classes to generate the puzzle, handle IO, solve the puzzle, and check the board

Folder structure:
- domain:
	- Contains the basic representation of the game elements.
 	- Board represents the highest level, and contains all the other domain objects.
	- Row, Column, and Square represent the same concept of a group of 9 Cells, so they inherit most of their methods from CellGroup
- logic:
  	- Classes here contain the logical portion of the code, and manipulate the domain objects
  	- Classes here do not interact with the player or the input/output
  	- PuzzleGenerator seeds the topleft, middle, and bottomright squares with random values to speed up puzzle creation
  	- The generated puzzle is guaranteed to have a unique solution
  	- SimpleSolver implements the interface Solver, so you can create your own solver and add it in easily by using the interface
- game:
	- Deals with the main flow of the game, as well as interacting with the player
 	- SudokuGame is the controller of the whole game, and is called by Main.main()
  	- You can scale the difficulty up or down by changing the default value of numfilled (currently 30) in SudokuSolver.run(), but PuzzleGenerator may struggle if this number is too low
  	- It has been mathematically proven that 17 is the lowest you can go, but this program may not be capable of producing such puzzles (yet)
- test:
	- test folder structure mirrors the above 3 folders
 	- EndtoEndTest mostly only interacts with SudokuGame, and makes testing easier by using seeded puzzles
  	- EndtoEndTest could be further improved to be more thorough in the checking of the program's outputs
