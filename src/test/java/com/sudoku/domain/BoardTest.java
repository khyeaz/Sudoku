package com.sudoku.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
    @SuppressWarnings("unused")
    void initialise() {
        board = new Board();
    }

    @Test
    void testBoardFirstCell() {
        String rowID = "A";
        String colID = "1";
        String squareID = "0";
        Cell cell = board.cells.get(rowID + colID);
        assertEquals(rowID, cell.getRow(), "Cell row property must match");
        assertEquals(colID, cell.getCol(), "Cell col property must match");
        
        Row row = board.rows.get(rowID);
        assertEquals(row, cell.getParentRow(), "Cell parent Row Object must match");

        Column col = board.cols.get(colID);
        assertEquals(col, cell.getParentCol(), "Cell parent Column Object must match");

        Square square = board.squares.get(squareID);
        assertEquals(square, cell.getParentSquare(), "Cell parent Square Object must match");
    }

    @Test
    void testBoardLastCell() {
        String rowID = "I";
        String colID = "9";
        String squareID = "8";
        Cell cell = board.cells.get(rowID + colID);
        assertEquals(rowID, cell.getRow(), "Cell row property must match");
        assertEquals(colID, cell.getCol(), "Cell col property must match");
        
        Row row = board.rows.get(rowID);
        assertEquals(row, cell.getParentRow(), "Cell parent Row Object must match");

        Column col = board.cols.get(colID);
        assertEquals(col, cell.getParentCol(), "Cell parent Column Object must match");

        Square square = board.squares.get(squareID);
        assertEquals(square, cell.getParentSquare(), "Cell parent Square Object must match");
    }

    @Test
    void testBoardRandomCell() {
        String rowID = "D";
        String colID = "6";
        String squareID = "4";
        Cell cell = board.cells.get(rowID + colID);
        assertEquals(rowID, cell.getRow(), "Cell row property must match");
        assertEquals(colID, cell.getCol(), "Cell col property must match");
        
        Row row = board.rows.get(rowID);
        assertEquals(row, cell.getParentRow(), "Cell parent Row Object must match");

        Column col = board.cols.get(colID);
        assertEquals(col, cell.getParentCol(), "Cell parent Column Object must match");

        Square square = board.squares.get(squareID);
        assertEquals(square, cell.getParentSquare(), "Cell parent Square Object must match");
    }

    @Test
    void testSquareCellRelationship() {
        String[] cellIDs = new String[] {"D4", "D5", "D6", "E4", "E5", "E6", "F4", "F5", "F6"};
        Cell[] expectedCells = new Cell[9];

        for (int i = 0; i < expectedCells.length; i++) {
            expectedCells[i] = board.cells.get(cellIDs[i]);
        }

        Square square = board.squares.get("4");
        Cell[] actualCells = square.getCells();

        for (int i = 0; i < expectedCells.length; i++) {
            Cell expectedCell = expectedCells[i];
            Cell actualCell = actualCells[i];
            assertEquals(expectedCell, actualCell, "Cell object must match for cell " + cellIDs[i]);
            assertEquals(expectedCell.getRow(), actualCell.getRow(), "row must match for cell " + cellIDs[i]);
            assertEquals(expectedCell.getCol(), actualCell.getCol(), "col must match for cell " + cellIDs[i]);
            assertEquals(expectedCell.getParentSquare(), square, "Parent square must match for cell " + cellIDs[i]);
        }
    }

    @Test
    void testBoardObjectCount() {
        assertEquals(81, board.getCells().size(), "Board must have 81 cells");
        assertEquals(9, board.getCols().size(), "Board must have 9 columns");
        assertEquals(9, board.getRows().size(), "Board must have 9 rows");
        assertEquals(9, board.getSquares().size(), "Board must have 9 squares");
    }

    @Test
    void testSetAllValuesFromString() {
        String line1 = "123456789\n";
        String line2 = "__123__45\n";
        String test1 = line1 + line2 + line1 + line2 + line1 + line2 + line1 + line2 + line1;
        test1 = test1.substring(0, test1.length() - 1);
        board.setAllValuesFromString(test1);
        assertEquals(test1, board.toString());
    }
}
