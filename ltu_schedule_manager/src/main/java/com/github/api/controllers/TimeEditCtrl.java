package com.github.api.controllers;

import com.github.api.RestApiRoutable;
import com.github.api.integration.timeedit.TimeEditClient;

import io.javalin.Javalin;

public class TimeEditCtrl implements RestApiRoutable {

    private final TimeEditClient teClient = new TimeEditClient();
    private String teClientURL; 

    @Override
    public void registerEndpoints(Javalin app) {
        
        app.get("/api/time-edit/courses/{courseId}/schedule", ctx -> {

            String courseId = ctx.pathParam("courseId");

            var schedule = teClient.searchAndGetSchedule(courseId);

            ctx.json(schedule);
        });

        app.get("/api/time-edit/fetch/{url}/schedule", ctx -> {

            String courseId = ctx.pathParam("url");

        });
    }


    
}
