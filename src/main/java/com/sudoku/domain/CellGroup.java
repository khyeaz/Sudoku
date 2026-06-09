package com.sudoku.domain;

public class CellGroup {
    private final Cell[] cells;
    private final String groupID;
    private final String groupType;

    public static final String GROUPTYPE_ROW = "ROW";
    public static final String GROUPTYPE_COL = "COL";
    public static final String GROUPTYPE_SQUARE = "SQUARE";

    public CellGroup(Cell[] cells, String groupID, String groupType) {
        this.cells = cells;
        this.groupID = groupID;
        this.groupType = groupType;
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

    public String getGroupType() {
        return groupType;
    }
}
