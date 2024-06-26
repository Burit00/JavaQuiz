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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


public class AddEditQuestionDialog extends Dialog<CreateQuestionCommand> {
    private final CreateQuestionCommand question;
    private AttachmentTypeEnum attachmentType;

    private TextField questionNameTextField;
    private TextArea codeTextArea;
    private String answerNameFromInput;
    private String codeFromTextArea;

    private Button confirmButton;

    private ToggleGroup questionTypeGroup;
    private ToggleGroup attachmentTypeGroup;
    private ToggleGroup answersGroup;
    private BorderPane contentPane;
    private VBox answerListBox;
    private VBox attachmentBox;


    private enum AttachmentTypeEnum {
        NONE,
        CODE,
    }

    public AddEditQuestionDialog() {
        this.setTitle("Dodaj pytanie");
        this.question = new CreateQuestionCommand();

        setupBuildUI();
    }

    public AddEditQuestionDialog(CreateQuestionCommand createQuestionCommand) {
        this.setTitle("Edytuj pytanie");
        this.question = (CreateQuestionCommand) createQuestionCommand.clone();
        if (question.getQuestionType() == QuestionTypeEnum.INPUT)
            answerNameFromInput = question.getCorrectAnswers().getFirst();

        setupBuildUI();
    }

    private void setupBuildUI() {
        if (question.getCode() != null) {
            attachmentType = AttachmentTypeEnum.CODE;
            codeFromTextArea = question.getCode();
        } else {
            attachmentType = AttachmentTypeEnum.NONE;
        }

        buildConfirmButton();
        buildUI();
    }

    private void buildConfirmButton() {
        getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        confirmButton = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
        confirmButton.setText("Zapisz");
        confirmButton.setGraphic(new Icon(AwesomeIconEnum.CHECK));
        confirmButton.setMinWidth(Double.MAX_VALUE);
        confirmButton.setOnAction(_ -> confirmDialog());
    }

    private void buildUI() {
        questionTypeGroup = new ToggleGroup();
        attachmentTypeGroup = new ToggleGroup();
        answersGroup = new ToggleGroup();
        contentPane = new BorderPane();
        answerListBox = new VBox();
        VBox centerBox = new VBox();

        setWidth(600);
        setHeight(800);

        getDialogPane().getStylesheets().addAll(
                QuizClientApplication.class.getResource("styles/global.css").toExternalForm(),
                QuizClientApplication.class.getResource("dialog/add-edit-question-dialog.css").toExternalForm()
        );

        centerBox.getChildren().addAll(
                buildQuestionNameBox(),
                buildQuestionTypeBox(),
                buildAttachmentTypeBox(),
                new Label("Odpowiedzi:"),
                buildAnswerListScrollBox(),
                buildAnswerNameInputBox()
        );

        centerBox.getStyleClass().add("scroll-wrapper");

        contentPane.setCenter(centerBox);
        buildAttachmentBox();

        getDialogPane().setContent(contentPane);
    }

