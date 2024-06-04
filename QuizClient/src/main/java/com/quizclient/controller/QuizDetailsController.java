package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.UUID;

public class QuizDetailsController {
    private QuizQuery quiz;

    @FXML
    private Button editQuizButton;

    @FXML
    private Button removeQuizButton;

    @FXML
    private void initialize() {
        editQuizButton.setOnAction(_ -> SceneLoader.loadEditQuizScene(quiz.getId()));
        removeQuizButton.setOnAction(_ -> {
            QuizHttpClient.deleteQuiz(quiz.getId());
            SceneLoader.loadSelectQuizScene();
        });
    }

    private void loadQuiz(UUID quizId) {
        quiz = QuizHttpClient.getQuiz(quizId);

        titleLabel.setText(quiz.getName());
        descriptionLabel.setText(quiz.getDescription());
        String timeForQuiz = "Czas na rozwiązanie testu: ";
        if(quiz.getTime() > 0)
            timeLabel.setText(timeForQuiz + quiz.getTime() + "min");
        else
            timeLabel.setText(timeForQuiz + "Brak ograniczenia czasowego");
    }

    public void setParameter(UUID quizId) {
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
