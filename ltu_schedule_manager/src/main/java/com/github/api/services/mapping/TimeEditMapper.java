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
