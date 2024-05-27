package com.quizclient.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {
    private static String BASE_URL;

    public HttpClient(String basedUrl) {
        BASE_URL = basedUrl;
    }

    public String get(String url) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(BASE_URL + url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        java.net.http.HttpClient httpClient = java.net.http.HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
