package com.ebsolutions.eventsadminservice.config;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Constants {
    public static final String EMPTY_STRING = StringUtils.EMPTY;
    public static final String FILE_STORAGE_LOCATION = "event-admin-service-file-storage";
    public static final String DATABASE_TABLE_NAME = "SERVICES_EVENTS_ADMIN_LOCAL";
    //        public static String DATABASE_TABLE_NAME = "SERVICES_EVENTS_ADMIN_BETA";
    public static final List<String> CSV_COLUMN_HEADERS =
            List.of(
                    CsvColumnHeaderNames.EVENT_ORGANIZER_NAME.label,
                    CsvColumnHeaderNames.EVENT_START_DATE.label,
                    CsvColumnHeaderNames.EVENT_START_TIME.label,
                    CsvColumnHeaderNames.EVENT_LENGTH.label,
                    CsvColumnHeaderNames.EVENT_END_DATE.label,
                    CsvColumnHeaderNames.EVENT_END_TIME.label,
                    CsvColumnHeaderNames.EVENT_NAME.label,
                    CsvColumnHeaderNames.EVENT_CATEGORY.label,
                    CsvColumnHeaderNames.EVENT_VENUE_NAME.label,
                    CsvColumnHeaderNames.EVENT_DESCRIPTION.label
            );

    public enum CsvColumnHeaderNames {
        EVENT_ORGANIZER_NAME("Event Organizer Name"),
        EVENT_START_DATE("Event Start Date"),
        EVENT_START_TIME("Event Start Time"),
        EVENT_LENGTH("Event Length"),
        EVENT_END_DATE("Event End Date"),
        EVENT_END_TIME("Event End Time"),
        EVENT_NAME("Event Name"),
        EVENT_CATEGORY("Event Category"),
        EVENT_VENUE_NAME("Event Venue Name"),
        EVENT_DESCRIPTION("Event Description");

        public final String label;

        private CsvColumnHeaderNames(String label) {
            this.label = label;
        }
    }
}
