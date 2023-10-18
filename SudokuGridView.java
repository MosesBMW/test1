package se.kth.gm.labb4.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;
import se.kth.gm.labb4.model.SudokuUtilities;
import se.kth.gm.labb4.model.SudokuModel;
import javafx.geometry.Insets;

public class SudokuGridView {
    private Label[][] numberTiles;
    private static final int GRID_SIZE = 9;
    private static final int SECTIONS_PER_ROW = 3;
    private static final int SECTION_SIZE = 3;
    private TilePane numberPane;

    private SudokuController controller;
    private SudokuButtonPanelView buttonPanelView;
    private SudokuModel model;

    public SudokuGridView(SudokuModel model, SudokuController controller, SudokuButtonPanelView buttonPanelView) {
        this.model = model;
        this.controller = controller;
        this.buttonPanelView = buttonPanelView;
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
    }

    public TilePane getNumberPane() {
        return numberPane;
    }

    public Label getTile(int row, int col) {
        return this.numberTiles[row][col];
    }

    private final void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label tile = new Label(); // data from model can be added later
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);

                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                tile.setOnMouseClicked(tileClickHandler); // Add this if you have a specific event handler
                numberTiles[row][col] = tile;
            }
        }
    }

    private final TilePane makeNumberPane() {
        TilePane root = new TilePane();
        root.setPrefColumns(SECTIONS_PER_ROW);
        root.setPrefRows(SECTIONS_PER_ROW);
        root.setStyle("-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: white;");

        double totalBorderWidth = (GRID_SIZE + SECTIONS_PER_ROW + 1) * 0.5; // varje ruta har en halv pixel kant
        root.setMaxWidth(GRID_SIZE * 32 + totalBorderWidth);
        root.setMaxHeight(GRID_SIZE * 32 + totalBorderWidth);

        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        section.getChildren().add(numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }
                root.getChildren().add(section);
            }
        }

        return root;
    }

    private EventHandler<MouseEvent> tileClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++){
                for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++){
                    if (event.getSource() == getTile(row, col)){
                        if (buttonPanelView.getSelectedNumber() != 0) {
                            controller.onPlaceValueSelected(row, col, buttonPanelView.getSelectedNumber());
                        } else {
                            controller.clearTile(row, col);
                        }
                    }
                }
            }
        }
    };
}
