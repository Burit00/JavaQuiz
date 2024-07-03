package com.quizserver.repositories;

import com.quizserver.models.resourcesViewer.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITopicRepository extends JpaRepository<Topic, UUID> {
}
