package com.sudoku.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sudoku.domain.Board;
import com.sudoku.domain.Cell;
import com.sudoku.domain.CellGroup;

/**
 * Solves by immediate deduction, then guess and check when stuck
 * No advanced methods yet
 */
public class SimpleSolver implements Solver {
    static List<Integer> allValues = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    @Override
    public Board solveAny(Board board) {
        List<Board> solutions =  solve(board, 1, null);
        if (!solutions.isEmpty()) {
            return solutions.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Board solveUnique(Board board) {
        List<Board> solutions = solve(board, 2, null);
        if (solutions.size() == 1) {
            return solutions.get(0);
        } else {
            return null;
        }
    }

    private List<Board> solve(Board board, int numSolutions, Map<String, Set<Integer>> possibilities) {
        if (possibilities == null) {
            possibilities = generatePossibilities(board);
        }

        fillGuaranteedCells(board, possibilities);
    }

    /**
     * 
     * @param board
     * @return Map of cellID to the set of possible values. Empty Cells only.
     */
    private Map<String, Set<Integer>> generatePossibilities(Board board) {

        Map<String, Set<Integer>> possibilities = new HashMap<>();

        for (String cellID : board.getCells().keySet()) {
            Cell cell = board.getCells().get(cellID);
            if (!cell.isFilled()) {
                possibilities.put(cellID, new HashSet<>(allValues));
            }
        }

        for (CellGroup group : board.getAllCellGroups()) {
            Set<Integer> seen = new HashSet<>();
            for (Cell cell : group.getCells()) {
                if (cell.isFilled()) {
                    seen.add(cell.getValue());
                } 
            }

            for (Cell cell : group.getCells()) {
                String cellID = cell.getRow() + cell.getCol();
                if (possibilities.containsKey(cellID)) {
                    possibilities.get(cellID).removeAll(seen);
                }
            }
        }

        return possibilities;
    }

    /**
     * Iteratively fills all cells where there is only 1 possible value
     * 
     * @param board
     * @param possibilities
     */
    private void fillGuaranteedCells(Board board, Map<String, Set<Integer>> possibilities) {
        Set<String> toFill = new HashSet<>();

        for (String cellID : possibilities.keySet()) {
            if (possibilities.get(cellID).size() == 1) {
                toFill.add(cellID);
            }
        }

        while (!toFill.isEmpty()) {
            for (String cellID : toFill) {
                Cell cell = board.getCells().get(cellID);
                int newValue = possibilities.get(cellID).iterator().next();

                cell.setValue(newValue);


            }
        }
    }
}
