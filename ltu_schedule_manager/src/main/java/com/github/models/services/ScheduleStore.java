package com.github.models.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.github.models.entities.TimeEditReservation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScheduleStore {
    private static final ScheduleStore INSTANCE = new ScheduleStore();

    private final TimEditService client = new TimEditService();

    private final ObservableList<TimeEditReservation> reservations = FXCollections.observableArrayList();

    private ScheduleStore() {}

    public ObservableList<TimeEditReservation> getReservations() {
        return reservations;
    }

    public ObservableList<TimeEditReservation> getSelectedReservations(){
        return reservations.stream()
                           .filter(TimeEditReservation::isSelected)
                           .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    
    public List<TimeEditReservation> getSelectedReservationsToList(){
        return reservations.stream()
                           .filter(TimeEditReservation::isSelected)
                           .toList();
    }

    public void loadCourseSchedule(String courseId) {
        client.getScheduleByCourse(courseId).thenAccept(newList -> {
            Platform.runLater(() -> reservations.setAll(newList));
        });
    }

    public void importFromUrl(String url) {
        client.fetchFromApi(url).thenAccept(newList -> {
            Platform.runLater(() -> reservations.setAll(newList));
        });
    }

    public static ScheduleStore getInstance() {
        return INSTANCE;
    }
}
