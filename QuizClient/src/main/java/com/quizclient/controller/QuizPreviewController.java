package com.quizclient.controller;

import com.quizclient.enums.AwesomeIconEnum;
import com.quizclient.model.command.Quiz.CreateQuizCommand;
import com.quizclient.model.query.Quiz.AnswerQuery;
import com.quizclient.model.query.Quiz.QuestionQuery;
import com.quizclient.model.query.Quiz.QuizDetailsQuery;
import com.quizclient.ui.Icon;
import com.quizclient.utils.SceneLoader;

import java.util.List;
import java.util.UUID;

public class QuizPreviewController extends SolveQuizAbstractController {
    private CreateQuizCommand updatedQuiz;

    public void setParameter(CreateQuizCommand updatedQuiz, int questionIndex) {
        setParameter(updatedQuiz);
        currentQuestion = questions.get(questionIndex);
        buildQuestion();
    }

    public void setParameter(CreateQuizCommand updatedQuiz) {
        this.updatedQuiz = updatedQuiz;
        quiz = new QuizDetailsQuery(
                null,
                updatedQuiz.getName(),
                updatedQuiz.getTime(),
                updatedQuiz.getDescription(),
                updatedQuiz.getQuestions().size()
        );

        List<QuestionQuery> mappedQuestions = updatedQuiz.getQuestions().stream().map(updatedQuestion -> {
            List<AnswerQuery> answers = updatedQuestion.getAnswers().stream().map(updatedAnswer ->
                    new AnswerQuery(null, null, updatedAnswer.getText(), updatedAnswer.getPublicId())).toList();

            return new QuestionQuery(
                    UUID.randomUUID(),
                    null,
                    updatedQuestion.getName(),
                    updatedQuestion.getCode(),
                    updatedQuestion.getQuestionType(),
                    answers);
        }).toList();

        setQuestions(mappedQuestions);

        buildUI();
    }

    protected void buildUI() {
        confirmButton.setText("Powrót do edycji");
        confirmButton.setGraphic(
                new Icon(AwesomeIconEnum.BACKWARD)
        );
        super.buildUI();
    }

    @Override
    protected void onConfirm() {
        SceneLoader.loadEditQuizScene(updatedQuiz);
    }
}
