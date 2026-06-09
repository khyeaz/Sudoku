package com.sudoku.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;
import com.sudoku.domain.Square;

public class PuzzleGenerator {

    public Board generate(int numFilled) throws Exception {
        Board board = new Board();
        fillDiagonalSquaresRandomly(board);

        Solver solver = new SimpleSolver();
        Board solved = solver.solveAny(board);

        if (!Checker.isFilledAndValid(solved)) {
            throw new Exception("Generated board is not filled or not valid");
        }

        Board puzzle = removeNumbers(solved, 81 - numFilled);

        return puzzle;
    } 

    void fillDiagonalSquaresRandomly(Board board) {
        /*
        0 1 2
        3 4 5
        6 7 8        
        */

        String[] squareIDs = new String[] {"0", "4", "8"};
        List<Square> squaresToFill = new ArrayList<>();

        for (String squareID : squareIDs) {
            squaresToFill.add(board.getSquares().get(squareID));
        }

        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        for (Square square : squaresToFill) {
            Collections.shuffle(values);
            Cell[] cells = square.getCells();

            for (int i = 0; i < values.size(); i++) {
                cells[i].setValue(values.get(i));
            }
        }
    }

    private Board removeNumbers(Board solved, int numtoRemove) throws Exception {
        Board board = BoardCloner.clone(solved);

        int removed = 0;
        List<String> toTryRemoving = new LinkedList<>(board.getCells().keySet());
        Collections.shuffle(toTryRemoving);
        Solver solver = new SimpleSolver();

        while (removed < numtoRemove && !toTryRemoving.isEmpty()) {
            String removeCellID = toTryRemoving.removeFirst();
            Board testBoard = BoardCloner.clone(board);
            testBoard.getCells().get(removeCellID).removeValue();

            Board result = solver.solveUnique(testBoard);

            if (result != null) {
                removed++;
                board = testBoard;
            }
        }

        if (removed < numtoRemove && toTryRemoving.isEmpty()) {
            throw new Exception("Unable to find valid removal");
        }

        return board;
    }
}
