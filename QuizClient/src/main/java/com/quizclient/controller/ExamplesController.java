package com.quizclient.controller;

import com.quizclient.api.ResourceHttpClient;
import com.quizclient.model.query.Resource.ExampleQuery;
import com.quizclient.model.query.Resource.TopicQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class ExamplesController {
    private TopicQuery topic;

    @FXML
    private VBox container;
    @FXML
    private Label topicNameLabel;
    @FXML
    private VBox examplesContainer;
    @FXML
    private ScrollPane scroll;

    public void setParameter(UUID topicId) {
        loadExamples(topicId);
        buildUI();
    }

    private void loadExamples(UUID topicId) {
        topic = ResourceHttpClient.getTopic(topicId);
    }

    private void buildUI() {
        topicNameLabel.setText(topic.getName());
        examplesContainer.setMaxWidth(Double.MAX_VALUE);
        scroll.setMaxWidth(Double.MAX_VALUE);
        container.setMaxWidth(Double.MAX_VALUE);
        for (ExampleQuery example: topic.getExamples())
            examplesContainer.getChildren().add(buildExampleItem(example));
    }

    private HBox buildExampleItem(ExampleQuery topic) {
        HBox exampleItem = new HBox();
        Label exampleNameLabel = new Label(topic.getName());

        exampleItem.getChildren().add(exampleNameLabel);
        exampleItem.setMaxWidth(Double.MAX_VALUE);
        exampleItem.setOnMouseClicked(_ -> SceneLoader.loadFilesViewer(topic.getId()));
        exampleNameLabel.getStyleClass().add("item");

        return exampleItem;
    }

    @FXML
    private void onBack() {
        SceneLoader.loadResourceViewScene();
    }
}
