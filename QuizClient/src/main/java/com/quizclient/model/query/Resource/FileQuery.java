package com.quizclient.model.query.Resource;

import java.util.UUID;

public class FileQuery {
    private UUID id;
    private String name;
    private String content;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
