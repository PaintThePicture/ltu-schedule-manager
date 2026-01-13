package com.github.api.services.mapping;

import com.github.api.dto.canvas.CanvasRawDTO;

public class CanvasMapper {
    
    public static CanvasWrapper toApiWrapper(
        String contextCode, 
        String title, 
        String startIso, 
        String endIso,
        String location,
        String description) {
        
        CanvasRawDTO raw = new CanvasRawDTO(
            contextCode,
            title,
            startIso,
            endIso,
            location,
            description  
        );

        return new CanvasWrapper(raw);
    }
}
