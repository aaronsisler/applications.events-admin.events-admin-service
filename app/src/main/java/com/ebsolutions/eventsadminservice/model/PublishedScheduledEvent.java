package com.ebsolutions.eventsadminservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
@Builder
public class PublishedScheduledEvent {
    private ScheduledEvent scheduledEvent;
    private Location location;
    private Organizer organizer;
    private LocalDate eventStartDate;
    private LocalTime eventStartTime;
    /**
     * Duration of event in minutes
     */
    private long eventLength;
    private LocalDate eventEndDate;
    private LocalTime eventEndTime;
    private String eventName;
    private String eventDescription;
    private String eventCategory;

}
