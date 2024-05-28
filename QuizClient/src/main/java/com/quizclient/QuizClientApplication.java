package com.quizclient;

import com.quizclient.utils.SceneLoader;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class QuizClientApplication extends Application {

    static {
        Font.loadFont(QuizClientApplication.class.getResource("font/fontawesome-webfont.ttf").toExternalForm(), 10);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Java Quiz!");
        SceneLoader.setStage(stage);
        SceneLoader.loadSelectQuizScene();
    }

    public static void main(String[] args) {
        launch();
    }
}