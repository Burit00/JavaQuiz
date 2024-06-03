package com.quizclient.model.command;

import com.quizclient.model.query.AnswerQuery;

public class AnswerCommand {
    private String questionId;
    public String getQuestionId() {return questionId;}
    public void setQuestionId(String questionId) {this.questionId = questionId;}
    private String text;
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    private String publicId;
    public String getPublicId() {return publicId;}
    public void setPublicId(String publicId) {this.publicId = publicId;}

    public AnswerCommand(String publicId, String questionId, String text) {
        this.questionId = questionId;
        this.text = text;
        this.publicId = publicId;
    }

    public AnswerCommand(AnswerCommand answer) {
        this.questionId = answer.questionId;
        this.text = answer.text;
        this.publicId = answer.publicId;
    }

    public static AnswerCommand mapFromAnswerQuery(AnswerQuery answerQuery) {
        return new AnswerCommand(answerQuery.getPublicId(), answerQuery.getQuestionId(), answerQuery.getText());
    }
}