    private VBox buildQuestionNameBox() {
        Label questionNameLabel = new Label("Wpisz pytanie: ");
        questionNameTextField = new TextField(question.getName());
        questionNameTextField.setPromptText("Nazwa pytania...");
        questionNameTextField.setOnKeyTyped(_ -> setDisableConfirmButton());
        questionNameTextField.getStyleClass().add("question-name-input");
        VBox questionNameBox = new VBox(questionNameLabel, questionNameTextField);
        questionNameBox.getStyleClass().add("question-name");

        return questionNameBox;
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

    private VBox buildQuestionTypeBox() {

        Label questionTypeLabel = new Label("Wybierz wariant odpowiedzi:");
        RadioButton radioQuestionTypeRadioButton = new RadioButton("Pojednczy wybór");
        RadioButton checkboxQuestionTypeRadioButton = new RadioButton("Wielokrotny wybór");
        RadioButton inputQuestionTypeRadioButton = new RadioButton("Pole tekstowe");

        radioQuestionTypeRadioButton.setOnAction(_ -> handleQuestionTypeChanged(QuestionTypeEnum.RADIO));
        checkboxQuestionTypeRadioButton.setOnAction(_ -> handleQuestionTypeChanged(QuestionTypeEnum.CHECKBOX));
        inputQuestionTypeRadioButton.setOnAction(_ -> handleQuestionTypeChanged(QuestionTypeEnum.INPUT));

        radioQuestionTypeRadioButton.setToggleGroup(questionTypeGroup);
        checkboxQuestionTypeRadioButton.setToggleGroup(questionTypeGroup);
        inputQuestionTypeRadioButton.setToggleGroup(questionTypeGroup);

        switch (question.getQuestionType()) {
            case RADIO -> radioQuestionTypeRadioButton.setSelected(true);
            case CHECKBOX -> checkboxQuestionTypeRadioButton.setSelected(true);
            case INPUT -> inputQuestionTypeRadioButton.setSelected(true);
        }

        HBox questionTypeRadioBox = new HBox(
                10,
                radioQuestionTypeRadioButton,
                checkboxQuestionTypeRadioButton,
                inputQuestionTypeRadioButton
        );

        VBox questionTypeBox = new VBox(
                questionTypeLabel,
                questionTypeRadioBox
        );

        questionTypeBox.getStyleClass().add("question-type");

        return questionTypeBox;
    }

    private VBox buildAttachmentTypeBox() {
        Label attachmentTypeLabel = new Label("Wybierz rodzaj dodatkowej treści:");
        RadioButton noneAttachmentRadioButton = new RadioButton("Brak dodatkowej treści");
        RadioButton codeAttachmentRadioButton = new RadioButton("Kod");

        noneAttachmentRadioButton.setOnAction(_ -> handleAttachmentTypeChanged(AttachmentTypeEnum.NONE));
        codeAttachmentRadioButton.setOnAction(_ -> handleAttachmentTypeChanged(AttachmentTypeEnum.CODE));

        noneAttachmentRadioButton.setToggleGroup(attachmentTypeGroup);
        codeAttachmentRadioButton.setToggleGroup(attachmentTypeGroup);

        switch (attachmentType) {
            case AttachmentTypeEnum.NONE -> noneAttachmentRadioButton.setSelected(true);
            case AttachmentTypeEnum.CODE -> codeAttachmentRadioButton.setSelected(true);
        }

        HBox attachmentTypeRadioBox = new HBox(
                10,
                noneAttachmentRadioButton,
                codeAttachmentRadioButton
        );

        VBox attachmentTypeBox = new VBox(
                attachmentTypeLabel,
                attachmentTypeRadioBox
        );

        attachmentTypeBox.getStyleClass().add("question-type");

        return attachmentTypeBox;
    }

    private void handleAttachmentTypeChanged(AttachmentTypeEnum attachmentType) {
        this.attachmentType = attachmentType;

        buildAttachmentBox();
        setDisableConfirmButton();
    }

    private void buildAttachmentBox() {
        if (attachmentType.equals(AttachmentTypeEnum.NONE)) {
            double attachmentWidth = attachmentBox != null ? attachmentBox.getWidth() : 0;
            setWidth(getWidth() - attachmentWidth);
            contentPane.setRight(null);

            return;
        }

        attachmentBox = new VBox(10);
        switch (attachmentType) {
            case CODE -> {
                Button importFileButton = new Button("Zaimportuj z pliku");
                codeTextArea = new TextArea(codeFromTextArea);

                VBox.setVgrow(codeTextArea, Priority.ALWAYS);
                codeTextArea.setFont(Font.font("Courier New"));
                codeTextArea.setOnKeyTyped(_ -> {
                    codeFromTextArea = codeTextArea.getText();
                    setDisableConfirmButton();
                });

                importFileButton.setMaxWidth(Double.MAX_VALUE);
                importFileButton.setOnAction(_ -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Wybierz plik");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Najlepszy język świata", "*.java")
                    );
                    File selectedFile = fileChooser.showOpenDialog(getOwner());
                    StringBuilder sb = readFile(selectedFile);

                    codeTextArea.setText(sb.toString());
                    codeFromTextArea = sb.toString();
                    setDisableConfirmButton();
                });

                attachmentBox.getChildren().addAll(
                        codeTextArea,
                        importFileButton
                );
            }
        }

        attachmentBox.setPrefWidth(490);
        attachmentBox.setPadding(new Insets(0, 0, 0, 10));
        contentPane.setRight(attachmentBox);
        setWidth(getWidth() + attachmentBox.getPrefWidth());
    }

    public StringBuilder readFile(File selectedFile) {
        StringBuilder fileContent = new StringBuilder(1024);
        try {
            FileReader fileReader = new FileReader(selectedFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String curLine;

            while ((curLine = bufferedReader.readLine()) != null)
                fileContent.append(curLine).append("\n");

        } catch (IOException _) {}
        return fileContent;
    }

    private void buildAnswerListBox() {
        setDisableConfirmButton();
        answerListBox.getChildren().clear();

        if (question.getQuestionType() == QuestionTypeEnum.INPUT) {
            if (answerNameFromInput != null && !answerNameFromInput.isBlank())
                buildAnswerRow(new CreateAnswerCommand(answerNameFromInput, answerNameFromInput));
            return;
        }

        for (CreateAnswerCommand answer : question.getAnswers())
            buildAnswerRow(answer);
    }

    private void buildAnswerRow(CreateAnswerCommand answer) {

        Node answerName = getAnswerNode(answer);
        HBox answerNameBox = new HBox(answerName);
        Button removeAnswerButton = new Button("Usuń");
        HBox answerRow = new HBox(answerNameBox);

        HBox.setHgrow(answerNameBox, Priority.ALWAYS);
        HBox.setMargin(answerNameBox, new Insets(0, 10, 0, 0));

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
        answerName.getStyleClass().add("answer-name");

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

        return new HBox(
                answerNameTextField,
                addAnswerButton
        );
    }

    private void handleQuestionTypeChanged(QuestionTypeEnum questionType) {
        question.setQuestionType(questionType);
        question.getCorrectAnswers().clear();

        if (question.getQuestionType() == QuestionTypeEnum.INPUT && answerNameFromInput != null)
            question.getCorrectAnswers().add(answerNameFromInput);

        if (question.getQuestionType() == QuestionTypeEnum.INPUT && answerNameFromInput == null && !question.getAnswers().isEmpty())
            answerNameFromInput = question.getAnswers().getFirst().getText();

        setDisableConfirmButton();
        buildAnswerListBox();
    }

    private void confirmDialog() {
        question.setName(questionNameTextField.getText());

        switch (attachmentType) {
            case NONE -> question.setCode(null);
            case CODE -> question.setCode(codeFromTextArea);
        }

        if (question.getQuestionType() == QuestionTypeEnum.INPUT) {
            question.getAnswers().clear();
            question.getCorrectAnswers().clear();
            question.getCorrectAnswers().add(answerNameFromInput);
        }

        setResult(question);
    }

    private void setDisableConfirmButton() {
        boolean hasNoName = questionNameTextField.getText() == null || questionNameTextField.getText().isBlank();
        boolean hasNoCorrectAnswers = question.getCorrectAnswers().isEmpty();
        boolean shouldHasCode = attachmentType.equals(AttachmentTypeEnum.CODE);
        boolean hasNoCode = codeFromTextArea == null || codeFromTextArea.isBlank();

        confirmButton.setDisable(
                hasNoName ||
                hasNoCorrectAnswers ||
                (shouldHasCode && hasNoCode)
        );
    }
}
