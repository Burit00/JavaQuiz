package com.quizclient.model.command;

import com.google.gson.Gson;
import com.quizclient.enums.QuestionTypeEnum;
import com.quizclient.model.query.QuestionQuery;

import java.util.ArrayList;
import java.util.List;

public class QuestionCommand {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String quizId;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
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

    private List<AnswerCommand> answers = new ArrayList<>();

    public List<AnswerCommand> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerCommand> answers) {
        this.answers = answers;
    }

    public QuestionCommand() {
    }

    public QuestionCommand(String id, String name, String quizId, QuestionTypeEnum questionType, List<AnswerCommand> answers, List<String> correctAnswers) {
        this.id = id;
        this.name = name;
        this.quizId = quizId;
        this.questionType = questionType;
        this.answers = answers.stream().map(AnswerCommand::new).toList();
        this.correctAnswers = correctAnswers.stream().toList();
    }

    public QuestionCommand(QuestionCommand question) {
        this(
                question.id,
                question.name,
                question.quizId,
                question.questionType,
                question.answers,
                question.correctAnswers
        );
    }

    public static QuestionCommand mapFromQuestionQuery(QuestionQuery questionQuery) {
        List<AnswerCommand> answers = questionQuery.getAnswers().stream().map(AnswerCommand::mapFromAnswerQuery).toList();

        QuestionCommand questionCommand = new QuestionCommand(
                questionQuery.getId(),
                questionQuery.getName(),
                questionQuery.getQuizId(),
                questionQuery.getQuestionType(),
                answers,
                questionQuery.getCorrectAnswers()
        );

        return questionCommand;
    }

    @Override
    public Object clone() {
        try{
            return super.clone();
        } catch (CloneNotSupportedException e) {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(this), QuestionCommand.class);
        }
    }
}
