package com.github.api.controllers;

import java.util.List;

import org.eclipse.jetty.http.HttpStatus;

import com.github.api.RestApiRoutable;
import com.github.api.services.integration.TimeEditClient;
import com.github.api.services.mapping.TimeEditSchemas;
import com.github.models.entities.TimeEditReservation;

import io.javalin.Javalin;

public class TimeEditCtrl implements RestApiRoutable {

    private final TimeEditClient teClient = new TimeEditClient();

    @Override
    public void registerEndpoints(Javalin app) {
        
        app.get("/api/time-edit/courses/{courseId}/schedule", ctx -> {

            String courseId = ctx.pathParam("courseId");

            ctx.future(() -> teClient.searchAndGetSchedule(courseId)
                                     .thenAccept(dtoList -> {
                                        if (dtoList.isEmpty()) {
                                            ctx.status(404).result(
                                                            ">>> API SERVER STATUS: Not Found\n" +
                                                            "\tSERVICE: TimeEdit\n" +
                                                            "\tDETAIL: No reservations found for course: " + courseId
                                            );
                                        } 
                                        ctx.json(dtoList);
                                     })
                                     .exceptionally(e -> {
                                        Throwable cause = (e.getCause() != null) ? e.getCause() : e;

                                        ctx.status(500).result(">>> API SERVER STATUS: Integration Error\n" +
                                                                    "\tSERVICE: TimeEdit\n" +
                                                                    "\tMESSAGE: " + cause.getMessage()
                                        );
                                        return null;
                                     })
            );
        });

        app.get("/api/time-edit/course/schedule/fetch", ctx -> {
            String rawUrl = ctx.queryParam("url");

            String timeEditUrl = rawUrl.trim().replace(".html", ".json")
                                              .replace(".xml", ".json");

            ctx.future(() -> teClient.fetchCleanedDto(timeEditUrl)
                                     .thenAccept(dtoList -> {
                                        if (dtoList.isEmpty()) { ctx.status(204); }
                                        
                                        ctx.json(dtoList); 
                                     })
                                     .exceptionally(e -> {
                                        Throwable cause = (e.getCause() != null) ? e.getCause() : e;

                                        ctx.status(500).result(">>> API SERVER STATUS: Integration Error\n" +
                                                                    "\tSERVICE: TimeEdit\n" +
                                                                    "\tMESSAGE: " + cause.getMessage()
                                        );
                                        return null;
                                     })
            );
        });
    }
}
