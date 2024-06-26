package com.quizserver.repositories;

import com.quizserver.models.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IScoreRepository extends JpaRepository<Score, UUID> {
    public List<Score> findAllByQuiz_Id(UUID quiz_id);
}
