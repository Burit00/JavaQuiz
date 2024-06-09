package com.quizclient.model.query;

import com.quizclient.enums.QuestionTypeEnum;

import java.util.List;
import java.util.UUID;

public class QuestionQuery {
    private UUID id;
    private String name;
    private String code;
    private String quizId;
    private QuestionTypeEnum questionType;
    private List<AnswerQuery> answers;

    public UUID getId() {return id;}
    public String getName() {return name;}
    public String getCode() {return code;}
    public String getQuizId() {return quizId;}
    public QuestionTypeEnum getQuestionType() {return questionType;}
    public List<AnswerQuery> getAnswers() {return answers;}

}
