package com.quizserver.config;

import com.quizserver.models.DTOs.commands.CreateAnswerCommand;
import com.quizserver.models.DTOs.commands.CreateQuestionCommand;
import com.quizserver.models.entities.Answer;
import com.quizserver.models.entities.Question;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CreateQuestionCommand.class, Question.class)
                .addMapping(CreateQuestionCommand::getCorrectAnswers, Question::setCorrectAnswers);

        modelMapper.typeMap(CreateAnswerCommand.class, Answer.class)
                .addMapping(CreateAnswerCommand::getPublicId, Answer::setPublicId);

        return modelMapper;
    }
}
