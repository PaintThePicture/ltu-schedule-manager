package com.github.api;

import java.util.Optional;

import io.javalin.Javalin;

public class RestApiServer {
    private static final RestApiServer instance = new RestApiServer();
    
    private Javalin app;    
    private Thread serverThread;
    private final int port = 7070;
    
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

    private void configureJavalin() {
        // Configure Javalin
        app = Javalin.create(conf -> {
            conf.showJavalinBanner = false;
        });

        System.out.println(">>> API SERVER STATUS: API server CONFIGURED");
    }

    private void configureRoutes() {
        // The endpoints
/*         ControllerEpok ctrlEpok = new ControllerEpok();
        ctrlEpok.registerEndpoints(app);

        ControllerLadok ctrlLadok = new ControllerLadok();
        ctrlLadok.registerEndpoints(app);

        ControllerStudentIts ctrlStudentIts = new ControllerStudentIts();
        ctrlStudentIts.registerEndpoints(app);

        System.out.printf(">>> API SERVER STATUS: API routes CONFIGURED\n\t" + 
                                         "ROUTES: \n\t'%s'%n \t'%s'%n \t'%s'%n", 
                                                  ctrlEpok, ctrlLadok, ctrlStudentIts
        ); */
    }

    private void start() {
        serverThread.setDaemon(true);
        serverThread.start();

        System.out.printf(">>> API SERVER STATUS: API is STARTED.\n\t" +
                                               "PORT: '%s'%n", port
        );
    }
}
