package com.quizserver.repositories;

import com.quizserver.models.resourcesViewer.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IFileRepository extends JpaRepository<File, UUID> {
    List<File> findAllByExample_Id(UUID exampleId);
}
