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
    //    @CsvBindByName(column = "Event Organizer Name")
    private String eventOrganizerName;
    //    @CsvBindByName(column = "Event Venue Name")
    private String eventVenueName;
    //    @CsvBindByName(column = "Event Start Date")
    private String eventStartDate;
    //    @CsvBindByName(column = "Event Start Time")
    private String eventStartTime;
    //    @CsvBindByName(column = "Event Length")
    private String eventLength;
    //    @CsvBindByName(column = "Event End Date")
    private String eventEndDate;
    //    @CsvBindByName(column = "Event End Time")
    private String eventEndTime;
    //    @CsvBindByName(column = "Event Name")
    private String eventName;
    //    @CsvBindByName(column = "Event Category")
    private String eventCategory;
    //    @CsvBindByName(column = "Event Description")
    private String eventDescription;
}
