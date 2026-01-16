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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.models.entities.CanvasAuthorization;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.WebClient;

/**
 * Service for interacting with Canvas API.
 */
public class CanvasService {
    private final WebClient webClient = WebClient.getInstance();

       private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Pushes a list of TimeEditReservation events to Canvas via backend API.
     * @param auth
     * @param reservations
     * @return
     */
    public CompletableFuture<String> pushToCanvas(CanvasAuthorization auth, List<TimeEditReservation> reservations) {
        String path = "http://localhost:7070/api/canvas/events/export?contextCode=";

        path = path + auth.getUserId();

        try {
            String jsonBody = mapper.writeValueAsString(reservations);
            return push(path, auth.getExposedToken(), jsonBody);

        } catch (JsonProcessingException e) {
            return CompletableFuture.completedFuture(">>> FRONTEND CANVAS SERVICE STATUS: Mapping error\n" + 
                                                      e.getMessage());
        }
    }
    /**
     * Pushes JSON body to the specified path with authorization token.
     * @param path
     * @param token
     * @param jsonBody
     * @return
     */
    private CompletableFuture<String> push(String path, String token, String jsonBody) {
        return webClient.postAsync(path, token, jsonBody)
                        .exceptionally(e -> {
                            Throwable actual = (e.getCause() != null) ? e.getCause() : e;
                            
                            System.err.println(">>> FRONTEND CANVAS SERVICE STATUS: Push failed\n\t" + 
                                               actual.getMessage());

                            return "FAILED: " + actual.getMessage();
        });
    }

}
