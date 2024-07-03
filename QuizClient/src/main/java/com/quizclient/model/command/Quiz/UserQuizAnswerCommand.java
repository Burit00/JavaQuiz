package com.quizclient.model.command.Quiz;

import java.util.List;
import java.util.UUID;

public class UserQuizAnswerCommand {
    private UUID quizId;
    private List<UserAnswerCommand> answers;

    public UserQuizAnswerCommand(UUID quizId, List<UserAnswerCommand> answers) {
        this.quizId = quizId;
        this.answers = answers;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public void setQuizId(UUID quizId) {
        this.quizId = quizId;
    }

    public List<UserAnswerCommand> getAnswers() {
        return answers;
    }

    public void setAnswers(List<UserAnswerCommand> answers) {
        this.answers = answers;
    }
}
