package com.sudoku.logic;

import com.sudoku.domain.Board;

public interface Solver {

    /**
     * 
     * @param board puzzle to be solved
     * @return new instance of Board with first solution found, or null if no solution could be found
     */
    public Board solveAny(Board board);

    /**
     * 
     * @param board puzzle to be solved
     * @return new instance of Board with solution, or null if multiple different solutions found, or no solution found
     */
    public Board solveUnique(Board board);
}
