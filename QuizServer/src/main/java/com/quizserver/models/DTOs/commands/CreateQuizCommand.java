package com.quizserver.models.DTOs.commands;

import java.util.List;
import java.util.UUID;

public class CreateQuizCommand {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int time = 0;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private List<CreateQuestionCommand> questions;

    public List<CreateQuestionCommand> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CreateQuestionCommand> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "{name=" + name + ", description=" + description + ", questions=" + questions + "}";
    }
}
