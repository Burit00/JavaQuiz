package com.quizclient.api;

import com.google.gson.reflect.TypeToken;
import com.quizclient.model.query.QuizQuery;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

public class QuizHttpClient {
    private static final HttpClient httpClient = new HttpClient("http://localhost:3000");

    public static List<QuizQuery> getQuizzes() {
        String response;

        try {
            response = httpClient.get("/quizzes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<QuizQuery> quizzes = new Gson().fromJson(response, new TypeToken<List<QuizQuery>>(){}.getType());

        return quizzes;
    }

    public static QuizQuery getQuiz(String quizId) {
        String response;
        try {
            response = httpClient.get("/quizzes/" + quizId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        QuizQuery quiz = new Gson().fromJson(response, new TypeToken<QuizQuery>() {}.getType());

        return quiz;
    }
}
