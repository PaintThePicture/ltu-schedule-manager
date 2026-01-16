/*
 * ltu-schedule-manager: Ett verktyg för att hantera och exportera scheman 
 * från TimeEdit till Canvas för studenter vid Luleå tekniska universitet.
 *
 * Copyright (C) 2025  Alexander Edemalm, 
 * Ronak Olyaee, Therese Henriksson, Jakob Nilsson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301 USA.
 */
package com.github.utilities;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/** 
 * Singleton WebClient for making HTTP requests.
 */
interface GenericHttpClient {
    /** 
     * Makes an asynchronous GET request to the specified URL.
     * @param url
     * @return
     */
    CompletableFuture<String> getAsync(String url);
    CompletableFuture<String> postAsync(String url, String token, String jsonBody);
    
    /**
     * Sends an asynchronous HTTP request using the provided HttpClient and HttpRequest.
     * @param httpClient
     * @param request
     * @return
     */
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
/** 
 * WebClient implementation using Java's HttpClient.
 */
public class WebClient implements GenericHttpClient {
    
    private static final WebClient INSTANCE = new WebClient();
    private final HttpClient httpClient;
    /**
     * Makes an asynchronous GET request to the specified URL.
     * @param url
     * @return
     */
    @Override
    public CompletableFuture<String> getAsync(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .GET()
                                         .build();

        return sendAsync(httpClient, request);
    }

    /** 
     * Makes an asynchronous POST request to the specified URL with authorization token and JSON body.
     * @param url
     * @param token
     * @param jsonBody
     * @return
     */
    @Override
    public CompletableFuture<String> postAsync(String url, String token, String jsonBody) {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .header("Authorization", "Bearer " + token)
                                         .header("Content-Type", "application/json")
                                         .header("Accept", "application/json")
                                         .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                                         .build();

        return sendAsync(httpClient, request);
    }
    // Private constructor for singleton pattern
    private WebClient() {
        this.httpClient = HttpClient.newBuilder()
                                    .connectTimeout(Duration.ofSeconds(8))
                                    .followRedirects(HttpClient.Redirect.NORMAL)
                                    .build();
    }
    /** 
     * Gets the singleton instance of WebClient.
     * @return
     */
    public static WebClient getInstance() {
        return INSTANCE;
    }
}
