package com.quizserver.models.quiz.DTOs.commands;

import com.quizserver.enums.QuestionTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CreateQuestionCommand {

    private String name;
    protected String code;
    private List<String> correctAnswers = new ArrayList<>();
    private QuestionTypeEnum questionType = QuestionTypeEnum.RADIO;
    private List<CreateAnswerCommand> answers = new ArrayList<>();

    public CreateQuestionCommand(){}

}
