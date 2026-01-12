package com.github.models.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEditReservation {

    // --- Rådata från TimeEdit ---
    private String id;
    private String startdate; 
    private String starttime; 
    private String enddate;  
    private String endtime;  
    
    private List<String> columns; // Den generiska listan som innehåller all kolumndata

    // --- Beräknad data till TableView/Canvas API ---
    private String activity; // calendar_event[title] i Canvas API
    private String location; // calendar_event[location_name] i Canvas API
    private String courseCode; // calendar_event[description] i Canvas API tillsammans med userComment
    private String dateTimeStart; // calendar_event[start_at] o Canvas API (Kombinerat Datum + Tid ISO 8601)
    private String dateTimeEnd;   // calendar_event[start_at] o Canvas API (Kombinerat Datum + Tid ISO 8601)
    private String displayDate;  // För TableView: "2025-01-02"
    private String displayTimeRange;  // För TableView: "08:15-09:45"
    
    // --- Fält som fylls i i GUIn ---
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private StringProperty userComment = new SimpleStringProperty(""); // calendar_event[description] i Canvas API tillsammans med courseCode

    // --- Getters och Setters för Rådata från TimeEdit ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getStartdate() { return startdate; }
    public void setStartdate(String startdate) { this.startdate = startdate; }

    public String getStarttime() { return starttime; }
    public void setStarttime(String starttime) { this.starttime = starttime; }

    public String getEnddate() { return enddate; }
    public void setEnddate(String enddate) { this.enddate = enddate; }

    public String getEndtime() { return endtime; }
    public void setEndtime(String endtime) { this.endtime = endtime; }

    public void setColumns(List<String> columns) {
        this.columns = columns;
        
        if (columns.size() > 0) this.activity = columns.get(0);
        if (columns.size() > 1) this.location = columns.get(1);
        
        if (columns.size() > 2) {
            String fullCode = columns.get(2);
            this.courseCode = fullCode.contains(",") ? fullCode.substring(0, fullCode.indexOf(',')) : fullCode;
        }

        // Kombinera datum och tid
        this.dateTimeStart = startdate + "T" + starttime + ":00Z";
        this.dateTimeEnd = enddate + "T" + endtime + ":00Z";

        this.displayDate = startdate; // Använder TimeEdit's startdate direkt

        // Tidspann (displayTimeRange t.ex. "08:15-09:45")
        this.displayTimeRange = starttime + "-" + endtime;
    }
    
    // --- Getters för JavaFX TableView/ Canvas API ---
    
    public String getActivity() { return activity; }
    public String getLocation() { return location; }
    public String getCourseCode() { return courseCode; }
    public String getDateTimeStart() { return dateTimeStart; }
    public String getDateTimeEnd() { return dateTimeEnd; }
    public String getDisplayDate() { return displayDate;}
    public String getDisplayTimeRange() {return displayTimeRange;}

    public StringProperty userCommentProperty() {
        return userComment;
    }

    public String getUserComment() {
        return userComment.get();
    }
    
    public void setUserComment(String userComment) {
        this.userComment.set(userComment);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected); 
    }

}