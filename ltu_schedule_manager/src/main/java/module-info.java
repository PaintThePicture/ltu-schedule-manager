module com.github {

    // Javafx Module Requirments
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    

    // Javalin and supporting dependencies
    requires io.javalin;
    requires org.slf4j;
    requires java.logging; // often needed by SLF4J implementations
    requires java.net.http;

    // Jackson (for JSON serialization/deserialization)
    // Javalin uses these internally to handle JSON requests/responses
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    
    // JDBC
    requires java.sql;
    requires com.fasterxml.jackson.annotation;

    // REST 
    exports com.github.api;

    exports com.github;
    
    opens com.github.api.controllers to 
        com.fasterxml.jackson.databind, io.javalin;
        
    opens com.github.api.integration.timeedit to 
        com.fasterxml.jackson.databind;

    // Javafx, Spring Core
    opens com.github.controllers to 
        javafx.fxml, 
        io.javalin, // Open to Javalin if it needs to access classes/records here
        com.fasterxml.jackson.databind; // Open to Jackson for JSON mapping

    opens com.github.models.entities to 
        javafx.fxml, 
        javafx.base,        // Krävs för PropertyValueFactory att komma åt getters/properties
        javafx.controls,    // Krävs för CheckBoxTableCell att komma åt CheckBox-properties
        com.fasterxml.jackson.databind;
    
    opens com.github.models.services to
        com.fasterxml.jackson.databind;
        
    opens com.github.utilities to 
        com.fasterxml.jackson.databind;
}
