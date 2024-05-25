package com.quizclient.model.query;

public class QuizQuery {
    public QuizQuery(String id, String name, int time, String description) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.description = description;
    }

    public QuizQuery() {
    }

    private String id;

    public String getId() {
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
}
