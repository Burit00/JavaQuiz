package com.quizserver.models.entities;


import com.quizserver.enums.QuestionTypeEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    public UUID getId() {
        return id;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private UUID quizId;
    public UUID getQuizId() {return quizId;}

    private QuestionTypeEnum questionType;
    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }
    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    private String correctAnswers;
    public List<String> getCorrectAnswers() {
        if(questionType == QuestionTypeEnum.INPUT)
            return List.of(correctAnswers);
        else
            return List.of(correctAnswers.split(" "));
    }
    public void setCorrectAnswers(List<String> correctAnswers) {
        if(questionType == QuestionTypeEnum.INPUT)
            this.correctAnswers = correctAnswers.get(0);
        else {
            this.correctAnswers = correctAnswers.stream().reduce("", (a, b) -> a + " " + b);
        }
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionId")
    private List<Answer> answers = new ArrayList<>();
    public List<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quizId=" + quizId +
                ", questionType=" + questionType +
                ", correctAnswers='" + correctAnswers + '\'' +
                ", answers=" + answers +
                '}';
    }
}
