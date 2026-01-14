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
                                     .thenAccept(reservations -> {
                                        if (reservations.isEmpty()) {
                                            ctx.status(HttpStatus.NOT_FOUND_404)
                                               .result(">>> API SERVER STATUS: Not found\n\t" + 
                                                       "No reservations found for course: " + courseId);
                                        } else {
                                            ctx.json(new TimeEditSchemas.ExportResponse(reservations));
                                        }
                                     })
                                     .exceptionally(e -> {
                                        Throwable cause = e.getCause();
                                            
                                        ctx.status(500).result(">>> API SERVER STATUS: Exception\n\t" + 
                                                                     cause.getMessage());
                                        return null;
                                     })
            );
        });

        app.get("/api/time-edit/course/schedule/fetch", ctx -> {
            
            String targetUrl = ctx.queryParam("url");

            String jsonUrl = targetUrl.replace(".html", ".json")
                                      .replace(".xml", ".json");
             
            ctx.future(() -> teClient.fetchReservations(jsonUrl)
                                     .thenAccept(reservations -> {
                                        ctx.json(new TimeEditSchemas.ExportResponse(reservations));
                                     })
                                     .exceptionally(e -> {
                                        Throwable cause = (e.getCause() != null) ? e.getCause() : e;
                                            
                                        ctx.status(500).result(">>> API SERVER STATUS: Exception\n\t" + 
                                                                     cause.getMessage());
                                        return null;
                                     })
            );
        });
    }
}
