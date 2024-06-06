package com.quizclient.ui;

import com.quizclient.contexts.AuthContext;
import com.quizclient.dialog.LoginDialog;
import com.quizclient.enums.AwesomeIconEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class Header extends AnchorPane {
    @FXML
    private Button loginButton;

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
        AuthContext.getIsLogged().subscribe(this::buildLoginButton);
    }

    private void buildLoginButton(boolean isLogged) {
        loginButton.setOnAction(_ -> {
            if (isLogged) onLogout(); else onLogin();
        });
        buildLoginButtonContent(isLogged);
    }

    private void buildLoginButtonContent(boolean isLogged) {
        AwesomeIconEnum icon = isLogged ? AwesomeIconEnum.STEP_BACKWARD : AwesomeIconEnum.USER;
        String text = isLogged ? "Wyloguj" : "Zaloguj";

        Icon buttonIcon = new Icon(icon);
        loginButton.setGraphic(buttonIcon);
        loginButton.setText(text);
    }

    private void onLogin() {
        LoginDialog dialog = new LoginDialog();
        dialog.showAndWait();
    }

    private void onLogout() {
        AuthContext.removeToken();
    }
}
