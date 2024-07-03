package com.quizclient.model.query.Quiz;

public class AnswerQuery {
    private String id;
    private String questionId;
    private String text;
    private String publicId;

    public AnswerQuery() {}

    public AnswerQuery(String id, String questionId, String text, String publicId) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.publicId = publicId;
    }

    public String getId() {return id;}
    public String getQuestionId() {return questionId;}
    public String getText() {return text;}
    public String getPublicId() {return publicId;}
}
