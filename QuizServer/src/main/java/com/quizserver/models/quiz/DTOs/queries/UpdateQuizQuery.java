package com.quizserver.models.quiz.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UpdateQuizQuery extends QuizDetailsQuery {
    private List<UpdateQuestionQuery> questions = new ArrayList<>();

}
