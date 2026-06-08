package com.sudoku.logic;

import com.sudoku.domain.Board;

public interface Solver {

    /**
     * 
     * @param board puzzle to be solved
     * @return new instance of Board with solution, or null if no solution could be found
     */
    public Board solve(Board board);
}
