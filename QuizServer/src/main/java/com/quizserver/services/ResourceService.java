package com.quizserver.services;

import com.quizserver.models.resourcesViewer.DTOs.commands.CreateFileCommand;
import com.quizserver.models.resourcesViewer.DTOs.commands.CreateTopicCommand;
import com.quizserver.models.resourcesViewer.DTOs.queries.ExampleDetailsQuery;
import com.quizserver.models.resourcesViewer.DTOs.queries.ExampleQuery;
import com.quizserver.models.resourcesViewer.DTOs.queries.TopicQuery;
import com.quizserver.models.resourcesViewer.entities.Example;
import com.quizserver.models.resourcesViewer.entities.File;
import com.quizserver.models.resourcesViewer.entities.Topic;
import com.quizserver.repositories.IExampleRepository;
import com.quizserver.repositories.IFileRepository;
import com.quizserver.repositories.ITopicRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceService {
    private final ITopicRepository topicRepository;
    private final IExampleRepository exampleRepository;
    private final IFileRepository fileRepository;

    private final ModelMapper modelMapper;

    public ResourceService(ITopicRepository topicRepository, IExampleRepository exampleRepository, IFileRepository fileRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.exampleRepository = exampleRepository;
        this.fileRepository = fileRepository;
        this.modelMapper = modelMapper;
    }

    public void createTopic(CreateTopicCommand topicCommand) {
        Topic newTopic = modelMapper.map(topicCommand, Topic.class);
        topicRepository.save(newTopic);
    }

    public List<TopicQuery> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        List<TopicQuery> topicQueryList;
        try {
            topicQueryList = modelMapper.map(topics, new TypeToken<List<TopicQuery>>() {}.getType());
        }catch (Exception e) {
            return new ArrayList<>();
        }
        return topicQueryList;
    }

    public void createFiles(List<CreateFileCommand> fileCommands) throws BadRequestException{
        List<File> filesToSave = new ArrayList<>();

        for(CreateFileCommand fileCommand: fileCommands) {
            Example example = exampleRepository.findById(fileCommand.getExampleId()).orElse(null);
            if(example == null)
                throw new BadRequestException("Not found example with id" + fileCommand.getExampleId());
            File newFile = modelMapper.map(fileCommand, File.class);
            newFile.setExample(example);
            filesToSave.add(newFile);
        }

        fileRepository.saveAll(filesToSave);
    }

    public ExampleDetailsQuery getExampleById(UUID exampleId) {
        Example example = exampleRepository.findById(exampleId).orElse(null);

        return modelMapper.map(example, ExampleDetailsQuery.class);
    }

    public TopicQuery getExamplesByTopicId(UUID topicId) throws BadRequestException {
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if(topic == null)
            throw new BadRequestException("Not found topic with id" + topicId);
        return modelMapper.map(topic, new TypeToken<TopicQuery>(){}.getType());
    }
}
