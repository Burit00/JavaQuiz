package com.quizclient.model.query.Quiz;

import com.quizclient.enums.QuestionTypeEnum;

import java.util.List;
import java.util.UUID;

public class QuestionQuery {
    private UUID id;
    private String quizId;
    private String name;
    private String code;
    private QuestionTypeEnum questionType;
    private List<AnswerQuery> answers;

    public QuestionQuery() {}

    public QuestionQuery(UUID id, String quizId, String name, String code, QuestionTypeEnum questionType, List<AnswerQuery> answers) {
        this.id = id;
        this.quizId = quizId;
        this.name = name;
        this.code = code;
        this.questionType = questionType;
        this.answers = answers;
    }

    public UUID getId() {return id;}
    public String getQuizId() {return quizId;}
    public String getName() {return name;}
    public String getCode() {return code;}
    public QuestionTypeEnum getQuestionType() {return questionType;}
    public List<AnswerQuery> getAnswers() {return answers;}

}
