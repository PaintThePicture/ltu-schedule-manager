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
package com.github.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Represents a reservation in the TimeEdit system.
 * This entity includes details such as activity, location, course code, and time range.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEditReservation {

    /** Unique identifier for the reservation. */
    private String id;

    /** Description of the activity associated with the reservation. */
    private String activity;

    /** Location where the reservation takes place. */
    private String location;

    /** Course code associated with the reservation. */
    private String courseCode;

    /** Display date for the reservation in ISO format. */
    private String displayDate;

    /** Start time of the reservation. */
    private String startTime;

    /** End time of the reservation. */
    private String endTime;

    /** Time range for display purposes. */
    private String displayTimeRange;

    /** Indicates whether the reservation is selected. */
    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    /** User comment associated with the reservation. */
    private final StringProperty userComment = new SimpleStringProperty("");

    /**
     * Gets the start time of the reservation in ISO format.
     * 
     * @return ISO-formatted start time, or null if date or time is missing.
     */
    public String getStartAsIso() {
        return (displayDate != null && startTime != null)
               ? displayDate + "T" + startTime + ":00Z"
               : null;
    }

    /**
     * Gets the end time of the reservation in ISO format.
     * 
     * @return ISO-formatted end time, or null if date or time is missing.
     */
    public String getEndAsIso() {
        return (displayDate != null && endTime != null) 
               ? displayDate + "T" + endTime + ":00Z"
               : null;
    }

    // Getters and setters for fields.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getDisplayTimeRange() {
        return displayTimeRange;
    }

    public void setDisplayTimeRange(String displayTimeRange) {
        this.displayTimeRange = displayTimeRange;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the selected property for binding.
     * 
     * @return The BooleanProperty representing the selected state.
     */
    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean value) { 
        selected.set(value); 
    }

    public boolean isSelected() {
        return selected.get();
    }

    /**
     * Gets the user comment property for binding.
     * 
     * @return The StringProperty representing the user comment.
     */
    public StringProperty userCommentProperty() {
        return userComment;
    }

    public String getUserComment() {
        return userComment.get();
    }

    public void setUserComment(String value) {
        userComment.set(value);
    }
}