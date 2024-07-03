package com.quizserver.services;

import com.quizserver.models.quiz.DTOs.commands.CreateAnswerCommand;
import com.quizserver.models.quiz.DTOs.commands.CreateQuestionCommand;
import com.quizserver.models.quiz.DTOs.commands.CreateQuizCommand;
import com.quizserver.models.quiz.DTOs.commands.UpdateQuizCommand;
import com.quizserver.models.quiz.DTOs.queries.QuizDetailsQuery;
import com.quizserver.models.quiz.DTOs.queries.QuizQuery;
import com.quizserver.models.quiz.DTOs.queries.UpdateQuizQuery;
import com.quizserver.models.quiz.entities.Answer;
import com.quizserver.models.quiz.entities.Question;
import com.quizserver.models.quiz.entities.Quiz;
import com.quizserver.models.auth.entities.User;
import com.quizserver.repositories.IQuestionRepository;
import com.quizserver.repositories.IQuizRepository;
import com.quizserver.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QuizService {

    private final IUserRepository userRepository;
    private final IQuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final IQuestionRepository questionRepository;

    @Autowired
    public QuizService(IUserRepository userRepository, IQuizRepository quizRepository,
                       IQuestionRepository questionRepository,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;


        modelMapper.typeMap(CreateQuestionCommand.class, Question.class)
                .addMapping(CreateQuestionCommand::getCorrectAnswers, Question::setCorrectAnswers);

        modelMapper.typeMap(CreateAnswerCommand.class, Answer.class)
                .addMapping(CreateAnswerCommand::getPublicId, Answer::setPublicId);
    }

    public List<QuizQuery> getQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return modelMapper.map(quizzes, new TypeToken<List<QuizQuery>>(){}.getType());
    }

    public QuizDetailsQuery getQuizById(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) { throw new IllegalArgumentException("Quiz with id "+ quizId +" not found"); }

        QuizDetailsQuery quizDetailsDto = modelMapper.map(quiz, QuizDetailsQuery.class);
        quizDetailsDto.setQuestionCount(quiz.getQuestions().size());

        return quizDetailsDto;
    }

    public void createQuiz(CreateQuizCommand newQuiz, Authentication authentication) {
        User quizOwner = userRepository.findByLogin(authentication.getName());
        Quiz quiz = modelMapper.map(newQuiz, Quiz.class);
        quiz.setOwner(quizOwner);
        quizRepository.save(quiz);
    }

    public UpdateQuizQuery getQuizByQuizIdForUpdate(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if(quiz == null)
            throw new IllegalArgumentException("Quiz with id " + quizId + " does not exist");

        return modelMapper.map(quiz, UpdateQuizQuery.class);
    }


    public UUID updateQuiz(UUID quizId, UpdateQuizCommand quizCommand) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if(quiz == null)
            throw new IllegalArgumentException("Quiz with id " + quizId + " does not exist");

        quiz.setName(quizCommand.getName());
        quiz.setDescription(quizCommand.getDescription());
        quiz.setTime(quizCommand.getTime());
        List<Question> oldQuestions = quiz.getQuestions();
        quiz.setQuestions(new ArrayList<>());

        for(CreateQuestionCommand questionCommand: quizCommand.getQuestions()) {
            Question question = modelMapper.map(questionCommand, Question.class);
            quiz.getQuestions().add(question);
        }

        questionRepository.deleteAll(oldQuestions);
        quiz = quizRepository.save(quiz);
        return quiz.getId();
    }

    public void deleteQuiz(UUID quizId) {
        quizRepository.deleteById(quizId);
    }
}
