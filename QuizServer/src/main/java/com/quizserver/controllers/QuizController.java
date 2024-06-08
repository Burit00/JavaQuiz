package com.quizserver.controllers;

import com.quizserver.models.DTOs.commands.CreateQuizCommand;
import com.quizserver.models.DTOs.commands.UpdateQuizCommand;
import com.quizserver.models.DTOs.commands.UserQuizAnswersCommand;
import com.quizserver.models.DTOs.queries.QuizQuery;
import com.quizserver.models.DTOs.queries.UpdateQuizQuery;
import com.quizserver.models.DTOs.queries.UserQuizScoreQuery;
import com.quizserver.services.AuthService;
import com.quizserver.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    private final QuizService quizService;
    private final AuthService authService;

    public QuizController(final QuizService quizService, final AuthService authService) {
        this.quizService = quizService;
        this.authService = authService;
    }

    @GetMapping
    public List<QuizQuery> getQuiz() {
        return quizService.getQuizzes();
    }

    @GetMapping("{quizId}")
    public QuizQuery getQuizById(@PathVariable("quizId") UUID quizId) {
        return quizService.getQuizById(quizId);
    }

    @GetMapping("{quizId}/updateForm")
    public ResponseEntity<UpdateQuizQuery> getQuizByQuizIdForUpdate(@PathVariable("quizId") UUID quizId, @RequestHeader(value = "Authorization", defaultValue = "token") String token) {
        System.out.println(token);
        if(!authService.isAdmin(token)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(quizService.getQuizByQuizIdForUpdate(quizId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postQuiz(@RequestBody CreateQuizCommand quizCommand, @RequestHeader(value = "Authorization", defaultValue = "token") String token) {
        if(!authService.isAdmin(token)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        quizService.createQuiz(quizCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{quizId}")
    public ResponseEntity<?> putQuiz(@PathVariable UUID quizId, @RequestBody UpdateQuizCommand quizCommand, @RequestHeader(value = "Authorization", defaultValue = "token") String token) {
        if(!authService.isAdmin(token)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        quizService.updateQuiz(quizId, quizCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{quizId}/calculateScore")
    public UserQuizScoreQuery calculateQuizScore(@PathVariable("quizId") UUID quizId, @RequestBody List<UserQuizAnswersCommand> userAnswers) {
        return quizService.calculateQuizScore(quizId, userAnswers);
    }

    @DeleteMapping("{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable UUID quizId, @RequestHeader(value = "Authorization", defaultValue = "token") String token) {
        if(!authService.isAdmin(token)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        quizService.deleteQuiz(quizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
