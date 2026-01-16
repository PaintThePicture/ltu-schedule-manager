package com.github.api.services.mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.api.dto.TimeEditRawDTO;
import com.github.models.entities.TimeEditReservation;

/**
 * Defines schema records for TimeEdit API responses.
 */
public interface TimeEditSchemas {
    @JsonIgnoreProperties(ignoreUnknown = true)
    // Schema for TimeEdit API response containing reservations
    record rawResponse(
    @JsonProperty("reservations") List<TimeEditRawDTO> reservations
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    // Schema for TimeEdit API response containing reservations as entities
    record ExportResponse(
        @JsonProperty("reservations") List<TimeEditReservation> reservations
    ) {}
}
