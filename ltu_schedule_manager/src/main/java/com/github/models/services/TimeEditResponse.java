package com.github.models.services; 

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.models.entities.TimeEditReservation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEditResponse {
    
    private List<TimeEditReservation> reservations;

    public List<TimeEditReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<TimeEditReservation> reservations) {
        this.reservations = reservations;
    }

    public TimeEditResponse() {

    }
}