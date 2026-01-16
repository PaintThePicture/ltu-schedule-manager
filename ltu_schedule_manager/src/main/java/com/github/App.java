/*
 * ltu-schedule-manager: Ett verktyg för att hantera och exportera scheman 
 * från TimeEdit till Canvas för studenter vid Luleå tekniska universitet.
 *
 * Copyright (C) 2025  Alexander Edemalm, 
 * Ronak Olyaee, Therese Henriksson, Jakob Nilsson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301 USA.
 */
package com.github;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import com.github.api.RestApiServer;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void init() {
        // förberereder javalin
        /* JavalinStartup.getInstance().setup(); */
        RestApiServer.getInstance().run();
    }
  
    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {

        // Startar javafx applicationen
        AppJavaFX.getInstance().run(stage);
    }

    @Override
    public void stop() throws Exception {
        RestApiServer.getInstance().stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }

}