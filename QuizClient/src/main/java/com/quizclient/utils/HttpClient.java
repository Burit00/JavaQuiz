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

    private enum HTTPMethodEnum {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private final String value;
        public String getValue() {
            return this.value;
        }
        private HTTPMethodEnum(String httpMethod) {
            this.value = httpMethod;
        }
    }

    public HttpClient(String basedUrl) {
        BASE_URL = basedUrl;
    }

    public String get(String url) throws IOException, InterruptedException {
        return send(url, HTTPMethodEnum.GET);
    }

    public String post(String url, Object body) throws IOException, InterruptedException {
        return send(url, HTTPMethodEnum.POST, body);
    }

    public String put(String url, Object body) throws IOException, InterruptedException {
        return send(url, HTTPMethodEnum.PUT, body);
    }

    public String delete(String url) throws IOException, InterruptedException {
        return send(url, HTTPMethodEnum.DELETE);
    }

    private String send(String url, HTTPMethodEnum method) throws IOException, InterruptedException {
        return send(url, method, null);
    }

    private String send(String url, HTTPMethodEnum method, Object body) throws IOException, InterruptedException {
        HttpRequest.BodyPublisher bodyPublisher = null;

        if (method == HTTPMethodEnum.GET || method == HTTPMethodEnum.DELETE) {
            bodyPublisher = HttpRequest.BodyPublishers.noBody();
        } else if (method == HTTPMethodEnum.POST || method == HTTPMethodEnum.PUT) {
            String bodyRequest = gson.toJson(body);
            bodyPublisher = HttpRequest.BodyPublishers.ofString(bodyRequest);
        }

        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(BASE_URL + url))
                .header("Content-Type", "application/json")
                .method(method.getValue(), bodyPublisher)
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
