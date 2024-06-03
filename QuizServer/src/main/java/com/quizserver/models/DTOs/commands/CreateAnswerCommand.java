package com.quizserver.models.DTOs.commands;

public class CreateAnswerCommand {

    private String text;
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    private String publicId;
    public String getPublicId() {return publicId;}
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public CreateAnswerCommand() {}

    public CreateAnswerCommand(String publicId, String text) {
        this.publicId = publicId;
        this.text = text;
    }
}
