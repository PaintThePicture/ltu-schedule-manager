package com.github.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell; 

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.models.TimeEditReservation;
import com.github.models.TimeEditResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

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

        if (url.endsWith(".html")) {
            url = url.replace(".html", ".json");
            tfLinkTimeEdit.setText(url); 
        }

        // Nedan kanske borde göras till en egen klass?
        try {
            // Hämta JSON-data från TimeEdit
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String jsonBody = response.body();

            // Parsning av JSON till Java-objekt (Jackson)
            ObjectMapper mapper = new ObjectMapper();
            TimeEditResponse timeEditData = mapper.readValue(jsonBody, TimeEditResponse.class);

            // Fyll TableView
            ObservableList<TimeEditReservation> reservations = 
                FXCollections.observableArrayList(timeEditData.getReservations());
                
            // Se till att uppdateringen sker på JavaFX Application Thread
            Platform.runLater(() -> {
                resultsTable.setItems(reservations);
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ett fel uppstod vid importen: " + e.getMessage());
        }
    }

    @FXML
    void clickSelectAll(ActionEvent event) {
        for (TimeEditReservation reservation : resultsTable.getItems()) {
            reservation.setSelected(true);
        }
        resultsTable.refresh(); 
    }

    @FXML
    void clickSelectNone(ActionEvent event) {
        for (TimeEditReservation reservation : resultsTable.getItems()) {
            reservation.setSelected(false);
        }
        resultsTable.refresh();
    }

    @FXML
    void clickSetComment(ActionEvent event) {
        String newComment = tfComment.getText();

        if (resultsTable.getItems() == null) {
            return;
        }

        for (TimeEditReservation reservation : resultsTable.getItems()) {
            if (reservation.isSelected()) {
                reservation.setUserComment(newComment);
            }
        }
        
        resultsTable.refresh();
    }

    @FXML
    void clickTransfer(ActionEvent event) {

    }

    @Override
    public void initialize() {
        colSelect.setCellValueFactory(new PropertyValueFactory<>("selected"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("displayDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("displayTimeRange"));
        colActivity.setCellValueFactory(new PropertyValueFactory<>("activity"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("userComment"));

        // Gör tabellen redigerbar
        resultsTable.setEditable(true);

        // Hanterar kommentar-kolumnen (TextField)
        colComment.setCellFactory(TextFieldTableCell.forTableColumn());
        colComment.setEditable(true);

        // Hanterar urvalskolumnen (CheckBox)
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));
    }

    @Override
    public void onUnload() {

    }
}