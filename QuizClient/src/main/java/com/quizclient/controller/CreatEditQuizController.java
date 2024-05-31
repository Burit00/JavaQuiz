package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.dialog.AddEditQuestionDialog;
import com.quizclient.dialog.SetQuizDetailsDialog;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.model.command.QuestionCommand;
import com.quizclient.model.command.QuizCommand;
import com.quizclient.ui.Icon;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreatEditQuizController {
    public QuizCommand quiz = new QuizCommand();
    public List<QuestionCommand> questions = new ArrayList<>();

    @FXML
    private Button saveQuizButton;

    @FXML
    private VBox questionListBox;

    @FXML
    private TextField quizNameTextField;

    public void setParameter(String quizId){
        quiz = QuizCommand.mapFromQuizQuery(QuizHttpClient.getQuiz(quizId));
        List<QuestionCommand> questionsFromApi = QuizHttpClient.getQuestionsWithAnswers(quizId)
                .stream().map(QuestionCommand::mapFromQuestionQuery).toList();

        questions.addAll(questionsFromApi);
        quizNameTextField.setText(quiz.getName());

        buildUI();
    }

    @FXML
    private void initialize() {
        buildUI();
    }

    private void buildUI() {
        setDisableSaveButton();
        buildQuestionList();
    }

    private void buildQuestionList() {
        questionListBox.getChildren().clear();

        for (QuestionCommand question: questions) {
            HBox questionRow = buildQuestionRow(question);
            questionListBox.getChildren().add(questionRow);
        }
    }

    private HBox buildQuestionRow(QuestionCommand question) {
        HBox questionRow = new HBox();

        Label questionLabel = new Label((questions.indexOf(question) + 1) + ". " + question.getName());

        Button editButton = new Button("Edytuj");
        Button removeButton = new Button("Usuń");

        editButton.setGraphic(new Icon(AwesomeIconEnum.PENCIL));
        removeButton.setGraphic(new Icon(AwesomeIconEnum.MINUS));

        editButton.getStyleClass().addAll("button", "edit-button");
        removeButton.getStyleClass().addAll("button", "remove-button");

        editButton.setOnAction(_ -> onEditQuestion(question));
        removeButton.setOnAction(_ -> onRemoveQuestion(question));

        HBox buttonsBox = new HBox(5);
        HBox.setHgrow(buttonsBox, Priority.ALWAYS);
        buttonsBox.setAlignment(Pos.TOP_RIGHT);
        buttonsBox.getStyleClass().add("action-buttons");
        buttonsBox.getChildren().addAll(editButton, removeButton);

        questionRow.getStyleClass().add("question-row");
        questionRow.getChildren().addAll(
                questionLabel,
                buttonsBox
        );

        return questionRow;
    }

    private void setDisableSaveButton() {
        saveQuizButton.setDisable(quiz.getName() == null || quiz.getName().isBlank() || questions.isEmpty());
    }

    @FXML
    private void onEditQuestion(QuestionCommand question) {
        AddEditQuestionDialog dialog = new AddEditQuestionDialog(question);
        Optional<QuestionCommand> questionResult = dialog.showAndWait();
        if (questionResult.isPresent())
            question = questionResult.get();

        setDisableSaveButton();
        buildQuestionList();
    }

    @FXML
    private void onRemoveQuestion(QuestionCommand question) {
        questions.remove(question);
        if(questions.isEmpty())
            setDisableSaveButton();
        buildQuestionList();
    }

    @FXML
    private void onCancel() {
        SceneLoader.loadSelectQuizScene();
    }

    @FXML
    private void onSaveQuiz() {
        quiz.setQuestions(questions);

        if (quiz.getId() == null) QuizHttpClient.postQuiz(quiz);
        else QuizHttpClient.putQuiz(quiz);

        SceneLoader.loadSelectQuizScene();
    }

    @FXML
    private void onAddQuestion() {
        AddEditQuestionDialog dialog = new AddEditQuestionDialog();
        Optional<QuestionCommand> questionResult = dialog.showAndWait();
        if(questionResult.isPresent()) {
            questions.add(questionResult.get());
            buildQuestionList();
            setDisableSaveButton();
        }
    }

    @FXML
    private void onSetQuizDetails() {
        SetQuizDetailsDialog dialog = new SetQuizDetailsDialog(quiz);
        Optional<QuizCommand> quizResult = dialog.showAndWait();

        quizResult.ifPresent(quizCommand -> quiz = quizCommand);
    }

    @FXML
    private  void onQuizNameTyped(KeyEvent event) {
        quiz.setName(((TextField)event.getSource()).getText());
        setDisableSaveButton();
    }
}
