package com.quizserver.models.DTOs.commands;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class UserAnswerCommand {
    private UUID questionId;
    private final List<String> answers = new ArrayList<>();
}
