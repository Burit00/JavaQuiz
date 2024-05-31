package com.quizclient.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {
    public static Gson gson = new Gson();
    private static String BASE_URL;
    private static final java.net.http.HttpClient httpClient = java.net.http.HttpClient.newHttpClient();

    public HttpClient(String basedUrl) {
        BASE_URL = basedUrl;
    }

    public String get(String url) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(BASE_URL + url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String post(String url, Object body) throws IOException, InterruptedException {
        String bodyRequest = gson.toJson(body);

        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(BASE_URL + url))
                .method("POST", HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String put(String url, Object body) throws IOException, InterruptedException {
        String bodyRequest = gson.toJson(body);

        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(BASE_URL + url))
                .method("PUT", HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
