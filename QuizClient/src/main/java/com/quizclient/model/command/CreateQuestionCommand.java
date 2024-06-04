package com.quizclient.model.command;

import com.google.gson.Gson;
import com.quizclient.enums.QuestionTypeEnum;
import com.quizclient.model.query.QuestionQuery;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionCommand {
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

    public CreateQuestionCommand() {
    }

    public CreateQuestionCommand(String name, QuestionTypeEnum questionType, List<CreateAnswerCommand> answers, List<String> correctAnswers) {
        this.name = name;
        this.questionType = questionType;
        this.answers = answers.stream().map(CreateAnswerCommand::new).toList();
        this.correctAnswers = correctAnswers.stream().toList();
    }

    public CreateQuestionCommand(CreateQuestionCommand question) {
        this(
                question.name,
                question.questionType,
                question.answers,
                question.correctAnswers
        );
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
