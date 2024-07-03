package com.quizserver.models.quiz.DTOs.queries;

import com.quizserver.enums.QuestionTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class QuestionQuery {
    protected UUID id;
    protected String name;
    protected String code;
    protected UUID quizId;
    protected QuestionTypeEnum questionType;
    protected List<AnswerQuery> answers;

}
