package com.quizclient.ui;

import com.quizclient.contexts.AuthContext;
import com.quizclient.dialog.SignInDialog;
import com.quizclient.dialog.SignUpDialog;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.helpers.AuthHelper;
import com.quizclient.model.auth.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class Header extends AnchorPane {
    @FXML
    private Label userGreetingLabel;

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
            boolean isUserLogged = AuthHelper.isLogged(user);

            buildLoginButton(isUserLogged);

            try {
                if (isUserLogged)
                    actionButtonsBox.getChildren().remove(signUpButton);
                else
                    actionButtonsBox.getChildren().addFirst(signUpButton);
            } catch (Exception _){}

            String userGreeting = isUserLogged ? getUserGreeting(user) : null;
            userGreetingLabel.setText(userGreeting);
        });

        buildSignUpButtonContent();
    }

    private String getUserGreeting(UserData user) {
        if (user == null) return null;

        String userGreeting = "Witaj " + user.getUsername() + "!\n";
        userGreeting +=       "Twoje uprawnienia: " + user.getRole();

        return userGreeting;
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
        signUpButton.getStyleClass().addAll("reversed", "small");
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
