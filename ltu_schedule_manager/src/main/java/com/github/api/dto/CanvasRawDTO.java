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