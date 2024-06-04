package com.quizserver.models.DTOs.commands;

import com.quizserver.enums.QuestionTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionCommand {
    public CreateQuestionCommand(){}
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private QuestionTypeEnum questionType = QuestionTypeEnum.RADIO;
    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }
    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    private List<String> correctAnswers = new ArrayList<>();

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    private List<CreateAnswerCommand> answers = new ArrayList<>();

    public List<CreateAnswerCommand> getAnswers() {
        return answers;
    }

    public void setAnswers(List<CreateAnswerCommand> answers) {
        this.answers = answers;
    }
}
