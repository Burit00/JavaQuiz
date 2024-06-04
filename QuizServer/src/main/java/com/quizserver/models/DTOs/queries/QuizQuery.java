package com.quizserver.models.DTOs.queries;

import java.util.UUID;

public class QuizQuery {
    protected UUID id;
    protected String name;
    protected int time;
    protected String description;

    public QuizQuery() {}

    public QuizQuery(UUID id, String name, int time, String description) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", name = " + name + ", time = " + time + ", description = " + description + "}";
    }
}
