package com.quizclient.utils;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quizclient.contexts.AuthContext;
import com.quizclient.helpers.AuthHelper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {
    public static Gson gson;
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

        GsonBuilder gsonBuilder = Converters.registerLocalDateTime(new GsonBuilder());
        gson = gsonBuilder.create();
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
        HttpRequest.BodyPublisher bodyPublisher =  HttpRequest.BodyPublishers.noBody();

        if (method == HTTPMethod.POST || method == HTTPMethod.PUT) {
            String bodyRequest = gson.toJson(body);
            bodyPublisher = HttpRequest.BodyPublishers.ofString(bodyRequest);
        }


        HttpRequest.Builder httpRequest = HttpRequest
                .newBuilder(URI.create(BASE_URL + url))
                .method(method.getValue(), bodyPublisher)
                .header("Content-Type", "application/json")
                .header("Accept", "*/*");

        if(AuthHelper.isLogged(AuthContext.getUserData().getValue()))
            httpRequest.header("Authorization", AuthContext.getToken());

        return httpClient.send(httpRequest.build(), HttpResponse.BodyHandlers.ofString());
    }
}
