package com.quizserver.models.quiz.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class Answer {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String publicId;
    private String text;


    @ManyToOne(cascade = CascadeType.ALL)
    private Question question;

    public Answer() {}

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", questionId=" + question.getId() +
                ", publicId='" + publicId + '\'' +
                '}';
    }
}
