package com.quizclient.dialog;

import com.quizclient.QuizClientApplication;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.ui.Icon;
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
        getDialogPane().getStylesheets().addAll(
                QuizClientApplication.class.getResource("styles/global.css").toExternalForm(),
                QuizClientApplication.class.getResource("dialog/dialog-base.css").toExternalForm()
        );
        Label resultLabel = new Label("Czy chcesz zakończyć quiz?");
        resultLabel.getStyleClass().add("warning-label");

        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

        Button yesButton = (Button)getDialogPane().lookupButton(ButtonType.OK);
        yesButton.setText("Zakończ");
        Icon applyIcon = new Icon();
        applyIcon.setValue(AwesomeIconEnum.CHECK);
        yesButton.setGraphic(applyIcon);
        yesButton.setOnAction(_ -> setResult(true));

        Button cancelButton = (Button)getDialogPane().lookupButton(ButtonType.CANCEL);

        Icon cancelIcon = new Icon();
        cancelIcon.setValue(AwesomeIconEnum.BAN);
        cancelButton.setGraphic(cancelIcon);
        cancelButton.setText("Anuluj");
        cancelButton.setOnAction(_ -> setResult(false));

        setOnCloseRequest(_ -> setResult(false));

        HBox buttonsBox = new HBox(cancelButton, yesButton);
        VBox contentBox = new VBox(resultLabel, buttonsBox);
        getDialogPane().setContent(contentBox);
    }

}
