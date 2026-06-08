package com.sudoku.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;
import com.sudoku.domain.Square;

public class PuzzleGeneratorTest {
    @Test
    void testFillDiagonalSquaresRandomly() {
        Board board = new Board();
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator();

        puzzleGenerator.fillDiagonalSquaresRandomly(board);

        /*
        0 1 2
        3 4 5
        6 7 8        
        */
        String[] filledSquareIDs = new String[] {"0", "4", "8"};
        String[] emptySquareIDs = new String[] {"1", "2", "3", "5", "6", "7"};
        Set<Integer> validValues = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        for (String filledSquareID : filledSquareIDs) {
            Square square = board.getSquares().get(filledSquareID);
            for (Cell cell : square.getCells()) {
                assertTrue(validValues.contains(cell.getValue()), "Cell " + cell.getRow() + cell.getCol() + "must be filled, and have a value from 1 to 9");
            }
        }

        for (String emptySquareID : emptySquareIDs) {
            Square square = board.getSquares().get(emptySquareID);
            for (Cell cell : square.getCells()) {
                assertNull(cell.getValue(), "Cell " + cell.getRow() + cell.getCol() + "must be empty");
            }
        }
    }

    @Test
    void testGenerate() {
        // TODO
    }
}
