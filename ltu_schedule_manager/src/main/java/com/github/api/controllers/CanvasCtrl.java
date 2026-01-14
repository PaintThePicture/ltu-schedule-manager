package com.github.api.controllers;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.api.RestApiRoutable;
import com.github.api.services.integration.CanvasClient;
import com.github.models.entities.TimeEditReservation;

import io.javalin.Javalin;

/**
 * Controller for handling Canvas-related API endpoints.
 * Currently responsible for exporting TimeEdit reservations to Canvas.
 * Implements the RestApiRoutable interface to register endpoints with Javalin.
 */
public class CanvasCtrl implements RestApiRoutable {

    /**
     * Client for interacting with the Canvas API.
     */
    private final CanvasClient caClient = new CanvasClient();

    /**
     * Registers the API endpoints for Canvas operations.
     * Currently registers:
     *      POST /api/canvas/events/export
     *          - Exports a list of TimeEditReservation objects to Canvas.
     *          - Expects an Authorization header (Bearer token) and an optional contextCode query parameter.
     *          - Returns 201 Created if successful, or 500 Internal Server Error if an error occurs.
     * 
     * @param app The Javalin application instance.
     */
    @Override
    public void registerEndpoints(Javalin app) {

        // Endpoint to export events to Canvas.
        app.post("/api/canvas/events/export", ctx -> {

            // Parse the request body into a list of TimeEditReservation objects.
            List<TimeEditReservation> reservations = ctx.bodyAsClass(new TypeReference<List<TimeEditReservation>>(){}.getType());
            
            // Extract the Authorization header and context code from the request.
            String authHeader = ctx.header("Authorization");
            String contextCode = ctx.queryParam("contextCode");

            // Remove the "Bearer" prefix from the Authorization header, if present.
            if (authHeader != null) {
                authHeader = authHeader.replaceFirst("(?i)Bearer ", "").trim();
            }
            final String token = authHeader;
            
            // Asynchronously export events to Canvas using the CanvasClient.
            ctx.future(() -> caClient.exportEvents(reservations, contextCode, token)
                                    .thenAccept(v -> ctx.status(201).result(
                                        ">>> API SERVER STATUS: Export Successful\n" +
                                        "\tSERVICE: Canvas\n" +
                                        "\tCOUNT: " + reservations.size()
                                    ))
                                    .exceptionally(e -> {
                                        
                                        // Handle any exceptions that occur during the export process.
                                        Throwable cause = (e.getCause() != null) ? e.getCause() : e;

                                        ctx.status(500).result(">>> API SERVER STATUS: Export Error\n" +
                                                                    "\tSERVICE: TimeEdit\n" +
                                                                    "\tMESSAGE: " + cause.getMessage()
                                        );
                                        return null;
                                    })
                            );
        });
    }
}