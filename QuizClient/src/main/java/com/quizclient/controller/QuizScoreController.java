package com.quizclient.controller;


import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.command.UserQuizAnswersCommand;
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


    public void setParameter(UUID quizId, List<UserQuizAnswersCommand>  userQuizAnswers) {
        calculateScore(quizId, userQuizAnswers);
        buildUI();
    }

    private void calculateScore(UUID quizId, List<UserQuizAnswersCommand> userQuizAnswers) {
        userQuizScore = QuizHttpClient.calculateQuizScore(quizId, userQuizAnswers);
    }

    private void buildUI() {
        quizNameLabel.setText(userQuizScore.getQuizName());
        String userQuizScorePointsText = userQuizScore.getCorrectQuestionsCount() + "/" + userQuizScore.getQuestionCount();
        userScorePointsLabel.setText("Zdobyte punkty: " + userQuizScorePointsText);
        double maxQuizScoreBiggerThanZero = userQuizScore.getQuestionCount() > 0 ? userQuizScore.getQuestionCount() : 1.0;
        double userQuizScorePercentage = (userQuizScore.getCorrectQuestionsCount() / maxQuizScoreBiggerThanZero) * 100;
        String userQuizScorePercentageText = String.format("%.2f", userQuizScorePercentage) + "%";
        userScorePercentageLabel.setText("Wynik procentowy: " + userQuizScorePercentageText);
    }

    @FXML
    private void onExitQuiz() {
        SceneLoader.loadSelectQuizScene();
    }

}
