package com.quizserver.models.resourcesViewer.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExampleDetailsQuery extends ExampleQuery {
    private List<FileQuery> files;
}
