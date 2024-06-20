package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.contexts.AuthContext;
import com.quizclient.dialog.DeleteQuizDialog;
import com.quizclient.helpers.AuthHelper;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Optional;
import java.util.UUID;

public class QuizDetailsController {
    private QuizQuery quiz;

    @FXML
    private Button editQuizButton;

    @FXML
    private Button removeQuizButton;

    @FXML
    private Button startQuizButton;

    @FXML
    private void initialize() {
        editQuizButton.setOnAction(_ -> SceneLoader.loadEditQuizScene(quiz.getId()));
        removeQuizButton.setOnAction(_ -> {
            DeleteQuizDialog dialog = new DeleteQuizDialog();
            Optional<Boolean> result = dialog.showAndWait();
            result.ifPresent(resultValue -> {
                if (resultValue) {
                    QuizHttpClient.deleteQuiz(quiz.getId());
                    SceneLoader.loadSelectQuizScene();
                }
            });

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
