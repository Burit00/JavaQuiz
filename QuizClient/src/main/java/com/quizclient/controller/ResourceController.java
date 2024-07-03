package com.quizclient.controller;

import com.quizclient.api.ResourceHttpClient;
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
    private VBox topicsContainer;

    @FXML
    public void initialize() {
        loadTopics();
        buildUI();
    }

    private void loadTopics() {
        topics = ResourceHttpClient.getTopics();
    }

    private void buildUI() {
        topicsContainer.setMaxWidth(Double.MAX_VALUE);
        for (TopicQuery topic: topics)
            topicsContainer.getChildren().add(buildTopicItem(topic));
    }

    private HBox buildTopicItem(TopicQuery topic) {
        HBox topicItem = new HBox();
        Label topicNameLabel = new Label(topic.getName());

        topicItem.getChildren().add(topicNameLabel);
        topicItem.setMaxWidth(Double.MAX_VALUE);
        topicItem.setOnMouseClicked(_ -> SceneLoader.loadExamplesViewScene(topic.getId()));
        topicNameLabel.getStyleClass().add("item");

        return topicItem;
    }
}
