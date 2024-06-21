package com.quizserver.models.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QuizQuery {
    protected UUID id;
    protected String name;

    @Override
    public String toString() {
        return "{id = " + id + ", name = " + name + "}";
    }
}
