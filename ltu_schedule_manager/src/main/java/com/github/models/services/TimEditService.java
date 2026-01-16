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
package com.github.models.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.api.dto.TimeEditRawDTO;
import com.github.api.services.mapping.TimeEditMapper;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

/**
 * Service for interacting with TimeEdit API.
 */
public class TimEditService {
    private final WebClient webClient = WebClient.getInstance();
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Fetches schedule for a course by its ID.
     * @param courseId
     * @return
     */
    public CompletableFuture<List<TimeEditReservation>> getScheduleByCourse(String courseId) {
        String path = "http://localhost:7070/api/time-edit/courses/";

        return WebClient.getInstance().getAsync(path + courseId + "/schedule")
                                      .thenApply(this::processApiResponse);
    }
    /**
     *  Fetches reservations from TimeEdit API via backend service.
     * @param timeEditUrl
     * @return
     */
    public CompletableFuture<List<TimeEditReservation>> fetchFromApi(String timeEditUrl) {
        String path = "http://localhost:7070/api/time-edit/course/schedule/fetch?url=";

        return WebClient.getInstance().getAsync(path + encodeUrl(timeEditUrl))
                                      .thenApply(this::processApiResponse);
    }
    // Processes the API JSON response into a list of TimeEditReservation entities
    private List<TimeEditReservation> processApiResponse(String json) {
        try {
            List<TimeEditRawDTO> dtos = mapper.readValue(json, new com.fasterxml.jackson.core.type.TypeReference<>() {});
            return dtos.stream().map(TimeEditMapper::toEntity).toList();
        } catch (Exception e) {
            System.err.println(">>> UI STATUS ERROR: Data Processing\n" +
                               "\tSTAGE: JSON Mapping\n" +
                               "\tMESSAGE: " + e.getMessage());
            return List.of();
        }
    }
    // Encodes the URL to be safely included as a query parameter
    private static String encodeUrl(String rawUrl) {
        return java.net.URLEncoder.encode(rawUrl, java.nio.charset.StandardCharsets.UTF_8);
    }
}
