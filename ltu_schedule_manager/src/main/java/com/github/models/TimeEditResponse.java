package com.github.models; 

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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