package com.github.api.controllers;

import java.util.List;

import org.eclipse.jetty.http.HttpStatus;

import com.github.api.RestApiRoutable;
import com.github.api.services.integration.TimeEditClient;
import com.github.api.services.mapping.TimeEditSchemas;
import com.github.models.entities.TimeEditReservation;

import io.javalin.Javalin;

/**
 * Controller for handling TimeEdit-related API endpoints.
 * Responsible for fetching course schedules from TimeEdit and returning them via API.
 * Implements the RestApiRoutable interface to register endpoints with Javalin.
 */
public class TimeEditCtrl implements RestApiRoutable {

    /**
     * Client for interacting with the TimeEdit API.
     */
    private final TimeEditClient teClient = new TimeEditClient();

    /**
     * Registers the API endpoints for TimeEdit operations.
     * 
     * 1. GET /api/time-edit/courses/{courseId}/schedule
     *    - Path parameter: courseId
     *    - Returns 200 with JSON list of reservations if found
     *    - Returns 404 if no reservations exist
     *    - Returns 500 on integration error
     * 
     * 2. GET /api/time-edit/course/schedule/fetch
     *    - Query parameter: url
     *    - Returns 200 with cleaned JSON list
     *    - Returns 204 if no data found
     *    - Returns 500 on integration error
     * @param app The Javalin application instance.
     */

    
    @Override
    public void registerEndpoints(Javalin app) {
        // Endpoint to fetch the schedule for a specific course by courseId.
        app.get("/api/time-edit/courses/{courseId}/schedule", ctx -> {

            // Extract the courseId from the path parameter.
            String courseId = ctx.pathParam("courseId");

            // Asynchronously fetch the schedule for the given courseId.
            ctx.future(() -> teClient.searchAndGetSchedule(courseId)
                                     .thenAccept(dtoList -> {
                                        if (dtoList.isEmpty()) {
                                            
                                            // Respond with 404 if no reservations are found.
                                            ctx.status(404).result(
                                                            ">>> API SERVER STATUS: Not Found\n" +
                                                            "\tSERVICE: TimeEdit\n" +
                                                            "\tDETAIL: No reservations found for course: " + courseId
                                            );
                                        } 

                                        // Respond with the fetched schedule as JSON.
                                        ctx.json(dtoList);
                                     })
                                     .exceptionally(e -> {

                                        // Handle any exceptions that occur during the fetch process.
                                        Throwable cause = (e.getCause() != null) ? e.getCause() : e;

                                        ctx.status(500).result(">>> API SERVER STATUS: Integration Error\n" +
                                                                    "\tSERVICE: TimeEdit\n" +
                                                                    "\tMESSAGE: " + cause.getMessage()
                                        );
                                        return null;
                                     })
            );
        });

        // Endpoint to fetch and clean a schedule from a raw TimeEdit URL.
        app.get("/api/time-edit/course/schedule/fetch", ctx -> {

            // Extract and clean the raw URL from the query parameter.
            String rawUrl = ctx.queryParam("url");

            // Convert .html or .xml to .json for the API call
            String timeEditUrl = rawUrl.trim().replace(".html", ".json")
                                              .replace(".xml", ".json");

            // Asynchronously fetch and clean the schedule from the TimeEdit URL.
            ctx.future(() -> teClient.fetchCleanedDto(timeEditUrl)
                                     .thenAccept(dtoList -> {
                                        if (dtoList.isEmpty()) { 

                                            // Respond with 204 if no data is found.
                                            ctx.status(204); 
                                        }
                                        // Respond with the cleaned schedule as JSON.
                                        ctx.json(dtoList); 
                                     })
                                     .exceptionally(e -> {

                                        // Handle any exceptions that occur during the fetch process.
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
