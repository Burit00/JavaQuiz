package com.quizserver.models.resourcesViewer.DTOs.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateFileCommand {
    private String name;
    private String content;
    private UUID exampleId;
}
