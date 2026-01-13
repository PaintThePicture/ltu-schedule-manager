package com.github.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import com.github.models.entities.TimeEditReservation;
import com.github.models.services.ScheduleStore;
import com.github.models.services.ScheduleTableBuilder;
import com.github.viewmodels.viewNavigation.ViewController;

public class PrimaryController implements ViewController {

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
        String url = tfLinkTimeEdit.getText();

        ScheduleStore.getInstance().importFromUrl(url);
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