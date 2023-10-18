package se.kth.gm.labb4.model;

import java.io.Serializable;


/**
 * Represents a cell in a Sudoku puzzle.
 *
 * This class holds information about the correct value of the cell, whether it was initially
 * shown to the user, and the user's entered value (if applicable).
 */
public class SudokuCell implements Serializable {
    private int correctValue;
    private boolean isShownFirst;
    private int userValue;

    /**
     * Constructs a SudokuCell object.
     *
     * @param userValue The user-entered value for the cell.
     * @param isShownFirst Indicates if the cell's correct value was initially shown to the user.
     * @param correctValue The correct value for the cell.
     */
    public SudokuCell(int userValue, boolean isShownFirst,  int correctValue) {
        this.correctValue = correctValue;
        this.isShownFirst = isShownFirst;
        if (isShownFirst) {
            this.userValue = userValue;
        }
        else {
            this.userValue = 0;
        }
    }

    /**
     * Gets the correct value of the cell.
     *
     * @return The correct value.
     */
    public int getCorrectValue() {
        return correctValue;
    }

    /**
     * Sets the correct value of the cell.
     *
     * @param value The correct value to set.
     */
    public void setCorrectValue(int value) {
        this.correctValue = value;
    }

    /**
     * Checks if the cell's correct value was initially shown to the user.
     *
     * @return True if the correct value was shown first, otherwise false.
     */
    public boolean isShownFirst() {
        return isShownFirst;
    }

    /**
     * Gets the user-entered value of the cell.
     *
     * @return The user-entered value.
     */
    public int getUserValue() {
        return userValue;
    }

    /**
     * Sets the user-entered value for the cell, but only if the correct value was not shown first.
     *
     * @param userValue The user-entered value to set.
     */
    public void setUserValue(int userValue) {
        if (!isShownFirst) {
            this.userValue = userValue;
        }
    }

    /**
     * Sets whether the cell's correct value was initially shown to the user.
     *
     * @param isShownFirst True if the correct value was shown first, otherwise false.
     */
    public void setShownFirst(boolean isShownFirst) {
        this.isShownFirst = isShownFirst;
    }
}
