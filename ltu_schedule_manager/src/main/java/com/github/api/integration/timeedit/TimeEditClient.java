package com.github.api.integration.timeedit;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
        } catch (InterruptedException e) {
            System.out.println(">>> API SERVER STATUS: API route Exception Interrupted \n");
            //e.printStackTrace();
        }
        return List.of();
    }

    public List<TimeEditReservation> searchAndGetSchedule(String courseId) {
        try {
        String searchBaseUrl = "https://cloud.timeedit.net/ltu/web/schedule1" + 
                               "/objects.json?fr=f&part=t&sid=3&types=28&search_text=";

        String searchJson = fetch(searchBaseUrl + courseId, client);

        return searchTimeEditId(searchJson).filter(nodes -> nodes.isArray() && !nodes.isEmpty())
                                           .map(id -> {
            
                                           
            String timeEditId = id.get(0).get("id").asText();

            String scheduleBaseeUrl = "https://cloud.timeedit.net/ltu/web/schedule1" + 
                                      "/s.json?sid=3&p=-20.w,12.w&objects=";
            
            return fetchReservations(scheduleBaseeUrl + timeEditId);

        }).orElse(List.of());

        
        } catch (IOException e) {
            System.out.println(">>> API SERVER STATUS: API route Exception IO \n");
            //e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(">>> API SERVER STATUS: API route Exception Interrupted \n");
            //e.printStackTrace();
        }
        return List.of();
    }

    private Optional<com.fasterxml.jackson.databind.JsonNode> searchTimeEditId(String searchJson) {
        com.fasterxml.jackson.databind.JsonNode root = null;
        com.fasterxml.jackson.databind.JsonNode objects = null;

        try {
            root = mapper.readTree(searchJson);
            objects = root.get("objects");
        } catch (JsonMappingException e) {
            System.out.println(">>> API SERVER STATUS: API route Exception JsonMapping \n");
            //e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println(">>> API SERVER STATUS: API route Exception JsonProcessing \n");
            //e.printStackTrace();
        }
        return Optional.ofNullable(objects);
    }
}

