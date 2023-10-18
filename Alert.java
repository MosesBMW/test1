package se.kth.gm.labb4.view;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Alert {
    private Stage stage;
    private Text titleText;
    private Text headerText;
    private Text contentText;
    private Button okButton;

    public Alert(AlertType type) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox(10);

        titleText = new Text();
        headerText = new Text();
        contentText = new Text();

        switch(type) {
            case ERROR:
                titleText.setText("Error");
                break;
            case INFORMATION:
                titleText.setText("Information");
                break;
            case WARNING:
                titleText.setText("Warning");
                break;
        }

        okButton = new Button("OK");
        okButton.setOnAction(e -> stage.close());

        vbox.getChildren().addAll(titleText, headerText, contentText, okButton);

        Scene scene = new Scene(vbox, 400, 200);
        stage.setScene(scene);
    }

    public void setTitle(String title) {
        stage.setTitle(title);
    }

    public void setHeaderText(String header) {
        headerText.setText(header);
    }

    public void setContentText(String content) {
        contentText.setText(content);
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    public void getContextText(String s) {
        contentText.setText(s);
    }
}
