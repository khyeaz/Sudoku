package com.sudoku.domain;

public class Row {
    Cell[] cells;
    int rowNum;

    public Row(Cell[] cells, int rowNum) {
        this.cells = cells;
        this.rowNum = rowNum;
    }

    
}
