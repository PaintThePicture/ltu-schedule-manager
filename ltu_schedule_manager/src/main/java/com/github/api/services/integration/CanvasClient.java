package com.github.api.services.integration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.dto.CanvasRawDTO;
import com.github.api.services.mapping.CanvasMapper;
import com.github.api.services.mapping.CanvasSchemas;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

public class CanvasClient {
    
    private final WebClient webClient = WebClient.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Exports a list of TimeEditReservation events to Canvas calendar.
     * @param reservations
     * @param contextCode
     * @param token
     * @return
     */
    public CompletableFuture<Void> exportEvents(List<TimeEditReservation> reservations, String contextCode, String token) {
        
        // Map each reservation to a JSON string and then asynchronously post it
        List<CompletableFuture<String>> futures = reservations.stream() 
                                                              .map(res -> mapToWrap(res, contextCode))
                                                              .map(json -> webClient.postAsync(EndPoint.CALENDAR.getValue(), token, json))
                                                              .toList();
        // Combine all futures into one
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }
    /**
     * Maps a TimeEditReservation to a JSON string for Canvas API.
     * @param res
     * @param contextCode
     * @return
     */
    private String mapToWrap(TimeEditReservation res, String contextCode) {
        // Convert TimeEditReservation to CanvasRawDTO using CanvasMapper
        try {
            CanvasRawDTO rawDTO = CanvasMapper.toApiWrapper(
                contextCode,
                res.getActivity(),
                res.getDisplayDate(),
                res.getStartTime(), 
                res.getEndTime(),
                res.getLocation(),
                res.getCourseCode(),
                res.getUserComment() 
            );
            // Wrap the DTO in the expected CanvasSchemas.ExportResponse structure
            return mapper.writeValueAsString(new CanvasSchemas.ExportResponse(rawDTO));
        } catch (Exception e) {
            throw new RuntimeException("Conversion Failed\n\tSOURCE: Canvas Mapper\n\tITEM: " + 
                                        res.getActivity() + "\n\tDETAIL: " + e.getMessage());
        }
    }
    // EndPoint enum for Canvas API endpoints  
    private enum EndPoint {
        CALENDAR("calendar_events");

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
