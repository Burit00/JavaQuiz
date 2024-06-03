package com.quizserver.controllers;

import com.quizserver.models.DTOs.queries.QuestionQuery;
import com.quizserver.services.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionQuery> getQuestions(@RequestParam(name = "quizId") UUID quizId) {
        return questionService.getAllQuestions(quizId);
    }
}
