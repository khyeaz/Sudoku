package com.sudoku.game;

import java.util.Scanner;

public class IOHandler {
    Scanner scanner;

    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_HINT = "hint";
    private static final String COMMAND_CHECK = "check";
    private static final String COMMAND_QUIT = "quit";

    static final String UNEXPECTED_ERROR = "An unexpected error occurred! Program is shutting down.";
    static final String ENTER_COMMAND = "Enter command (e.g., A3 4, C5 clear, hint, check, quit):";
    static final String INVALID_COMMAND = "Invalid command given!";
    static final String THANKS = "Thanks for playing!";
    static final String COMPLETE = "You have successfully completed the Sudoku puzzle!\nPress any key to play again...";

    public IOHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public PlayerInput getPlayerInput() {

        while (true) { 
            System.out.println(ENTER_COMMAND);
            String rawInput = scanner.nextLine();

            if (rawInput == null || rawInput.isBlank()) {
                System.out.println(INVALID_COMMAND);
                continue;
            }

            rawInput = rawInput.trim();

            switch (rawInput) {
                case COMMAND_HINT:
                    return new PlayerInput(PlayerInput.ACTION_HINT, null, null);
                case COMMAND_CHECK:
                    return new PlayerInput(PlayerInput.ACTION_CHECK, null, null);
                case COMMAND_QUIT:
                    return new PlayerInput(PlayerInput.ACTION_QUIT, null, null);
                default:
                    break; // check for remaining 2 cases
            }

            if (!rawInput.contains(" ")) {
                System.out.println(INVALID_COMMAND);
                continue;
            }

            String[] split = rawInput.split(" ");
            String cellID = split[0];
            String suffix = split[1];

            if (!isValidCellID(cellID)) {
                System.out.println(INVALID_COMMAND);
                continue;
            }

            if (COMMAND_CLEAR.equals(suffix)) {
                return new PlayerInput(PlayerInput.ACTION_CLEAR, cellID, null);
            }

            if (!isValidCellValue(suffix)) {
                System.out.println(INVALID_COMMAND);
                continue;
            }

             return new PlayerInput(PlayerInput.ACTION_FILL, cellID, Integer.valueOf(suffix));
        }
    }

    private boolean isValidCellID(String cellID) {
        return cellID != null && cellID.matches("^[A-I][1-9]$");
    }

    private boolean isValidCellValue(String value) {
        return value != null && value.matches("^[1-9]$");
    }

    void printThanks() {
        System.out.println(THANKS);
    }

    void printUnexpectedError() {
        System.out.println(UNEXPECTED_ERROR);
    }

    void printComplete() {
        System.out.println(COMPLETE);
    }

}
