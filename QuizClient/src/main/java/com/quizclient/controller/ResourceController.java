package com.quizclient.controller;

import com.quizclient.api.ResourceHttpClient;
import com.quizclient.model.query.Resource.ExampleQuery;
import com.quizclient.model.query.Resource.TopicQuery;
import com.quizclient.utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ResourceController {
    private List<TopicQuery> topics;

    @FXML
    private Accordion topicsAccordion;

    @FXML
    public void initialize() {
        loadTopics();
        buildUI();
    }

    private void loadTopics() {
        topics = ResourceHttpClient.getTopics();
    }

    private void buildUI() {
        for (TopicQuery topic: topics)
            topicsAccordion.getPanes().add(buildTitledPane(topic));
    }

    private TitledPane buildTitledPane(TopicQuery topic) {
        TitledPane topicsTitledPane = new TitledPane(topic.getName(), null);
        topicsTitledPane.setContent(buildTitledPaneContent(topic.getExamples()));


        return topicsTitledPane;
    }

    private VBox buildTitledPaneContent(List<ExampleQuery> examples) {
        VBox contentBox = new VBox(10);

        for(ExampleQuery example: examples) {
            Label exampleNameLabel = new Label( String.format("%d. %s", examples.indexOf(example), example.getName()));
            exampleNameLabel.setUnderline(true);
            exampleNameLabel.setOnMouseClicked(_ -> SceneLoader.loadFilesViewer(example.getId()));
            HBox exampleBox = new HBox(exampleNameLabel);

            contentBox.getChildren().add(exampleBox);
        }

        return contentBox;
    }
}
