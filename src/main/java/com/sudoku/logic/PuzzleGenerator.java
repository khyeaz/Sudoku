package com.sudoku.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;
import com.sudoku.domain.Square;

public class PuzzleGenerator {

    public Board generate(int numFilled) {
        Board board = new Board();
        fillDiagonalSquaresRandomly(board);

        Solver solver = new SimpleSolver();
        Board solved = solver.solve(board);

        // TODO

        return null;
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
}
