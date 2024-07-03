package com.quizserver.models.resourcesViewer.DTOs.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTopicCommand {
    private String name;
    private List<CreateExampleCommand> examples;
}
