package com.quizserver.models.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
public class UserQuizScoreQuery {
    private UUID id;
    private String quizName;
    private String userName;
    private int userPoints;
    private int maxPoints;
    private OffsetDateTime date;
}
