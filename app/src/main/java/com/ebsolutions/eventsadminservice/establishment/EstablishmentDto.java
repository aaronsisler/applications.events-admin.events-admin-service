package com.ebsolutions.eventsadminservice.establishment;

import com.ebsolutions.eventsadminservice.shared.DatabaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Getter
@Setter
@DynamoDbBean
@SuperBuilder
@AllArgsConstructor
public class EstablishmentDto extends DatabaseDto {
}
