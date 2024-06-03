package com.quizserver.services;

import com.quizserver.models.DTOs.queries.AnswerQuery;
import com.quizserver.models.DTOs.queries.QuestionQuery;
import com.quizserver.models.entities.Answer;
import com.quizserver.models.entities.Question;
import com.quizserver.repositories.IQuestionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
    private final IQuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionService(final IQuestionRepository questionRepository, final ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;

        modelMapper.typeMap(Answer.class, AnswerQuery.class)
                .addMapping(Answer::getId, AnswerQuery::setId)
                .addMapping(Answer::getPublicId, AnswerQuery::setPublicId);

        modelMapper.typeMap(Question.class, QuestionQuery.class)
                .addMapping(Question::getId, QuestionQuery::setId)
                .addMapping(Question::getName, QuestionQuery::setName)
                .addMapping(Question::getQuestionType, QuestionQuery::setQuestionType)
                .addMapping(Question::getQuizId, QuestionQuery::setQuizId);
    }

    public List<QuestionQuery> getAllQuestions(UUID quizId) {
        List<Question> questions = questionRepository.findAllByQuizId(quizId);

        return modelMapper.map(questions, new TypeToken<List<QuestionQuery>>() {}.getType());
    }
}
