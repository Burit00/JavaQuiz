package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.contexts.AuthContext;
import com.quizclient.helpers.AuthHelper;
import com.quizclient.model.query.Quiz.QuizQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class SelectQuizController {
    private List<QuizQuery> quizzes;

    @FXML
    private Button createQuizButton;

    private void loadQuizzes() {
        quizzesContainer.getChildren().clear();
        quizzes = QuizHttpClient.getQuizzes();
    }

    private void buildUI() {
        AuthContext.getUserData().subscribe(user ->
                createQuizButton.setDisable(!AuthHelper.isAdmin(user)));

        if(quizzes == null || quizzes.isEmpty())
            return;


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
