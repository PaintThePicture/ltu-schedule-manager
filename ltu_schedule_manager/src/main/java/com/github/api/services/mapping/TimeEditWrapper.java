package com.github.api.services.mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.api.dto.timeedit.TimeEditRawDTO;

public record TimeEditWrapper(
    @JsonProperty("reservations") List<TimeEditRawDTO> reservations
) {}
