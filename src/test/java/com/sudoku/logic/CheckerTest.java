package com.sudoku.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.domain.Board;
import com.sudoku.domain.CellGroup;

public class CheckerTest {
    Board board;

    @BeforeEach
    @SuppressWarnings("unused")
    void initialise () {
        board = new Board();
    }

    @Test
    void testIsFilled() {
        board.setAllValuesFromString("617248539\n395716842\n842593176\n284369715\n539471268\n761825493\n158934627\n926157384\n473682951");
        assertTrue(Checker.isFilled(board));
        
        board.getCells().get("E5").removeValue();
        assertFalse(Checker.isFilled(board));
    }

    @Test
    void testIsValidDetailed() {
        CheckerResult result = Checker.isValidDetailed(board);
        assertTrue(result.isValid());


        Board testBoard = BoardCloner.clone(board);
        testBoard.getCells().get("A1").setValue(6);
        testBoard.getCells().get("A9").setValue(6);
        result = Checker.isValidDetailed(testBoard);
        assertFalse(result.isValid());
        assertEquals(6, result.getRepeatedValue());
        assertEquals(CellGroup.GROUPTYPE_ROW, result.getInvalidGroupType());
        assertEquals("A", result.getInvalidGroupID());

        testBoard = BoardCloner.clone(board);
        testBoard.getCells().get("B1").setValue(2);
        testBoard.getCells().get("F1").setValue(2);
        result = Checker.isValidDetailed(testBoard);
        assertFalse(result.isValid());
        assertEquals(2, result.getRepeatedValue());
        assertEquals(CellGroup.GROUPTYPE_COL, result.getInvalidGroupType());
        assertEquals("1", result.getInvalidGroupID());

        testBoard = BoardCloner.clone(board);
        testBoard.getCells().get("D4").setValue(9);
        testBoard.getCells().get("F6").setValue(9);
        result = Checker.isValidDetailed(testBoard);
        assertFalse(result.isValid());
        assertEquals(9, result.getRepeatedValue());
        assertEquals(CellGroup.GROUPTYPE_SQUARE, result.getInvalidGroupType());
        assertEquals("4", result.getInvalidGroupID());
    }
}
