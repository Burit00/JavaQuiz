package com.quizserver.services;

import com.quizserver.models.DTOs.commands.CreateAnswerCommand;
import com.quizserver.models.DTOs.commands.CreateQuestionCommand;
import com.quizserver.models.DTOs.commands.CreateQuizCommand;
import com.quizserver.models.DTOs.queries.QuizQuery;
import com.quizserver.models.entities.Answer;
import com.quizserver.models.entities.Question;
import com.quizserver.models.entities.Quiz;
import com.quizserver.repositories.IQuizRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuizService {

    private final IQuizRepository quizRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuizService(IQuizRepository quizRepository, ModelMapper modelMapper) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;


        modelMapper.typeMap(CreateQuestionCommand.class, Question.class)
                .addMapping(CreateQuestionCommand::getCorrectAnswers, Question::setCorrectAnswers);

        modelMapper.typeMap(CreateAnswerCommand.class, Answer.class)
                .addMapping(CreateAnswerCommand::getPublicId, Answer::setPublicId);
    }

    public List<QuizQuery> getQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizQuery> quizzesQuery = modelMapper.map(quizzes, new TypeToken<List<QuizQuery>>(){}.getType());
        System.out.println(quizzes);
        return quizzesQuery;
    }

    public QuizQuery getQuizById(UUID quizId) {
        boolean quizExists = quizRepository.existsById(quizId);
        if (!quizExists) { throw new IllegalArgumentException("Quiz with id "+ quizId +" not found"); }

        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        return modelMapper.map(quiz, QuizQuery.class);
    }

    public void createQuiz(CreateQuizCommand newQuiz) {
        Quiz quiz = modelMapper.map(newQuiz, Quiz.class);
        quizRepository.save(quiz);
    }
}
