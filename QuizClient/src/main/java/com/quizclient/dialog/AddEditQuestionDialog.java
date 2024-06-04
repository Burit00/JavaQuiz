package com.quizclient.dialog;

import com.quizclient.QuizClientApplication;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.enums.QuestionTypeEnum;
import com.quizclient.model.command.CreateAnswerCommand;
import com.quizclient.model.command.CreateQuestionCommand;
import com.quizclient.ui.Icon;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.UUID;


public class AddEditQuestionDialog extends Dialog<CreateQuestionCommand> {
    private final CreateQuestionCommand question;

    private TextField questionNameTextField;
    private String answerNameFromInput;
    private Button confirmButton;

    private final ToggleGroup questionTypeGroup = new ToggleGroup();
    private final ToggleGroup answersGroup = new ToggleGroup();
    private final VBox answerListBox = new VBox();


    public AddEditQuestionDialog() {
        this.setTitle("Dodaj pytanie");
        this.question = new CreateQuestionCommand();
        buildUI();
    }

    public AddEditQuestionDialog(CreateQuestionCommand createQuestionCommand) {
        this.setTitle("Edytuj pytanie");
        this.question = (CreateQuestionCommand) createQuestionCommand.clone();
        if(question.getQuestionType() == QuestionTypeEnum.INPUT)
            answerNameFromInput = question.getCorrectAnswers().getFirst();

        buildUI();
    }

    private void buildUI() {
        getDialogPane().setPrefSize(500, 500);
        getDialogPane().getStylesheets().addAll(
                QuizClientApplication.class.getResource("styles/global.css").toExternalForm(),
                QuizClientApplication.class.getResource("dialog/add-edit-question-dialog.css").toExternalForm()
        );

        buildConfirmButton();

        VBox topBox = new VBox(
                buildQuestionNameBox(),
                buildQuestionTypeBox()
        );

        VBox centerBox = new VBox(
                new Label("Odpowiedzi:"),
                buildAnswerListScrollBox()
        );
        centerBox.getStyleClass().add("scroll-wrapper");

        BorderPane contentPane = new BorderPane();
        contentPane.setTop(topBox);
        contentPane.setCenter(centerBox);
        contentPane.setBottom(buildAnswerNameInputBox());

        getDialogPane().setContent(contentPane);
    }

    private void buildConfirmButton() {
        getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        confirmButton = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
        confirmButton.setText("Zapisz");
        confirmButton.setGraphic(new Icon(AwesomeIconEnum.CHECK));
        confirmButton.setMinWidth(Double.MAX_VALUE);
        confirmButton.setOnAction(_ -> confirmDialog());
    }

    private ScrollPane buildAnswerListScrollBox() {
        answerListBox.getStyleClass().add("answer-list");
        buildAnswerListBox();

        ScrollPane answersScrollPane = new ScrollPane();
        answersScrollPane.setContent(answerListBox);
        answersScrollPane.getStyleClass().add("scroll");
        VBox.setVgrow(answersScrollPane, Priority.ALWAYS);

        return answersScrollPane;
    }

    private VBox buildQuestionNameBox() {
        Label questionNameLabel = new Label("Wpisz pytanie: ");
        questionNameTextField = new TextField(question.getName());
        questionNameTextField.setPromptText("Nazwa pytania...");
        questionNameTextField.setOnKeyTyped(_ -> setDisableConfirmButton());
        VBox questionNameBox = new VBox(questionNameLabel, questionNameTextField);
        questionNameBox.getStyleClass().add("question-name");

        return questionNameBox;
    }

    private VBox buildQuestionTypeBox() {
        Label questionTypeLabel = new Label("Wybierz wariant odpowiedzi");
        RadioButton radioQuestionTypeRadioButton = new RadioButton("Pojednczy wybór");
        RadioButton checkboxQuestionTypeRadioButton = new RadioButton("Wielokrotny wybór");
        RadioButton inputQuestionTypeRadioButton = new RadioButton("Pole tekstowe");

        radioQuestionTypeRadioButton.setUserData(QuestionTypeEnum.RADIO);
        checkboxQuestionTypeRadioButton.setUserData(QuestionTypeEnum.CHECKBOX);
        inputQuestionTypeRadioButton.setUserData(QuestionTypeEnum.INPUT);

        radioQuestionTypeRadioButton.setOnAction(_ -> handleQuestionTypeChanged(QuestionTypeEnum.RADIO));
        checkboxQuestionTypeRadioButton.setOnAction(_ -> handleQuestionTypeChanged(QuestionTypeEnum.CHECKBOX));
        inputQuestionTypeRadioButton.setOnAction(_ -> handleQuestionTypeChanged(QuestionTypeEnum.INPUT));

        questionTypeGroup.getToggles().addAll(
                radioQuestionTypeRadioButton,
                checkboxQuestionTypeRadioButton,
                inputQuestionTypeRadioButton
        );

        switch (question.getQuestionType()) {
            case RADIO -> radioQuestionTypeRadioButton.setSelected(true);
            case CHECKBOX -> checkboxQuestionTypeRadioButton.setSelected(true);
            case INPUT -> inputQuestionTypeRadioButton.setSelected(true);
        }

        VBox questionTypeBox = new VBox(
                questionTypeLabel,
                radioQuestionTypeRadioButton,
                checkboxQuestionTypeRadioButton,
                inputQuestionTypeRadioButton
        );

        questionTypeBox.getStyleClass().add("question-type");

        return questionTypeBox;
    }

