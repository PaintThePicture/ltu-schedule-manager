/*
 * ltu-schedule-manager: Ett verktyg för att hantera och exportera scheman 
 * från TimeEdit till Canvas för studenter vid Luleå tekniska universitet.
 *
 * Copyright (C) 2025  Alexander Edemalm, 
 * Ronak Olyaee, Therese Henriksson, Jakob Nilsson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301 USA.
 */
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
