package com.sudoku.domain;

public class Square extends CellGroup {

    /*
    0 1 2
    3 4 5
    6 7 8

    numbering of the big squares follows the above pattern
    it also applies to indexing of small squares within the big squares
    */

    public Square(Cell[] cells, String squareID) {
        super(cells, squareID);
    }
}
