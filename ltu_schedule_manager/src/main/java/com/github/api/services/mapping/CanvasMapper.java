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
package com.github.api.services.mapping;

import com.github.api.dto.CanvasRawDTO;

/**
 * Maps input parameters to CanvasRawDTO objects.
 */
public class CanvasMapper {
    // Maps input parameters to a CanvasRawDTO
    public static CanvasRawDTO toApiWrapper(
        String contextCode, 
        String title, 
        String displayDate,
        String startTime, 
        String endTime,
        String location,
        String courseCode,
        String description) {
        
        return new CanvasRawDTO(
            contextCode,
            title,
            formatToIso(displayDate, startTime),
            formatToIso(displayDate, endTime),
            location,
            formatDescription(courseCode, description)  
        );
    }

    // Helper method to format date and time to ISO 8601 format
    private static String formatToIso(String date, String time) {
        if (date == null || time == null) {
            return null;
        }
        return date + "T" + time + ":00Z";
    }
    // Helper method to format the description with course code
    private static String formatDescription(String courseCode, String description) {
        return "Kurskod: " + courseCode + " | " + description;
    }
}
