package com.github.api.dto.canvas;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CanvasRawDTO(
    @JsonProperty("context_code") String contextCode,
    @JsonProperty("title") String title,
    @JsonProperty("start_at") String startAt,
    @JsonProperty("end_at") String endAt,
    @JsonProperty("location_name") String locationName,
    @JsonProperty("description") String description
) {}