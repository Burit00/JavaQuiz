package com.quizclient.model.command;


import java.util.ArrayList;
import java.util.List;

public class CreateQuizCommand {

    public CreateQuizCommand() {
    }

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected int time = 0;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected List<CreateQuestionCommand> questions = new ArrayList<>();

    public List<CreateQuestionCommand> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CreateQuestionCommand> questions) {
        this.questions = questions;
    }
}
