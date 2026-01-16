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

    /**
     * Fetches and cleans TimeEditRawDTO list from the given URL.
     * @param url
     * @return
     */
    public CompletableFuture<List<TimeEditRawDTO>> fetchCleanedDto(String url) {
        // Fetch raw JSON from the API
        return webClient.getAsync(url).thenApply(json -> {
                try {
                    TimeEditSchemas.rawResponse wrapper = mapper.readValue(json, TimeEditSchemas
                                                                .rawResponse.class);
                    // Return the list of reservations or an empty list if null
                    return wrapper.reservations() != null ? wrapper.reservations() : List.of();
                } catch (Exception e) {
                    throw new RuntimeException("Mapping Failed\n\tSOURCE: TimeEdit API\n\tDETAIL: " + 
                                                e.getMessage());
                }
            });
    }
    /**
     * Searches for the internal ID of a course and fetches its schedule.
     * @param courseId
     * @return
     */
    public CompletableFuture<List<TimeEditRawDTO>>  searchAndGetSchedule(String courseId) {
        // First fetch the internal ID, then fetch the schedule using that ID
        return fetchInternalId(courseId).thenCompose(id -> id.isEmpty()
                                       ? CompletableFuture.completedFuture(List.<TimeEditRawDTO>of())
                                       : fetchCleanedDto(EndPoint.SCHEDULE.getValue() + id));
    }

    // Fetches the internal ID for a given course ID
    private CompletableFuture<String> fetchInternalId(String courseId) {
        // Make an asynchronous GET request to search for the course
        return webClient.getAsync(EndPoint.SEARCH.getValue() + courseId) 
                        .thenApply(json -> {
                            try {
                                return mapper.readTree(json).at("/objects/0/id").asText().toUpperCase();
                            } catch (Exception e) {
                                return "";
                            }
                        });
    }
    // EndPoint enum for TimeEdit API endpoints
    private enum EndPoint {
        SEARCH("objects.json?fr=f&part=t&sid=3&types=28&search_text="),
        SCHEDULE("s.json?sid=3&p=-26.w,52.w&objects=");

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

