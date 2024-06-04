package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.enums.QuestionTypeEnum;
import com.quizclient.model.command.UserQuizAnswersCommand;
import com.quizclient.model.query.AnswerQuery;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.ui.Icon;
import com.quizclient.dialog.ConfirmQuizDialog;
import com.quizclient.utils.SceneLoader;
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
    private UserQuizAnswersCommand currentQuestionAnswer;
    private int timeLeft;
    private Timeline timer = new Timeline();

    private final List<UserQuizAnswersCommand> userQuizAnswers = new ArrayList<>();
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
        if (previousQuestionButton.isDisabled()) return;

        saveAnswer();
        int currentQuestionIndex = questions.indexOf(currentQuestion);
        int previousQuestionIndex = currentQuestionIndex - 1;
        currentQuestion = questions.get(previousQuestionIndex);
        currentQuestionAnswer = findUserQuestionAnswerCommand(currentQuestion.getId());
        buildQuestion();
    }

    @FXML
    private void onNextQuestion() {
        if (nextQuestionButton.isDisabled()) return;

        saveAnswer();
        int currentQuestionIndex = questions.indexOf(currentQuestion);
        int nextQuestionIndex = currentQuestionIndex + 1;
        currentQuestion = questions.get(nextQuestionIndex);
        currentQuestionAnswer = findUserQuestionAnswerCommand(currentQuestion.getId());
        buildQuestion();

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

        if (questions.isEmpty()) {
            currentQuestion = null;
            questionLabel.setText("Nie znaleziono pytań");
            return;
        }

        for (QuestionQuery question : questions) {
            userQuizAnswers.add(new UserQuizAnswersCommand(question.getId(), new ArrayList<>()));
        }

        currentQuestion = questions.getFirst();
        currentQuestionAnswer = findUserQuestionAnswerCommand(currentQuestion.getId());
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
            checkBox.setUserData(answer.getPublicId());

            answersBox.getChildren().add(checkBox);

            int currentQuestionIndex = questions.indexOf(currentQuestion);
            List<String> savedAnswers = userQuizAnswers.get(currentQuestionIndex).getAnswers();

            if (savedAnswers.contains(answer.getPublicId())) {
                checkBox.setSelected(true);
            }
        }
    }

    private void buildRadioAnswers() {
        radioGroup = new ToggleGroup();

        for (AnswerQuery answer : currentQuestion.getAnswers()) {
            RadioButton radioButton = new RadioButton(answer.getText());
            radioButton.getStyleClass().add("answer");
            radioButton.setUserData(answer.getPublicId());

            radioButton.setToggleGroup(radioGroup);
            answersBox.getChildren().add(radioButton);

            int currentQuestionIndex = questions.indexOf(currentQuestion);
            List<String> savedAnswers = userQuizAnswers.get(currentQuestionIndex).getAnswers();

            if (savedAnswers.contains(answer.getPublicId())) {
                radioButton.setSelected(true);
            }
        }
    }

    private void buildInputAnswers() {
        TextField textField = new TextField();
        textField.setPromptText("Odpowiedź");

        answersBox.getChildren().add(textField);

        int currentQuestionIndex = questions.indexOf(currentQuestion);
        List<String> savedAnswers = userQuizAnswers.get(currentQuestionIndex).getAnswers();

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
        saveAnswer();
        SceneLoader.loadQuizScoreScene(quiz.getId(), userQuizAnswers);
    }

    private void saveAnswer() {
        if (currentQuestion == null) return;

        currentQuestionAnswer.getAnswers().clear();
        QuestionTypeEnum questionType = currentQuestion.getQuestionType();
        switch (questionType) {
            case CHECKBOX -> saveCheckboxAnswers();
            case RADIO -> saveRadioAnswers();
            case INPUT -> saveInputAnswers();
        }
    }

    private void saveCheckboxAnswers() {
        for (Node checkboxNode : answersBox.getChildren()) {
            CheckBox checkbox = (CheckBox) checkboxNode;
            String answer = (String) checkbox.getUserData();

            if (checkbox.isSelected())
                currentQuestionAnswer.getAnswers().add(answer);
        }
    }

    private void saveRadioAnswers() {
        RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();

        if (selectedRadioButton == null) return;

        String answer = (String) selectedRadioButton.getUserData();
        currentQuestionAnswer.getAnswers().add(answer);
    }

    private void saveInputAnswers() {
        TextField input = (TextField) answersBox.getChildren().getFirst();
        currentQuestionAnswer.getAnswers().add(input.getText());
    }

    private UserQuizAnswersCommand findUserQuestionAnswerCommand(UUID questionId) {
        return userQuizAnswers.stream().filter(userQuestionAnswer ->
                userQuestionAnswer.getQuestionId().equals(questionId)).findFirst().orElse(null);
    }
}
