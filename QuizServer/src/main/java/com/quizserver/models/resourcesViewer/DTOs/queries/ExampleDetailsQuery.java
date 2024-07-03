package com.quizserver.models.resourcesViewer.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExampleDetailsQuery extends ExampleQuery {
    private List<FileQuery> files;
    private UUID topicId;
}
