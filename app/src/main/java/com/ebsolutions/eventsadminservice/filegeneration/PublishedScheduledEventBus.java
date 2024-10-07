package com.ebsolutions.eventsadminservice.filegeneration;

import com.ebsolutions.eventsadminservice.model.Location;
import com.ebsolutions.eventsadminservice.model.Organizer;
import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishedScheduledEventBus {
  private ScheduledEvent scheduledEvent;
  private Location location;
  private Organizer organizer;
  private LocalDate eventStartDate;
  private LocalTime eventStartTime;
  private LocalDate eventEndDate;
  private LocalTime eventEndTime;
  private String eventName;
  private String eventDescription;
  private String eventCategory;
}
