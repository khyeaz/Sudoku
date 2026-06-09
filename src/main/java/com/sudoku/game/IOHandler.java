package com.sudoku.game;

import java.util.Scanner;

public class IOHandler {
    Scanner scanner;

    static final String UNEXPECTED_ERROR = "An unexpected error occurred! Program is shutting down.";

    public IOHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printUnexpectedError() {
        System.out.println(UNEXPECTED_ERROR);
    }

}
