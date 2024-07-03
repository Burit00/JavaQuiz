package com.quizserver.models.resourcesViewer.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FileQuery {
    private UUID id;
    private String name;
    private String content;

    @Override
    public String toString() {
        return "Topic{id=" + id + ", name=" + name + ", content = " + content + "}";
    }
}
