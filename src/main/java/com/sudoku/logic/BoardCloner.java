package com.sudoku.logic;

import java.util.Map;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;

public class BoardCloner {
    public static Board clone(Board originalBoard) {
        Board newBoard = new Board();
        Map<String, Cell> newCells = newBoard.getCells();

        for (Cell ogCell : originalBoard.getCells().values()) {
            if (ogCell.isFilled()) {
                String cellID = ogCell.getRow() + ogCell.getCol();
                newCells.get(cellID).setValue(ogCell.getValue());
            }
        }

        return newBoard;
    }
}
