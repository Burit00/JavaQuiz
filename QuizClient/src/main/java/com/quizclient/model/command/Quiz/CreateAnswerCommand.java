package com.quizclient.model.command.Quiz;

public class CreateAnswerCommand {
    private String text;
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    private String publicId;
    public String getPublicId() {return publicId;}
    public void setPublicId(String publicId) {this.publicId = publicId;}

    public CreateAnswerCommand(String publicId, String text) {
        this.text = text;
        this.publicId = publicId;
    }

    public CreateAnswerCommand(CreateAnswerCommand answer) {
        this.text = answer.text;
        this.publicId = answer.publicId;
    }
}
