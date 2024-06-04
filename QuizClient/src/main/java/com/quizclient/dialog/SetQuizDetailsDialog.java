package com.quizclient.dialog;

import com.google.gson.Gson;
import com.quizclient.QuizClientApplication;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.model.command.CreateQuizCommand;
import com.quizclient.ui.Icon;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SetQuizDetailsDialog extends Dialog<CreateQuizCommand> {
    private final Label timeLabel = new Label();
    private final CreateQuizCommand quiz;

    public SetQuizDetailsDialog(CreateQuizCommand quiz) {
        Gson gson = new Gson();
        this.quiz = gson.fromJson(gson.toJson(quiz), CreateQuizCommand.class);
        buildUI();
    }

    private void buildUI() {
        this.setTitle("Edytuj szczegóły quizu");
        getDialogPane().setPrefSize(500, 500);
        getDialogPane().getStylesheets().addAll(
                QuizClientApplication.class.getResource("dialog/set-quiz-details-dialog.css").toExternalForm(),
                QuizClientApplication.class.getResource("styles/global.css").toExternalForm()
        );

        buildConfirmButton();
        setTimeLabel();

        VBox contentBox = new VBox(10);

        Slider timeSlider = buildTimeSlider();


        contentBox.getChildren().addAll(
                timeLabel,
                timeSlider,
                new Label("Opis quizu"),
                buildDescriptionTextArea()
        );

        getDialogPane().setContent(contentBox);
    }

    private Slider buildTimeSlider() {
        Slider timeSlider = new Slider(0, 120, quiz.getTime());
        timeSlider.setShowTickLabels(true);
        timeSlider.setOnMouseDragged(event -> {
            int timeValue = (int)((Slider)event.getSource()).getValue();
            quiz.setTime(timeValue);
            setTimeLabel();
        });
        timeSlider.setOnRotate(event -> {
            int timeValue = (int)((Slider)event.getSource()).getValue();
            quiz.setTime(timeValue);
            setTimeLabel();
        });
        return timeSlider;
    }

    private void buildConfirmButton() {
        getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        Button confirmButton = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
        confirmButton.setText("Zatwierdź");
        confirmButton.setGraphic(new Icon(AwesomeIconEnum.CHECK));
        confirmButton.setOnAction(_ -> setResult(quiz));
    }

    private TextArea buildDescriptionTextArea() {
        TextArea textArea = new TextArea(quiz.getDescription());
        textArea.setWrapText(true);

        textArea.setOnKeyTyped(_ -> quiz.setDescription(textArea.getText()));

        VBox.setVgrow(textArea, Priority.ALWAYS);

        return textArea;
    }


    private void setTimeLabel() {
        String timeForQuiz = "Czas na rozwiązanie quizu: ";
        if (quiz.getTime() > 0)
            timeLabel.setText(timeForQuiz + quiz.getTime() + "min");
        else
            timeLabel.setText(timeForQuiz + "Brak limitu czasowego");
    }
}
