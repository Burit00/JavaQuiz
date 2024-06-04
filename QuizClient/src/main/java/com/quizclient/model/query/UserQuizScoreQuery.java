package com.quizclient.model.query;

public class UserQuizScoreQuery {
    private int questionCount;
    private int correctQuestionsCount;
    private String quizName;

    public int getQuestionCount() {
        return questionCount;
    }

    public int getCorrectQuestionsCount() {
        return correctQuestionsCount;
    }

    public String getQuizName() {
        return quizName;
    }
}
