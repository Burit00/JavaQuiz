package com.quizclient.utils;

import com.google.gson.Gson;
import com.quizclient.contexts.AuthContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {
    public static Gson gson = new Gson();
    private final String BASE_URL;
    private static final java.net.http.HttpClient httpClient = java.net.http.HttpClient.newHttpClient();

    public enum HTTPMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private final String value;
        public String getValue() {
            return this.value;
        }
        private HTTPMethod(String httpMethod) {
            this.value = httpMethod;
        }
    }

    public HttpClient(String basedUrl) {
        BASE_URL = basedUrl;
    }

    public String get(String url) throws IOException, InterruptedException {
        HttpResponse<String> response = send(url, HTTPMethod.GET);
        return response.body();
    }

    public String post(String url, Object body) throws IOException, InterruptedException {
        HttpResponse<String> response = send(url, HTTPMethod.POST, body);
        return response.body();
    }

    public String put(String url, Object body) throws IOException, InterruptedException {
        HttpResponse<String> response = send(url, HTTPMethod.PUT, body);
        return response.body();
    }

    public String delete(String url) throws IOException, InterruptedException {
        HttpResponse<String> response = send(url, HTTPMethod.DELETE);
        return response.body();
    }

    public HttpResponse<String> send(String url, HTTPMethod method) throws IOException, InterruptedException {
        return send(url, method, null);
    }

    public HttpResponse<String> send(String url, HTTPMethod method, Object body) throws IOException, InterruptedException {
        HttpRequest.BodyPublisher bodyPublisher = null;

        if (method == HTTPMethod.GET || method == HTTPMethod.DELETE) {
            bodyPublisher = HttpRequest.BodyPublishers.noBody();
        } else if (method == HTTPMethod.POST || method == HTTPMethod.PUT) {
            String bodyRequest = gson.toJson(body);
            System.out.println(bodyRequest);
            bodyPublisher = HttpRequest.BodyPublishers.ofString(bodyRequest);
        }

        String token = AuthContext.getToken();

        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(BASE_URL + url))
                .method(method.getValue(), bodyPublisher)
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .header("Authorization", token != null ? token : "")
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}
