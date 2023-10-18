package se.kth.gm.labb4.view;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import se.kth.gm.labb4.model.SudokuModel;
import javafx.event.EventHandler;


public class SudokuButtonPanelView extends VBox {
    private int selectedNumber = 0;
    private VBox leftVBox;
    private VBox rightVBox;

    private SudokuModel model;
    private SudokuController controller;


    public SudokuButtonPanelView(SudokuModel model, SudokuController controller) {
        this.model = model;
        this.controller = controller;
        setSpacing(10);  // Avstånd mellan de två VBox:arna

        // Första VBox för Check och Hint
        leftVBox = new VBox(5);
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.setPadding(new Insets(0, 0, 0, 10));

        Button checkButton = new Button("Check");
        checkButton.setOnAction(CheckButtonEventHandler);
        leftVBox.getChildren().add(checkButton);

        Button hintButton = new Button("Hint");
        hintButton.setOnAction(HintButtonEventHandler);
        leftVBox.getChildren().add(hintButton);

        // Andra VBox för nummerknapparna och C
        rightVBox = new VBox(5);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPadding(new Insets(0, 10, 0, 0));

        for (int i = 1; i <= 9; i++) {
            final int num = i;
            Button button = new Button(String.valueOf(i));
            button.setOnAction(e -> selectedNumber = num);
            rightVBox.getChildren().add(button);
        }

        Button clearButton = new Button("C");
        clearButton.setOnAction(e -> selectedNumber = 0);
        rightVBox.getChildren().add(clearButton);

        // Lägg till båda VBox:arna till huvud-HBox
        getChildren().addAll(leftVBox, rightVBox);
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    // Getter för den vänstra VBox:en
    public VBox getLeftButtons() {
        return leftVBox;
    }

    // Getter för den högra VBox:en
    public VBox getRightButtons() {
        return rightVBox;
    }

    private EventHandler<ActionEvent> CheckButtonEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.checkBoard();
        }
    };

    private EventHandler<ActionEvent> HintButtonEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller.randomHintCell();
        }
    };
}

