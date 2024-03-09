package com.ebsolutions.eventsadminservice.config;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Constants {
    public static final String EMPTY_STRING = StringUtils.EMPTY;
    public static final String FILE_STORAGE_LOCATION = "event-admin-service-file-storage";
    public static final List<String> CSV_COLUMN_HEADERS =
            List.of(
                    "Event Organizer Name",
                    "Event Start Date",
                    "Event Start Time",
                    "Event Length",
                    "Event End Date",
                    "Event End Time",
                    "Event Name",
                    "Event Category",
                    "Event Venue Name",
                    "Event Description"
            );
}
