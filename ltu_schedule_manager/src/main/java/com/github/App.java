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