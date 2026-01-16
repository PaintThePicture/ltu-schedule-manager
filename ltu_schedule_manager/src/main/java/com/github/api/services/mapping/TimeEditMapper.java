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

import java.util.List;

import com.github.api.dto.TimeEditRawDTO;
import com.github.models.entities.TimeEditReservation;
import com.github.utilities.StringOps;

/**
 * Maps TimeEditRawDTO objects to TimeEditReservation entities.
 */
public class TimeEditMapper {

    // Helper functional interface for safe list access
    private interface func {
        static String getValue(List<String> list, int index, String defaultValue) {
            return (index < list.size()) ? list.get(index) : defaultValue;
        }
        
    }
    
    /**
     * Converts a TimeEditRawDTO to a TimeEditReservation entity.
     *
     * @param raw the TimeEditRawDTO object
     * @return the mapped TimeEditReservation entity
     */
    public static TimeEditReservation toEntity(TimeEditRawDTO raw) {
        
        TimeEditReservation res = new TimeEditReservation();

        var cols = raw.columns() != null ? raw.columns() : List.<String>of();

        res.setId(raw.id());
        res.setActivity(func.getValue(cols, 0, "N/A"));
        res.setLocation(func.getValue(cols, 1, "N/A"));
        
        String rawData = func.getValue(cols, 2, "N/A");
        
        // Extract course code from rawData
        res.setCourseCode(StringOps.UtilSplit.commaAsStream(rawData)
                                             .findFirst()
                                             .orElse("N/A"));
        
        res.setDisplayDate(raw.startDate());
        res.setStartTime(raw.startTime());
        res.setEndTime(raw.endTime());
        res.setDisplayTimeRange(raw.startTime() + "-" + raw.endTime());

        return res;
    }
}
