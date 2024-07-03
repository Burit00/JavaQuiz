package com.quizserver.models.quiz.DTOs.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CreateQuizCommand {

    protected String name;
    protected int time = 0;
    protected String description;
    protected List<CreateQuestionCommand> questions = new ArrayList<>();

    public CreateQuizCommand() {}

    @Override
    public String toString() {
        return "{name=" + name + ", description=" + description + ", questions=" + questions + "}";
    }
}
