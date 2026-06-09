package com.sudoku.game;

import java.util.Scanner;

import com.sudoku.domain.Board;
import com.sudoku.logic.PuzzleGenerator;
import com.sudoku.logic.SimpleSolver;
import com.sudoku.logic.Solver;

public class SudokuGame {
    IOHandler iohandler;
    PuzzleGenerator puzzleGenerator;
    Solver solver;
    Board board;
    Board solution;

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            initialise(scanner);

            boolean playNewGame = true;

            while (playNewGame) {
                setupBoard();

                boolean thisGameContinues = true;

                while (thisGameContinues) {
                    PlayerInput playerInput = iohandler.getPlayerInput();

                    switch (playerInput.getAction()) {
                        case PlayerInput.ACTION_QUIT: 
                            thisGameContinues = false;
                            playNewGame = false;
                            break;
                        case PlayerInput.ACTION_HINT:
                            hint();
                            break;
                        case PlayerInput.ACTION_CHECK:
                            check();
                            break;
                        case PlayerInput.ACTION_CLEAR:
                            clear(playerInput.getCellID());
                            break;
                        case PlayerInput.ACTION_FILL:
                            thisGameContinues = fill(playerInput.getCellID(), playerInput.getFillValue());
                            break;
                        default: 
                            break;
                    }
                }

                if (playNewGame) {
                    iohandler.printComplete();
                } else {
                    iohandler.printThanks();
                }
            }

        } catch (Exception e) {
            iohandler.printUnexpectedError();
        }
    }

    private void initialise(Scanner scanner) throws Exception {
        iohandler = new IOHandler(scanner);
        puzzleGenerator = new PuzzleGenerator();
        solver = new SimpleSolver();
    }

    private void setupBoard() throws Exception {
        board = puzzleGenerator.generate(30);
        solution = solver.solveUnique(board);
    }

    private void hint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void clear(String cellID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    private void check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean fill(String cellID, Integer fillValue) {
        // check cell is not prefilled

        // fill

        // check whether board is filled and valid
        // return false if game is finished

        // print message and return true if invalid detected
    }
}
