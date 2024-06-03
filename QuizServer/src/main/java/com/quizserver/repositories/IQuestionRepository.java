package com.quizserver.repositories;

import com.quizserver.models.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IQuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByQuizId(UUID quizId);
}
