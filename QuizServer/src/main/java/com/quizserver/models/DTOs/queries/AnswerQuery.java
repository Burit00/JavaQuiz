package com.quizserver.models.DTOs.queries;

import java.util.UUID;

public class AnswerQuery {
    protected UUID id;
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    protected UUID questionId;
    public UUID getQuestionId() {return questionId;}
    public void setQuestionId(UUID questionId) {this.questionId = questionId;}
    protected String text;
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    protected String publicId;
    public String getPublicId() {return publicId;}
    public void setPublicId(String publicId) {this.publicId = publicId;}
}
