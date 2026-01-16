package com.github.api.services.mapping;

import com.github.api.dto.CanvasRawDTO;

/**
 * Maps input parameters to CanvasRawDTO objects.
 */
public class CanvasMapper {
    
    public static CanvasRawDTO toApiWrapper(
        String contextCode, 
        String title, 
        String displayDate,
        String startTime, 
        String endTime,
        String location,
        String courseCode,
        String description) {
        
        return new CanvasRawDTO(
            contextCode,
            title,
            formatToIso(displayDate, startTime),
            formatToIso(displayDate, endTime),
            location,
            formatDescription(courseCode, description)  
        );
    }

    // Helper method to format date and time to ISO 8601 format
    private static String formatToIso(String date, String time) {
        if (date == null || time == null) {
            return null;
        }
        return date + "T" + time + ":00Z";
    }

    private static String formatDescription(String courseCode, String description) {
        return "Kurskod: " + courseCode + " | " + description;
    }
}
