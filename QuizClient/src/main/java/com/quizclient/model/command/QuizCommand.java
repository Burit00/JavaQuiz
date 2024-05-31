package com.quizclient.model.command;

import com.quizclient.model.query.QuizQuery;

import java.util.List;

public class QuizCommand {
    public QuizCommand(String id, String name, int time, String description) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.description = description;
    }

    public QuizCommand() {
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    private List<QuestionCommand> questions;

    public List<QuestionCommand> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCommand> questions) {
        this.questions = questions;
    }

    public static QuizCommand mapFromQuizQuery(QuizQuery quizQuery) {
        return new QuizCommand(quizQuery.getId(), quizQuery.getName(), quizQuery.getTime(), quizQuery.getDescription());
    }
}
