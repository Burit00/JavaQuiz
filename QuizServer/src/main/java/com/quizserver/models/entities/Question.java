package com.quizserver.models.entities;


import com.quizserver.enums.QuestionTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class Question {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String code;
    private QuestionTypeEnum questionType;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String correctAnswers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Answer> answers = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Quiz quiz;

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
                ", quizId=" + quiz.getId() +
                ", questionType=" + questionType +
                ", correctAnswers='" + correctAnswers + '\'' +
                ", answers=" + answers +
                '}';
    }
}
