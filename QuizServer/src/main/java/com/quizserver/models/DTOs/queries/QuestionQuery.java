package com.quizserver.models.DTOs.queries;

import com.quizserver.enums.QuestionTypeEnum;

import java.util.List;
import java.util.UUID;

public class QuestionQuery {
    protected UUID id;
    protected String name;
    protected String code;
    protected UUID quizId;
    protected QuestionTypeEnum questionType;
    protected List<AnswerQuery> answers;

    public UUID getId() {return id;}
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public UUID getQuizId() {return quizId;}
    public void setQuizId(UUID quizId) {
        this.quizId = quizId;
    }

    public QuestionTypeEnum getQuestionType() {return questionType;}
    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public List<AnswerQuery> getAnswers() {return answers;}
    public void setAnswers(List<AnswerQuery> answers) {
        this.answers = answers;
    }
}
