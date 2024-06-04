package com.quizserver.models.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID questionId;
    private String publicId;
    private String text;

    public UUID getId() {
        return id;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getPublicId() {return publicId;}
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public Answer() {}

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", questionId=" + questionId +
                ", publicId='" + publicId + '\'' +
                '}';
    }
}
