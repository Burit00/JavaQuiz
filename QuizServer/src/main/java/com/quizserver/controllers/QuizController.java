package com.quizserver.controllers;

import com.quizserver.models.DTOs.commands.CreateQuizCommand;
import com.quizserver.models.DTOs.commands.UpdateQuizCommand;
import com.quizserver.models.DTOs.queries.QuizQuery;
import com.quizserver.models.DTOs.queries.UpdateQuizQuery;
import com.quizserver.services.QuizService;
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
    public List<QuizQuery> getQuiz() {
        return quizService.getQuizzes();
    }

    @GetMapping("{quizId}")
    public QuizQuery getQuizById(@PathVariable("quizId") UUID quizId) {
        return quizService.getQuizById(quizId);
    }

    @GetMapping("{quizId}/updateForm")
    public UpdateQuizQuery getQuizByQuizIdForUpdate(@PathVariable("quizId") UUID quizId) {
        return quizService.getQuizByQuizIdForUpdate(quizId);
    }

    @PostMapping
    public void postQuiz(@RequestBody CreateQuizCommand quizCommand) {
        quizService.createQuiz(quizCommand);
    }

    @PutMapping("{quizId}")
    public void postQuiz(@PathVariable UUID quizId, @RequestBody UpdateQuizCommand quizCommand) {
        quizService.updateQuiz(quizId, quizCommand);
    }

    @DeleteMapping("{quizId}")
    public void deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuiz(quizId);
    }

}
