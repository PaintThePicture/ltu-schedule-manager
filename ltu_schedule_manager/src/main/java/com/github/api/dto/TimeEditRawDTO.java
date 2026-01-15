package com.github.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Raw reservation data returned by the TimeEdit API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TimeEditRawDTO(
    /** Reservation identifier. */
    @JsonProperty("id") String id, 
     /** Start date. */
    @JsonProperty("startdate") String startDate,
    /** Start time. */
    @JsonProperty("starttime") String startTime,
     /** End date. */
    @JsonProperty("enddate") String endDate,
    /** End time. */
    @JsonProperty("endtime") String endTime,
     /** Columns. */
    @JsonProperty("columns") List<String> columns
){}
