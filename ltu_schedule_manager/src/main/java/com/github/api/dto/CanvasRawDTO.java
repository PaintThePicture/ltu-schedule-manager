package com.github.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CanvasRawDTO(
    @JsonProperty("context_code") String contextCode,
    @JsonProperty("title") String title,
    @JsonProperty("start_at") String startAt,
    @JsonProperty("end_at") String endAt,
    @JsonProperty("location_name") String locationName,
    @JsonProperty("description") String description
) {}