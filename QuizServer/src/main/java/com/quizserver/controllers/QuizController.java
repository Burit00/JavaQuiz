package com.quizserver.controllers;

import com.quizserver.models.DTOs.commands.CreateQuizCommand;
import com.quizserver.models.DTOs.commands.UpdateQuizCommand;
import com.quizserver.models.DTOs.queries.QuizQuery;
import com.quizserver.models.DTOs.queries.QuizDetailsQuery;
import com.quizserver.models.DTOs.queries.UpdateQuizQuery;
import com.quizserver.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(final QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<QuizQuery>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @GetMapping("{quizId}")
    public ResponseEntity<QuizDetailsQuery> getQuizById(@PathVariable("quizId") UUID quizId) {
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    @GetMapping("{quizId}/updateForm")
    public ResponseEntity<UpdateQuizQuery> getQuizByQuizIdForUpdate(@PathVariable("quizId") UUID quizId) {
        return new ResponseEntity<>(quizService.getQuizByQuizIdForUpdate(quizId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postQuiz(@RequestBody CreateQuizCommand quizCommand, Authentication authentication) {
        quizService.createQuiz(quizCommand, authentication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{quizId}")
    public ResponseEntity<UUID> putQuiz(@PathVariable UUID quizId, @RequestBody UpdateQuizCommand quizCommand) {
        return ResponseEntity.ok(quizService.updateQuiz(quizId, quizCommand));
    }


    @DeleteMapping("{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuiz(quizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
