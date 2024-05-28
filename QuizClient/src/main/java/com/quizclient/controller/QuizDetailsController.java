package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class QuizDetailsController {
    private QuizQuery quiz;

    private void loadQuiz(String quizId) {
        quiz = QuizHttpClient.getQuiz(quizId);

        titleLabel.setText(quiz.getName());
        descriptionLabel.setText(quiz.getDescription());
        timeLabel.setText("Czas na rozwiązanie testu: " + quiz.getTime() + "min");

    }

    public void setParameter(String quizId) {
        loadQuiz(quizId);
    }

    @FXML
    private void onExitQuiz() {
        SceneLoader.loadSelectQuizScene();
    }

    @FXML
    private void onStartQuiz() {
        SceneLoader.loadSolveQuizScene(quiz);
    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label descriptionLabel;
}
