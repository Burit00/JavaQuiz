package com.quizserver.models.quiz.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class UserQuizScoreQuery {
    private UUID id;
    private String quizName;
    private String userName;
    private int userPoints;
    private int maxPoints;
    private LocalDateTime date;
}
