package com.quizserver.repositories;

import com.quizserver.models.resourcesViewer.entities.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IExampleRepository extends CrudRepository<Example, UUID> {
}
