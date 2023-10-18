package se.kth.gm.labb4.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import se.kth.gm.labb4.model.SudokuModel;
import se.kth.gm.labb4.model.SudokuUtilities;

public class SudokuMenuBarView extends MenuBar {
    private SudokuModel model;
    private SudokuController controller;
    private MenuItem loadGameItem;
    private MenuItem saveGameItem;
    private MenuItem exitItem;
    private MenuItem checkItem;
    private MenuItem hintItem;

    private MenuItem newRoundItem;

    private MenuItem levelItem;

    private MenuItem clearItem;

    private MenuItem rulesItem;

    private MenuItem easyItem;
    private MenuItem mediumItem;
    private MenuItem hardItem;


    public SudokuMenuBarView(SudokuModel model, SudokuController controller) {
        this.model = model;
        this.controller = controller;
        // File menu
        Menu fileMenu = new Menu("File");
        loadGameItem = new MenuItem("Load game");
        loadGameItem.setOnAction(loadGameHandler);
        saveGameItem = new MenuItem("Save game");
        saveGameItem.setOnAction(saveGameHandler);
        exitItem = new MenuItem("Exit");
        exitItem.setOnAction(exitHandler);
        fileMenu.getItems().addAll(loadGameItem, saveGameItem, exitItem);

        // Game menu
        Menu gameMenu = new Menu("Game");
        newRoundItem = new MenuItem("New round");
        newRoundItem.setOnAction(newRoundHandler);
        Menu levelSubMenu = new Menu("Level");
        MenuItem easyItem = new MenuItem("Easy");
        easyItem.setOnAction(easyLevelHandler);
        MenuItem mediumItem = new MenuItem("Medium");
        mediumItem.setOnAction(mediumLevelHandler);
        MenuItem hardItem = new MenuItem("Hard");
        hardItem.setOnAction(hardLevelHandler);
        levelSubMenu.getItems().addAll(easyItem, mediumItem, hardItem);
        gameMenu.getItems().addAll(newRoundItem, levelSubMenu);

        Menu helpMenu = new Menu("Help");
        clearItem = new MenuItem("Clear");
        clearItem.setOnAction(clearHandler);
        checkItem = new MenuItem("Check");
        checkItem.setOnAction(checkHandler);
        rulesItem = new MenuItem("Rules");
        rulesItem.setOnAction(rulesHandler);
        helpMenu.getItems().addAll(clearItem, checkItem, rulesItem);

        getMenus().addAll(fileMenu, gameMenu, helpMenu);
    }

    private EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }
    };

    private EventHandler<ActionEvent> checkHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.checkBoard();
        }
    };

    private EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.clearAllTiles();
        }
    };

    private EventHandler<ActionEvent> rulesHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.rulesInfo();
        }
    };

    private EventHandler<ActionEvent> easyLevelHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel.EASY);
        }
    };

    private EventHandler<ActionEvent> mediumLevelHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel.MEDIUM);
        }
    };

    private EventHandler<ActionEvent> hardLevelHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel.HARD);
        }
    };

    private EventHandler<ActionEvent> newRoundHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.startNewRoundWithSameLevel();
        }
    };

    private EventHandler<ActionEvent> saveGameHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.saveGame();
        }
    };

    private EventHandler<ActionEvent> loadGameHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.loadGame();
        }
    };
}


