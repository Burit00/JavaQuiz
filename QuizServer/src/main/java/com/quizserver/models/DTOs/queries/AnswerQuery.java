package com.quizserver.models.DTOs.queries;

import java.util.UUID;

public class AnswerQuery {
    private UUID id;
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    private UUID questionId;
    public UUID getQuestionId() {return questionId;}
    public void setQuestionId(UUID questionId) {this.questionId = questionId;}
    private String text;
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    private String publicId;
    public String getPublicId() {return publicId;}
    public void setPublicId(String publicId) {this.publicId = publicId;}
}
