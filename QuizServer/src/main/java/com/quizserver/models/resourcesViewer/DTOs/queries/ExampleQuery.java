package com.quizserver.models.resourcesViewer.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExampleQuery {
    protected UUID id;
    protected String name;


    @Override
    public String toString() {
        return "Example{id=" + id + ", name=" + name + "}";
    }
}
