package com.quizclient.model.command;

import java.util.List;
import java.util.UUID;

public class UserQuizAnswersCommand {
    private UUID questionId;

    private List<String> answers;

    public UserQuizAnswersCommand(UUID questionId, List<String> answers) {
        this.questionId = questionId;
        this.answers = answers;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
