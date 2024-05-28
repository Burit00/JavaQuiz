package com.quizclient.controller;


import com.quizclient.utils.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.List;

public class QuizScoreController {
    @FXML
    private Label userScorePercentageLabel;
    @FXML
    private Label userScorePointsLabel;
    private int userQuizScore;
    private int maxQuizScore;


    public void setParameter(List<List<String>> correctQuizAnswers, List<List<String>> userQuizAnswers) {
        userQuizScore = calculateScore(correctQuizAnswers, userQuizAnswers);
        maxQuizScore = correctQuizAnswers.size();
        buildUI();
    }

    private int calculateScore(List<List<String>> correctQuizAnswers, List<List<String>> userQuizAnswers) {
        int score = 0;

        for (int questionIndex = 0; questionIndex < correctQuizAnswers.size(); questionIndex++) {
            if(correctQuizAnswers.get(questionIndex).size() != correctQuizAnswers.get(questionIndex).size()) continue;

            List<String> userQuestionAnswers = userQuizAnswers.get(questionIndex);
            List<String> correctQuestionAnswers = correctQuizAnswers.get(questionIndex);

            boolean isAnswerCorrect = true;
            for(String userAnswer: userQuestionAnswers) {
                if(!correctQuestionAnswers.contains(userAnswer)) {
                    isAnswerCorrect = false;
                    break;
                }
            }

            if(isAnswerCorrect) ++score;
        }

        return score;
    }

    private void buildUI() {
        String userQuizScorePointsText = userQuizScore + "/" + maxQuizScore;
        userScorePointsLabel.setText("Zdobyte punkty: " + userQuizScorePointsText);
        double maxQuizScoreBiggerThanZero = maxQuizScore > 0 ? maxQuizScore : 1.0;
        double userQuizScorePercentage = (userQuizScore / maxQuizScoreBiggerThanZero) * 100;
        String userQuizScorePercentageText = String.format("%.2f", userQuizScorePercentage) + "%";
        userScorePercentageLabel.setText("Wynik procentowy: " + userQuizScorePercentageText);
    }

    @FXML
    private void onExitQuiz(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        SceneLoader.loadScene("select-quiz-view.fxml", stage);
    }

}
