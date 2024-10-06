package com.ebsolutions.eventsadminservice.filegeneration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
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
