package com.github.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TimeEditRawDTO(
    @JsonProperty("id") String id, 
    @JsonProperty("startdate") String startDate,
    @JsonProperty("starttime") String startTime,
    @JsonProperty("enddate") String endDate,
    @JsonProperty("endtime") String endTime,
    @JsonProperty("columns") List<String> columns
){}
