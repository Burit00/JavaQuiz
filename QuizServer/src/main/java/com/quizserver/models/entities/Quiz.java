package com.quizserver.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Setter
    private String name;
    @Setter
    private int time = 0;
    @Setter
    private String description;

    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private List<Score> scores;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;

    public Quiz() {
        questions = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "{id = " + id + ", name = " + name + ", time = " + time + ", description = " + description + ", questions = " + questions + "}";
    }
}
