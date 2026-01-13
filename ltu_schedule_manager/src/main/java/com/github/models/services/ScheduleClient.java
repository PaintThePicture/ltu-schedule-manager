package com.github.models.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

record TimeEditResponse(List<TimeEditReservation> reservations) {}

public class ScheduleClient {
    private final WebClient webClient = WebClient.getInstance();
    
    private final ObjectMapper mapper = new ObjectMapper();

    public CompletableFuture<List<TimeEditReservation>> getScheduleByCourse(String courseId) {
        String targetUrl = "http://localhost:7070/api/time-edit/courses/" + courseId + "/schedule";

        return webClient.getAsync(targetUrl)
                        .thenApply(json -> {
                            try {
                                TimeEditResponse response = mapper.readValue(json, TimeEditResponse.class);
                                
                                return response.reservations() != null ? response.reservations() : List.<TimeEditReservation>of();
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

    public CompletableFuture<List<TimeEditReservation>> getScheduleByUrl(String url) {
        String encodedUrl = java.net.URLEncoder.encode(url, java.nio.charset.StandardCharsets.UTF_8);
        String targetUrl = "http://localhost:7070/api/time-edit/course/schedule/fetch?url=" + encodedUrl;
        
        return webClient.getAsync(targetUrl)
                        .thenApply(json -> {
                            try {
                                TimeEditResponse response = mapper.readValue(json, TimeEditResponse.class);
                                
                                return response.reservations() != null ? response.reservations() : List.<TimeEditReservation>of();
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
