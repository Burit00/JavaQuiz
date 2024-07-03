package com.quizserver.repositories;

import com.quizserver.models.quiz.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz, UUID> {
}
