package com.quizclient.dialog;

import com.quizclient.QuizClientApplication;
import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.command.LoginUserCommand;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginDialog extends Dialog<Boolean> {
    private final TextField usernameInput = new TextField();
    private final PasswordField passwordInput = new PasswordField();

    private final Label emptyUsernameWarningLabel = new Label("Pole nie może być puste!");
    private final Label emptyPasswordWarningLabel = new Label("Pole nie może być puste!");
    private final Label wrongUserNameOrPasswordLabel = new Label("Nie poprawna nazwa użytkownika lub hasło");

    private final VBox form = new VBox(5);


    public LoginDialog() {
        buildUI();
    }

    private void buildUI() {
        setTitle("Logowanie");

        buildWrongUserLabel();
        buildCancelButton();
        buildLoginButton();

        form.getChildren().addAll(
                buildUsernameInputBox(),
                buildPasswordInputBox()
        );

        getDialogPane().setContent(form);

        setOnCloseRequest(_ -> onCancel());
        getDialogPane().setPrefHeight(250);
        getDialogPane().getStylesheets().addAll(
            QuizClientApplication.class.getResource("styles/global.css").toExternalForm(),
            QuizClientApplication.class.getResource("dialog/dialog-base.css").toExternalForm()
        );
    }

    private void buildWrongUserLabel() {
        wrongUserNameOrPasswordLabel.getStyleClass().add("wrong-username-or-password");
    }

    private void buildLoginButton() {
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        Button loginButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        loginButton.addEventFilter(ActionEvent.ANY, event -> {
            event.consume();
            onLogin();
        });
        loginButton.setText("Zaloguj się");
    }

    private void buildCancelButton() {
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Button cancelButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText("Anuluj");
    }

    private VBox buildUsernameInputBox() {
        usernameInput.setPromptText("Nazwa użytkownika...");
        usernameInput.setOnKeyTyped(_ ->
                ((VBox)usernameInput.getParent()).getChildren().remove(emptyUsernameWarningLabel));

        emptyUsernameWarningLabel.getStyleClass().add("warning");

        Label inputLabel = new Label("Nazwa użytkownika:");
        inputLabel.getStyleClass().add("input-label");

        return new VBox(
                inputLabel,
                usernameInput
        );
    }

    private VBox buildPasswordInputBox() {
        passwordInput.setPromptText("Hasło...");
        passwordInput.setOnKeyTyped(_ ->
                ((VBox)passwordInput.getParent()).getChildren().remove(emptyPasswordWarningLabel));

        emptyPasswordWarningLabel.getStyleClass().add("warning");

        return new VBox(
                new Label("Hasło:"),
                passwordInput
        );
    }

    private void onLogin() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        boolean isFormValid = true;

        if (username == null || username.isBlank()) {
            usernameInput.requestFocus();
            ((VBox)usernameInput.getParent()).getChildren().remove(emptyUsernameWarningLabel);
            ((VBox)usernameInput.getParent()).getChildren().add(emptyUsernameWarningLabel);
            isFormValid = false;
        }

        if (password == null || password.isBlank()) {
            passwordInput.requestFocus();
            ((VBox)passwordInput.getParent()).getChildren().remove(emptyPasswordWarningLabel);
            ((VBox)passwordInput.getParent()).getChildren().add(emptyPasswordWarningLabel);
            isFormValid = false;
        }

        if(!isFormValid) return;

        LoginUserCommand user = new LoginUserCommand(
            username,
            password
        );

        boolean isLoginSuccessful = QuizHttpClient.login(user);
        if(isLoginSuccessful) {
            setResult(true);
            return;
        }

        form.getChildren().remove(wrongUserNameOrPasswordLabel);
        form.getChildren().addFirst(wrongUserNameOrPasswordLabel);
    }

    private void onCancel() {
        setResult(null);
    }


}
