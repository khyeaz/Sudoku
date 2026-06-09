package com.sudoku.game;

import java.util.Objects;
import java.util.Scanner;

import com.sudoku.domain.Board;
import com.sudoku.domain.CellGroup;
import com.sudoku.logic.CheckerResult;

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
    static final String HINT1 = "Hint: Cell ";
    static final String HINT2 = " = ";
    static final String CLEARED = "Move accepted. Cleared Cell ";
    static final String CHECK_PASSED = "No rule violations detected.";
    static final String CHECK_FAILED1 = "Number ";
    static final String CHECK_FAILED2 = " already exists in ";
    static final String GROUP_ROW = "Row ";
    static final String GROUP_COL = "Column ";
    static final String GROUP_SQUARE = "the same 3x3 subgrid ";
    static final String PREFILLED1 = "Invalid move. ";
    static final String PREFILLED2 = " is pre-filled";
    static final String COMPLETE_INVALID = "Board is filled up, but some rule violations exist. Run the 'check' command to find out where.";
    static final String ACCEPTED = "Move accepted.";
    static final String HEADER = "  1 2 3 4 5 6 7 8 9\n";
    static final String START = "Welcome to Sudoku!\n\nHere is your puzzle:";
    static final String CURRENT = "\nCurrent grid:";

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

    boolean isValidCellID(String cellID) {
        return cellID != null && cellID.matches("^[A-I][1-9]$");
    }

    boolean isValidCellValue(String value) {
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

        // wait for any key
        scanner.nextLine();
    }

    void printHint(String chosenCellID, Integer value) {
        System.out.println(HINT1 + chosenCellID + HINT2 + value);
    }

    void printCleared(String cellID) {
        System.out.println(CLEARED + cellID);
    }

    void printCheck(CheckerResult result) {
        if (result.isValid()) {
            System.out.println(CHECK_PASSED);
        } else {
            String cellGroupType = "";
            String cellGroupID = "";

            switch (result.getInvalidGroupType()) {
                case CellGroup.GROUPTYPE_COL:
                    cellGroupType = GROUP_COL;
                    cellGroupID = result.getInvalidGroupID();
                    break;
                case CellGroup.GROUPTYPE_ROW:
                    cellGroupType = GROUP_ROW;
                    cellGroupID = result.getInvalidGroupID();
                    break;
                case CellGroup.GROUPTYPE_SQUARE:
                    cellGroupType = GROUP_SQUARE;
                    cellGroupID = getSquareDescription(result.getInvalidGroupID());
                    break;
                default:
                    break;
            }
            System.out.println(CHECK_FAILED1 + result.getRepeatedValue() + CHECK_FAILED2 + cellGroupType + cellGroupID);
        }
    }

    private String getSquareDescription(String squareID) {
        String description = "(";
        Integer index = Integer.valueOf(squareID);

        switch (index/3) {
            case 0:
                description += "top-";
                break;
            case 1:
                description += "middle-";
                break;
            case 2:
                description += "bottom-";
                break;
            default:
                break;
        }

        switch (index % 3) {
            case 0:
                description += "left)";
                break;
            case 1:
                description += "middle)";
                break;
            case 2:
                description += "right)";
                break;
            default:
                break;
        }

        return description;
    }

    void printPrefilled(String cellID) {
        System.out.println(PREFILLED1 + cellID + PREFILLED2);
    }

    void printCompleteButInvalid() {
        System.out.println(COMPLETE_INVALID);
    }

    void printAccepted() {
        System.out.println(ACCEPTED);
    }

    private void printBoard(Board board) {
        String result = HEADER;

        for (String row : Board.rowIDs) {
            result += row + " ";

            for (String col : Board.colIDs) {
                result += Objects.toString(board.getCells().get(row+col).getValue(), "_") + " ";
            }

            result += "\n";
        }

        System.out.println(result);
    }

    void printBoardStart(Board board) {
        System.out.println(START);
        printBoard(board);
    }

    void printBoardCurrent(Board board) {
        System.out.println(CURRENT);
        printBoard(board);
    }

}
