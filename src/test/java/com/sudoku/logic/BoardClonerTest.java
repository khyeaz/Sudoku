package com.sudoku.logic;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;

// we dont respect the sudoku constraints for this test
// there may be repeated numbers in the same row, column, or square
public class BoardClonerTest {
    @Test
    void testClone() {
        Board originalBoard = new Board();
        Random rand = new Random();

        for (Cell cell : originalBoard.getCells().values()) {
            int newValue = rand.nextInt(10);

            // we also leave some cells empty
            if (newValue != 0) {
                cell.setValue(newValue);
            }
        }

        Board newBoard = BoardCloner.clone(originalBoard);

        for (String cellID : originalBoard.getCells().keySet()) {
            assertEquals(originalBoard.getCells().get(cellID).getValue(), newBoard.getCells().get(cellID).getValue(), "Cell " + cellID + " must match original Cell");
        }

        // ensure that the objects are not the same
        assertNotEquals(originalBoard, newBoard, "Cloned Board must be a new instance");

        assertNotNull(newBoard.getCells().get("A1"));
        assertNotEquals(originalBoard.getCells().get("A1"), newBoard.getCells().get("A1"), "Cloned Board must contain cells that are a new instance");
    }
}
