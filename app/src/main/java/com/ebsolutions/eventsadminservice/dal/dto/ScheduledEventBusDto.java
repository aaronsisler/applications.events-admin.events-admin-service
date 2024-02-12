package com.ebsolutions.eventsadminservice.dal.dto;

import com.ebsolutions.eventsadminservice.model.Location;
import com.ebsolutions.eventsadminservice.model.Organizer;
import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Serdeable
@Slf4j
@Builder
@AllArgsConstructor
public class ScheduledEventBusDto {
    private Organizer organizer;
    private Location location;
    private ScheduledEvent scheduledEvent;
}
