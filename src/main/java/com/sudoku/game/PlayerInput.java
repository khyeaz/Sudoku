package com.sudoku.game;

public class PlayerInput {
    private final String action;
    private final String cellID;
    private final Integer fillValue;

    public static final String ACTION_FILL = "FILL";
    public static final String ACTION_CLEAR = "CLEAR";
    public static final String ACTION_HINT = "HINT";
    public static final String ACTION_CHECK = "CHECK";
    public static final String ACTION_QUIT = "QUIT";


    public PlayerInput(String action, String cellID, Integer fillValue) {
        this.action = action;
        this.cellID = cellID;
        this.fillValue = fillValue;
    }

    public String getAction() {
        return action;
    }

    public String getCellID() {
        return cellID;
    }

    public Integer getFillValue() {
        return fillValue;
    }
}
