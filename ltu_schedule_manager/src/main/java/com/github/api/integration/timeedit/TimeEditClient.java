package com.github.api.integration.timeedit;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

@JsonIgnoreProperties(ignoreUnknown = true)
record TimeEditResponse(List<TimeEditReservation> reservations) {}

public class TimeEditClient {
    
    private final WebClient webClient = WebClient.getInstance();

    private final ObjectMapper mapper = new ObjectMapper();

    public List<TimeEditReservation> fetchReservations(String URL) {
        try {
            String json = webClient.fetchAsync(URL).join();

            TimeEditResponse response = mapper.readValue(json, TimeEditResponse.class);

            return response.reservations() != null ? response.reservations() : List.<TimeEditReservation>of();
        
        } catch (Exception e) {
            System.err.println(">>> API SERVER STATUS: API route Exception at retrieving\n" + 
                               e.getMessage() + "\n");
        } 
        return List.of();
    }

    public List<TimeEditReservation> searchAndGetSchedule(String courseId) {
        try {

            String searchJson = webClient.fetchAsync(EndPoint.SEARCH + courseId).join();

            return searchTimeEditId(searchJson).filter(nodes -> nodes.isArray() && !nodes.isEmpty())
                                               .map(nodes -> {
                                                
                String timeEditId = nodes.get(0).get("id").asText().toUpperCase();
                                                
                return fetchReservations(EndPoint.SCHEDULE + timeEditId);

            }).orElse(List.of());

        } catch (Exception e) {
            System.err.println(">>> API SERVER STATUS: API route Exception at searching\n" + 
                               e.getMessage() + "\n");
        }
        return List.of();
    }

    private Optional<com.fasterxml.jackson.databind.JsonNode> searchTimeEditId(String searchJson) {
        try {
            return Optional.ofNullable(mapper.readTree(searchJson).get("objects"));
        } catch (Exception e) {
            System.out.println(">>> API SERVER STATUS: API route Exception Json Parsing\n" +
                               e.getMessage() + "\n");
        }
        return Optional.empty();
    }

    private enum EndPoint {
        SEARCH("/objects.json?fr=f&part=t&sid=3&types=28&search_text="),
        SCHEDULE("/s.json?sid=3&p=-20.w,12.w&objects=");

        private final String value;
        private final static String BASE_URL = "https://cloud.timeedit.net/ltu/web/schedule1";

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

