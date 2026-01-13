package com.github.utilities;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

interface GenericHttpClient {
    CompletableFuture<String> getAsync(String url);
    CompletableFuture<String> postAsync(String url, String token, String jsonBody);


    default CompletableFuture<String> sendAsync(HttpClient httpClient, HttpRequest request) {
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                                    .thenApply(response -> {
                                                    if (response.statusCode() >= 400) {
                                                        throw new RuntimeException("Error: http " + response.statusCode());
                                                    }
                                                    return response.body();
                                                    });
    }
    
}

public class WebClient implements GenericHttpClient {
    private static final WebClient INSTANCE = new WebClient();
    private final HttpClient httpClient;
    
    @Override
    public CompletableFuture<String> getAsync(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .GET()
                                         .build();

        return sendAsync(httpClient, request);
    }

    @Override
    public CompletableFuture<String> postAsync(String url, String token, String jsonBody) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .header("Authorization", "Bearer " + token)
                                         .header("Content-Type", "application/json")
                                         .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                                         .build();

        return sendAsync(httpClient, request);
    }

    private WebClient() {
        this.httpClient = HttpClient.newBuilder()
                                    .connectTimeout(Duration.ofSeconds(8))
                                    .followRedirects(HttpClient.Redirect.NORMAL)
                                    .build();
    }

    public static WebClient getInstance() {
        return INSTANCE;
    }
}
