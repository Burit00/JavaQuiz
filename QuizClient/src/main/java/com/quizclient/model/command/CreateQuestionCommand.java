package com.quizclient.model.command;

import com.google.gson.Gson;
import com.quizclient.enums.QuestionTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionCommand {
    private String name;
    private String code;
    private QuestionTypeEnum questionType = QuestionTypeEnum.RADIO;
    private List<String> correctAnswers = new ArrayList<>();
    private List<CreateAnswerCommand> answers = new ArrayList<>();


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

    @Override
    public Object clone() {
        try{
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(this), CreateQuestionCommand.class);
        }
    }
}