    private void buildAnswerListBox() {
        setDisableConfirmButton();
        answerListBox.getChildren().clear();

        if (question.getQuestionType() == QuestionTypeEnum.INPUT) {
            if (answerNameFromInput != null && !answerNameFromInput.isBlank())
                buildAnswerRow(new CreateAnswerCommand(UUID.randomUUID().toString(), answerNameFromInput));
            return;
        }

        for (CreateAnswerCommand answer : question.getAnswers())
            buildAnswerRow(answer);

    }

    private void buildAnswerRow(CreateAnswerCommand answer) {
        setDisableConfirmButton();
        Node answerName = getAnswerNode(answer);
        answerName.getStyleClass().add("answer-name");

        HBox answerNameBox = new HBox(answerName);
        HBox.setHgrow(answerNameBox, Priority.ALWAYS);
        HBox.setMargin(answerNameBox, new Insets(0, 10, 0, 0));

        HBox answerRow = new HBox(answerNameBox);

        Button removeAnswerButton = new Button("Usuń");
        removeAnswerButton.setGraphic(new Icon(AwesomeIconEnum.MINUS));
        removeAnswerButton.getStyleClass().add("remove-answer-button");
        removeAnswerButton.setOnAction(_ -> {
            if (question.getQuestionType() == QuestionTypeEnum.INPUT) {
                question.getCorrectAnswers().remove(answerNameFromInput);
                answerNameFromInput = null;
            }

            question.getAnswers().remove(answer);
            buildAnswerListBox();
        });
        answerRow.getChildren().add(removeAnswerButton);
        answerRow.getStyleClass().add("answer-row");

        answerListBox.getChildren().add(answerRow);
    }

    private Node getAnswerNode(CreateAnswerCommand answer) {
        Node answerName = null;

        switch (question.getQuestionType()) {
            case RADIO -> {
                answerName = new RadioButton(answer.getText());
                answerName.setUserData(answer.getPublicId());
                ((RadioButton) answerName).setSelected(question.getCorrectAnswers().contains(answer.getPublicId()));
                answersGroup.getToggles().add((RadioButton) answerName);

                ((RadioButton) answerName).setOnAction(event -> {
                    if (((RadioButton) event.getSource()).isSelected()) {
                        List<String> correctAnswers = question.getCorrectAnswers();
                        correctAnswers.clear();
                        correctAnswers.add(answer.getPublicId());
                        setDisableConfirmButton();
                    }
                });
            }
            case CHECKBOX -> {
                answerName = new CheckBox(answer.getText());
                answerName.setUserData(answer.getPublicId());
                ((CheckBox) answerName).setSelected(question.getCorrectAnswers().contains(answer.getPublicId()));
                ((CheckBox) answerName).setOnAction(event -> {
                    if (((CheckBox) event.getSource()).isSelected()) {
                        question.getCorrectAnswers().add(answer.getPublicId());
                    } else {
                        question.getCorrectAnswers().remove(answer.getPublicId());
                    }
                    setDisableConfirmButton();
                });
            }
            case INPUT -> {
                answerName = new Label(answerNameFromInput);
                question.getCorrectAnswers().clear();
                question.getCorrectAnswers().add(answerNameFromInput);
                setDisableConfirmButton();
            }
        }

        return answerName;
    }

    private HBox buildAnswerNameInputBox() {
        TextField answerNameTextField = new TextField();
        answerNameTextField.getStyleClass().add("answer-name-input");
        answerNameTextField.setPromptText("Nazwa odpowiedzi...");
        HBox.setHgrow(answerNameTextField, Priority.ALWAYS);
        HBox.setMargin(answerNameTextField, new Insets(0, 10, 0, 0));

        Button addAnswerButton = new Button();
        addAnswerButton.setDisable(true);
        addAnswerButton.setGraphic(new Icon(AwesomeIconEnum.PLUS));
        addAnswerButton.getStyleClass().add("add-answer-button");
        addAnswerButton.setOnAction(_ -> {
            if (question.getQuestionType() == QuestionTypeEnum.INPUT)
                answerNameFromInput = answerNameTextField.getText();
            else
                question.getAnswers().add(new CreateAnswerCommand(UUID.randomUUID().toString(), answerNameTextField.getText()));

            answerNameTextField.setText(null);
            addAnswerButton.setDisable(true);
            buildAnswerListBox();
        });

        answerNameTextField.setOnKeyTyped(event -> {
            String newAnswerName = ((TextField) event.getSource()).getText();
            addAnswerButton.setDisable(newAnswerName == null || newAnswerName.isBlank());
        });

        HBox answerInputBox = new HBox(
                answerNameTextField,
                addAnswerButton
        );

        return answerInputBox;
    }

    private void handleQuestionTypeChanged(QuestionTypeEnum questionType) {
        question.setQuestionType(questionType);
        question.getCorrectAnswers().clear();

        if(question.getQuestionType() == QuestionTypeEnum.INPUT && answerNameFromInput != null)
            question.getCorrectAnswers().add(answerNameFromInput);

        if (question.getQuestionType() == QuestionTypeEnum.INPUT && answerNameFromInput == null && !question.getAnswers().isEmpty())
            answerNameFromInput = question.getAnswers().getFirst().getText();

        setDisableConfirmButton();
        buildAnswerListBox();
    }

    private void confirmDialog() {
        question.setName(questionNameTextField.getText());

        if(question.getQuestionType() == QuestionTypeEnum.INPUT) {
            question.getAnswers().clear();
            question.getCorrectAnswers().clear();
            question.getCorrectAnswers().add(answerNameFromInput);
        }


        setResult(question);
    }

    private void setDisableConfirmButton() {
        confirmButton.setDisable(
                question.getCorrectAnswers().isEmpty() ||
                questionNameTextField.getText() == null ||
                questionNameTextField.getText().isBlank()
        );
    }
}
