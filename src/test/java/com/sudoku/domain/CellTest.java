package com.sudoku.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CellTest {
    @Test
    void testNewCell() {
        int col = 1;
        char row = 'A';
        Cell cell = new Cell(col, row);
        assertEquals(col, cell.getCol());
        assertEquals(row, cell.getRow());
    }

    @Test
    void testValidValue() {
        int col = 2;
        char row = 'B';
        Cell cell = new Cell(col, row);

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
        int col = 9;
        char row = 'I';
        Cell cell = new Cell(col, row);

        int value = 0;
        assertThrows(IllegalArgumentException.class, () -> cell.setValue(value));

        int value2 = 10;
        assertThrows(IllegalArgumentException.class, () -> cell.setValue(value2));
    }
}
