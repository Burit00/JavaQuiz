package com.quizserver.repositories;

import com.quizserver.models.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer, UUID> {
}
