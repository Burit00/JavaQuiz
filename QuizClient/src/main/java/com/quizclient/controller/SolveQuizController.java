package com.quizclient.controller;

import com.quizclient.QuizClientApplication;
import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.enums.QuestionTypeEnum;
import com.quizclient.model.query.AnswerQuery;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SolveQuizController {
    private QuizQuery quiz;
    private List<QuestionQuery> questions;
    private QuestionQuery currentQuestion;
    private int timeLeft;

    private ToggleGroup radioGroup;

    private void loadAnswers() {
        questions = QuizHttpClient.getQuestions(quiz.getId());
        if (questions.size() > 0) {
            currentQuestion = questions.get(0);
        } else {
            currentQuestion = null;
            questionLabel.setText("Nie znaleziono pytania");
        }
    }

    private void buildUI() {
        titleLabel.setText(quiz.getName());
        timeLabel.setText(quiz.getTime() + "min");

        int numberOfQuestion = (questions.indexOf(currentQuestion) + 1);

        questionLabel.setText(numberOfQuestion + ". " + currentQuestion.getName());
        answersBox.getChildren().clear();
        QuestionTypeEnum questionType = currentQuestion.getQuestionType();
        switch (questionType) {
            case CHECKBOX -> buildCheckboxAnswers();
            case RADIO -> buildRadioAnswers();
            case INPUT -> buildInputAnswers();
        }
    }

    private void buildRadioAnswers() {
        radioGroup = new ToggleGroup();

        for(AnswerQuery answer: currentQuestion.getAnswers()) {
            RadioButton radioButton = new RadioButton(answer.getText());
            radioButton.getStyleClass().add("answer");

            radioButton.setToggleGroup(radioGroup);
            answersBox.getChildren().add(radioButton);
        }
    }

    private void buildCheckboxAnswers() {
        for(AnswerQuery answer: currentQuestion.getAnswers()) {
            CheckBox checkBox = new CheckBox(answer.getText());
            checkBox.setId(answer.getId());

            answersBox.getChildren().add(checkBox);
        }
    }

    private void buildInputAnswers() {
        TextField textField = new TextField();
        textField.setPromptText("Odpowiedź");

        answersBox.getChildren().add(textField);
    }

    public void setParameter(QuizQuery quiz) {
        this.quiz = quiz;
        timeLeft = quiz.getTime();
        this.loadAnswers();
        this.buildUI();
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

    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label timeLabel;

    @FXML
    public VBox answersBox;

    @FXML
    public Label questionLabel;
}
