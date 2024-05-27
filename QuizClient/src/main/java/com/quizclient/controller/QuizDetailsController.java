package com.quizclient.controller;

import com.quizclient.QuizClientApplication;
import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.utils.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    private void onStartQuiz(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        FXMLLoader fxmlLoader = SceneLoader.loadScene("solve-quiz-view.fxml", stage);

        SolveQuizController controller = fxmlLoader.getController();
        controller.setParameter(this.quiz, stage);
    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label descriptionLabel;
}
