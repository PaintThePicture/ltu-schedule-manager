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

    public CompletableFuture<Void> exportEvents(List<TimeEditReservation> reservations, String contextCode, String token) {
            List<CompletableFuture<String>> futures = reservations.stream() 
                                                                  .map(res -> mapToWrap(res, contextCode))
                                                                  .map(json -> webClient.postAsync(EndPoint.CALENDAR.getValue(), token, json))
                                                                  .toList();
        
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private String mapToWrap(TimeEditReservation res, String contextCode) {
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

            return mapper.writeValueAsString(new CanvasSchemas.ExportResponse(rawDTO));
        } catch (Exception e) {
            throw new RuntimeException("Conversion Failed\n\tSOURCE: Canvas Mapper\n\tITEM: " + 
                                        res.getActivity() + "\n\tDETAIL: " + e.getMessage());
        }
    }

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
