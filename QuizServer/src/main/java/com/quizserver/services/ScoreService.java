package com.quizserver.services;

import com.quizserver.models.DTOs.commands.UserAnswerCommand;
import com.quizserver.models.DTOs.commands.UserQuizAnswerCommand;
import com.quizserver.models.DTOs.queries.UserQuizScoreQuery;
import com.quizserver.models.entities.Question;
import com.quizserver.models.entities.Quiz;
import com.quizserver.models.entities.Score;
import com.quizserver.models.entities.User;
import com.quizserver.repositories.IQuizRepository;
import com.quizserver.repositories.IScoreRepository;
import com.quizserver.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScoreService {
    private final IScoreRepository scoreRepository;
    private final IQuizRepository quizRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScoreService(IScoreRepository scoreRepository,
                        IQuizRepository quizRepository,
                        IUserRepository userRepository,
                        ModelMapper modelMapper) {
        this.scoreRepository = scoreRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserQuizScoreQuery calculateScore(UserQuizAnswerCommand userAnswers, Authentication authentication) {
        Quiz quizFromDB = quizRepository.findById(userAnswers.getQuizId()).orElse(null);

        if(quizFromDB == null)
            throw new IllegalArgumentException("Quiz with id " + userAnswers.getQuizId() + " does not exist");

        int correctAnswers = 0;
        for(Question questionFromDB: quizFromDB.getQuestions()) {
            UserAnswerCommand userAnswer = userAnswers.getAnswers().stream().filter(answer ->
                    answer.getQuestionId().equals(questionFromDB.getId())).findFirst().orElse(null);

            if (userAnswer == null) continue;

            if(questionFromDB.checkUserAnswer(userAnswer.getAnswers()))
                ++correctAnswers;
        }

        String username = authentication.getName();
        User user = userRepository.findByLogin(username);

        Score score = new Score(
                correctAnswers,
                quizFromDB.getQuestions().size(),
                user,
                quizFromDB
        );
        scoreRepository.save(score);

        final UserQuizScoreQuery userQuizScore = modelMapper.map(score, UserQuizScoreQuery.class);

        return userQuizScore;
    }

    public List<UserQuizScoreQuery> getScoresByQuizId(UUID quizId) {
        List<Score> scores = scoreRepository.findAllByQuiz_Id(quizId);
        List<UserQuizScoreQuery> userQuizScores = modelMapper.map(scores, new TypeToken<List<UserQuizScoreQuery>>() {}.getType());

        return userQuizScores;
    }
}
