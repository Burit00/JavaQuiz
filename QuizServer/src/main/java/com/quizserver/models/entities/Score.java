package com.quizserver.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class Score {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int userPoints;

    private int maxPoints;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Quiz quiz;

    public Score() {}

    public Score(int userPoints, int maxPoints, User user, Quiz quiz) {
        this.userPoints = userPoints;
        this.maxPoints = maxPoints;
        this.user = user;
        this.quiz = quiz;
    }

    public UUID getQuizId() {
        return quiz != null ? quiz.getId() : null;
    }

    public UUID getUserId() {
        return user != null ? user.getId() : null;
    }
}
