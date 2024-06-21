package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.contexts.AuthContext;
import com.quizclient.dialog.DeleteQuizDialog;
import com.quizclient.helpers.AuthHelper;
import com.quizclient.model.query.QuizDetailsQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Optional;
import java.util.UUID;

public class QuizDetailsController {
    private QuizDetailsQuery quiz;

    @FXML
    private Button editQuizButton;

    @FXML
    private Button removeQuizButton;

    @FXML
    private Button startQuizButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label questionCountLabel;

    @FXML
    private void onExitQuiz() {
        SceneLoader.loadSelectQuizScene();
    }

    @FXML
    private void onStartQuiz() {
        SceneLoader.loadSolveQuizScene(quiz);
    }

    @FXML
    private void initialize() {
        editQuizButton.setOnAction(_ -> SceneLoader.loadEditQuizScene(quiz.getId()));
        removeQuizButton.setOnAction(_ -> {
            DeleteQuizDialog dialog = new DeleteQuizDialog();
            Optional<Boolean> result = dialog.showAndWait();
            boolean shouldDeleteQuiz = result.isPresent() && result.get();
            if (!shouldDeleteQuiz) return;

            QuizHttpClient.deleteQuiz(quiz.getId());
            SceneLoader.loadSelectQuizScene();
        });

        this.setPermissions();
    }

    public void setParameter(UUID quizId) {
        loadQuiz(quizId);
    }

    private void loadQuiz(UUID quizId) {
        quiz = QuizHttpClient.getQuiz(quizId);

        if (quiz == null) {
            titleLabel.setText("Nie znaleziono quizu");
            return;
        }

        titleLabel.setText(quiz.getName());
        descriptionLabel.setText(quiz.getDescription());
        String timeForQuiz = "Czas na rozwiązanie testu: ";
        if (quiz.getTime() > 0)
            timeLabel.setText(timeForQuiz + quiz.getTime() + "min");
        else
            timeLabel.setText(timeForQuiz + "Brak ograniczenia czasowego");
        String questionCountText = "Liczba pytań: " + quiz.getQuestionCount();
        questionCountLabel.setText(questionCountText);
    }

    private void setPermissions() {
        AuthContext.getUserData().subscribe(user -> {
            boolean isAdmin = AuthHelper.isAdmin(user);
            boolean isUser = AuthHelper.isUser(user);

            editQuizButton.setDisable(!isAdmin);
            removeQuizButton.setDisable(!isAdmin);

            startQuizButton.setDisable(!isUser);
        });
    }
}
