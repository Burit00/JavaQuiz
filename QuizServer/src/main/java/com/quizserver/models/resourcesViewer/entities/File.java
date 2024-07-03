package com.quizserver.models.resourcesViewer.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Example example;

    @Override
    public String toString() {
        return "Topic{id=" + id + ", name=" + name + ", content = " + content + "}";
    }
}
