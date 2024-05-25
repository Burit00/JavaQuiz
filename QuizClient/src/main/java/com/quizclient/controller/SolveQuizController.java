package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuizQuery;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class SolveQuizController {
    private String quizId;

    private void loadQuiz() {
        QuizQuery quiz = QuizHttpClient.getQuiz(this.quizId);

        main.getChildren().clear();
        Label title = new Label(quiz.getName());
        title.getStyleClass().add("title");
        Label time = new Label("Czas na rozwiązanie quizu: " + quiz.getTime() + "min");
        time.getStyleClass().add("time");
        Label description = new Label(quiz.getDescription());
        description.getStyleClass().add("description");

        ScrollPane descriptionScollPane = new ScrollPane(description);
        descriptionScollPane.setMinHeight(200);

        VBox head = new VBox(title, time);
        head.getStyleClass().add("head");

        VBox content = new VBox(head, descriptionScollPane){{setAlignment(Pos.TOP_CENTER);}};
        main.setCenter(content);
    }

    public void setParameter(String quizId) {
        this.quizId = quizId;
        loadQuiz();
    }

    @FXML
    private BorderPane main;

    @FXML
    private Button startButton;
}
