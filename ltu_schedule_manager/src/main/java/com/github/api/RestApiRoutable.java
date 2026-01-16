package com.github.api;

import io.javalin.Javalin;

/**
 * Interface for registering REST API endpoints.
 */
public interface RestApiRoutable {
     @SuppressWarnings("exports")
     void registerEndpoints(Javalin app);
}
