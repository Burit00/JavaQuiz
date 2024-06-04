package com.quizserver.models.DTOs.commands;

public class CreateAnswerCommand {

    private String text;
    public String getText() {return text;}

    private String publicId;
    public String getPublicId() {return publicId;}

    public CreateAnswerCommand() {}

    public CreateAnswerCommand(String publicId, String text) {
        this.publicId = publicId;
        this.text = text;
    }
}
