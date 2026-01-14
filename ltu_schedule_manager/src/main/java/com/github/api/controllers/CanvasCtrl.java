package com.github.api.controllers;

import com.github.api.RestApiRoutable;
import com.github.api.dto.CanvasRawDTO;
import com.github.api.services.integration.CanvasClient;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class CanvasCtrl implements RestApiRoutable {

    private final CanvasClient caClient = new CanvasClient();

    @Override
    public void registerEndpoints(Javalin app) {
        app.post("/api/canvas/events", ctx -> {

            String authHeader = ctx.header("Authorization");
            String token;

            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                ctx.status(HttpStatus.UNAUTHORIZED).result("Error: Canvas Token saknas");
                return;
            }

            CanvasRawDTO eventData = ctx.bodyAsClass(CanvasRawDTO.class);
            
            ctx.future(() -> caClient.pushEvent(token, eventData)
                .thenAccept(response -> {
                    ctx.status(HttpStatus.CREATED)
                       .result(response);
                })
                .exceptionally(e -> {
                    Throwable cause = (e.getCause() != null) ? e.getCause() : e;

                    ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result(">>> API SERVER STATUS: EXCEPTION\n\t" + 
                                                                         cause.getMessage());
                    return null;
                })
            );
        });
    }
}
