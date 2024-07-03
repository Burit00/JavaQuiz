package com.quizserver.services;

import com.quizserver.models.quiz.DTOs.queries.QuestionQuery;
import com.quizserver.models.quiz.entities.Question;
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
    }

    public List<QuestionQuery> getAllQuestions(UUID quizId) {
        List<Question> questions = questionRepository.findAllByQuiz_Id(quizId);

        return modelMapper.map(questions, new TypeToken<List<QuestionQuery>>() {}.getType());
    }
}
