package com.quizclient.model.query;

import com.quizclient.enums.QuestionTypeEnum;

import java.util.List;

public class QuestionQuery {
    private String id;
    public String getId() {return id;}
    private String name;
    public String getName() {return name;}
    private String quizId;
    public String getQuizId() {return quizId;}
    private QuestionTypeEnum questionType;
    public QuestionTypeEnum getQuestionType() {return questionType;}
    private List<AnswerQuery> answers;
    public List<AnswerQuery> getAnswers() {return answers;}

}
