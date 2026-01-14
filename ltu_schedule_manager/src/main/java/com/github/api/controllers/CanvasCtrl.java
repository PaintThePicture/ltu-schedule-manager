package com.github.api.controllers;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.api.RestApiRoutable;
import com.github.api.services.integration.CanvasClient;
import com.github.models.entities.TimeEditReservation;

import io.javalin.Javalin;


public class CanvasCtrl implements RestApiRoutable {

    private final CanvasClient caClient = new CanvasClient();

    @Override
    public void registerEndpoints(Javalin app) {
        app.post("/api/canvas/events/export", ctx -> {
            List<TimeEditReservation> reservations = ctx.bodyAsClass(new TypeReference<List<TimeEditReservation>>(){}.getType());
            
            String authHeader = ctx.header("Authorization");
            String contextCode = ctx.queryParam("contextCode");

            if (authHeader != null) {
                authHeader = authHeader.replaceFirst("(?i)Bearer ", "").trim();
            }
            final String token = authHeader;
            
            ctx.future(() -> caClient.exportEvents(reservations, contextCode, token)
                                    .thenAccept(v -> ctx.status(201).result(
                                        ">>> API SERVER STATUS: Export Successful\n" +
                                        "\tSERVICE: Canvas\n" +
                                        "\tCOUNT: " + reservations.size()
                                    ))
                                    .exceptionally(e -> {
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