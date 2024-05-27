package com.quizclient.api;

import com.google.gson.reflect.TypeToken;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizQuery;
import com.google.gson.Gson;
import com.quizclient.utils.HttpClient;

import java.io.IOException;
import java.util.List;

public class QuizHttpClient {
    private static final Gson gson = new Gson();
    private static final HttpClient httpClient = new HttpClient("http://localhost:3000");

    public static List<QuizQuery> getQuizzes() {
        String response;

        try {
            response = httpClient.get("/quizzes");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<QuizQuery> quizzes = gson.fromJson(response, new TypeToken<List<QuizQuery>>(){}.getType());

        return quizzes;
    }

    public static QuizQuery getQuiz(String quizId) {
        String response;

        try {
            response = httpClient.get("/quizzes/" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        QuizQuery quiz = gson.fromJson(response, QuizQuery.class);

        return quiz;
    }

    public static List<QuestionQuery> getQuestionsWithAnswers(String quizId) {
        String response;

        try {
            response = httpClient.get("/questions?_embed=answers&quizId=" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<QuestionQuery> questions = gson.fromJson(response, new TypeToken<List<QuestionQuery>>(){}.getType());

        return questions;
    }
}
