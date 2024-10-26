package com.ebsolutions.eventsadminservice.user;

import com.ebsolutions.eventsadminservice.shared.DatabaseDto;
import java.util.List;
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
public class UserDto extends DatabaseDto {
  private List<String> clientIds;
}
