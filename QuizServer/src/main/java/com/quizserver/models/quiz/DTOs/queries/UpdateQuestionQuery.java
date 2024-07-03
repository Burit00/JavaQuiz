package com.quizserver.models.quiz.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateQuestionQuery extends QuestionQuery {
    private List<String> correctAnswers;

}
