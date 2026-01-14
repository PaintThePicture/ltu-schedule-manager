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

import com.github.models.entities.TimeEditReservation;
import com.github.models.services.CanvasService;
import com.github.models.services.ScheduleStore;
import com.github.models.services.ScheduleTableBuilder;
import com.github.models.services.TimEditService;
import com.github.utilities.AltertWindowBuilder;
import com.github.viewmodels.viewNavigation.ViewController;

public class PrimaryController implements ViewController {

    private final com.github.models.services.TimEditService timeEditService = new TimEditService();
    private final com.github.models.services.CanvasService canvasService = new CanvasService();

    @FXML 
    private TableView<TimeEditReservation> resultsTable;
    @FXML
    private TableColumn<TimeEditReservation, Boolean> colSelect;
    @FXML
    private TableColumn<TimeEditReservation, String> colDate;
    @FXML
    private TableColumn<TimeEditReservation, String> colTime;
    @FXML
    private TableColumn<TimeEditReservation, String> colActivity;
    @FXML
    private TableColumn<TimeEditReservation, String> colLocation;
    @FXML
    private TableColumn<TimeEditReservation, String> colCourseCode;
    @FXML
    private TableColumn<TimeEditReservation, String> colComment;
    @FXML
    private TextField tfLinkTimeEdit;
    @FXML
    private TextField tfComment;
 
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

    @FXML
    void clickSelectAll(ActionEvent event) {
        ScheduleStore.getInstance().getReservations().forEach(r -> r.setSelected(true));
        
        resultsTable.refresh();
    }

    @FXML
    void clickSelectNone(ActionEvent event) {
        ScheduleStore.getInstance().getReservations().forEach(r -> r.setSelected(false));
        
        resultsTable.refresh();
    }

    @FXML
    void clickSetComment(ActionEvent event) {
        String comment = tfComment.getText();

        ScheduleStore.getInstance().getReservations().stream()
                     .filter(TimeEditReservation::isSelected)
                     .forEach(r -> r.setUserComment(comment));

        resultsTable.refresh();
    }

    @FXML
    void clickTransfer(ActionEvent event) {
        String hardcodedToken = "";
        String targetUserId = "user_";

        List<TimeEditReservation> toExport = ScheduleStore.getInstance().getSelectedReservationsToList();

        canvasService.pushToCanvas(hardcodedToken, targetUserId, toExport)
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

    @Override
    public void onUnload() {

    }
}