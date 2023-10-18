package se.kth.gm.labb4.model;

import java.io.Serializable;
import java.text.Format;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a model for a Sudoku game.
 */
public class SudokuModel implements Serializable {

    private SudokuCell[][] board;
    private SudokuUtilities.SudokuLevel currentLevel;

    /**
     * Default constructor. Initializes an empty Sudoku board.
     */
    public SudokuModel() {
        board = new SudokuCell[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                board[i][j] = new SudokuCell(0, false, 0);
            }
        }
    }

    /**
     * Constructs a Sudoku model with a specified difficulty level.
     *
     * @param level The difficulty level of the Sudoku.
     */
    public SudokuModel(SudokuUtilities.SudokuLevel level) {
        this();
        this.currentLevel = level;

        int[][] initialSudoku = new int[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        int[][] correctSudoku = new int[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        int[][][] generatedSudoku = SudokuUtilities.generateSudokuMatrix(level);

        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                initialSudoku[i][j] = generatedSudoku[i][j][0];
                correctSudoku[i][j] = generatedSudoku[i][j][1];
                if (initialSudoku[i][j] != 0) {
                    board[i][j] = new SudokuCell(initialSudoku[i][j], true, correctSudoku[i][j]);
                } else {
                    board[i][j] = new SudokuCell(initialSudoku[i][j], false, correctSudoku[i][j]);
                }
            }
        }
    }

    /**
     * Checks if the filled cells have the correct values.
     *
     * @return true if all filled cells are correct, false otherwise.
     */
    public boolean areFilledCellsCorrect() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuCell cell = board[i][j];
                int userValue = cell.getUserValue();
                if (userValue != 0 && userValue != cell.getCorrectValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieves the current Sudoku difficulty level.
     *
     * @return The current Sudoku difficulty level.
     */
    public SudokuUtilities.SudokuLevel getCurrentLevel() {
        System.out.println("Current Level: " + currentLevel);
        return currentLevel;
    }

    /**
     * Sets the current Sudoku difficulty level.
     *
     * @param level The difficulty level to set.
     */
    public void setCurrentLevel(SudokuUtilities.SudokuLevel level) {
        currentLevel = level;
    }

    /**
     * Retrieves a specific cell from the Sudoku board.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The Sudoku cell at the specified position.
     */
    public SudokuCell getCell(int row, int col){
        return board[row][col];
    }

    /**
     * Retrieves the user value of a specific cell.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The user value of the cell at the specified position.
     */
    public int getValue(int row, int col){
        return board[row][col].getUserValue();
    }

    /**
     * Sets the user value for a specific cell.
     *
     * @param row The row index.
     * @param col The column index.
     * @param value The value to set.
     */
    public void setValue(int row, int col, int value) {
        board[row][col].setUserValue(value);
    }

    /**
     * Resets the status of all the cells.
     */
    public void resetAllCellStatuses() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                getCell(row, col).setShownFirst(false);
            }
        }
    }

    /**
     * Checks if the Sudoku board is complete.
     *
     * @return true if the board is complete, false otherwise.
     */
    public boolean isComplete(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (board[i][j].getUserValue() == 0){
                    return false;
                }
            }
        }
        return true;
    }
}
