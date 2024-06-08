package com.quizserver.models.entities;


import com.quizserver.enums.QuestionTypeEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String code;
    private UUID quizId;
    private QuestionTypeEnum questionType;
    private String correctAnswers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "questionId")
    private List<Answer> answers = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public UUID getQuizId() {return quizId;}

    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }
    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }


    public List<String> getCorrectAnswers() {
        if(questionType == QuestionTypeEnum.INPUT)
            return List.of(correctAnswers);
        else
            return List.of(correctAnswers.split(" "));
    }
    public void setCorrectAnswers(List<String> correctAnswers) {
        if(questionType == QuestionTypeEnum.INPUT)
            this.correctAnswers = correctAnswers.get(0);
        else
            this.correctAnswers = String.join(" ", correctAnswers);

    }

    public List<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean checkUserAnswer(List<String> userAnswer) {
        userAnswer = new ArrayList<>(new HashSet<>(userAnswer));
        if(getCorrectAnswers().size() != userAnswer.size()) return false;

        return new HashSet<>(userAnswer).containsAll(getCorrectAnswers());
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
