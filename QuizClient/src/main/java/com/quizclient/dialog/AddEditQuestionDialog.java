package com.quizclient.dialog;

import com.quizclient.QuizClientApplication;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.enums.QuestionTypeEnum;
import com.quizclient.model.command.AnswerCommand;
import com.quizclient.model.command.QuestionCommand;
import com.quizclient.ui.Icon;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.UUID;


public class AddEditQuestionDialog extends Dialog<QuestionCommand> {
    private final QuestionCommand question;

    private TextField questionNameTextField;
    private String answerNameFromInput;
    private final ToggleGroup questionTypeGroup = new ToggleGroup();
    private final ToggleGroup answersGroup = new ToggleGroup();
    private VBox answerListBox = new VBox();
    private Button confirmButton;


    public AddEditQuestionDialog() {
        this.question = new QuestionCommand();
        buildUI();
    }

    public AddEditQuestionDialog(QuestionCommand questionCommand) {
        this.question = (QuestionCommand) questionCommand.clone();
        buildUI();
    }

    private void buildUI() {
        getDialogPane().setMinHeight(500);
        getDialogPane().setMinWidth(500);

        getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        confirmButton = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
        confirmButton.setText("Zapisz");
        confirmButton.setGraphic(new Icon(AwesomeIconEnum.CHECK));
        confirmButton.setMinWidth(Double.MAX_VALUE);
        confirmButton.setOnAction(_ -> confirmDialog());

        answerListBox = new VBox();
        answerListBox.getStyleClass().add("answer-list");

        BorderPane borderPane = new BorderPane();
        VBox questionNameBox = buildQuestionName();
        VBox questionTypeBox = buildQuestionTypeRadio();
        HBox answerInputBox = buildAnswerInputBox();

        VBox topBox = new VBox(
                questionNameBox,
                questionTypeBox
        );

        buildAnswerListBox();
        ScrollPane answersScrollPane = new ScrollPane();
        answersScrollPane.setContent(answerListBox);
        answersScrollPane.getStyleClass().add("scroll");
        VBox.setVgrow(answersScrollPane, Priority.ALWAYS);

        VBox centerBox = new VBox(
                new Label("Odpowiedzi:"),
                answersScrollPane
        );
        centerBox.getStyleClass().add("scroll-wrapper");

        borderPane.setTop(topBox);
        borderPane.setCenter(centerBox);
        borderPane.setBottom(answerInputBox);

        getDialogPane().setContent(borderPane);

        getDialogPane().getStylesheets().addAll(
                QuizClientApplication.class.getResource("styles/global.css").toExternalForm(),
                QuizClientApplication.class.getResource("dialog/add-edit-question-dialog.css").toExternalForm()
        );
    }

    private VBox buildQuestionName() {
        Label questionNameLabel = new Label("Wpisz pytanie: ");
        questionNameTextField = new TextField(question.getName());
        questionNameTextField.setPromptText("Nazwa pytania...");
        VBox questionNameBox = new VBox(questionNameLabel, questionNameTextField);
        questionNameBox.getStyleClass().add("question-name");

        return questionNameBox;
    }

    private VBox buildQuestionTypeRadio() {
        Label questionTypeLabel = new Label("Wybierz wariant odpowiedzi");
        RadioButton radioQuestionTypeRadioButton = new RadioButton("Pojednczy wybór");
        RadioButton checkboxQuestionTypeRadioButton = new RadioButton("Wielokrotny wybór");
        RadioButton inputQuestionTypeRadioButton = new RadioButton("Pole tekstowe");

        radioQuestionTypeRadioButton.setUserData(QuestionTypeEnum.RADIO);
        checkboxQuestionTypeRadioButton.setUserData(QuestionTypeEnum.CHECKBOX);
        inputQuestionTypeRadioButton.setUserData(QuestionTypeEnum.INPUT);

        radioQuestionTypeRadioButton.setOnAction(this::handleQuestionType);
        checkboxQuestionTypeRadioButton.setOnAction(this::handleQuestionType);
        inputQuestionTypeRadioButton.setOnAction(this::handleQuestionType);

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
        confirmButton.setDisable(question.getCorrectAnswers().isEmpty());
        answerListBox.getChildren().clear();

        if (question.getQuestionType() == QuestionTypeEnum.INPUT) {
            if (answerNameFromInput == null && !question.getAnswers().isEmpty())
                buildAnswerRow(question.getAnswers().getFirst());
            else if (answerNameFromInput != null)
                buildAnswerRow(new AnswerCommand(UUID.randomUUID().toString(), question.getId(), answerNameFromInput));
            return;
        }

        for (AnswerCommand answer : question.getAnswers())
            buildAnswerRow(answer);

    }

