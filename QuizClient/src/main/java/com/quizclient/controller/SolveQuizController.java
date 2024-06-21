package com.quizclient.controller;

import com.quizclient.api.QuizHttpClient;
import com.quizclient.model.query.QuestionQuery;
import com.quizclient.model.query.QuizDetailsQuery;
import javafx.animation.Timeline;
import javafx.fxml.FXML;

import java.util.*;

public class SolveQuizController extends SolveQuizAbstractController {

    private Timeline timer = new Timeline();


    @FXML
    @Override
    protected void onConfirm() {
        Optional<Boolean> result = confirmQuizDialog.showAndWait();

        if (result.isPresent() && result.get()) {
            timer.stop();
            showScore();
        }
    }

    public void setParameter(QuizDetailsQuery quiz) {
        this.quiz = quiz;
        this.loadQuestions();
        this.buildUI();
    }

    private void loadQuestions() {
        List<QuestionQuery> loadedQuestions = QuizHttpClient.getQuestionsWithAnswers(quiz.getId());
        super.setQuestions(loadedQuestions);
    }

    protected void buildUI() {
        if (quiz.getTime() > 0) buildTimer();
        super.buildUI();
    }

}
