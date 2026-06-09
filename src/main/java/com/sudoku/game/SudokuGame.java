package com.sudoku.game;

import java.util.Scanner;

import com.sudoku.domain.Board;
import com.sudoku.logic.PuzzleGenerator;

public class SudokuGame {
    IOHandler iohandler;
    PuzzleGenerator puzzleGenerator;
    Board board;

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            initialise(scanner);

            boolean keepPlaying = true;

            while (keepPlaying) {
                
            }

        } catch (Exception e) {
            iohandler.printUnexpectedError();
        }
    }

    private void initialise(Scanner scanner) throws Exception {
        iohandler = new IOHandler(scanner);
        puzzleGenerator = new PuzzleGenerator();
        board = puzzleGenerator.generate(30);
    }
}
