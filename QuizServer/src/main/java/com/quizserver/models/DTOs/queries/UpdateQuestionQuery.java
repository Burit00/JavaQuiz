package com.quizserver.models.DTOs.queries;

import java.util.List;

public class UpdateQuestionQuery extends QuestionQuery {
    private List<String> correctAnswers;

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }
    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
