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

import java.util.*;
import java.util.function.Consumer;

public class CreatEditQuizController {
    public CreateQuizCommand quiz = new CreateQuizCommand();
    public List<CreateQuestionCommand> questions;

    @FXML
    private Button saveQuizButton;

    @FXML
    private Button previewButton;

    @FXML
    private VBox questionListBox;

    @FXML
    private TextField quizNameTextField;

    @FXML
    private void initialize() {
        setup();
    }

    public void setParameter(UUID quizId){
        quiz = QuizHttpClient.getQuizDetailsForUpdate(quizId);
        setup();
    }

    public void setParameter(CreateQuizCommand quiz){
        this.quiz = quiz;
        setup();
    }

    private void setup() {
        questions = quiz.getQuestions();
        buildUI();
    }

    private void buildUI() {
        quizNameTextField.setText(quiz.getName());
        buildQuestionList();
        setDisableSaveAndPreviewButton();
    }

    private void buildQuestionList() {
        questionListBox.getChildren().clear();

        for (CreateQuestionCommand question: questions) {
            HBox questionRow = buildQuestionRow(question);
            questionListBox.getChildren().add(questionRow);
        }
    }

    private record AnswerActionButtonProps(String text, AwesomeIconEnum icon, String buttonClass, Consumer<CreateQuestionCommand> function){}
    private final List<AnswerActionButtonProps> answerActionButtonProps = Arrays.asList(
            new AnswerActionButtonProps("Edytuj", AwesomeIconEnum.PENCIL, "edit-button", this::onEditQuestion),
            new AnswerActionButtonProps("Podgląd", AwesomeIconEnum.SEARCH, "preview-button", this::onPreviewQuestion),
            new AnswerActionButtonProps("Usuń", AwesomeIconEnum.MINUS, "remove-button", this::onRemoveQuestion)
    );

    private HBox buildQuestionRow(CreateQuestionCommand question) {
        HBox questionRow = new HBox();

        Label questionLabel = new Label((questions.indexOf(question) + 1) + ". " + question.getName());


        HBox buttonsBox = new HBox(5);
        HBox.setHgrow(buttonsBox, Priority.ALWAYS);
        buttonsBox.setAlignment(Pos.TOP_RIGHT);
        buttonsBox.getStyleClass().add("action-buttons");

        for (AnswerActionButtonProps buttonProps: answerActionButtonProps) {
            Button button = new Button(buttonProps.text);

            button.setGraphic(new Icon(buttonProps.icon));

            Consumer<CreateQuestionCommand> action = buttonProps.function();
            button.setOnAction(_ -> action.accept(question));

            button.getStyleClass().addAll("mini", "rounded", "reversed", "text-white", buttonProps.buttonClass);
            buttonsBox.getChildren().add(button);
        }

        questionRow.getStyleClass().add("question-row");
        questionRow.getChildren().addAll(
                questionLabel,
                buttonsBox
        );

        return questionRow;
    }

    private void setDisableSaveAndPreviewButton() {
        boolean isQuizReadyToSave = quiz.getName() == null || quiz.getName().isBlank() || questions.isEmpty();

        saveQuizButton.setDisable(isQuizReadyToSave);
        previewButton.setDisable(isQuizReadyToSave);
    }

    private void onEditQuestion(CreateQuestionCommand question) {
        AddEditQuestionDialog dialog = new AddEditQuestionDialog(question);
        Optional<CreateQuestionCommand> questionResult = dialog.showAndWait();
        questionResult.ifPresent(createQuestionCommand ->
                questions.set(questions.indexOf(question), createQuestionCommand));

        buildQuestionList();
    }

    private void onRemoveQuestion(CreateQuestionCommand question) {
        questions.remove(question);
        if(questions.isEmpty())
            setDisableSaveAndPreviewButton();
        buildQuestionList();
    }

    private void onPreviewQuestion(CreateQuestionCommand question) {
        SceneLoader.loadQuizPreviewScene(quiz, questions.indexOf(question));
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
            setDisableSaveAndPreviewButton();
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
        setDisableSaveAndPreviewButton();
    }

    @FXML
    private void onShowPreview() {
        SceneLoader.loadQuizPreviewScene(quiz);
    }
}
