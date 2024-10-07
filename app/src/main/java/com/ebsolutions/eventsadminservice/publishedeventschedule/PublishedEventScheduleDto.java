package com.ebsolutions.eventsadminservice.publishedeventschedule;

import com.ebsolutions.eventsadminservice.shared.DatabaseDto;
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
public class PublishedEventScheduleDto extends DatabaseDto {
  private String eventScheduleId;
  private Integer eventScheduleYear;
  private Integer eventScheduleMonth;
  private String filename;
}
