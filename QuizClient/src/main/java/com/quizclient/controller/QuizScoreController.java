package com.quizclient.controller;


import com.quizclient.QuizClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
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

            if(userQuestionAnswers.containsAll(correctQuestionAnswers))
                ++score;
        }

        return score;
    }

    private void buildUI() {
        String userQuizScorePointsText = userQuizScore + "/" + maxQuizScore;
        userScorePointsLabel.setText("Twój wynik to: " + userQuizScorePointsText + " punktów");
        Double userQuizScorePercentage = (userQuizScore / (double) maxQuizScore) * 100;
        String userQuizScorePercentageText = String.format("%.2f", userQuizScorePercentage) + "%";
        userScorePercentageLabel.setText("Wynik procentowy: " + userQuizScorePercentageText);
    }

    @FXML
    private void onExitQuiz(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizClientApplication.class.getResource("select-quiz-view.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setScene(new Scene(root));
        stage.show();
    }

}
