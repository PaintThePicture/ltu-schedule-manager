package com.github.models.services;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.dto.CanvasRawDTO;
import com.github.utilities.WebClient;

public class CanvasService {
    private final WebClient webClient = WebClient.getInstance();

       private final ObjectMapper mapper = new ObjectMapper().configure(com.fasterxml.jackson
                                                                        .databind.DeserializationFeature
                                                                        .FAIL_ON_UNKNOWN_PROPERTIES, false);

    public CompletableFuture<String> pushToCanvas(String token, CanvasRawDTO eventData) {
        String path = "http://localhost:7070/api/canvas/events";

        try {
            String jsonBody = mapper.writeValueAsString(eventData);

            return push(path, token, jsonBody);

        } catch (JsonProcessingException e) {
            return CompletableFuture.completedFuture(">>> FRONTEND CANVAS SERVICE STATUS: Mapping error\n" + 
                                                      e.getMessage());
        }
    }

    private CompletableFuture<String> push(String path, String token, String jsonBody) {
        return webClient.postAsync(path, token, jsonBody)
                        .exceptionally(e -> {
                            // Unwrappa felet (t.ex. "Error: http 401" från GenericHttpClient)
                            Throwable actual = (e.getCause() != null) ? e.getCause() : e;
                            
                            System.err.println(">>> FRONTEND CANVAS SERVICE STATUS: Push failed\n\t" + 
                                               actual.getMessage());

                            return "FAILED: " + actual.getMessage();
        });
    }

}
