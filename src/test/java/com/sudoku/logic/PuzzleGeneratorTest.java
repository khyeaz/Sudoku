package com.sudoku.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;
import com.sudoku.domain.Square;

public class PuzzleGeneratorTest {
    PuzzleGenerator puzzleGenerator;

    @BeforeEach
    @SuppressWarnings("unused")
    void initialise() {
        puzzleGenerator = new PuzzleGenerator();
    }

    @Test
    void testFillDiagonalSquaresRandomly() {
        Board board = new Board();

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
    void testGenerate10blank() throws Exception {
        int numFilled = 81 - 10;
        Board board = puzzleGenerator.generate(numFilled);

        int filledCount = 0;
        int blankCount = 0;
        for (Cell cell : board.getCells().values()) {
            if (cell.isFilled()) {
                filledCount++;
            } else {
                blankCount++;
            }
        }

        assertEquals(numFilled, filledCount);
        assertEquals(81 - numFilled, blankCount);
    }

    @Test
    void testGenerate30blank() throws Exception {
        int numFilled = 81 - 30;
        Board board = puzzleGenerator.generate(numFilled);

        int filledCount = 0;
        int blankCount = 0;
        for (Cell cell : board.getCells().values()) {
            if (cell.isFilled()) {
                filledCount++;
            } else {
                blankCount++;
            }
        }

        assertEquals(numFilled, filledCount);
        assertEquals(81 - numFilled, blankCount);
    }

    @Test
    void testGenerate51blank() throws Exception {
        int numFilled = 81 - 51;
        Board board = puzzleGenerator.generate(numFilled);

        int filledCount = 0;
        int blankCount = 0;
        for (Cell cell : board.getCells().values()) {
            if (cell.isFilled()) {
                filledCount++;
            } else {
                blankCount++;
            }
        }

        assertEquals(numFilled, filledCount);
        assertEquals(81 - numFilled, blankCount);
    }
}
