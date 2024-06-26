package com.quizserver.controllers;

import com.quizserver.models.DTOs.commands.UserQuizAnswerCommand;
import com.quizserver.models.DTOs.queries.UserQuizScoreQuery;
import com.quizserver.services.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/score")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<UserQuizScoreQuery> calculateQuizScore(@RequestBody UserQuizAnswerCommand userAnswers, Authentication authentication) {
        return new ResponseEntity<>(scoreService.calculateScore(userAnswers, authentication), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserQuizScoreQuery>> getScores(@RequestParam UUID quizId) {
        return ResponseEntity.ok(scoreService.getScoresByQuizId(quizId));
    }
}
