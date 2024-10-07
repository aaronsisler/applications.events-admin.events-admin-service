package com.ebsolutions.eventsadminservice.scheduledevent;

import com.ebsolutions.eventsadminservice.shared.DatabaseDto;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Getter
@Setter
@DynamoDbBean
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledEventDto extends DatabaseDto {
  private String clientId;
  private String eventId;
  private String locationId;
  private String organizerId;
  private String scheduledEventType;
  private String scheduledEventInterval;
  private String scheduledEventDay;
  private String description;
  private String category;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDate scheduledEventDate;
  private Integer cost;
}
