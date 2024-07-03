package com.quizclient.model.command.Quiz;

import java.util.UUID;

public class UpdateQuizCommand extends CreateQuizCommand {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
