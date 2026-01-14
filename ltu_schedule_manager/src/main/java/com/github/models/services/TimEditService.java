package com.github.models.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.services.mapping.TimeEditSchemas;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

record TimeEditResponse(List<TimeEditReservation> reservations) {}

public class TimEditService {
    private final WebClient webClient = WebClient.getInstance();
    
    private final ObjectMapper mapper = new ObjectMapper().configure(com.fasterxml.jackson
                                                                        .databind.DeserializationFeature
                                                                        .FAIL_ON_UNKNOWN_PROPERTIES, false);

    public CompletableFuture<List<TimeEditReservation>> getScheduleByCourse(String courseId) {
        String targetUrl = "http://localhost:7070/api/time-edit/courses/" + courseId + "/schedule";

        return fetch(targetUrl);
    }

    public CompletableFuture<List<TimeEditReservation>> getScheduleByUrl(String url) {
        String encodedUrl = java.net.URLEncoder.encode(url, java.nio.charset.StandardCharsets.UTF_8);
        String targetUrl = "http://localhost:7070/api/time-edit/course/schedule/fetch?url=" + encodedUrl;
        
        return fetch(targetUrl);
    }

    private CompletableFuture<List<TimeEditReservation>> fetch(String path) {
        return webClient.getAsync(path)
                        .thenApply(json -> {
                            try {
                                return mapper.readValue(json, TimeEditSchemas.ExportResponse.class).reservations();
                                
                            } catch (Exception e) {
                                throw new RuntimeException(">>> FRONTEND SCHEDULE SERVICE STATUS: mapping error\n" + 
                                                            e.getMessage());
                            }
                        })
                        .exceptionally(e -> {
                            Throwable cause = (e.getCause() != null) ? e.getCause() : e;
                
                            System.err.println(">>> FRONTEND SCHEDULE SERVICE STATUS: API call failed\n\t" + 
                                                cause.getMessage());

                            return List.<TimeEditReservation>of();
                        });
    }
}
