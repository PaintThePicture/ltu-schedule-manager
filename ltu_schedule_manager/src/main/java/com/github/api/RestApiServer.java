package com.github.api;

import java.util.List;
import java.util.Optional;

import com.github.api.controllers.CanvasCtrl;
import com.github.api.controllers.TimeEditCtrl;

import io.javalin.Javalin;

/**
 * Singleton class to manage the REST API server using Javalin.
 */
public class RestApiServer {
    private static final RestApiServer instance = new RestApiServer();
    
    private Javalin app;    
    private Thread serverThread;
    private final int port = 7070;
    
    /**
     * Starts the REST API server in a separate thread.
     */
    public void run() {
        System.out.println(">>> API SERVER STATUS: API is initializing ");

        // If thread is active prints status, else creates thread
        Optional.ofNullable(serverThread).ifPresentOrElse(st -> {
            System.out.printf(">>> API SERVER STATUS: %s%n", st.isAlive());
        },() -> {
            // Create new thread
            serverThread = new Thread(() -> {  
                configureJavalin(); // configures javalin
                
                configureRoutes(); // configure endpoints
                
                app.start(port); // Start Javalin
            });
            // Start thread
            start();
        });
    }
    /**
     * Stops the REST API server if it is running.
     */
    public void stop() {
        // IF server thread active, shutsdown api server
        Optional.ofNullable(serverThread).ifPresent(st -> {
            // Stops javalin
            if(app != null) { app.stop(); }

            try {
                st.join(); // Gracefull stop of server thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                
                System.out.println(">>> API SERVER STATUS: Thread interrupted.\n");
            } finally {
                // Resets REST API server
                app = null;
                serverThread = null;

                System.out.println(">>> API SERVER STATUS: Succesfully shutdown.\n");
            }
        });
    }

    public int getPort() {
        return port; 
    }

    public static RestApiServer getInstance() {
        return instance;
    }
    // Configures Javalin server
    private void configureJavalin() {
        // Configure Javalin
        app = Javalin.create(conf -> {
            conf.showJavalinBanner = false;
        });

        System.out.println(">>> API SERVER STATUS: API server CONFIGURED");
    }
    // Configures API routes
    private void configureRoutes() {

        List<RestApiRoutable> routes = List.of(
            new CanvasCtrl(),
            new TimeEditCtrl()
        );
        
        System.out.println(">>> API SERVER STATUS: API routes CONFIGURED\n\t" + 
                                          "ROUTES:"
        );
        
        routes.forEach(route -> {
            route.registerEndpoints(app);
            System.out.println("\t" + route.getClass().getSimpleName());
        });
    }
    // Starts the server thread
    private void start() {
        serverThread.setDaemon(true);
        serverThread.start();

        System.out.printf(">>> API SERVER STATUS: API is STARTED.\n\t" +
                                               "PORT: '%s'%n", port
        );
    }
}
