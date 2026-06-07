package com.sudoku.domain;

public class Column {
    Cell[] cells;
    char colChar;

    public Column(Cell[] cells, char colChar) {
        this.cells = cells;
        this.colChar = colChar;
    }

    
}
