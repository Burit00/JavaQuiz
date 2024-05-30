package com.quizclient.model.command;

import com.quizclient.model.query.AnswerQuery;

import java.util.UUID;

public class AnswerCommand {
    private String id = UUID.randomUUID().toString();

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    private String questionId;
    public String getQuestionId() {return questionId;}
    public void setQuestionId(String questionId) {this.questionId = questionId;}
    private String text;
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public AnswerCommand(String id, String questionId, String text) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
    }

    public AnswerCommand(AnswerCommand answer) {
        this.id = answer.id;
        this.questionId = answer.questionId;
        this.text = answer.text;
    }

    public static AnswerCommand mapFromAnswerQuery(AnswerQuery answerQuery) {
        return new AnswerCommand(answerQuery.getId(), answerQuery.getQuestionId(), answerQuery.getText());
    }
}
