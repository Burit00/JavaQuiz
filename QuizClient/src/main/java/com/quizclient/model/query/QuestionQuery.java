package com.quizclient.model.query;

import com.quizclient.enums.QuestionTypeEnum;

import java.util.List;
import java.util.UUID;

public class QuestionQuery {
    private UUID id;
    public UUID getId() {return id;}
    private String name;
    public String getName() {return name;}
    private String quizId;
    public String getQuizId() {return quizId;}
    private QuestionTypeEnum questionType;
    public QuestionTypeEnum getQuestionType() {return questionType;}
    private List<AnswerQuery> answers;
    public List<AnswerQuery> getAnswers() {return answers;}

}
