package com.github.api.services.integration;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.services.mapping.CanvasWrapper;
import com.github.utilities.WebClient;

public class CanvasClient {
    
    private final WebClient webClient = WebClient.getInstance();
    
    private final ObjectMapper mapper = new ObjectMapper();

    public CompletableFuture<String> pushEvent(String token, CanvasWrapper wrapper) {
        try {
            String jsonBody = mapper.writeValueAsString(wrapper);

            return webClient.postAsync(EndPoint.CALENDAR.getValue(), token, jsonBody);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private enum EndPoint {
        CALENDAR("/calendar_events");

        private final String value;
        private final static String BASE_URL = "https://canvas.ltu.se/api/v1/";

        EndPoint(String path) {
            this.value = BASE_URL + path;
        }

        public String getValue() {
            return this.value;
        }
        
        @Override
        public String toString() {
            return this.value;
        }
    }
}
