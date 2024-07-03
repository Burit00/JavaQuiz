package com.quizclient.model.query.Quiz;

import java.util.UUID;

public class QuizQuery {
    protected UUID id;
    protected String name;

    public QuizQuery() {
    }

    public QuizQuery(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "{id" + id + ", name=" + name + "}";
    }
}
