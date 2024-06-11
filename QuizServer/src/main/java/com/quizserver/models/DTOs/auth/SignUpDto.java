package com.quizserver.models.DTOs.auth;

import com.quizserver.enums.UserRole;

public record SignUpDto(
        String login,
        String password,
        UserRole role
){}