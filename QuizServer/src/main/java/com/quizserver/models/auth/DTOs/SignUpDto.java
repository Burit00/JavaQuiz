package com.quizserver.models.auth.DTOs;

import com.quizserver.enums.UserRole;

public record SignUpDto(
        String login,
        String password,
        UserRole role
){}