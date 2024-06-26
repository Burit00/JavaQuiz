package com.quizclient.model.query;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserQuizScoreQuery {
    private UUID id;
    private String quizName;
    private String userName;
    private int userPoints;
    private int maxPoints;
    private LocalDateTime date;

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getUserName() {
        return userName;
    }
}
