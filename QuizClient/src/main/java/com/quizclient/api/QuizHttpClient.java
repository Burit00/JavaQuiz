package com.quizclient.api;

import com.google.gson.reflect.TypeToken;
import com.quizclient.model.command.QuizCommand;
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

        return gson.fromJson(response, new TypeToken<List<QuizQuery>>() {}.getType());
    }

    public static QuizQuery getQuiz(String quizId) {
        String response;

        try {
            response = httpClient.get("/quizzes/" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(response, QuizQuery.class);
    }

    public static List<QuestionQuery> getQuestionsWithAnswers(String quizId) {
        String response;

        try {
            response = httpClient.get("/questions?_embed=answers&quizId=" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(response, new TypeToken<List<QuestionQuery>>() {
        }.getType());

    }

    public static void postQuiz(QuizCommand quiz) {
//        TODO:
//        String  response;
//        try {
//            response = httpClient.post("/quizzes", quiz);
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void putQuiz(QuizCommand quiz) {
//        TODO:
//        String  response;
//        try {
//            response = httpClient.put("/quizzes/" + quiz.getId(), quiz);
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
