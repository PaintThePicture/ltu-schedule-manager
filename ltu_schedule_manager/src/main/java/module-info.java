module com.github {
    // 1. Core Java and JavaFX Modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.net.http;
    requires java.sql;
    requires java.logging;

    // 2. External Library Dependencies
    requires io.javalin;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    // 3. EXPORTS (Allows classes to be imported by other packages)
    exports com.github;
    exports com.github.controllers;
    exports com.github.models.entities;
    exports com.github.models.services;
    exports com.github.api.dto;
    exports com.github.utilities;
    exports com.github.api;
    exports com.github.viewmodels.viewNavigation;

    // 4. OPENS (Required for Reflection - used by JavaFX FXML and Jackson JSON)
    
    // Required for JavaFX to inject members into @FXML annotated fields
    opens com.github.controllers to javafx.fxml;
    opens com.github.viewmodels.viewNavigation to javafx.fxml;
    
    // Required for JavaFX TableView to access fields via PropertyValueFactory
    opens com.github.models.entities to javafx.base;

    // Required for Jackson to serialize/deserialize JSON to Java objects
    opens com.github.api.dto to com.fasterxml.jackson.databind;
    opens com.github.api.services.mapping to com.fasterxml.jackson.databind;
    opens com.github.models.services to com.fasterxml.jackson.databind;
}