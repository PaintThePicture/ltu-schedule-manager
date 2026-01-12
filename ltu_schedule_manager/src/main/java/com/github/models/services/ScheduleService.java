package com.github.models.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

public class ScheduleService {
    private final WebClient webClient = WebClient.getInstance();
    
    private final ObjectMapper mapper = new ObjectMapper();

    public CompletableFuture<List<TimeEditReservation>> getScheduleByCourse(String courseId) {
        String url = "http://localhost:7070/api/time-edit/courses/" + courseId + "/schedule";

        return webClient.fetchAsync(url)
                        .thenApply(json -> {
                            try {
                                return mapper.readValue(json, new TypeReference<List<TimeEditReservation>>() {});
                            } catch (Exception e) {
                                System.err.println(">>> FRONTEND ScheduleService STATUS: mapping error\n" +
                                                    e.getMessage() + "\n");
                                return List.<TimeEditReservation>of();
                            }
                        }).exceptionally(e -> {
                            System.err.println(">>> FRONTEND ScheduleService STATUS: network error\n" +
                                                e.getMessage() + "\n");
                            return List.<TimeEditReservation>of();
                        });
    }
}
