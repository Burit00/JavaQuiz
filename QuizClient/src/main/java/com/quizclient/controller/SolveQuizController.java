package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.enums.QuestionTypeEnum;
import com.quizclient.model.query.AnswerQuery;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SolveQuizController {
    private Stage stage;
    private QuizQuery quiz;
    private List<QuestionQuery> questions;
    private final List<List<String>> userQuizAnswers = new ArrayList<>();
    private QuestionQuery currentQuestion;
    private int timeLeft;

    private ToggleGroup radioGroup;

    @FXML
    private Button nextQuestionButton;

    @FXML
    private Button previousQuestionButton;

    private void loadAnswers() {
        questions = QuizHttpClient.getQuestionsWithAnswers(quiz.getId());

        if (!questions.isEmpty()) {
            currentQuestion = questions.getFirst();
        } else {
            currentQuestion = null;
            questionLabel.setText("Nie znaleziono pytań");
        }

        for (QuestionQuery _ : questions) {
            userQuizAnswers.add(new ArrayList<>());
        }
    }

    private void buildUI() {
        titleLabel.setText(quiz.getName());
        timeLabel.setText(quiz.getTime() + "min");

        int numberOfQuestion = (questions.indexOf(currentQuestion) + 1);

        if (numberOfQuestion == 0) {
            setDisablePreviousQuestionButton();
            setDisableNextQuestionButton();

            return;
        }

        questionLabel.setText(numberOfQuestion + ". " + currentQuestion.getName());
        answersBox.getChildren().clear();
        QuestionTypeEnum questionType = currentQuestion.getQuestionType();
        switch (questionType) {
            case CHECKBOX -> buildCheckboxAnswers();
            case RADIO -> buildRadioAnswers();
            case INPUT -> buildInputAnswers();
        }


        setDisablePreviousQuestionButton();
        setDisableNextQuestionButton();
    }

    private void buildCheckboxAnswers() {
        for (AnswerQuery answer : currentQuestion.getAnswers()) {
            CheckBox checkBox = new CheckBox(answer.getText());
            checkBox.setUserData(answer.getId());

            answersBox.getChildren().add(checkBox);

            int currentQuestionIndex = questions.indexOf(currentQuestion);
            List<String> savedAnswers = userQuizAnswers.get(currentQuestionIndex);

            if (savedAnswers.contains(answer.getId())) {
                checkBox.setSelected(true);
            }
        }
    }

    private void buildRadioAnswers() {
        radioGroup = new ToggleGroup();

        for (AnswerQuery answer : currentQuestion.getAnswers()) {
            RadioButton radioButton = new RadioButton(answer.getText());
            radioButton.getStyleClass().add("answer");
            radioButton.setUserData(answer.getId());

            radioButton.setToggleGroup(radioGroup);
            answersBox.getChildren().add(radioButton);

            int currentQuestionIndex = questions.indexOf(currentQuestion);
            List<String> savedAnswers = userQuizAnswers.get(currentQuestionIndex);

            if (savedAnswers.contains(answer.getId())) {
                radioButton.setSelected(true);
            }
        }
    }

    private void buildInputAnswers() {
        TextField textField = new TextField();
        textField.setPromptText("Odpowiedź");

        answersBox.getChildren().add(textField);

        int currentQuestionIndex = questions.indexOf(currentQuestion);
        List<String> savedAnswers = userQuizAnswers.get(currentQuestionIndex);

        if (!savedAnswers.isEmpty())
            textField.setText(savedAnswers.getFirst());
    }

    public void setParameter(QuizQuery quiz, Stage stage) {
        this.stage = stage;
        this.quiz = quiz;
        int secondsInMinutes = 60;
        timeLeft = quiz.getTime() * secondsInMinutes;
        this.loadAnswers();
        this.buildUI();
    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private VBox answersBox;

    @FXML
    private Label questionLabel;

    @FXML
    private void onPreviousQuestion() {
        if (!previousQuestionButton.isDisabled()) {
            saveAnswer();
            int currentQuestionIndex = questions.indexOf(currentQuestion);
            int previousQuestionIndex = currentQuestionIndex - 1;
            currentQuestion = questions.get(previousQuestionIndex);
            buildUI();
        }
    }

    @FXML
    private void onNextQuestion() {
        if (!nextQuestionButton.isDisabled()) {
            saveAnswer();
            int currentQuestionIndex = questions.indexOf(currentQuestion);
            int nextQuestionIndex = currentQuestionIndex + 1;
            currentQuestion = questions.get(nextQuestionIndex);
            buildUI();
        }
    }

    private void setDisableNextQuestionButton() {
        int currentQuestionIndex = questions.indexOf(currentQuestion);
        boolean hasNextQuestion = currentQuestionIndex < questions.size() - 1;
        nextQuestionButton.setDisable(!hasNextQuestion);
    }

    private void setDisablePreviousQuestionButton() {
        int currentQuestionIndex = questions.indexOf(currentQuestion);
        boolean hasPreviousQuestion = currentQuestionIndex > 0;
        previousQuestionButton.setDisable(!hasPreviousQuestion);
    }

    @FXML
    private void onConfirmQuiz() {
        saveAnswer();
        FXMLLoader fxmlLoader = SceneLoader.loadScene("quiz-score-view.fxml", stage);
        QuizScoreController controller = fxmlLoader.getController();

        List<List<String>> correctQuizAnswers = questions.stream()
                .map(question -> question.getCorrectAnswers())
                .toList();

        controller.setParameter(correctQuizAnswers, userQuizAnswers);
    }

    private void saveAnswer() {
        QuestionTypeEnum questionType = currentQuestion.getQuestionType();
        switch (questionType) {
            case CHECKBOX -> saveCheckboxAnswers();
            case RADIO -> saveRadioAnswers();
            case INPUT -> saveInputAnswers();
        }
    }

    private void saveCheckboxAnswers() {
        int currentQuestionIndex = questions.indexOf(currentQuestion);
        userQuizAnswers.get(currentQuestionIndex).clear();

        for (Node checkboxNode : answersBox.getChildren()) {
            CheckBox checkbox = (CheckBox) checkboxNode;
            String answer = (String) checkbox.getUserData();

            if (checkbox.isSelected())
                userQuizAnswers.get(currentQuestionIndex).add(answer);
        }
    }

    private void saveRadioAnswers() {
        RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String answer = (String) selectedRadioButton.getUserData();

            int currentQuestionIndex = questions.indexOf(currentQuestion);
            userQuizAnswers.get(currentQuestionIndex).clear();
            userQuizAnswers.get(currentQuestionIndex).add(answer);
        }
    }

    private void saveInputAnswers() {
        int currentQuestionIndex = questions.indexOf(currentQuestion);
        userQuizAnswers.get(currentQuestionIndex).clear();
        TextField input = (TextField) answersBox.getChildren().getFirst();

        userQuizAnswers.get(currentQuestionIndex).add(input.getText());
    }
}
