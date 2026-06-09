package com.sudoku.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Board {
    Map<String, Row> rows;
    Map<String, Column> cols;
    Map<String, Square> squares;
    Map<String, Cell> cells;

    private static final String[] rowIDs = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    private static final String[] colIDs = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public Board() {
        rows = new HashMap<>();
        cols = new HashMap<>();
        squares = new HashMap<>();
        cells = new HashMap<>();

        // populate cells
        for (String rowID : rowIDs) {
            for (String colID : colIDs) {
                Cell cell = new Cell(rowID, colID);

                String cellID = rowID + colID;
                cells.put(cellID, cell);
            }
        }

        // create row-cell relationships
        for (String rowID : rowIDs) {
            Row row = new Row(new Cell[9], rowID);
            rows.put(rowID, row);

            for (int i = 0; i < colIDs.length; i++) {
                String cellID = rowID + colIDs[i];
                Cell cell = cells.get(cellID);
                cell.setParentRow(row);
                row.setCell(i, cell);
            }
        }

        // create col-cell relationships
        for (String colID : colIDs) {
            Column col = new Column(new Cell[9], colID);
            cols.put(colID, col);

            for (int i = 0; i < rowIDs.length; i++) {
                String cellID = rowIDs[i] + colID;
                Cell cell = cells.get(cellID);
                cell.setParentCol(col);
                col.setCell(i, cell);
            }
        }

        // create square-cell relationships
        for (int i = 0; i < 9; i++) {
            Square square = new Square(new Cell[9], i + "");
            squares.put(i + "", square);

            // get cellID of topleftmost cell in square
            int rowIndex = (i / 3) * 3; // 0, 3, or 6
            int colIndex = (i % 3) * 3; // 0, 3, or 6
            int squareIndex = 0;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    Cell cell = cells.get(rowIDs[rowIndex] + colIDs[colIndex]);
                    square.setCell(squareIndex, cell);
                    cell.setParentSquare(square);

                    colIndex++;
                    squareIndex++;
                }
                rowIndex++;
                colIndex -= 3;
            }
        }
    }

    public Map<String, Row> getRows() {
        return rows;
    }

    public Map<String, Column> getCols() {
        return cols;
    }

    public Map<String, Square> getSquares() {
        return squares;
    }

    public Map<String, Cell> getCells() {
        return cells;
    }

    public List<CellGroup> getAllCellGroups() {
        List<CellGroup> groups = new ArrayList<>();
        groups.addAll(cols.values());
        groups.addAll(rows.values());
        groups.addAll(squares.values());

        return groups;
    }

    @Override
    public String toString() {
        String result = "";

        for (String row : rowIDs) {
            for (String col : colIDs) {
                result += Objects.toString(cells.get(row+col).getValue(), "_");
            }

            result += "\n";
        }

        return result.substring(0, result.length() - 1);
    }

    public void setAllValuesFromString(String values) {
        String[] split = values.split("");
        int splitIndex = 0;

        for (String row : rowIDs) {
            for (String col : colIDs) {
                if ("\n".equals(split[splitIndex])) {
                    splitIndex++;
                }

                Cell cell = cells.get(row + col);
                if ("_".equals(split[splitIndex])) {
                    cell.removeValue();
                } else {
                    cell.setValue(Integer.parseInt(split[splitIndex]));
                }
                splitIndex++;
            }
        }
    }
}
