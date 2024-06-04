package com.quizclient.model.query;

import java.util.UUID;

public class QuizQuery {
    public QuizQuery(UUID id, String name, int time, String description) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.description = description;
    }

    public QuizQuery() {
    }

    private UUID id;

    public UUID getId() {
        return id;
    }

    private String name;

    public String getName() {
        return name;
    }

    private int time;

    public int getTime() {
        return time;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{name=" + name + ", time = " + time + ", description=" + description + "}";
    }
}
