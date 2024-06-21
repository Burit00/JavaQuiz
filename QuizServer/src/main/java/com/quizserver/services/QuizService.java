package com.quizserver.services;

import com.quizserver.models.DTOs.commands.*;
import com.quizserver.models.DTOs.queries.QuizDetailsQuery;
import com.quizserver.models.DTOs.queries.QuizQuery;
import com.quizserver.models.DTOs.queries.UpdateQuizQuery;
import com.quizserver.models.DTOs.queries.UserQuizScoreQuery;
import com.quizserver.models.entities.Answer;
import com.quizserver.models.entities.Question;
import com.quizserver.models.entities.Quiz;
import com.quizserver.repositories.IQuestionRepository;
import com.quizserver.repositories.IQuizRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QuizService {

    private final IQuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final IQuestionRepository questionRepository;

    @Autowired
    public QuizService(IQuizRepository quizRepository, IQuestionRepository questionRepository, ModelMapper modelMapper) {
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

    public void createQuiz(CreateQuizCommand newQuiz) {
        Quiz quiz = modelMapper.map(newQuiz, Quiz.class);
        quizRepository.save(quiz);
    }

    public UpdateQuizQuery getQuizByQuizIdForUpdate(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if(quiz == null)
            throw new IllegalArgumentException("Quiz with id " + quizId + " does not exist");

        return modelMapper.map(quiz, UpdateQuizQuery.class);
    }

    public void updateQuiz(UUID quizId, UpdateQuizCommand quizCommand) {
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
        quizRepository.save(quiz);
    }

    public void deleteQuiz(UUID quizId) {
        quizRepository.deleteById(quizId);
    }

    public UserQuizScoreQuery calculateQuizScore(UUID quizId, List<UserQuizAnswersCommand> userAnswers) {
        Quiz quizFromDB = quizRepository.findById(quizId).orElse(null);

        if(quizFromDB == null)
            throw new IllegalArgumentException("Quiz with id " + quizId + " does not exist");

        int correctAnswers = 0;
        for(Question questionFromDB: quizFromDB.getQuestions()) {
            UserQuizAnswersCommand userAnswer = userAnswers.stream().filter(answer ->
                    answer.getQuestionId().equals(questionFromDB.getId())).findFirst().orElse(null);

            if (userAnswer == null) continue;

            if(questionFromDB.checkUserAnswer(userAnswer.getAnswers()))
                ++correctAnswers;
        }

        final UserQuizScoreQuery userQuizScore = new UserQuizScoreQuery();
        userQuizScore.setQuizName(quizFromDB.getName());
        userQuizScore.setQuestionCount(quizFromDB.getQuestions().size());
        userQuizScore.setCorrectQuestionsCount(correctAnswers);
        return userQuizScore;
    }
}
