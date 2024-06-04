package com.quizserver.models.DTOs.queries;

import java.util.ArrayList;
import java.util.List;

public class UpdateQuizQuery extends QuizQuery {
    private List<UpdateQuestionQuery> questions = new ArrayList<>();
    public List<UpdateQuestionQuery> getQuestions() {
        return questions;
    }
    public void setQuestions(List<UpdateQuestionQuery> questions) {
        this.questions = questions;
    }
}
