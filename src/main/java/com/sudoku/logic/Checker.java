package com.sudoku.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;
import com.sudoku.domain.CellGroup;

public class Checker {

    public static boolean isValid(Board board) {
        return isValidDetailed(board).isValid();
    }


    public static CheckerResult isValidDetailed(Board board) {
        List<CellGroup> groups = board.getAllCellGroups();

        for (CellGroup cellGroup : groups) {
            Set<Integer> seen = new HashSet<>();

            for (Cell cell : cellGroup.getCells()) {
                Integer value = cell.getValue();
                if (value != null && seen.contains(value)) {
                    boolean valid = false;
                    int repeatedValue = value;
                    String groupType = cellGroup.getGroupType();
                    String groupID = cellGroup.getGroupID();
                    return new CheckerResult(valid, repeatedValue, groupType, groupID);
                } else {
                    seen.add(value);
                }
            }
        }

        return new CheckerResult(true, null, null, null);
    }

    public static boolean isFilled(Board board) {
        for (Cell cell : board.getCells().values()) {
            if (!cell.isFilled()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isFilledAndValid(Board board) {
        return isFilled(board) && isValid(board);
    }
}
