package com.quizclient;

import com.quizclient.utils.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class QuizClientApplication extends Application {
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