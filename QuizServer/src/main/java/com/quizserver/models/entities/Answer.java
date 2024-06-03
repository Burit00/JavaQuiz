package com.quizserver.models.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String text;

    private UUID questionId;

    public UUID getId() {
        return id;
    }

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    private String publicId;
    public String getPublicId() {return publicId;}
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

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
