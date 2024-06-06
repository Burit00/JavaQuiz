package com.quizclient.api;

import com.google.gson.reflect.TypeToken;
import com.quizclient.contexts.AuthContext;
import com.quizclient.model.command.CreateQuizCommand;
import com.quizclient.model.command.LoginUserCommand;
import com.quizclient.model.command.UpdateQuizCommand;
import com.quizclient.model.command.UserQuizAnswersCommand;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizQuery;
import com.quizclient.model.query.UserQuizScoreQuery;
import com.quizclient.utils.HttpClient;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuizHttpClient {
    private static final HttpClient httpClient = new HttpClient("http://localhost:8080/api/v1/");

    public static boolean login(LoginUserCommand loginUser) {
        HttpResponse<String> response;

        try {
            response = httpClient.send("auth/login", HttpClient.HTTPMethod.POST, loginUser);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(response.statusCode() != 200){
            AuthContext.removeToken();
            return false;
        }

        AuthContext.setToken(response.body());
        return true;
    }

    public static List<QuizQuery> getQuizzes() {
        String response;

        try {
            response = httpClient.get("quiz");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.equals("[]")) return new ArrayList<>();
        return HttpClient.gson.fromJson(response, new TypeToken<List<QuizQuery>>() {}.getType());
    }

    public static QuizQuery getQuiz(UUID quizId) {
        String response;

        try {
            response = httpClient.get("quiz/" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return HttpClient.gson.fromJson(response, QuizQuery.class);
    }

    public static UpdateQuizCommand getQuizDetailsForUpdate(UUID quizId) {
        String response;

        try {
            response = httpClient.get("quiz/" + quizId + "/updateForm");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return HttpClient.gson.fromJson(response, UpdateQuizCommand.class);
    }

    public static List<QuestionQuery> getQuestionsWithAnswers(UUID quizId) {
        String response;

        try {
            response = httpClient.get("question?quizId=" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(response.equals("[]")) return new ArrayList<>();
        return HttpClient.gson.fromJson(response, new TypeToken<List<QuestionQuery>>() {
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

    public static void deleteQuiz(UUID quizId) {
        try {
            httpClient.delete("quiz/" + quizId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserQuizScoreQuery calculateQuizScore(UUID quizId, List<UserQuizAnswersCommand> userQuizAnswers) {
        String response;

        try {
            response = httpClient.post("quiz/" + quizId + "/calculateScore", userQuizAnswers);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return HttpClient.gson.fromJson(response, new TypeToken<UserQuizScoreQuery>() {}.getType());
    }
}
