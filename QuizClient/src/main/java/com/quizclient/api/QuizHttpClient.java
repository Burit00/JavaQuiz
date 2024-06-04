package com.quizclient.api;

import com.google.gson.reflect.TypeToken;
import com.quizclient.model.command.CreateQuizCommand;
import com.quizclient.model.command.UpdateQuizCommand;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizQuery;
import com.google.gson.Gson;
import com.quizclient.utils.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizHttpClient {
    private static final Gson gson = new Gson();
    private static final HttpClient httpClient = new HttpClient("http://localhost:8080/api/v1/");

    public static List<QuizQuery> getQuizzes() {
        String response;

        try {
            response = httpClient.get("quiz");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.equals("[]")) return new ArrayList<>();
        return gson.fromJson(response, new TypeToken<List<QuizQuery>>() {}.getType());
    }

    public static QuizQuery getQuiz(String quizId) {
        String response;

        try {
            response = httpClient.get("quiz/" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(response, QuizQuery.class);
    }

    public static UpdateQuizCommand getQuizDetailsForUpdate(String quizId) {
        String response;

        try {
            response = httpClient.get("quiz/" + quizId + "/updateForm");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(response, UpdateQuizCommand.class);
    }

    public static List<QuestionQuery> getQuestionsWithAnswers(String quizId) {
        String response;

        try {
            response = httpClient.get("question?quizId=" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(response.equals("[]")) return new ArrayList<>();
        return gson.fromJson(response, new TypeToken<List<QuestionQuery>>() {
        }.getType());

    }

    public static void postQuiz(CreateQuizCommand quiz) {
        try {
            httpClient.post("quiz", quiz);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putQuiz(UpdateQuizCommand quiz) {
        try {
            httpClient.put("quiz/" + quiz.getId(), quiz);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
