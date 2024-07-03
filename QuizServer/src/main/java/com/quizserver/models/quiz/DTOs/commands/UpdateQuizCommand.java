package com.quizserver.models.quiz.DTOs.commands;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateQuizCommand extends CreateQuizCommand {
    private UUID id;

    @Override
    public String toString() {
        return "{id = " + id + ", name=" + name + ", description=" + description + ", questions=" + questions + "}";
    }

}
