package dialog;

import com.quizclient.QuizClientApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConfirmQuizDialog extends Dialog<Boolean> {

    public ConfirmQuizDialog() {
        super();
        setTitle("Zakończ Quiz");
        buildUI();
    }

    private void buildUI() {
        getDialogPane().getStylesheets().add(QuizClientApplication.class.getResource("styles/global.css").toExternalForm());
        Label resultLabel = new Label("Czy chcesz zakończyć quiz?");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button yesButton = (Button)getDialogPane().lookupButton(ButtonType.OK);
        yesButton.setText("Zakończ");
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setResult(true);
            }
        });

        Button cancelButton = (Button)getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText("Anuluj");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setResult(false);
            }
        });

        getDialogPane().getScene().getWindow()
                .setOnCloseRequest(_ -> setResult(false));

        HBox buttonsBox = new HBox(cancelButton, yesButton);
        VBox contentBox = new VBox(resultLabel, buttonsBox);
        getDialogPane().setContent(contentBox);
    }

}
