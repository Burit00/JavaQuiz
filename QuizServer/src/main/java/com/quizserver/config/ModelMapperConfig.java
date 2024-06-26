package com.quizserver.config;

import com.quizserver.models.DTOs.commands.CreateAnswerCommand;
import com.quizserver.models.DTOs.commands.CreateQuestionCommand;
import com.quizserver.models.DTOs.queries.AnswerQuery;
import com.quizserver.models.DTOs.queries.QuestionQuery;
import com.quizserver.models.DTOs.queries.UserQuizScoreQuery;
import com.quizserver.models.entities.Answer;
import com.quizserver.models.entities.Question;
import com.quizserver.models.entities.Score;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        setAnswerMapping(modelMapper);
        setQuestionMapping(modelMapper);
        setScoreMapping(modelMapper);

        return modelMapper;
    }

    private void setQuestionMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(CreateQuestionCommand.class, Question.class)
                .addMapping(CreateQuestionCommand::getCorrectAnswers, Question::setCorrectAnswers);

        modelMapper.typeMap(Question.class, QuestionQuery.class)
                .addMapping(Question::getId, QuestionQuery::setId)
                .addMapping(Question::getName, QuestionQuery::setName)
                .addMapping(Question::getQuestionType, QuestionQuery::setQuestionType)
                .addMapping(question -> question.getQuiz().getId(), QuestionQuery::setQuizId);
    }

    private void setAnswerMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(CreateAnswerCommand.class, Answer.class)
                .addMapping(CreateAnswerCommand::getPublicId, Answer::setPublicId);

        modelMapper.typeMap(Answer.class, AnswerQuery.class)
                .addMapping(Answer::getId, AnswerQuery::setId)
                .addMapping(Answer::getPublicId, AnswerQuery::setPublicId);
    }

    private void setScoreMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(Score.class, UserQuizScoreQuery.class)
                .addMapping(score -> score.getUser().getUsername(), UserQuizScoreQuery::setUserName)
                .addMapping(score -> score.getQuiz().getName(), UserQuizScoreQuery::setQuizName);
    }
}
