package com.quizserver.models.quiz.DTOs.queries;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AnswerQuery {
    protected UUID id;
    protected String publicId;
    protected UUID questionId;
    protected String text;

}
