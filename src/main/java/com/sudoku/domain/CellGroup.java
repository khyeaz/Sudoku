package com.sudoku.domain;

public class CellGroup {
    private final Cell[] cells;
    private final String groupID;

    public CellGroup(Cell[] cells, String groupID) {
        this.cells = cells;
        this.groupID = groupID;
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCell(int index, Cell cell) {
        cells[index] = cell;
    }

    public String getGroupID() {
        return groupID;
    }
}
