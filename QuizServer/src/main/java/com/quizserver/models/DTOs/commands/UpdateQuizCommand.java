package com.quizserver.models.DTOs.commands;

import java.util.UUID;

public class UpdateQuizCommand extends CreateQuizCommand {
    private UUID id;

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", name=" + name + ", description=" + description + ", questions=" + questions + "}";
    }

}
