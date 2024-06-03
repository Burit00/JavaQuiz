package com.quizserver.controllers;

import com.quizserver.models.DTOs.commands.CreateQuizCommand;
import com.quizserver.models.DTOs.queries.QuizQuery;
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

    @PostMapping
    public void postQuiz(@RequestBody CreateQuizCommand quizCommand) {
        quizService.createQuiz(quizCommand);
    }

}
