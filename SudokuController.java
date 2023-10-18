package se.kth.gm.labb4.view;
import se.kth.gm.labb4.model.*;
import java.util.ArrayList;
import java.util.Random;
import javafx.stage.FileChooser;
import java.io.*;

/**
 * Controller for the Sudoku game, managing interactions between the model and view.
 */
public class SudokuController {
    private SudokuModel model;
    private SudokuView view;

    /**
     * Constructs a new SudokuController.
     *
     * @param model The model for the Sudoku game.
     * @param view The view for the Sudoku game.
     */
    public SudokuController(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Starts a new game round with the current difficulty level.
     */
    public void startNewRoundWithSameLevel() {
        SudokuUtilities.SudokuLevel currentLevel = model.getCurrentLevel();
        onInitNewGameRoundSelected(currentLevel);
    }

    /**
     * Initializes a new game round with the specified difficulty level.
     *
     * @param level The difficulty level of the Sudoku.
     */
    public void onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel level) {
        model.setCurrentLevel(level);
        clearWholeBoard();
        model.resetAllCellStatuses();
        int[][][] newBoard = SudokuUtilities.generateSudokuMatrix(level);
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                model.setValue(row, col, newBoard[row][col][0]);
                if(newBoard[row][col][0] != 0) {
                    model.getCell(row, col).setShownFirst(true);  // Set the isShownFirst property for filled cells.
                }
                model.getCell(row, col).setCorrectValue(newBoard[row][col][1]);
            }
        }
        view.updateBoard(model);
    }

    /**
     * Places a value on the board if the cell is not already filled.
     *
     * @param row The row index.
     * @param col The column index.
     * @param value The value to be placed.
     */
    public void onPlaceValueSelected(int row, int col, int value) {
        SudokuCell cell = model.getCell(row, col);

        if (!cell.isShownFirst() && cell.getUserValue() == 0) {
            model.setValue(row, col, value);
            view.updateCell(row, col, value);
        }
    }

    /**
     * Checks if the board is completed and shows a message accordingly.
     */
    public void onCheckCompletionSelected() {
        if (model.isComplete()) {
            if (model.areFilledCellsCorrect()){
                view.showCompletionMessage();
            }
            else {
                view.showInCompletionMessage();
            }
        }
    }

    /**
     * Clears the tile at the specified position.
     *
     * @param row The row index.
     * @param col The column index.
     */
    public void clearTile(int row, int col){
        if(!model.getCell(row, col).isShownFirst()) {
            model.setValue(row, col, 0);
            view.updateCell(row, col, 0);
        }
    }

    /**
     * Clears all tiles that weren't part of the original puzzle.
     */
    public void clearAllTiles() {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                if (!model.getCell(row, col).isShownFirst()) {
                    model.setValue(row, col, 0);
                    view.updateCell(row, col, 0);
                }
            }
        }
    }

    /**
     * Clears the entire board, including tiles that were part of the original puzzle.
     */
    public void clearWholeBoard() {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                model.setValue(row, col, 0);
                view.updateCell(row, col, 0);
            }
        }
        view.updateBoard(model);
    }

    /**
     * Checks if the filled cells on the board are correct and shows feedback to the user.
     */
    public void checkBoard() {
        Alert feedbackAlert = new Alert(AlertType.INFORMATION);
        feedbackAlert.setTitle("Sudoku Feedback");
        feedbackAlert.setHeaderText(null);

        if (model.areFilledCellsCorrect()) {
            feedbackAlert.setContentText("Allt ser bra ut hittills!");
        } else {
            feedbackAlert.setContentText("Det finns fel på brädet!");
        }

        feedbackAlert.showAndWait();
    }

    /**
     * Displays the rules of Sudoku to the user.
     */
    public void rulesInfo(){
        Alert rulesAlert = new Alert(AlertType.INFORMATION);
        rulesAlert.setTitle("Sudoku Rules");
        rulesAlert.setHeaderText(null);

        rulesAlert.setContentText("Fill in the grid with the numbers 1 through 9.\n\nEach row, column, and region must contain all nine digits,\n\n with no repetition.");

        rulesAlert.showAndWait();
    }

    /**
     * Fills a random empty cell with its correct value as a hint.
     */
    public void randomHintCell() {
        ArrayList<int[]> emptyCells = new ArrayList<>();

        // 1. Skapa en lista av tomma celler
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (model.getCell(i, j).getUserValue() == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        // Om det inte finns några tomma celler, avsluta
        if (emptyCells.isEmpty()) return;

        // 2. Välj en slumpmässig tom cell
        Random rand = new Random();
        int[] randomEmptyCell = emptyCells.get(rand.nextInt(emptyCells.size()));

        int row = randomEmptyCell[0];
        int col = randomEmptyCell[1];

        // 3. Fyll den valda cellen med sitt korrekta värde
        model.setValue(row, col, model.getCell(row, col).getCorrectValue());
        view.updateCell(row, col, model.getCell(row, col).getCorrectValue());
    }

    /**
     * Saves the current state of the game to a .sudoku file.
     */
    public void saveGame() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sudoku files (*.sudoku)", "*.sudoku");
        fileChooser.getExtensionFilters().add(extFilter);
        java.io.File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(model);
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Ett fel uppstod när spelet skulle sparas.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Loads a saved game from a .sudoku file and updates the view.
     */
    public void loadGame() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sudoku files (*.sudoku)", "*.sudoku");
        fileChooser.getExtensionFilters().add(extFilter);
        java.io.File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                model = (SudokuModel) in.readObject();
                for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
                    for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                        view.updateCell(row, col, model.getCell(row, col).getUserValue());
                    }
                }
                view.updateBoard(model);
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Ett fel uppstod när spelet skulle laddas.");
                alert.showAndWait();
            }
        }
    }
}