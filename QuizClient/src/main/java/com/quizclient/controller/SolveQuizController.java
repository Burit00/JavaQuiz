package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.enums.QuestionTypeEnum;
import com.quizclient.model.query.AnswerQuery;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.ui.Icon;
import com.quizclient.utils.SceneLoader;
import com.quizclient.dialog.ConfirmQuizDialog;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.*;

public class SolveQuizController {
    private static final int secondsInMinute = 60;

    private QuizQuery quiz;
    private List<QuestionQuery> questions;
    private QuestionQuery currentQuestion;
    private int timeLeft;
    private Timeline timer = new Timeline();

    private final List<List<String>> userQuizAnswers = new ArrayList<>();
    private final ConfirmQuizDialog confirmQuizDialog = new ConfirmQuizDialog();

    private ToggleGroup radioGroup;

    @FXML
    private Button nextQuestionButton;

    @FXML
    private Button previousQuestionButton;

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
            buildQuestion();
        }
    }

    @FXML
    private void onNextQuestion() {
        if (!nextQuestionButton.isDisabled()) {
            saveAnswer();
            int currentQuestionIndex = questions.indexOf(currentQuestion);
            int nextQuestionIndex = currentQuestionIndex + 1;
            currentQuestion = questions.get(nextQuestionIndex);
            buildQuestion();
        }
    }

    @FXML
    private void onConfirmQuiz() {
        Optional<Boolean> result = confirmQuizDialog.showAndWait();

        if (result.isPresent() && result.get()) {
            timer.stop();
            showScore();
        }
    }

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
        if (quiz.getTime() > 0) buildTimer();
        buildQuestion();
    }

    private void buildTimer() {
        timeLabel.setGraphic(new Icon(AwesomeIconEnum.CLOCK_ALT));
        timeLeft = quiz.getTime() * secondsInMinute;

        setTimerLabel();
        timer = new Timeline(new KeyFrame(Duration.seconds(1), _ -> {
            setTimerLabel();
            if (timeLeft != 0) return;

            timer.stop();

            if (confirmQuizDialog.isShowing()) confirmQuizDialog.close();

            showScore();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void setTimerLabel() {
        --timeLeft;
        int minutes = timeLeft / secondsInMinute;
        int seconds = timeLeft % secondsInMinute;

        String secondsString = String.format("%02d", seconds);
        String minutesString = String.format("%02d", minutes);

        timeLabel.setText(minutesString + ":" + secondsString);
    }

    private void buildQuestion() {
        int numberOfQuestion = (questions.indexOf(currentQuestion) + 1);

        if (numberOfQuestion != 0) {
            questionLabel.setText(numberOfQuestion + ". " + currentQuestion.getName());
            answersBox.getChildren().clear();
            QuestionTypeEnum questionType = currentQuestion.getQuestionType();
            switch (questionType) {
                case CHECKBOX -> buildCheckboxAnswers();
                case RADIO -> buildRadioAnswers();
                case INPUT -> buildInputAnswers();
            }
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

        if (!savedAnswers.isEmpty()) textField.setText(savedAnswers.getFirst());
    }

    public void setParameter(QuizQuery quiz) {
        this.quiz = quiz;
        this.loadAnswers();
        this.buildUI();
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

    private void showScore() {
//        TODO
//        saveAnswer();
//        List<List<String>> correctQuizAnswers = questions.stream().map(QuestionQuery::getCorrectAnswers).toList();
//        SceneLoader.loadQuizScoreScene(correctQuizAnswers, userQuizAnswers);
    }

    private void saveAnswer() {
        if (currentQuestion == null) return;

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

            if (checkbox.isSelected()) userQuizAnswers.get(currentQuestionIndex).add(answer);
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
