package com.github.models.services;

import com.github.models.entities.TimeEditReservation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScheduleStore {
    private static final ScheduleStore INSTANCE = new ScheduleStore();

    private final ScheduleClient client = new ScheduleClient();

    private final ObservableList<TimeEditReservation> reservations = FXCollections.observableArrayList();

    private ScheduleStore() {}

    public ObservableList<TimeEditReservation> getReservations() {
        return reservations;
    }

    public void loadCourseSchedule(String courseId) {
        client.getScheduleByCourse(courseId).thenAccept(newList -> {
            Platform.runLater(() -> reservations.setAll(newList));
        });
    }

    public void importFromUrl(String url) {
        client.getScheduleByUrl(url).thenAccept(newList -> {
            Platform.runLater(() -> reservations.setAll(newList));
        });
    }

    public static ScheduleStore getInstance() {
        return INSTANCE;
    }
}
