package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.dialog.AddEditQuestionDialog;
import com.quizclient.dialog.SetQuizDetailsDialog;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.model.command.CreateQuestionCommand;
import com.quizclient.model.command.CreateQuizCommand;
import com.quizclient.model.command.UpdateQuizCommand;
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
import java.util.UUID;

public class CreatEditQuizController {
    public CreateQuizCommand quiz = new CreateQuizCommand();
    public List<CreateQuestionCommand> questions = new ArrayList<>();

    @FXML
    private Button saveQuizButton;

    @FXML
    private VBox questionListBox;

    @FXML
    private TextField quizNameTextField;

    public void setParameter(UUID quizId){
        quiz = QuizHttpClient.getQuizDetailsForUpdate(quizId);
        quizNameTextField.setText(quiz.getName());
        questions = quiz.getQuestions();
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

        for (CreateQuestionCommand question: questions) {
            HBox questionRow = buildQuestionRow(question);
            questionListBox.getChildren().add(questionRow);
        }
    }

    private HBox buildQuestionRow(CreateQuestionCommand question) {
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
    private void onEditQuestion(CreateQuestionCommand question) {
        AddEditQuestionDialog dialog = new AddEditQuestionDialog(question);
        Optional<CreateQuestionCommand> questionResult = dialog.showAndWait();
        questionResult.ifPresent(createQuestionCommand ->
                questions.set(questions.indexOf(question), createQuestionCommand));

        setDisableSaveButton();
        buildQuestionList();
    }

    @FXML
    private void onRemoveQuestion(CreateQuestionCommand question) {
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

        if (quiz instanceof UpdateQuizCommand) QuizHttpClient.putQuiz(((UpdateQuizCommand)quiz));
        else QuizHttpClient.postQuiz(quiz);

        SceneLoader.loadSelectQuizScene();
    }

    @FXML
    private void onAddQuestion() {
        AddEditQuestionDialog dialog = new AddEditQuestionDialog();
        Optional<CreateQuestionCommand> questionResult = dialog.showAndWait();
        if(questionResult.isPresent()) {
            questions.add(questionResult.get());
            buildQuestionList();
            setDisableSaveButton();
        }
    }

    @FXML
    private void onSetQuizDetails() {
        SetQuizDetailsDialog dialog = new SetQuizDetailsDialog(quiz);
        Optional<CreateQuizCommand> quizResult = dialog.showAndWait();

        quizResult.ifPresent(quizCommand -> {
            quiz.setDescription(quizCommand.getDescription());
            quiz.setTime(quizCommand.getTime());
        });
    }

    @FXML
    private  void onQuizNameTyped(KeyEvent event) {
        quiz.setName(((TextField)event.getSource()).getText());
        setDisableSaveButton();
    }
}
