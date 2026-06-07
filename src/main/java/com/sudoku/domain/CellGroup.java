package com.sudoku.domain;

public class CellGroup {
    Cell[] cells;
    String groupID;

    public CellGroup(Cell[] cells, String groupID) {
        this.cells = cells;
        this.groupID = groupID;
    }

    
}
