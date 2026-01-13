package com.github.api.controllers;

import java.util.List;

import org.eclipse.jetty.http.HttpStatus;

import com.github.api.RestApiRoutable;
import com.github.api.services.integration.TimeEditClient;
import com.github.api.services.mapping.TimeEditWrapper;
import com.github.models.entities.TimeEditReservation;

import io.javalin.Javalin;

record TimeEditResponse(List<TimeEditReservation> reservations) {}

public class TimeEditCtrl implements RestApiRoutable {

    private final TimeEditClient teClient = new TimeEditClient();

    @Override
    public void registerEndpoints(Javalin app) {
        
        app.get("/api/time-edit/courses/{courseId}/schedule", ctx -> {

            String courseId = ctx.pathParam("courseId");

            ctx.future(() -> teClient.searchAndGetSchedule(courseId)
                                     .thenAccept(reservations -> {
                                        if (reservations.isEmpty()) {
                                            ctx.status(HttpStatus.NOT_FOUND_404).result("Error: Inga reservationer hittades för '" + 
                                                                             courseId + "'.");
                                        } else {
                                            ctx.json(new TimeEditResponse(reservations));
                                        }
                                     }).exceptionally(e -> {
                                        ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500).result("Error: internt fel \n" + 
                                                                                    e.getMessage());
                                     return null;  
                                     })
            );
        });

        app.get("/api/time-edit/course/schedule/fetch", ctx -> {

            String targetUrl = ctx.queryParam("url");

            if(targetUrl.isEmpty() || targetUrl.isBlank()) {
                ctx.status(400).result("Error: parameter för 'url' saknas");
                return;
            }

            String jsonUrl = targetUrl.replace(".html", ".json")
                                      .replace(".xml", ".json)");
             
            var schedule = teClient.fetchReservations(jsonUrl);
            ctx.json(new TimeEditResponse(schedule));
        });
    }
}
