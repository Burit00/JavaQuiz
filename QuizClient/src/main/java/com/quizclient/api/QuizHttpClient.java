package com.quizclient.api;

import com.google.gson.reflect.TypeToken;
import com.quizclient.contexts.AuthContext;
import com.quizclient.model.command.*;
import com.quizclient.model.query.*;
import com.quizclient.utils.HttpClient;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuizHttpClient {
    private static final HttpClient httpClient = new HttpClient("http://localhost:8080/api/v1/");

    public static boolean signIn(SignInCommand loginUser) {
        HttpResponse<String> response;

        try {
            response = httpClient.send("auth/sign-in", HttpClient.HTTPMethod.POST, loginUser);
        } catch (IOException | InterruptedException _) {
            return false;
        }

        if(response.statusCode() != 200){
            AuthContext.removeToken();
            return false;
        }

        JwtQuery jwt = HttpClient.gson.fromJson(response.body(), JwtQuery.class);
        AuthContext.setToken(jwt.getAccessToken());
        return true;
    }

    public static boolean signUp(SignUpCommand newUser) {
        HttpResponse<String> response;

        try {
            response = httpClient.send("auth/sign-up", HttpClient.HTTPMethod.POST, newUser);
        } catch (IOException | InterruptedException _) {
            return false;
        }

        return response.statusCode() == 201;
    }

    public static List<QuizQuery> getQuizzes() {
        String response;

        try {
            response = httpClient.get("quiz");
        } catch (IOException | InterruptedException _) {
            return null;
        }

        if (response.equals("[]")) return new ArrayList<>();
        return HttpClient.gson.fromJson(response, new TypeToken<List<QuizQuery>>() {}.getType());
    }

    public static QuizDetailsQuery getQuiz(UUID quizId) {
        String response;

        try {
            response = httpClient.get("quiz/" + quizId);
        } catch (IOException | InterruptedException _) {
            return null;
        }

        return HttpClient.gson.fromJson(response, QuizDetailsQuery.class);
    }

    public static UpdateQuizCommand getQuizDetailsForUpdate(UUID quizId) {
        String response;

        try {
            response = httpClient.get("quiz/" + quizId + "/updateForm");
        } catch (IOException | InterruptedException _) {
            return null;
        }

        return HttpClient.gson.fromJson(response, UpdateQuizCommand.class);
    }

    public static List<QuestionQuery> getQuestionsWithAnswers(UUID quizId) {
        String response;

        try {
            response = httpClient.get("question?quizId=" + quizId);
        } catch (IOException | InterruptedException _) {
            return null;
        }

        if(response.equals("[]")) return new ArrayList<>();
        return HttpClient.gson.fromJson(response, new TypeToken<List<QuestionQuery>>() {
        }.getType());

    }

    public static void postQuiz(CreateQuizCommand quiz) {
        try {
            httpClient.post("quiz", quiz);
        } catch (IOException | InterruptedException _) {}
    }

    public static void putQuiz(UpdateQuizCommand quiz) {
        try {
            httpClient.put("quiz/" + quiz.getId(), quiz);
        } catch (IOException | InterruptedException _) {}
    }

    public static void deleteQuiz(UUID quizId) {
        try {
            httpClient.delete("quiz/" + quizId);
        } catch (IOException | InterruptedException _) {}
    }

    public static UserQuizScoreQuery calculateQuizScore(UUID quizId, List<UserAnswerCommand> userAnswers) {
        UserQuizAnswerCommand userQuizAnswer = new UserQuizAnswerCommand(quizId, userAnswers);
        String response;

        try {
            response = httpClient.post("score", userQuizAnswer);
        } catch (IOException | InterruptedException _) {
            return null;
        }

        return HttpClient.gson.fromJson(response, new TypeToken<UserQuizScoreQuery>() {}.getType());
    }
}
