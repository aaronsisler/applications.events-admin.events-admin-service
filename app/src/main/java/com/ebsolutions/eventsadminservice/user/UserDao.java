package com.ebsolutions.eventsadminservice.user;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

import com.ebsolutions.eventsadminservice.model.User;
import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.SortKeyType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.shared.util.MetricsStopwatch;
import com.ebsolutions.eventsadminservice.shared.util.UniqueIdGenerator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

@Slf4j
@Repository
@AllArgsConstructor
public class UserDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public User read(String userId) throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(SortKeyType.USER.name(), SortKeyType.USER, userId);

      DynamoDbTable<UserDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(UserDto.class));
      UserDto userDto = dtoDynamoDbTable.getItem(key);

      return userDto == null
          ? null
          : User.builder()
          .userId(StringUtils.remove(userDto.getSortKey(), SortKeyType.USER.name()))
          .name(userDto.getName())
          .clientIds(userDto.getClientIds())
          .createdOn(userDto.getCreatedOn())
          .lastUpdatedOn(userDto.getLastUpdatedOn())
          .build();
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<User> readAll() throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      DynamoDbTable<UserDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(UserDto.class));
      List<UserDto> userDtos = dtoDynamoDbTable
          .query(r -> r.queryConditional(
              keyEqualTo(s -> s.partitionValue(SortKeyType.USER.name()).build()))
          )
          .items()
          .stream()
          .toList();

      return userDtos.stream()
          .map(userDto ->
              User.builder()
                  .userId(StringUtils.remove(userDto.getSortKey(), SortKeyType.USER.name()))
                  .name(userDto.getName())
                  .clientIds(userDto.getClientIds())
                  .createdOn(userDto.getCreatedOn())
                  .lastUpdatedOn(userDto.getLastUpdatedOn())
                  .build()
          ).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<User> create(List<User> users) {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      LocalDateTime now = LocalDateTime.now();

      List<UserDto> userDtos = new ArrayList<>();

      users.forEach(user ->
          userDtos.add(UserDto.builder()
              .partitionKey(SortKeyType.USER.name())
              .sortKey(SortKeyType.USER + UniqueIdGenerator.generate())
              .name(user.getName())
              .clientIds(user.getClientIds())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      DynamoDbTable<UserDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(UserDto.class));
      WriteBatch.Builder<UserDto> writeBatchBuilder = WriteBatch.builder(
              UserDto.class)
          .mappedTableResource(dtoDynamoDbTable);

      userDtos.forEach(writeBatchBuilder::addPutItem);

      WriteBatch writeBatch = writeBatchBuilder.build();

      BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest
          .builder()
          .addWriteBatch(writeBatch)
          .build();

      BatchWriteResult batchWriteResult =
          dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);

      if (!batchWriteResult.unprocessedPutItemsForTable(dtoDynamoDbTable).isEmpty()) {
        batchWriteResult.unprocessedPutItemsForTable(dtoDynamoDbTable).forEach(key ->
            log.info(key.toString()));
      }

      return userDtos.stream().map(userDto ->
          User.builder()
              .userId(StringUtils.remove(userDto.getSortKey(), SortKeyType.USER.name()))
              .name(userDto.getName())
              .clientIds(userDto.getClientIds())
              .createdOn(userDto.getCreatedOn())
              .lastUpdatedOn(userDto.getLastUpdatedOn())
              .build()
      ).collect(Collectors.toList());

    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }
}
