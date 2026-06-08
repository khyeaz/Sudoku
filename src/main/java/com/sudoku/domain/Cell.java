package com.sudoku.domain;

public class Cell {
    String col;
    String row;
    Integer value;
    Row parentRow; 
    Column parentCol;
    Square parentSquare;

    public Cell(String row, String col) {
        this.row = row;
        this.col = col;
        value = null;
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

    public void removeValue() {
        value = null;
    }

    public String getCol() {
        return col;
    }

    public String getRow() {
        return row;
    }

    public Row getParentRow() {
        return parentRow;
    }

    public void setParentRow(Row parentRow) {
        this.parentRow = parentRow;
    }

    public CellGroup getParentCol() {
        return parentCol;
    }

    public void setParentCol(Column parentCol) {
        this.parentCol = parentCol;
    }

    public Square getParentSquare() {
        return parentSquare;
    }

    public void setParentSquare(Square parentSquare) {
        this.parentSquare = parentSquare;
    }
}
