package com.quizserver.models.quiz.DTOs.commands;

import lombok.Getter;

@Getter
public class CreateAnswerCommand {
    private String text;
    private String publicId;

    public CreateAnswerCommand() {}

    public CreateAnswerCommand(String publicId, String text) {
        this.publicId = publicId;
        this.text = text;
    }
}
