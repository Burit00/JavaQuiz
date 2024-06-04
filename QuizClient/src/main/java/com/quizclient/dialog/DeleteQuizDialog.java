package com.quizclient.dialog;

import com.quizclient.QuizClientApplication;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.ui.Icon;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

public class DeleteQuizDialog extends Dialog<Boolean> {
    public DeleteQuizDialog() {
        buildUI();
    }

    private void buildUI() {
        setTitle("Usuń Quiz");

        buildCancelButton();
        buildConfirmButton();
        buildWarningLabel();

        setOnCloseRequest(_ -> setResult(false));

        getDialogPane().getStylesheets().addAll(
                QuizClientApplication.class.getResource("styles/global.css").toExternalForm(),
                QuizClientApplication.class.getResource("dialog/dialog-base.css").toExternalForm()
        );
    }

    private void buildWarningLabel() {
        Label warningLabel = new Label("Czy na pewno chcecsz usunąć ten quiz?");
        warningLabel.getStyleClass().add("warning-label");

        getDialogPane().setContent(warningLabel);
    }

    private void buildCancelButton() {
        getDialogPane().getButtonTypes().add(ButtonType.NO);
        Button cancelButton = (Button)getDialogPane().lookupButton(ButtonType.NO);
        cancelButton.setText("Anuluj");
        cancelButton.setGraphic(new Icon(AwesomeIconEnum.BAN));
        cancelButton.setOnAction(_ -> setResult(false));
    }

    private void buildConfirmButton() {
        getDialogPane().getButtonTypes().add(ButtonType.YES);
        Button deleteQuizButton = (Button)getDialogPane().lookupButton(ButtonType.YES);
        deleteQuizButton.setText("Usuń");
        deleteQuizButton.setGraphic(new Icon(AwesomeIconEnum.TRASH_ALT));
        deleteQuizButton.setOnAction(_ -> setResult(true));
    }
}
