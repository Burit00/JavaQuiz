package com.quizserver.models.DTOs.queries;

public class UserQuizScoreQuery {

    private int questionCount;
    private int correctQuestionsCount;
    private String quizName;

    public int getQuestionCount() {
        return questionCount;
    }
    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getCorrectQuestionsCount() {
        return correctQuestionsCount;
    }
    public void setCorrectQuestionsCount(int correctQuestionsCount) {
        this.correctQuestionsCount = correctQuestionsCount;
    }

    public String getQuizName() {
        return quizName;
    }
    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }
}
