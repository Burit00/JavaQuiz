package com.quizclient.controller;


import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.command.UserAnswerCommand;
import com.quizclient.model.query.UserQuizScoreQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;
import java.util.UUID;

public class QuizScoreController {
    @FXML
    private Label quizNameLabel;
    @FXML
    private Label userScorePercentageLabel;
    @FXML
    private Label userScorePointsLabel;

    private UserQuizScoreQuery userQuizScore;


    public void setParameter(UUID quizId, List<UserAnswerCommand>  userQuizAnswers) {
        calculateScore(quizId, userQuizAnswers);
        buildUI();
    }

    private void calculateScore(UUID quizId, List<UserAnswerCommand> userQuizAnswers) {
        userQuizScore = QuizHttpClient.calculateQuizScore(quizId, userQuizAnswers);
        System.out.println(userQuizScore);
    }

    private void buildUI() {
        quizNameLabel.setText(userQuizScore.getQuizName());
        String userQuizScorePointsText = userQuizScore.getMaxPoints() + "/" + userQuizScore.getUserPoints();
        userScorePointsLabel.setText("Zdobyte punkty: " + userQuizScorePointsText);
        double maxQuizScoreBiggerThanZero = userQuizScore.getMaxPoints() > 0 ? userQuizScore.getMaxPoints() : 1.0;
        double userQuizScorePercentage = (userQuizScore.getUserPoints() / maxQuizScoreBiggerThanZero) * 100;
        String userQuizScorePercentageText = String.format("%.2f", userQuizScorePercentage) + "%";
        userScorePercentageLabel.setText("Wynik procentowy: " + userQuizScorePercentageText);
    }

    @FXML
    private void onExitQuiz() {
        SceneLoader.loadSelectQuizScene();
    }

}
