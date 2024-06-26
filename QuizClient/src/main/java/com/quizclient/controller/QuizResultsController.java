package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuizDetailsQuery;
import com.quizclient.model.query.UserQuizScoreQuery;
import com.quizclient.utils.SceneLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class QuizResultsController {
    private QuizDetailsQuery quiz;
    private List<UserQuizScoreQuery> quizScores;

    @FXML
    private Label quizNameLabel;
    @FXML
    private BorderPane tableContainer;

    public void setParameter(UUID quizId) {
        loadQuizDetails(quizId);
        buildUI();
    }

    private void loadQuizDetails(UUID quizId) {
        quiz = QuizHttpClient.getQuiz(quizId);
        quizScores = QuizHttpClient.getQuizScores(quizId);
    }

    private void buildUI() {
        quizNameLabel.setText(quiz.getName());
        buildTable();
    }

    private void buildTable() {
        if(quizScores.isEmpty()) {
            Label noScoresLabel = new Label("Brak wyników");
            noScoresLabel.getStyleClass().add("no-scores");
            tableContainer.setCenter(noScoresLabel);
            return;
        }

        TableView<UserQuizScoreQuery> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<UserQuizScoreQuery, String > userColumn = new TableColumn<>("Użytkownik");
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<UserQuizScoreQuery, Integer> userPointsColumn = new TableColumn<>("Zdobyte Punkty");
        userPointsColumn.setCellValueFactory(new PropertyValueFactory<>("userPoints"));

        TableColumn<UserQuizScoreQuery, Integer> maxPointsColumn = new TableColumn<>("Liczba Pytań");
        maxPointsColumn.setCellValueFactory(new PropertyValueFactory<>("maxPoints"));

        TableColumn<UserQuizScoreQuery, String> percentageScoreColumn = new TableColumn<>("Wynik Procentowy");
        percentageScoreColumn.setCellValueFactory(value -> getPercentageScore(value.getValue()));

        TableColumn<UserQuizScoreQuery, String> dateColumn = new TableColumn<>("Data");
        dateColumn.setCellValueFactory(value -> getDate(value.getValue()));

        table.getColumns().addAll(
                userColumn,
                userPointsColumn,
                maxPointsColumn,
                percentageScoreColumn,
                dateColumn
        );

        ObservableList<UserQuizScoreQuery> data = FXCollections.observableArrayList(quizScores);
        table.setItems(data);

        tableContainer.setCenter(table);
    }

    private static SimpleStringProperty getPercentageScore(UserQuizScoreQuery userScore) {
        double maxQuizScoreBiggerThanZero = userScore.getMaxPoints() > 0 ? userScore.getMaxPoints() : 1.0;
        double userScorePercentage = (userScore.getUserPoints() / maxQuizScoreBiggerThanZero) * 100;
        return new SimpleStringProperty(String.format("%.2f", userScorePercentage) + "%");
    }

    private static SimpleStringProperty getDate(UserQuizScoreQuery userScore) {
        String quizSolutionDate = userScore.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        return new SimpleStringProperty(quizSolutionDate);
    }

    public void onBack() {
        SceneLoader.loadQuizDetailsScene(quiz.getId());
    }
}
