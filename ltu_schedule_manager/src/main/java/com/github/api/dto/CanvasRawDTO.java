package com.github.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Raw calendar event data from the Canvas API.
 *
 * This record mirrors the external Canvas JSON format and is mapped
 * to internal schedule structures before display or transfer.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CanvasRawDTO(
    /** Canvas context code. */
    @JsonProperty("context_code") String contextCode,
    /** Event title. */
    @JsonProperty("title") String title,
    /** Start timestamp (ISO-8601) */
    @JsonProperty("start_at") String startAt,
    /** End timestamp (ISO-8601) */
    @JsonProperty("end_at") String endAt,
    /** Location */
    @JsonProperty("location_name") String locationName,
    /** Event description or details. */
    @JsonProperty("description") String description
) {}