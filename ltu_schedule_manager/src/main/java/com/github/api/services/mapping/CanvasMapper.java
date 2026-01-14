package com.github.api.services.mapping;

import com.github.api.dto.CanvasRawDTO;

public class CanvasMapper {
    
    public static CanvasRawDTO toApiWrapper(
        String contextCode, 
        String title, 
        String startIso, 
        String endIso,
        String location,
        String description) {
        
        return new CanvasRawDTO(
            contextCode,
            title,
            startIso,
            endIso,
            location,
            description  
        );
    }
}
