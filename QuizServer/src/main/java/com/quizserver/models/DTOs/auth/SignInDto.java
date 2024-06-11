package com.quizserver.models.DTOs.auth;

public record SignInDto(
        String login,
        String password
){}