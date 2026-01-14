package com.github.api.services.mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.api.dto.TimeEditRawDTO;
import com.github.models.entities.TimeEditReservation;

public interface TimeEditSchemas {
    @JsonIgnoreProperties(ignoreUnknown = true)
    record rawResponse(
    @JsonProperty("reservations") List<TimeEditRawDTO> reservations
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ExportResponse(
        @JsonProperty("reservations") List<TimeEditReservation> reservations
    ) {}
}
