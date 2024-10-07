package com.ebsolutions.eventsadminservice.shared;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Getter
@Setter
@DynamoDbBean
@SuperBuilder
@NoArgsConstructor
public abstract class DatabaseDto implements Serializable {
  @NonNull
  @Getter(onMethod_ = @DynamoDbPartitionKey)
  private String partitionKey;

  @NonNull
  @Getter(onMethod_ = @DynamoDbSortKey)
  private String sortKey;

  @NonNull
  private String name;

  private LocalDateTime createdOn;

  private LocalDateTime lastUpdatedOn;
}