    private void buildAnswerRow(AnswerCommand answer) {
        confirmButton.setDisable(question.getCorrectAnswers().isEmpty());
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

    private Node getAnswerNode(AnswerCommand answer) {
        Node answerName = null;

        switch (question.getQuestionType()) {
            case RADIO -> {
                answerName = new RadioButton(answer.getText());
                answerName.setUserData(answer.getId());
                answersGroup.getToggles().add((RadioButton) answerName);
                ((RadioButton) answerName).setOnAction(event -> {
                    if (((RadioButton) event.getSource()).isSelected()) {
                        List<String> correctAnswers = question.getCorrectAnswers();
                        correctAnswers.clear();
                        correctAnswers.add(answer.getId());
                        confirmButton.setDisable(false);
                    }
                });
            }
            case CHECKBOX -> {
                answerName = new CheckBox(answer.getText());
                answerName.setUserData(answer.getId());
                ((CheckBox) answerName).setOnAction(event -> {
                    if (((CheckBox) event.getSource()).isSelected()) {
                        question.getCorrectAnswers().add(answer.getId());
                    } else {
                        question.getCorrectAnswers().remove(answer.getId());
                    }
                    confirmButton.setDisable(question.getCorrectAnswers().isEmpty());
                });
            }
            case INPUT -> {
                answerName = new Label(answerNameFromInput);
                question.getCorrectAnswers().clear();
                question.getCorrectAnswers().add(answerNameFromInput);
                confirmButton.setDisable(false);
            }
        }
        return answerName;
    }

    private HBox buildAnswerInputBox() {
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
                question.getAnswers().add(new AnswerCommand(UUID.randomUUID().toString(), question.getId(), answerNameTextField.getText()));

            answerNameTextField.setText(null);
            addAnswerButton.setDisable(true);
            buildAnswerListBox();
        });

        answerNameTextField.setOnKeyTyped(event -> {
            String newAnswerName = ((TextField) event.getSource()).getText();
            addAnswerButton.setDisable(newAnswerName == null || newAnswerName.isEmpty());
        });

        HBox answerInputBox = new HBox(
                answerNameTextField,
                addAnswerButton
        );

        return answerInputBox;
    }

    private void handleQuestionType(ActionEvent event) {
        RadioButton questionTypeRadio = (RadioButton) event.getSource();
        question.setQuestionType((QuestionTypeEnum) questionTypeRadio.getUserData());

        question.getCorrectAnswers().clear();
        confirmButton.setDisable(true);


        if(answerNameFromInput != null && !answerNameFromInput.isBlank())
            question.getCorrectAnswers().add(answerNameFromInput);

        if (question.getQuestionType() == QuestionTypeEnum.INPUT && answerNameFromInput == null && !question.getAnswers().isEmpty())
            answerNameFromInput = question.getAnswers().getFirst().getText();

        buildAnswerListBox();
    }

    private void confirmDialog() {
        QuestionTypeEnum questionType = QuestionTypeEnum.valueOf(questionTypeGroup.getSelectedToggle().getUserData().toString());

        question.setName(questionNameTextField.getText());
        question.setQuestionType(questionType);

        switch (questionType) {
            case RADIO -> {
                RadioButton selectedRadioButton = (RadioButton) answersGroup.getSelectedToggle();
                question.getCorrectAnswers().clear();
                question.getCorrectAnswers().add(selectedRadioButton.getUserData().toString());
            }
            case CHECKBOX -> {
                FilteredList<Node> selectedCheckBoxes = answerListBox.getChildren().filtered(node -> ((CheckBox) node).isSelected());
                List<String> correctAnswers = selectedCheckBoxes.stream().map(checkBox -> checkBox.getUserData().toString()).toList();
                question.setCorrectAnswers(correctAnswers);
            }
            case INPUT -> {
                question.getCorrectAnswers().clear();
                question.getCorrectAnswers().add(answerNameFromInput);
                question.getAnswers().clear();
            }
        }

        setResult(question);
    }
}
