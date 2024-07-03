package com.quizserver.controllers;

import com.quizserver.models.resourcesViewer.DTOs.commands.CreateFileCommand;
import com.quizserver.models.resourcesViewer.DTOs.commands.CreateTopicCommand;
import com.quizserver.models.resourcesViewer.DTOs.queries.ExampleDetailsQuery;
import com.quizserver.models.resourcesViewer.DTOs.queries.TopicQuery;
import com.quizserver.services.ResourceService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("topics")
    public ResponseEntity<List<TopicQuery>> getTopics() {
        return ResponseEntity.ok(resourceService.getAllTopics());
    }

    @PostMapping("topics")
    public ResponseEntity<?> postTopics(@RequestBody CreateTopicCommand topic) {
        resourceService.createTopic(topic);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("examples/{exampleId}")
    public ResponseEntity<ExampleDetailsQuery> getFiles(@PathVariable UUID exampleId) {
        return ResponseEntity.ok(resourceService.getExampleById(exampleId));
    }

    @PostMapping("files")
    public ResponseEntity<?> postFiles(@RequestBody List<CreateFileCommand> files) throws BadRequestException {
        resourceService.createFiles(files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
