package com.quizserver.models.DTOs.commands;

import com.quizserver.enums.QuestionTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionCommand {

    private String name;
    protected String code;
    private List<String> correctAnswers = new ArrayList<>();
    private QuestionTypeEnum questionType = QuestionTypeEnum.RADIO;
    private List<CreateAnswerCommand> answers = new ArrayList<>();

    public CreateQuestionCommand(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }
    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }
    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<CreateAnswerCommand> getAnswers() {
        return answers;
    }
    public void setAnswers(List<CreateAnswerCommand> answers) {
        this.answers = answers;
    }
}
