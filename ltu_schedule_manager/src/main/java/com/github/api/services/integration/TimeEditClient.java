package com.github.api.services.integration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.dto.TimeEditRawDTO;
import com.github.api.services.mapping.TimeEditSchemas;
import com.github.utilities.WebClient;

public class TimeEditClient {
    
    private final WebClient webClient = WebClient.getInstance();

    private final ObjectMapper mapper = new ObjectMapper();

    public CompletableFuture<List<TimeEditRawDTO>> fetchCleanedDto(String url) {
    return webClient.getAsync(url).thenApply(json -> {
            try {
                TimeEditSchemas.rawResponse wrapper = mapper.readValue(json, TimeEditSchemas
                                                            .rawResponse.class);
                
                return wrapper.reservations() != null ? wrapper.reservations() : List.of();
            } catch (Exception e) {
                throw new RuntimeException("Mapping Failed\n\tSOURCE: TimeEdit API\n\tDETAIL: " + 
                                            e.getMessage());
            }
        });
    }

    public CompletableFuture<List<TimeEditRawDTO>>  searchAndGetSchedule(String courseId) {
        return fetchInteralId(courseId).thenCompose(id -> id.isEmpty()
                                       ? CompletableFuture.completedFuture(List.<TimeEditRawDTO>of())
                                       : fetchCleanedDto(EndPoint.SCHEDULE.getValue() + id));
    }

    private CompletableFuture<String> fetchInteralId(String courseId) {
        return webClient.getAsync(EndPoint.SEARCH.getValue() + courseId) 
                        .thenApply(json -> {
                            try {
                                return mapper.readTree(json).at("/objects/0/id").asText().toUpperCase();
                            } catch (Exception e) {
                                return "";
                            }
                        });
    }

    private enum EndPoint {
        SEARCH("objects.json?fr=f&part=t&sid=3&types=28&search_text="),
        SCHEDULE("s.json?sid=3&p=-20.w,12.w&objects=");

        private final String value;
        private final static String BASE_URL = "https://cloud.timeedit.net/ltu/web/schedule1/";

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

