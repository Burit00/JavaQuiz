package com.quizserver.models.resourcesViewer.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TopicQuery {
    private UUID id;
    private String name;
    private List<ExampleQuery> examples;


    @Override
    public String toString() {
        return "Topic{id=" + id + ", name=" + name + ", examples = " + examples + "}";
    }
}
