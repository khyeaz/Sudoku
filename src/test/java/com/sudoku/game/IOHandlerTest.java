package com.sudoku.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IOHandlerTest {
    IOHandler ioHandler;

    @BeforeEach
    @SuppressWarnings("unused")
    void initialise() {
        ioHandler = new IOHandler(null);
    }


    @Test
    void testIsValidCellID() {
        assertTrue(ioHandler.isValidCellID("A1"));
        assertTrue(ioHandler.isValidCellID("I1"));
        assertTrue(ioHandler.isValidCellID("A9"));
        assertTrue(ioHandler.isValidCellID("I9"));
        assertTrue(ioHandler.isValidCellID("C5"));

        assertFalse(ioHandler.isValidCellID("J9"));
        assertFalse(ioHandler.isValidCellID("A0"));
        assertFalse(ioHandler.isValidCellID(" "));
        assertFalse(ioHandler.isValidCellID(""));
    }

    @Test
    void testIsValidCellValue() {
        assertTrue(ioHandler.isValidCellValue("1"));
        assertTrue(ioHandler.isValidCellValue("3"));
        assertTrue(ioHandler.isValidCellValue("9"));

        assertFalse(ioHandler.isValidCellValue("0"));
        assertFalse(ioHandler.isValidCellValue("10"));
        assertFalse(ioHandler.isValidCellValue("A"));
    }
}
