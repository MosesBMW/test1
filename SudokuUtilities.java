package se.kth.gm.labb4.model;
import java.util.Random;

/**
 * Utility class for generating Sudoku puzzles and performing Sudoku-related operations.
 *
 * This class provides methods for generating Sudoku puzzles of different difficulty levels,
 * performing operations like mirroring puzzles, swapping numbers, and converting Sudoku puzzles
 * between different representations.
 */
public class SudokuUtilities {

    public enum SudokuLevel {EASY, MEDIUM, HARD}

    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Generates a 3-dimensional matrix representing an initial Sudoku puzzle and its solution.
     *
     * @param level The level, which indicates the difficulty of the initial puzzle.
     * @return A 3-dimensional int matrix:
     *         - [row][col][0] represents the initial values, with zero representing an empty cell.
     *         - [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        switch (level) {
            case EASY: representationString = easy; break;
            case MEDIUM: representationString = medium; break;
            case HARD: representationString = hard; break;
            default: representationString = medium;
        }

        int [][][] matrix = convertStringToIntMatrix(representationString);

        Random random = new Random();
        int operation = random.nextInt(4);
        System.out.println(operation); //test

        switch (operation){
            case 1:
                matrix = mirrorHorizontally(matrix);
                break;
            case 2:
                matrix = mirrorVertically(matrix);
                break;
            case 3:
                int n1 = random.nextInt(9) + 1;
                int n2;
                do{
                    n2 = random.nextInt(9) + 1;
                }while (n1 == n2);
                matrix = swapNumb(matrix, n1, n2);
                break;
            default:
                break;
        }

        return matrix;
    }


    /**
     * Mirrors the Sudoku matrix horizontally.
     *
     * @param sudokuMatrix The Sudoku matrix to be mirrored.
     * @return The horizontally mirrored Sudoku matrix.
     */
    public static int[][][] mirrorHorizontally(int[][][] sudokuMatrix){
        int[][][] mirrorSudoku = new int[GRID_SIZE][GRID_SIZE][2];
        for (int row = 0; row < GRID_SIZE; row++){
            for (int col = 0; col < GRID_SIZE; col++){
                mirrorSudoku[row][col][0] = sudokuMatrix[GRID_SIZE - 1 - row][col][0];
                mirrorSudoku[row][col][1] = sudokuMatrix[GRID_SIZE - 1 - row][col][1];
            }
        }
        return mirrorSudoku;
    }

    /**
     * Mirrors the Sudoku matrix vertically.
     *
     * @param sudokuMatrix The Sudoku matrix to be mirrored.
     * @return The vertically mirrored Sudoku matrix.
     */
    public static int[][][] mirrorVertically(int[][][] sudokuMatrix){
        int[][][] mirrorSudoku = new int[GRID_SIZE][GRID_SIZE][2];
        for (int row = 0; row < GRID_SIZE; row++){
            for (int col = 0; col < GRID_SIZE; col++){
                mirrorSudoku[row][col][0] = sudokuMatrix[row][GRID_SIZE - 1 - col][0];
                mirrorSudoku[row][col][1] = sudokuMatrix[row][GRID_SIZE - 1 - col][1];
            }
        }
        return mirrorSudoku;
    }

    /**
     * Swaps two numbers in the Sudoku matrix.
     *
     * @param sudokuMatrix The Sudoku matrix in which the numbers should be swapped.
     * @param n1 The first number to be swapped.
     * @param n2 The second number to be swapped.
     * @return The Sudoku matrix with the swapped numbers.
     */
    public static int[][][] swapNumb(int [][][] sudokuMatrix, int n1, int n2){
        int[][][] swapSudoku = new int[GRID_SIZE][GRID_SIZE][2];
        for (int row = 0; row < GRID_SIZE; row++){
            for (int col = 0; col < GRID_SIZE; col++){
                if(sudokuMatrix[row][col][0] == n1){
                    swapSudoku[row][col][0] = n2;
                } else if (sudokuMatrix[row][col][0] == n2){
                    swapSudoku[row][col][0] = n1;
                } else {
                    swapSudoku[row][col][0] = sudokuMatrix[row][col][0];
                }

                if(sudokuMatrix[row][col][1] == n1){
                    swapSudoku[row][col][1] = n2;
                } else if (sudokuMatrix[row][col][1] == n2){
                    swapSudoku[row][col][1] = n1;
                } else {
                    swapSudoku[row][col][1] = sudokuMatrix[row][col][1];
                }
            }
        }
        return swapSudoku;
    }


    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param stringRepresentation A string of 2*81 characters, 0-9. The first 81 characters represents
     *                             the initial values, '0' representing an empty cell.
     *                             The following 81 characters represents the solution.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    /*package private*/
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " + stringRepresentation.length());

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();

        int charIndex = 0;
        // initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        // solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        return values;
    }

    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" + // solution values after this substring
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";

    private static final String medium =
            "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";

    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}
