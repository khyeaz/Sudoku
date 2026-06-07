package com.sudoku.domain;

public class Cell {
    int col;
    char row;
    int value;
    Row parentRow; 
    CellGroup parentCol;
    Square square;

    public Cell(int col, char row) {
        this.col = col;
        this.row = row;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("Cell value must be between 1 and 9");
        }
        this.value = value;
    }

    public int getCol() {
        return col;
    }

    public char getRow() {
        return row;
    }
}
