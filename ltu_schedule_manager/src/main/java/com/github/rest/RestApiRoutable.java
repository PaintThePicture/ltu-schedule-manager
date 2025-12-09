package com.github.rest;

import io.javalin.Javalin;

public interface RestApiRoutable {
     @SuppressWarnings("exports")
     void registerEndpoints(Javalin app);
}
