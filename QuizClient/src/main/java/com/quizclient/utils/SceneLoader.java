package com.quizclient.utils;

import com.quizclient.QuizClientApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    public static FXMLLoader loadScene(String fileName, Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizClientApplication.class.getResource(fileName));
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setScene(new Scene(root));
        stage.show();

        return fxmlLoader;
    }
}
