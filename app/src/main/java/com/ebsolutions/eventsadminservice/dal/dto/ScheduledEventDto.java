package com.ebsolutions.eventsadminservice.dal.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@DynamoDbBean
@Serdeable
@Slf4j
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledEventDto extends DatabaseDto {
    private String locationId;
    private List<String> organizerIds;
    private LocalTime startTime;
    private LocalTime endTime;
    private String scheduledEventType;
    private String scheduledEventInterval;
    private String scheduledEventDay;
    private LocalDate scheduledEventDate;
    private Integer cost;
}
