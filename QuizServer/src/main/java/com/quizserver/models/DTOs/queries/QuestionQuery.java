package com.quizserver.models.DTOs.queries;

import com.quizserver.enums.QuestionTypeEnum;

import java.util.List;
import java.util.UUID;

public class QuestionQuery {
    private UUID id;
    private String name;
    private UUID quizId;
    private QuestionTypeEnum questionType;
    private List<AnswerQuery> answers;

    public UUID getId() {return id;}
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

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