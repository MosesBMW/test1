package se.kth.gm.labb4.view;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.BorderPane;
import se.kth.gm.labb4.model.SudokuModel;
import se.kth.gm.labb4.model.SudokuUtilities;
import javafx.scene.layout.VBox;



public class SudokuView extends BorderPane {
    private SudokuGridView gridView;
    private SudokuButtonPanelView buttonPanelView;
    private SudokuMenuBarView menuBarView;
    private SudokuController controller;

    public SudokuView(SudokuModel model) {
        this.controller = new SudokuController(model, this);
        buttonPanelView = new SudokuButtonPanelView(model, controller);
        gridView = new SudokuGridView(model, controller, buttonPanelView);
        menuBarView = new SudokuMenuBarView(model, controller);

        this.setCenter(gridView.getNumberPane());
        this.setLeft(buttonPanelView.getLeftButtons());
        this.setRight(buttonPanelView.getRightButtons());
        this.setTop(menuBarView);
        updateBoard(model);
    }

    public void updateBoard(SudokuModel model){
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++){
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++){
                int value = model.getValue(row, col);
                if(value == 0){
                    gridView.getTile(row, col).setText("");
                }else {
                    gridView.getTile(row, col).setText(Integer.toString(value));
                }
            }
        }
    }
    public void updateCell(int row, int col, int value){
        if(value == 0) {
            gridView.getTile(row, col).setText("");
        } else {
            gridView.getTile(row, col).setText(Integer.toString(value));
        }
        controller.onCheckCompletionSelected();
    }

    public void showCompletionMessage(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Grattis!");
        alert.setHeaderText(null);
        alert.setContentText("Du har löst Sudoku-pusslet korrekt!");
        alert.showAndWait();
    }

    public void showInCompletionMessage() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Tyvärr!");
        alert.setHeaderText(null);
        alert.setContentText("Sudoku-pusslet är inte korrekt löst");
        alert.showAndWait();
    }
}
