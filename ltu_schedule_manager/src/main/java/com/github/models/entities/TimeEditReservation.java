package com.github.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEditReservation {

    private String id;
    private String activity;
    private String location;
    private String courseCode;
    private String displayDate;

    private String startTime;
    private String endTime;

    private String displayTimeRange;

    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final StringProperty userComment = new SimpleStringProperty("");
    

    public String getStartAsIso() {
        return (displayDate != null && startTime != null)
               ? displayDate + "T" + startTime + ":00Z"
               : null;
    }
    
    public String getEndAsIso() {
        return (displayDate != null && endTime != null) 
               ? displayDate + "T" + endTime + ":00Z"
               : null;
    }

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
    public BooleanProperty selectedProperty() {
        return selected;
    }
    public void setSelected(boolean value) { 
        selected.set(value); 
    }
    public boolean isSelected() {
        return selected.get();
    }
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