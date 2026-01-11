package com.github.api.integration.timeedit;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.models.TimeEditReservation;
import com.github.models.TimeEditResponse;
import com.github.utilities.GenericHttpClient;

public class TimeEditClient implements GenericHttpClient {
    
    private static final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<TimeEditReservation> fetchReservations(String URL) {
        try {
            String json = fetch(URL, client);

            return mapper.readValue(json, TimeEditResponse.class).getReservations();
        } catch (IOException e) {
            System.out.println(">>> API SERVER STATUS: API route Exception IO \n");
            //e.printStackTrace();
            return List.of();
        } catch (InterruptedException e) {
            System.out.println(">>> API SERVER STATUS: API route Exception Interrupted \n");
            //e.printStackTrace();
            return List.of();
        }
    }
}

