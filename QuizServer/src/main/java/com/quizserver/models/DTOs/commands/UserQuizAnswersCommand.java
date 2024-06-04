package com.quizserver.models.DTOs.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserQuizAnswersCommand {
    private UUID questionId;
    private final List<String> answers= new ArrayList<>();

    public UUID getQuestionId() {
        return questionId;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
