package com.quizclient.controller;

import com.quizclient.api.ResourceHttpClient;
import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.model.query.Resource.ExampleDetailsQuery;
import com.quizclient.model.query.Resource.FileQuery;
import com.quizclient.ui.Icon;
import com.quizclient.utils.SceneLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.UUID;

public class FilesController {
    private ExampleDetailsQuery example;

    @FXML
    private Label exampleNameLabel;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private VBox filesContainer;

    @FXML
    private ScrollPane scrollPane;

    public void initialize() {
        scrollPane.setMaxWidth(Double.MAX_VALUE);
        scrollPane.setMaxHeight(Double.MAX_VALUE);
        mainAnchorPane.setMaxWidth(Double.MAX_VALUE);
        mainAnchorPane.setMaxHeight(Double.MAX_VALUE);
    }

    public void setParameter(UUID exampleId) {
        loadFiles(exampleId);
        buildUI();
    }

    private void loadFiles(UUID exampleId) {
        example = ResourceHttpClient.getExample(exampleId);
    }

    private void buildUI() {
        exampleNameLabel.setText(example.getName());

        int fileIndex = 0;
        for (FileQuery file: example.getFiles()) {
            if (fileIndex != 0) filesContainer.getChildren().add(new Separator());
            filesContainer.getChildren().add(buildFileBox(file));
            ++fileIndex;
        }
    }

    private VBox buildFileBox(FileQuery file) {

        Label fileNameLabel = new Label(file.getName());

        Button copyNameButton = new Button("Kopiuj nazwę");
        copyNameButton.setGraphic(new Icon(AwesomeIconEnum.COPY));
        copyNameButton.getStyleClass().addAll("small");
        copyNameButton.setOnAction(_ -> copyToClipboard(file.getName()));

        Button copyContentButton = new Button("Kopiuj zawartość");
        copyContentButton.setGraphic(new Icon(AwesomeIconEnum.COPY));
        copyContentButton.getStyleClass().addAll("small");
        copyContentButton.setOnAction(_ -> copyToClipboard(file.getContent()));

        HBox buttons = new HBox(5, copyNameButton, copyContentButton);

        AnchorPane.setRightAnchor(buttons, 0.0);
        AnchorPane.setBottomAnchor(buttons, 0.0);
        AnchorPane.setBottomAnchor(fileNameLabel, 0.0);
        AnchorPane fileNameAnchorPane = new AnchorPane(fileNameLabel, buttons);
        fileNameAnchorPane.getStyleClass().addAll("file-label");

        TextArea codeArea = new TextArea(file.getContent());
        codeArea.setEditable(false);
        codeArea.getStyleClass().add("code");


        return new VBox(10, fileNameAnchorPane, codeArea);
    }

    private void copyToClipboard(String text) {
        StringSelection stringToClipboard = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringToClipboard, null);
        showToast();
    }

    private void showToast() {
        Label toastLabel = new Label("Skopiowano!");
        toastLabel.getStyleClass().add("toast-text");
        HBox toastTextWrapper = new HBox(toastLabel);
        toastTextWrapper.setAlignment(Pos.CENTER);
        toastTextWrapper.getStyleClass().add("toast");
        HBox toastCenterWrapper = new HBox(toastTextWrapper);
        toastCenterWrapper.setAlignment(Pos.CENTER);
        toastCenterWrapper.getStyleClass().add("toast-wrapper");

        AnchorPane.setBottomAnchor(toastCenterWrapper, 0.0);
        AnchorPane.setLeftAnchor(toastCenterWrapper, 0.0);
        AnchorPane.setRightAnchor(toastCenterWrapper, 0.0);

        mainAnchorPane.getChildren().add(toastCenterWrapper);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2)));
        timeline.setOnFinished(_-> mainAnchorPane.getChildren().remove(toastCenterWrapper));
        timeline.play();
    }

    @FXML
    private void onBack() {
        SceneLoader.loadExamplesViewScene(example.getTopicId());
    }
}
