package com.sudoku.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
    static final String EXCEPTION_CONTRADICTION = "Contradiction reached";

    @Override
    public Board solveAny(Board board) {
        List<Board> solutions =  solve(BoardCloner.clone(board), 1, null);
        if (!solutions.isEmpty()) {
            return solutions.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Board solveUnique(Board board) {
        List<Board> solutions = solve(BoardCloner.clone(board), 2, null);
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

        try {
            fillGuaranteedCells(board, possibilities);
        } catch (Exception e) {
            // normal program flow
            // invalid guess was made earlier, so no solution possible
            if (EXCEPTION_CONTRADICTION.equals(e.getMessage())) {
                return new ArrayList<>();
            } else {
                // Some other exception, not expected
                return null;
            }
        }

        // complete
        if (possibilities.isEmpty() && Checker.isFilledAndValid(board)) {
            return Arrays.asList(board);
        }

        // need to guess
        // from among the cells that have the minimum number of possible values, we choose 1 randomly
        int minCandidates = 9;
        List<String> cellsToGuess = new ArrayList<>();

        for (String cellID : possibilities.keySet()) {
            Set<Integer> candidates = possibilities.get(cellID);
            if (candidates.size() == minCandidates) {
                cellsToGuess.add(cellID);
            } else if (candidates.size() < minCandidates) {
                minCandidates = candidates.size();
                cellsToGuess = Arrays.asList(cellID);
            }
        }
        Random rand = new Random();
        String chosenCellID = cellsToGuess.get(rand.nextInt(cellsToGuess.size()));
        List<Integer> guesses = new ArrayList<>();
        guesses.addAll(possibilities.get(chosenCellID));
        Collections.shuffle(guesses);

        List<Board> solutions = new ArrayList<>();
        for (Integer guess : guesses) {
            Board newBoard = BoardCloner.clone(board);
            Map<String, Set<Integer>> newPoss = new HashMap<>();
            for (Entry<String, Set<Integer>> entry : possibilities.entrySet()) {
                HashSet<Integer> newSet = new HashSet<>();
                newSet.addAll(entry.getValue());
                newPoss.put(entry.getKey(), newSet);
            }

            Cell cell = newBoard.getCells().get(chosenCellID);

            cell.setValue(guess);
            newPoss.remove(chosenCellID);

            // remove the possibility for this cell's neighbours to have the same value
            List<CellGroup> groups = Arrays.asList(cell.getParentCol(), cell.getParentRow(), cell.getParentSquare());
            for (CellGroup group : groups) {
                Cell[] cells = group.getCells();

                for (Cell cellToAdjust : cells) {
                    String cellIDToAdjust = cellToAdjust.getRow() + cellToAdjust.getCol();
                    if (newPoss.containsKey(cellIDToAdjust)) {
                        Set<Integer> candidates = newPoss.get(cellIDToAdjust);
                        candidates.remove(guess);
                    }
                }
            }

            solutions.addAll(solve(newBoard, numSolutions, newPoss));
            if (solutions.size() >= numSolutions) {
                return solutions;
            }
        }

        return solutions;
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
    private void fillGuaranteedCells(Board board, Map<String, Set<Integer>> possibilities) throws Exception {
        Set<String> toFill = new HashSet<>();

        for (String cellID : possibilities.keySet()) {
            int numCandidates = possibilities.get(cellID).size();
            if (numCandidates == 1) {
                toFill.add(cellID);
            } else if (numCandidates == 0 && !board.getCells().get(cellID).isFilled()) {
                throw new Exception(EXCEPTION_CONTRADICTION);
            }
        }

        while (!toFill.isEmpty()) {
            Set<String> toFillNext = new HashSet<>();

            for (String cellID : toFill) {
                Cell cell = board.getCells().get(cellID);
                int newValue = possibilities.get(cellID).iterator().next();

                cell.setValue(newValue);
                possibilities.remove(cellID);

                // remove the possibility for this cell's neighbours to have the same value
                List<CellGroup> groups = Arrays.asList(cell.getParentCol(), cell.getParentRow(), cell.getParentSquare());
                for (CellGroup group : groups) {
                    Cell[] cells = group.getCells();

                    for (Cell cellToAdjust : cells) {
                        String cellIDToAdjust = cellToAdjust.getRow() + cellToAdjust.getCol();
                        if (possibilities.containsKey(cellIDToAdjust)) {
                            Set<Integer> candidates = possibilities.get(cellIDToAdjust);
                            boolean removed = candidates.remove(newValue);

                            if (removed) {
                                if (candidates.isEmpty() && !cellToAdjust.isFilled()) {
                                    throw new Exception(EXCEPTION_CONTRADICTION);
                                } else if (candidates.size() == 1) {
                                    toFillNext.add(cellIDToAdjust);
                                }
                            }
                        }
                    }
                }
            }

            toFill = toFillNext;
        }
    }
}
