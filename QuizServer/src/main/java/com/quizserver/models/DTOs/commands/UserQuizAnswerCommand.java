package com.quizserver.models.DTOs.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class UserQuizAnswerCommand {
    private UUID quizId;
    private List<UserAnswerCommand> answers;

    public UserQuizAnswerCommand(UUID quizId, List<UserAnswerCommand> answers) {
        this.quizId = quizId;
        this.answers = answers;
    }
}
