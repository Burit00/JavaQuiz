package com.quizserver.models.resourcesViewer.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;

    @ManyToOne
    private Topic topic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "example_id")
    private List<File> files = new ArrayList<>();

    @Override
    public String toString() {
        return "Example{id=" + id + ", name=" + name + ", files= " + files + "}";
    }
}
