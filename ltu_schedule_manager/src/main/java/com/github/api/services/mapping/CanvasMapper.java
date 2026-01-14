package com.github.api.services.mapping;

import com.github.api.dto.CanvasRawDTO;

public class CanvasMapper {
    
    public static CanvasRawDTO toApiWrapper(
        String contextCode, 
        String title, 
        String displayDate,
        String startTime, 
        String endTime,
        String location,
        String description) {
        
        return new CanvasRawDTO(
            contextCode,
            title,
            formatToIso(displayDate, startTime),
            formatToIso(displayDate, endTime),
            location,
            description  
        );
    }
    private static String formatToIso(String date, String time) {
        if (date == null || time == null) {
            return null;
        }
        return date + "T" + time + ":00Z";
    }
}
