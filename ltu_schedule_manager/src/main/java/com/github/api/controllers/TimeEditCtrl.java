package com.github.api.controllers;

import com.github.api.RestApiRoutable;
import com.github.api.integration.timeedit.TimeEditClient;

import io.javalin.Javalin;

public class TimeEditCtrl implements RestApiRoutable {

    private final TimeEditClient teClient = new TimeEditClient();

    @Override
    public void registerEndpoints(Javalin app) {
        
        app.get("/api/time-edit/courses/{courseId}/schedule", ctx -> {

            String courseId = ctx.pathParam("courseId");

            if (courseId.isEmpty() || courseId.isBlank()) {
                ctx.status(400).result("Error: Kurskod saknas.");
                return;
            }

            var schedule = teClient.searchAndGetSchedule(courseId);

            if(schedule.isEmpty()) {
                ctx.status(404).result("Error: schema för kurs " + courseId + " kunde inte hittas");
            } else {
                ctx.json(schedule);
            }
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
            ctx.json(schedule);
        });
    }


    
}
