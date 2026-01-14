package com.github.api.services.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.api.dto.CanvasRawDTO;

public interface CanvasSchemas {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ExportResponse(
        @JsonProperty("calendar_event") CanvasRawDTO calendarEvent
    ) {}
} 