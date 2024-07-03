package com.quizclient.api;

import com.google.gson.reflect.TypeToken;
import com.quizclient.model.command.Resource.CreateFileCommand;
import com.quizclient.model.query.Resource.ExampleDetailsQuery;
import com.quizclient.model.query.Resource.TopicQuery;
import com.quizclient.utils.HttpClient;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ResourceHttpClient {
    private static final HttpClient httpClient = new HttpClient("http://localhost:8080/api/v1/");

    public static List<TopicQuery> getTopics() {
        String response;

        try {
            response = httpClient.get("topics");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return HttpClient.gson.fromJson(response, new TypeToken<List<TopicQuery>>(){}.getType());
    }

    public static TopicQuery getTopic(UUID topicId) {
        String response;

        try {
            response = httpClient.get("topics/" + topicId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        return HttpClient.gson.fromJson(response, new TypeToken<TopicQuery>(){}.getType());
    }

    public static ExampleDetailsQuery getExample(UUID exampleId) {
        String response;

        try {
            response = httpClient.get("examples/" + exampleId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return HttpClient.gson.fromJson(response, ExampleDetailsQuery.class);
    }

    public static ExampleDetailsQuery createFile(List<CreateFileCommand> files) {
        String response;

        try {
            response = httpClient.post("files", files);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return HttpClient.gson.fromJson(response, ExampleDetailsQuery.class);
    }
}
