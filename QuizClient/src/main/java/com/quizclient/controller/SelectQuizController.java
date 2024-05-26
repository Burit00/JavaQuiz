package com.quizclient.controller;

import com.quizclient.QuizClientApplication;
import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuizQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SelectQuizController {
    private void loadQuizzes() {
        quizzesContainer.getChildren().clear();
        List<QuizQuery> quizzes = QuizHttpClient.getQuizzes();

        for (QuizQuery quiz : quizzes) {
            Button quizButton = new Button(quiz.getName());
            quizButton.getStyleClass().add("quiz");
            quizButton.setOnAction(event -> openQuiz(event, quiz));

            quizzesContainer.getChildren().add(quizButton);
        }
    }

    private void openQuiz(ActionEvent event, QuizQuery quiz) {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizClientApplication.class.getResource("quiz-details-view.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        QuizDetailsController controller = fxmlLoader.getController();
        controller.setParameter(quiz.getId());
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void initialize() {
        loadQuizzes();
    }

    @FXML
    private FlowPane quizzesContainer;
}
