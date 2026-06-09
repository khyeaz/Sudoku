package com.sudoku.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;
import com.sudoku.logic.Checker;
import com.sudoku.logic.CheckerResult;
import com.sudoku.logic.PuzzleGenerator;
import com.sudoku.logic.SimpleSolver;
import com.sudoku.logic.Solver;

public class SudokuGame {
    IOHandler ioHandler;
    PuzzleGenerator puzzleGenerator;
    Solver solver;
    Board board;
    Board solution;
    Set<String> prefilledCellIDs;

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            initialise(scanner);

            boolean playNewGame = true;

            while (playNewGame) {
                setupBoard();

                boolean thisGameContinues = true;

                while (thisGameContinues) {
                    PlayerInput playerInput = ioHandler.getPlayerInput();

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
                            thisGameContinues = fillAndDecideContinue(playerInput.getCellID(), playerInput.getFillValue());
                            break;
                        default: 
                            break;
                    }

                    ioHandler.printBoardCurrent(board);
                }

                if (playNewGame) {
                    ioHandler.printComplete();
                } else {
                    ioHandler.printThanks();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            ioHandler.printUnexpectedError();
        }
    }

    private void initialise(Scanner scanner) throws Exception {
        ioHandler = new IOHandler(scanner);
        puzzleGenerator = new PuzzleGenerator();
        solver = new SimpleSolver();
    }

    private void setupBoard() throws Exception {
        board = puzzleGenerator.generate(30);
        solution = solver.solveUnique(board);
        prefilledCellIDs = new HashSet<>();

        for (Entry<String, Cell> entry : board.getCells().entrySet()) {
            if (entry.getValue().isFilled()) {
                prefilledCellIDs.add(entry.getKey());
            }
        }

        ioHandler.printBoardStart(board);
    }

    private void hint() {
        List<String> emptyCellIDS = new ArrayList<>();
        for (Entry<String, Cell> entry : board.getCells().entrySet()) {
            if (!entry.getValue().isFilled()) {
                emptyCellIDS.add(entry.getKey());
            }
        }

        Collections.shuffle(emptyCellIDS);
        String chosenCellID = emptyCellIDS.get(0);
        Integer value = solution.getCells().get(chosenCellID).getValue();
        ioHandler.printHint(chosenCellID, value);
    }

    private void clear(String cellID) {
        if (prefilledCellIDs.contains(cellID)) {
            ioHandler.printPrefilled(cellID);
        } else {
            board.getCells().get(cellID).removeValue();
            ioHandler.printCleared(cellID);
        }
    }


    private void check() {
        CheckerResult result = Checker.isValidDetailed(board);
        ioHandler.printCheck(result);
    }

    // returns false if game is complete
    private boolean fillAndDecideContinue(String cellID, Integer fillValue) {

        if (prefilledCellIDs.contains(cellID)) {
            ioHandler.printPrefilled(cellID);
            return true;
        }

        board.getCells().get(cellID).setValue(fillValue);
        ioHandler.printAccepted();

        if (Checker.isFilled(board)) {
            if (Checker.isValid(board)) {
                return false;
            } else {
                ioHandler.printCompleteButInvalid();
            }

        }

        return true;
    }
}
