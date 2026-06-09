package com.sudoku.logic;

public class CheckerResult {
    boolean valid;
    String invalidGroupType;
    String invalidGroupID;
    Integer repeatedValue;

    public CheckerResult(boolean valid, Integer repeatedValue, String invalidGroupType, String invalidGroupID) {
        this.invalidGroupID = invalidGroupID;
        this.invalidGroupType = invalidGroupType;
        this.repeatedValue = repeatedValue;
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public String getInvalidGroupType() {
        return invalidGroupType;
    }

    public String getInvalidGroupID() {
        return invalidGroupID;
    }

    public int getRepeatedValue() {
        return repeatedValue;
    }
}
