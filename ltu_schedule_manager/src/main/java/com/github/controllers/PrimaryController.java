package com.github.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.github.models.entities.CanvasAuthorization;
import com.github.models.entities.TimeEditReservation;
import com.github.models.services.CanvasService;
import com.github.models.services.ScheduleStore;
import com.github.models.services.ScheduleTableBuilder;
import com.github.models.services.TimEditService;
import com.github.utilities.AltertWindowBuilder;
import com.github.viewmodels.viewNavigation.ViewController;

/**
 * Controller for the main schedule view.
 */
public class PrimaryController implements ViewController {
    /** Service for fetching reservations from TimeEdit. */
    private final com.github.models.services.TimEditService timeEditService = new TimEditService();
    /** Service for pushing reservations to Canvas. */
    private final com.github.models.services.CanvasService canvasService = new CanvasService();

    /** Table showing imported reservations. */
    @FXML 
    private TableView<TimeEditReservation> resultsTable;
     /** Selection column. */
    @FXML
    private TableColumn<TimeEditReservation, Boolean> colSelect;
    /** Date column. */
    @FXML
    private TableColumn<TimeEditReservation, String> colDate;
    /** Time column. */
    @FXML
    private TableColumn<TimeEditReservation, String> colTime;
    /** Activity column. */
    @FXML
    private TableColumn<TimeEditReservation, String> colActivity;
    /** Location column. */
    @FXML
    private TableColumn<TimeEditReservation, String> colLocation;
    /** Course code column. */
    @FXML
    private TableColumn<TimeEditReservation, String> colCourseCode;
    /** Comment column. */   
    @FXML
    private TableColumn<TimeEditReservation, String> colComment;
    /** Search input for TimeEdit URL or course code. */
    @FXML
    private TextField tfLinkTimeEdit;
    /** Comment text applied to selected reservations. */
    @FXML
    private TextField tfComment;
    
    /** Imports reservations from TimeEdit based on the search input. */
    @FXML
    void clickImport(ActionEvent event) {
        String input = tfLinkTimeEdit.getText();

        CompletableFuture<List<TimeEditReservation>> future;

        if (input.isEmpty()) {
            new AltertWindowBuilder.Builder(AlertType.WARNING, "Tomt sökfält!", "Sökfält är tomt")
                                   .withHeader("Inga ord att söka med")
                                   .build()
                                   .show();
        }

        if (input.contains("http")) {
            future = timeEditService.fetchFromApi(input);
        } else {
            future = timeEditService.getScheduleByCourse(input);
        }
        
        future.thenAccept(list -> {
               Platform.runLater(() -> {
                    ScheduleStore.getInstance().getReservations().setAll(list);
                    resultsTable.refresh();

                    System.out.println(">>> UI STATUS SUCCESS: \n\tACTION: Import\n\tCOUNT: " + list.size());
                    });
               })
               .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        Throwable cause = (ex.getCause() != null) ? ex.getCause() : ex;
            
                        System.err.println(">>> UI STATUS ERROR: Task Failed\n" +
                                           "\tSOURCE: RestApiServer\n" +
                                           "\tDETAIL: " + cause.getMessage());
                    });
                return null;
               });
    }
    
    /** Selects all reservations in the table. */
    @FXML
    void clickSelectAll(ActionEvent event) {
        ScheduleStore.getInstance().getReservations().forEach(r -> r.setSelected(true));
        
        resultsTable.refresh();
    }
    /** Deselects all reservations in the table. */
    @FXML
    void clickSelectNone(ActionEvent event) {
        ScheduleStore.getInstance().getReservations().forEach(r -> r.setSelected(false));
        
        resultsTable.refresh();
    }
    /** Applies the comment field to all selected reservations. */
    @FXML
    void clickSetComment(ActionEvent event) {
        String comment = tfComment.getText();

        ScheduleStore.getInstance().getReservations().stream()
                     .filter(TimeEditReservation::isSelected)
                     .forEach(r -> r.setUserComment(comment));

        resultsTable.refresh();
    }
    /** Transfers selected reservations to Canvas. */
    @FXML
    void clickTransfer(ActionEvent event) {

        List<TimeEditReservation> toExport = ScheduleStore.getInstance().getSelectedReservationsToList();

        CanvasAuthorization auth = new CanvasAuthorization("", "");
        
        canvasService.pushToCanvas(auth, toExport)
                     .whenComplete((response, ex) -> {
                        auth.wipe(); 
                        System.out.println(">>> Security: Token wiped from memory");
                     })
                     .thenAccept(response -> {
                        Platform.runLater(() -> {
                            System.out.println(">>> UI STATUS SUCCESS: \n\tACTION: Transfer\n\tDETAIL: " + response);
                            
                            toExport.forEach(res -> res.setSelected(false));
                            resultsTable.refresh(); 
                        });
                     })
                     .exceptionally(ex -> {
                        Platform.runLater(() -> {
                            Throwable cause = (ex.getCause() != null) ? ex.getCause() : ex;
                            System.err.println(">>> UI STATUS ERROR: Task Failed\n" +
                                            "\tSOURCE: CanvasService\n" +
                                            "\tDETAIL: " + cause.getMessage());
                        });
                        return null;
                     });
    }
    /** Initializes the schedule table and column bindings after FXML loading. */
    @Override
    public void initialize() {
        new ScheduleTableBuilder.Builder(resultsTable, colSelect, colDate, colTime)
            .withActivity(colActivity)
            .withLocation(colLocation)
            .withCourseCode(colCourseCode)
            .withComment(colComment)
            .build();

        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }
    /** Called when the view is unloaded. */
    @Override
    public void onUnload() {

    }
}