package com.quizclient.model.query.Resource;

import java.util.List;
import java.util.UUID;

public class ExampleDetailsQuery extends ExampleQuery {
    private List<FileQuery> files;
    private UUID topicId;

    public List<FileQuery> getFiles() {
        return files;
    }

    public UUID getTopicId() {
        return topicId;
    }
}
