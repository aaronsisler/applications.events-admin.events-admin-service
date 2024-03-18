package com.ebsolutions.eventsadminservice.dal.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@EqualsAndHashCode(callSuper = true)
@Data
@DynamoDbBean
@Serdeable
@Slf4j
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PublishedEventScheduleDto extends DatabaseDto {
    private String eventScheduleId;
    private Integer eventScheduleYear;
    private Integer eventScheduleMonth;
    private String fileLocation;
//    private List<LocationBlackout> locationBlackouts;
//    private List<EventBlackout> eventBlackouts;
}
