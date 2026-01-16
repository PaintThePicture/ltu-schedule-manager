package com.github.models.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.models.entities.CanvasAuthorization;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

public class CanvasService {
    private final WebClient webClient = WebClient.getInstance();

       private final ObjectMapper mapper = new ObjectMapper();

    public CompletableFuture<String> pushToCanvas(CanvasAuthorization auth, List<TimeEditReservation> reservations) {
        String path = "http://localhost:7070/api/canvas/events/export?contextCode=";

        path = path + auth.getUserId();

        try {
            String jsonBody = mapper.writeValueAsString(reservations);
            return push(path, auth.getExposedToken(), jsonBody);

        } catch (JsonProcessingException e) {
            return CompletableFuture.completedFuture(">>> FRONTEND CANVAS SERVICE STATUS: Mapping error\n" + 
                                                      e.getMessage());
        }
    }

    private CompletableFuture<String> push(String path, String token, String jsonBody) {
        return webClient.postAsync(path, token, jsonBody)
                        .exceptionally(e -> {
                            Throwable actual = (e.getCause() != null) ? e.getCause() : e;
                            
                            System.err.println(">>> FRONTEND CANVAS SERVICE STATUS: Push failed\n\t" + 
                                               actual.getMessage());

                            return "FAILED: " + actual.getMessage();
        });
    }

}
