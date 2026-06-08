package com.sudoku.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CellTest {
    @Test
    void testNewCell() {
        String col = "1";
        String row = "A";
        Cell cell = new Cell(row, col);
        assertEquals(col, cell.getCol());
        assertEquals(row, cell.getRow());
    }

    @Test
    void testValidValue() {
        String col = "2";
        String row = "B";
        Cell cell = new Cell(row, col);

        int value = 1;
        cell.setValue(value);
        assertEquals(value, cell.getValue());

        value = 9;
        cell.setValue(value);
        assertEquals(value, cell.getValue());
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    void testInvalidValue() {
        String col = "9";
        String row = "I";
        Cell cell = new Cell(row, col);

        int value = 0;
        assertThrows(IllegalArgumentException.class, () -> cell.setValue(value));

        int value2 = 10;
        assertThrows(IllegalArgumentException.class, () -> cell.setValue(value2));
    }
}
