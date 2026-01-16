/*
 * ltu-schedule-manager: Ett verktyg för att hantera och exportera scheman 
 * från TimeEdit till Canvas för studenter vid Luleå tekniska universitet.
 *
 * Copyright (C) 2025  Alexander Edemalm, 
 * Ronak Olyaee, Therese Henriksson, Jakob Nilsson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301 USA.
 */
package com.github.models.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.github.models.entities.TimeEditReservation;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton store for managing schedule reservations.
 */
public class ScheduleStore {
    private static final ScheduleStore INSTANCE = new ScheduleStore();

    private final TimEditService client = new TimEditService();

    private final ObservableList<TimeEditReservation> reservations = FXCollections.observableArrayList();

    private ScheduleStore() {}

    /**
     * Gets the observable list of reservations.
     * @return
     */
    public ObservableList<TimeEditReservation> getReservations() {
        return reservations;
    }

    /**
     * Gets the list of selected reservations.
     * @return
     */
    public ObservableList<TimeEditReservation> getSelectedReservations(){
        return reservations.stream()
                           .filter(TimeEditReservation::isSelected)
                           .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    /**
     * Gets the list of selected reservations as a standard list.
     * @return
     */
    public List<TimeEditReservation> getSelectedReservationsToList(){
        return reservations.stream()
                           .filter(TimeEditReservation::isSelected)
                           .toList();
    }
    /**
     * Loads schedule for the specified course ID.
     * @param courseId
     */
    public void loadCourseSchedule(String courseId) {
        client.getScheduleByCourse(courseId).thenAccept(newList -> {
            Platform.runLater(() -> reservations.setAll(newList));
        });
    }
    /**
     * Imports reservations from the specified URL.
     * @param url
     */
    public void importFromUrl(String url) {
        client.fetchFromApi(url).thenAccept(newList -> {
            Platform.runLater(() -> reservations.setAll(newList));
        });
    }
    /**
     * Gets the singleton instance of ScheduleStore.
     * @return
     */
    public static ScheduleStore getInstance() {
        return INSTANCE;
    }
}
