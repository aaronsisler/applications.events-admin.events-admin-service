package com.ebsolutions.eventsadminservice.dal.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbUpdateBehavior;

import java.time.Instant;

@Data
@DynamoDbBean
@Serdeable
@Slf4j
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatabaseDto {
    @NonNull
    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private String partitionKey;

    @NonNull
    @Getter(onMethod_ = @DynamoDbSortKey)
    private String sortKey;
    @NonNull
    private Instant createdOn;
    @NonNull
    private Instant lastUpdatedOn;
    /**
     * Date that record is no longer to remain in database
     * Must be in epoch seconds
     * i.e. should not be considered for items past expiryTime
     */
    private long expiryTime;

    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_IF_NOT_EXISTS)
    public Instant createdOn() {
        return createdOn;
    }

    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_ALWAYS)
    public Instant lastUpdatedOn() {
        return lastUpdatedOn;
    }
}
