package com.quizserver.models.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuizDetailsQuery extends QuizQuery {
    private int questionCount;
    private int time;
    private String description;

    @Override
    public String toString() {
        return "QuizDetailsQuery{questionCount=" + questionCount + ", time=" + time + ", description=" + description + "}";
    }
}
