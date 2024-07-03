package com.quizserver.models.auth.DTOs;

public record SignInDto(
        String login,
        String password
){}