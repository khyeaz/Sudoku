package com.sudoku.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellGroupTest {
    private Cell[] cells;
    private CellGroup cellGroup;
    private final String groupID = "A";

    @BeforeEach
    @SuppressWarnings("unused")
    void initialise() {
        cells = new Cell[9];

        for (int i = 0; i < 9; i++) {
            Cell cell = new Cell("A", i + "");
            cells[i] = cell;
            cell.setValue(i + 1);
        }

        cellGroup = new CellGroup(cells, groupID, CellGroup.GROUPTYPE_ROW);
    }

    @Test
    void testGetCells() {
        assertEquals(cells, cellGroup.getCells());
    }

    @Test
    void testGetGroupID() {
        assertEquals(groupID, cellGroup.getGroupID());
    }

    @Test
    void testSetCell() {
        int index = 3;
        Cell oldCell = cellGroup.getCells()[index];
        Cell newCell = new Cell("B", "4");

        cellGroup.setCell(index, newCell);

        assertEquals(newCell, cellGroup.getCells()[index]);
        assertNotEquals(oldCell, cellGroup.getCells()[index]);
    }

    @Test
    void testCellGroupType() {
        cells = new Cell[9];

        for (int i = 0; i < 9; i++) {
            Cell cell = new Cell("A", i + "");
            cells[i] = cell;
            cell.setValue(i + 1);
        }

        Row row = new Row(cells, "A");
        Column col = new Column(cells, "1");
        Square sqaure = new Square(cells, "0");

        assertEquals(CellGroup.GROUPTYPE_ROW, row.getGroupType());
        assertEquals(CellGroup.GROUPTYPE_COL, col.getGroupType());
        assertEquals(CellGroup.GROUPTYPE_SQUARE, sqaure.getGroupType());
    }
}
