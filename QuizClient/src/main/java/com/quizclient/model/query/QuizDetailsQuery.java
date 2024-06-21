package com.quizclient.model.query;

public class QuizDetailsQuery extends QuizQuery {
    private int time;
    private String description;
    private int questionCount;

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
