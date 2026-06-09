package com.sudoku.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.domain.Board;

public class SimpleSolverTest {
    SimpleSolver simpleSolver;

    @BeforeEach
    @SuppressWarnings("unused")
    void initialise() {
        simpleSolver = new SimpleSolver();
    }

    @Test
    void testFillGuaranteedCellsBasic() throws Exception {
        String solved = "617248539\n395716842\n842593176\n284369715\n539471268\n761825493\n158934627\n926157384\n473682951";
        String puzzle = "_17248539\n3957168_2\n8425931_6\n28_369715\n539_71268\n76_825493\n1589346_7\n9261573_4\n473_82951";
    
        Board puzzleBoard = new Board();
        puzzleBoard.setAllValuesFromString(puzzle);
        simpleSolver.fillGuaranteedCells(puzzleBoard, simpleSolver.generatePossibilities(puzzleBoard));
        assertEquals(solved, puzzleBoard.toString());
    }

    @Test
    void testFillGuaranteedCellsAdvanced() throws Exception {
        String solved = "961527843\n284963175\n537184629\n429751386\n176438952\n853296417\n692815734\n715349268\n348672591";
        String puzzle = "96____843\n_8_96_1_5\n53_184___\n4_9_5__86\n_764___52\n__32964__\n6_2__57_4\n___34926_\n348__2_9_";
    
        Board puzzleBoard = new Board();
        puzzleBoard.setAllValuesFromString(puzzle);
        simpleSolver.fillGuaranteedCells(puzzleBoard, simpleSolver.generatePossibilities(puzzleBoard));
        assertEquals(solved, puzzleBoard.toString());
    }



    @Test
    void testSolveEasy() {
        String solved = "961527843\n284963175\n537184629\n429751386\n176438952\n853296417\n692815734\n715349268\n348672591";
        String puzzle = "96____843\n_8_96_1_5\n53_184___\n4_9_5__86\n_764___52\n__32964__\n6_2__57_4\n___34926_\n348__2_9_";
    
        Board puzzleBoard = new Board();
        puzzleBoard.setAllValuesFromString(puzzle);
        Board solvedBoard = simpleSolver.solve(puzzleBoard, 1, null).iterator().next();

        assertEquals(solved, solvedBoard.toString());
    }

    @Test
    void testSolveMedium() {
        String solved = "123678945\n584239761\n967145328\n372461589\n691583274\n458792613\n836924157\n219857436\n745316892";
        String puzzle = "_2_6_8___\n58___97__\n____4____\n37____5__\n6_______4\n__8____13\n____2____\n__98___36\n___3_6_9_";
    
        Board puzzleBoard = new Board();
        puzzleBoard.setAllValuesFromString(puzzle);
        Board solvedBoard = simpleSolver.solve(puzzleBoard, 1, null).iterator().next();

        assertEquals(solved, solvedBoard.toString());
    }

    @Test
    void testSolveHard() {
        String solved = "581672439\n792843651\n364591782\n438957216\n256184973\n179326845\n845219367\n913768524\n627435198";
        String puzzle = "___6__4__\n7____36__\n____91_8_\n_________\n_5_18___3\n___3_6_45\n_4_2___6_\n9_3______\n_2____1__";
    
        Board puzzleBoard = new Board();
        puzzleBoard.setAllValuesFromString(puzzle);
        Board solvedBoard = simpleSolver.solve(puzzleBoard, 1, null).iterator().next();

        assertEquals(solved, solvedBoard.toString());
    }

    @Test
    void testSolveVeryHard() {
        String solved = "162857493\n534129678\n789643521\n475312986\n913586742\n628794135\n356478219\n241935867\n897261354";
        String puzzle = "1____7_9_\n_3__2___8\n__96__5__\n__53__9__\n_1__8___2\n6____4___\n3______1_\n_41_____7\n__7___3__";
    
        Board puzzleBoard = new Board();
        puzzleBoard.setAllValuesFromString(puzzle);
        Board solvedBoard = simpleSolver.solve(puzzleBoard, 1, null).iterator().next();

        assertEquals(solved, solvedBoard.toString());
    }


}
