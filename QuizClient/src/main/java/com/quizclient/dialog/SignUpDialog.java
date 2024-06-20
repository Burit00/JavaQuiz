package com.quizclient.dialog;

import com.quizclient.QuizClientApplication;
import com.quizclient.api.QuizHttpClient;
import com.quizclient.enums.UserRoleEnum;
import com.quizclient.model.command.SignUpCommand;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class SignUpDialog extends Dialog<Boolean> {
    private final TextField usernameInput = new TextField();
    private final PasswordField passwordInput = new PasswordField();
    private final PasswordField confirmPasswordInput = new PasswordField();

    private final Label emptyUsernameWarningLabel = new Label("Pole nie może być puste!");
    private final Label emptyPasswordWarningLabel = new Label("Pole nie może być puste!");
    private final Label wrongConfirmPasswordWarningLabel = new Label("Hasła muszą być identyczne!");
    private final Label wrongUserNameOrPasswordLabel = new Label("Ta nazwa użytkownika jest już zajęta");

    private final VBox form = new VBox(5);


    public SignUpDialog() {
        buildUI();
    }

    private void buildUI() {
        setTitle("Rejestracja");

        buildWrongUserLabel();
        buildCancelButton();
        buildLoginButton();

        form.getChildren().addAll(
                buildUsernameInputBox(),
                buildPasswordInputBox(),
                buildRepeatPasswordInputBox()
        );

        getDialogPane().setContent(form);

        setOnCloseRequest(_ -> onCancel());
        getDialogPane().setPrefHeight(300);
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
        Button signUpButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        signUpButton.addEventFilter(ActionEvent.ANY, event -> {
            event.consume();
            onSignUp();
        });
        signUpButton.setText("Zarejestruj się");
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

    private VBox buildRepeatPasswordInputBox() {
        confirmPasswordInput.setPromptText("Hasło...");
        confirmPasswordInput.setOnKeyTyped(_ ->
                ((VBox) confirmPasswordInput.getParent()).getChildren().remove(wrongConfirmPasswordWarningLabel));

        wrongConfirmPasswordWarningLabel.getStyleClass().add("warning");

        return new VBox(
                new Label("Powtórz hasło:"),
                confirmPasswordInput
        );
    }

    private void onSignUp() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();

        boolean isFormValid = isConfirmPasswordValid(confirmPassword, password)
                            && isPasswordValid(password)
                            && isUsernameValid(username);

        if(!isFormValid) return;

        SignUpCommand user = new SignUpCommand(
            username,
            password,
            confirmPassword,
            UserRoleEnum.USER
        );

        boolean isLoginSuccessful = QuizHttpClient.signUp(user);
        if(isLoginSuccessful) {
            setResult(true);
            return;
        }

        form.getChildren().remove(wrongUserNameOrPasswordLabel);
        form.getChildren().addFirst(wrongUserNameOrPasswordLabel);
        usernameInput.requestFocus();
    }

    private boolean isUsernameValid(String username) {
        if (username != null && !username.isBlank()) return true;

        usernameInput.requestFocus();
        ((VBox)usernameInput.getParent()).getChildren().remove(emptyUsernameWarningLabel);
        ((VBox)usernameInput.getParent()).getChildren().add(emptyUsernameWarningLabel);

        return false;
    }

    private boolean isPasswordValid(String password) {
        if (password != null && !password.isBlank()) return true;

        passwordInput.requestFocus();
        ((VBox)passwordInput.getParent()).getChildren().remove(emptyPasswordWarningLabel);
        ((VBox)passwordInput.getParent()).getChildren().add(emptyPasswordWarningLabel);

        return false;
    }

    private boolean isConfirmPasswordValid(String confirmPassword, String password) {
        if (Objects.equals(password, confirmPassword)) return true;

        confirmPasswordInput.requestFocus();
        ((VBox) confirmPasswordInput.getParent()).getChildren().remove(wrongConfirmPasswordWarningLabel);
        ((VBox) confirmPasswordInput.getParent()).getChildren().add(wrongConfirmPasswordWarningLabel);

        return false;
    }

    private void onCancel() {
        setResult(null);
    }


}
