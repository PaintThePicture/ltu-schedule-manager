package com.github.api.services.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.api.dto.canvas.CanvasRawDTO;

public record CanvasWrapper(
    @JsonProperty("calendar_event") CanvasRawDTO calendarEvent
) {}