package com.github.utilities;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

interface GenericHttpClient {
    CompletableFuture<String> fetchAsync(String url);
    
}

public class WebClient implements GenericHttpClient {
    private static final WebClient INSTANCE = new WebClient();
    private final HttpClient httpClient;
    
    private WebClient() {
        this.httpClient = HttpClient.newBuilder()
                                    .connectTimeout(Duration.ofSeconds(8))
                                    .followRedirects(HttpClient.Redirect.NORMAL)
                                    .build();
    }
    

    @Override
    public CompletableFuture<String> fetchAsync(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .GET()
                                         .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                                         .thenApply(response -> {
                                                            if (response.statusCode() >= 400) {
                                                                throw new RuntimeException("Error: http " + response.statusCode());
                                                            }
                                                            return response.body();
                                                         });
    }

    public static WebClient getInstance() {
        return INSTANCE;
    }
}
