package com.quizclient.controller;

import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class MainAppController {
    @FXML
    public FlowPane main;

    @FXML
    private void onQuizAppOpen() {
        SceneLoader.loadSelectQuizScene();
    }

    @FXML
    private void onResourceViewAppOpen() {
        SceneLoader.loadResourceViewScene();
    }

    @FXML
    public void initialize() {
        main.maxHeight(Double.MAX_VALUE);
    }
}
