package com.quizclient.model.query;

import java.util.UUID;

public class QuizDetailsQuery extends QuizQuery {
    private int time;
    private String description;
    private int questionCount;

    public QuizDetailsQuery(){}
    public QuizDetailsQuery(UUID id, String name, int time, String description, int questionCount) {
        super(id, name);
        this.time = time;
        this.description = description;
        this.questionCount = questionCount;
    }

    public int getTime() {
        return time;
    }
    public String getDescription() {
        return description;
    }
    public int getQuestionCount() {
        return questionCount;
    }

    @Override
    public String toString() {
        return "{id" + id + ", name=" + name + ", time = " + time + ", description=" + description + ", questionCount=" + questionCount + "}";
    }
}
