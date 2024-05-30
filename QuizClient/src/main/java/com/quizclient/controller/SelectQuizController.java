package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class SelectQuizController {
    private List<QuizQuery> quizzes;

    private void loadQuizzes() {
        quizzesContainer.getChildren().clear();
        try{
            quizzes = QuizHttpClient.getQuizzes();
        }catch(Exception e){
            quizzes = new ArrayList<>();
        }

    }

    private void buildUI() {
        for (QuizQuery quiz : quizzes) {
            Button quizButton = new Button(quiz.getName());
            quizButton.getStyleClass().add("quiz");
            quizButton.setOnAction(_ -> openQuiz(quiz));

            quizzesContainer.getChildren().add(quizButton);
        }
    }

    private void openQuiz(QuizQuery quiz) {
        SceneLoader.loadQuizDetailsScene(quiz.getId());
    }

    @FXML
    private void initialize() {
        loadQuizzes();
        buildUI();
    }

    @FXML
    private FlowPane quizzesContainer;

    @FXML
    private void onCreateQuiz() {
        SceneLoader.loadCreateQuizScene();
    }
}