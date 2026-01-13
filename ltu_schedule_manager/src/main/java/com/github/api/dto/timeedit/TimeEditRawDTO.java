package com.github.api.dto.timeedit;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TimeEditRawDTO(
    String id, 
    @JsonProperty("startdate") String startDate,
    @JsonProperty("starttime") String startTime,
    @JsonProperty("enddate") String endDate,
    @JsonProperty("endtime") String endTime,
    List<String> columns
){}
