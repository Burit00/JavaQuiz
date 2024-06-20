package com.quizclient.ui;

import com.quizclient.contexts.AuthContext;
import com.quizclient.dialog.SignInDialog;
import com.quizclient.dialog.SignUpDialog;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.helpers.AuthHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class Header extends AnchorPane {
    @FXML
    private Button signInButton;

    private final Button signUpButton = new Button("Zarejestruj się");
    @FXML
    private HBox actionButtonsBox;

    public Header() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("header.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        buildUI();
    }

    private void buildUI() {
        AuthContext.getUserData().subscribe(user -> {
            buildLoginButton(AuthHelper.isLogged(user));
            try {
                if (AuthHelper.isLogged(user))
                    actionButtonsBox.getChildren().remove(signUpButton);
                else
                    actionButtonsBox.getChildren().addFirst(signUpButton);
            } catch (Exception _){}

        });
        buildSignUpButtonContent();
    }

    private void buildLoginButton(boolean isLogged) {
        signInButton.setOnAction(_ -> {
            if (isLogged) onLogout(); else onLogin();
        });
        buildSignInButtonContent(isLogged);
    }

    private void buildSignInButtonContent(boolean isLogged) {
        AwesomeIconEnum icon = isLogged ? AwesomeIconEnum.STEP_BACKWARD : AwesomeIconEnum.USER;
        String text = isLogged ? "Wyloguj" : "Zaloguj";

        Icon buttonIcon = new Icon(icon);
        signInButton.setGraphic(buttonIcon);
        signInButton.setText(text);
    }

    private void buildSignUpButtonContent() {
        Icon buttonIcon = new Icon(AwesomeIconEnum.PLUS);
        signUpButton.setGraphic(buttonIcon);
        signUpButton.getStyleClass().add("sign-up-in-button");
        signUpButton.setOnAction(_ -> onSignUp());
    }

    private void onLogin() {
        SignInDialog dialog = new SignInDialog();
        dialog.showAndWait();
    }

    private void onLogout() {
        AuthContext.removeToken();
    }

    private void onSignUp() {
        SignUpDialog dialog = new SignUpDialog();
        dialog.showAndWait();
    }
}
