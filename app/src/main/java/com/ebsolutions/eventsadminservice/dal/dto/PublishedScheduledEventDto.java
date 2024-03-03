package com.ebsolutions.eventsadminservice.dal.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Serdeable
@AllArgsConstructor
@NoArgsConstructor
public class PublishedScheduledEventDto {
    private String eventOrganizerName;
    private String eventVenueName;
    private String eventStartDate;
    private String eventStartTime;
    private String eventLength;
    private String eventEndDate;
    private String eventEndTime;
    private String eventName;
    private String eventCategory;
    private String eventDescription;
}
