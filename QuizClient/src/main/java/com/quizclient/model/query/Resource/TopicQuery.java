package com.quizclient.model.query.Resource;

import java.util.List;
import java.util.UUID;

public class TopicQuery {
    private UUID id;
    private String name;
    private List<ExampleQuery> examples;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ExampleQuery> getExamples() {
        return examples;
    }
}
