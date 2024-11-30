package com.ebsolutions.eventsadminservice.scheduledevent;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
import com.ebsolutions.eventsadminservice.model.ScheduledEventInterval;
import com.ebsolutions.eventsadminservice.model.ScheduledEventType;
import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.RecordType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.DayOfWeekConverter;
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
public class ScheduledEventDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public ScheduledEvent read(String eventScheduleId, String scheduledEventId)
      throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(eventScheduleId, RecordType.SCHEDULED_EVENT, scheduledEventId);

      DynamoDbTable<ScheduledEventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(ScheduledEventDto.class));

      ScheduledEventDto scheduledEventDto = dtoDynamoDbTable.getItem(key);

      return scheduledEventDto == null
          ? null
          : ScheduledEvent.builder()
          .eventScheduleId(scheduledEventDto.getPartitionKey())
          .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(),
              RecordType.SCHEDULED_EVENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .clientId(scheduledEventDto.getClientId())
          .eventId(scheduledEventDto.getEventId())
          .locationId(scheduledEventDto.getLocationId())
          .organizerId(scheduledEventDto.getOrganizerId())
          .name(scheduledEventDto.getName())
          .description(scheduledEventDto.getDescription())
          .category(scheduledEventDto.getCategory())
          .startTime(scheduledEventDto.getStartTime())
          .endTime(scheduledEventDto.getEndTime())
          .scheduledEventType(
              ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
          .scheduledEventInterval(scheduledEventDto.getScheduledEventInterval() != null ?
              ScheduledEventInterval.fromValue(scheduledEventDto.getScheduledEventInterval()) :
              null)
          .scheduledEventDay(DayOfWeekConverter.convert(scheduledEventDto.getScheduledEventDay()))
          .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
          .cost(scheduledEventDto.getCost())
          .createdOn(scheduledEventDto.getCreatedOn())
          .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
          .build();
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<ScheduledEvent> readAll(String eventScheduleId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      DynamoDbTable<ScheduledEventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(ScheduledEventDto.class));

      List<ScheduledEventDto> scheduledEventDtos = dtoDynamoDbTable
          .query(r -> r.queryConditional(
              sortBeginsWith(s
                  -> s.partitionValue(eventScheduleId).sortValue(RecordType.SCHEDULED_EVENT.name()
                      .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER))
                  .build()))
          )
          .items()
          .stream()
          .toList();

      return scheduledEventDtos.stream()
          .map(scheduledEventDto ->
              ScheduledEvent.builder()
                  .eventScheduleId(scheduledEventDto.getPartitionKey())
                  .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(),
                      RecordType.SCHEDULED_EVENT.name()
                          .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
                  .clientId(scheduledEventDto.getClientId())
                  .eventId(scheduledEventDto.getEventId())
                  .locationId(scheduledEventDto.getLocationId())
                  .organizerId(scheduledEventDto.getOrganizerId())
                  .name(scheduledEventDto.getName())
                  .description(scheduledEventDto.getDescription())
                  .category(scheduledEventDto.getCategory())
                  .startTime(scheduledEventDto.getStartTime())
                  .endTime(scheduledEventDto.getEndTime())
                  .scheduledEventType(
                      ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
                  .scheduledEventInterval(scheduledEventDto.getScheduledEventInterval() != null ?
                      ScheduledEventInterval.fromValue(
                          scheduledEventDto.getScheduledEventInterval()) : null)
                  .scheduledEventDay(
                      DayOfWeekConverter.convert(scheduledEventDto.getScheduledEventDay()))
                  .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
                  .cost(scheduledEventDto.getCost())
                  .createdOn(scheduledEventDto.getCreatedOn())
                  .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
                  .build()
          ).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<ScheduledEvent> create(List<ScheduledEvent> scheduledEvents) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    LocalDateTime now = LocalDateTime.now();
    List<ScheduledEventDto> scheduledEventDtos = new ArrayList<>();

    try {
      scheduledEvents.forEach(scheduledEvent ->
          scheduledEventDtos.add(
              ScheduledEventDto.builder()
                  .partitionKey(scheduledEvent.getEventScheduleId())
                  .sortKey(RecordType.SCHEDULED_EVENT +
                      Constants.DATABASE_RECORD_TYPE_DELIMITER +
                      UniqueIdGenerator.generate())
                  .clientId(scheduledEvent.getClientId())
                  .eventId(scheduledEvent.getEventId())
                  .locationId(scheduledEvent.getLocationId())
                  .organizerId(scheduledEvent.getOrganizerId())
                  .name(scheduledEvent.getName())
                  .description(scheduledEvent.getDescription())
                  .category(scheduledEvent.getCategory())
                  .startTime(scheduledEvent.getStartTime())
                  .endTime(scheduledEvent.getEndTime())
                  .scheduledEventType(scheduledEvent.getScheduledEventType().getValue())
                  .scheduledEventInterval(scheduledEvent.getScheduledEventInterval() != null ?
                      scheduledEvent.getScheduledEventInterval().getValue() : null)
                  .scheduledEventDay(scheduledEvent.getScheduledEventDay() != null ?
                      scheduledEvent.getScheduledEventDay().name() : null)
                  .scheduledEventDate(scheduledEvent.getScheduledEventDate())
                  .cost(scheduledEvent.getCost())
                  .createdOn(now)
                  .lastUpdatedOn(now)
                  .build()
          ));

      DynamoDbTable<ScheduledEventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(ScheduledEventDto.class));

      WriteBatch.Builder<ScheduledEventDto> writeBatchBuilder =
          WriteBatch
              .builder(ScheduledEventDto.class)
              .mappedTableResource(dtoDynamoDbTable);

      scheduledEventDtos.forEach(writeBatchBuilder::addPutItem);

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

      return scheduledEventDtos.stream().map(scheduledEventDto ->
          ScheduledEvent.builder()
              .eventScheduleId(scheduledEventDto.getPartitionKey())
              .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(),
                  RecordType.SCHEDULED_EVENT.name()
                      .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
              .clientId(scheduledEventDto.getClientId())
              .eventId(scheduledEventDto.getEventId())
              .locationId(scheduledEventDto.getLocationId())
              .organizerId(scheduledEventDto.getOrganizerId())
              .name(scheduledEventDto.getName())
              .description(scheduledEventDto.getDescription())
              .category(scheduledEventDto.getCategory())
              .startTime(scheduledEventDto.getStartTime())
              .endTime(scheduledEventDto.getEndTime())
              .scheduledEventType(
                  ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
              .scheduledEventInterval(scheduledEventDto.getScheduledEventInterval() != null ?
                  ScheduledEventInterval.fromValue(scheduledEventDto.getScheduledEventInterval()) :
                  null)
              .scheduledEventDay(
                  DayOfWeekConverter.convert(scheduledEventDto.getScheduledEventDay()))
              .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
              .cost(scheduledEventDto.getCost())
              .createdOn(scheduledEventDto.getCreatedOn())
              .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
              .build()
      ).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }

  /**
   * This will replace the entire database object with the input event
   *
   * @param scheduledEvent the object to replace the current database object
   */
  public ScheduledEvent update(ScheduledEvent scheduledEvent) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      assert scheduledEvent.getScheduledEventId() != null;

      ScheduledEventDto scheduledEventDto = ScheduledEventDto.builder()
          .partitionKey(scheduledEvent.getEventScheduleId())
          .sortKey(RecordType.SCHEDULED_EVENT +
              Constants.DATABASE_RECORD_TYPE_DELIMITER +
              scheduledEvent.getScheduledEventId())
          .clientId(scheduledEvent.getClientId())
          .eventId(scheduledEvent.getEventId())
          .locationId(scheduledEvent.getLocationId())
          .organizerId(scheduledEvent.getOrganizerId())
          .name(scheduledEvent.getName())
          .description(scheduledEvent.getDescription())
          .category(scheduledEvent.getCategory())
          .startTime(scheduledEvent.getStartTime())
          .endTime(scheduledEvent.getEndTime())
          .scheduledEventType(scheduledEvent.getScheduledEventType().getValue())
          .scheduledEventInterval(scheduledEvent.getScheduledEventInterval() != null ?
              scheduledEvent.getScheduledEventInterval().getValue() : null)
          .scheduledEventDay(scheduledEvent.getScheduledEventDay() != null ?
              scheduledEvent.getScheduledEventDay().name() : null)
          .scheduledEventDate(scheduledEvent.getScheduledEventDate())
          .cost(scheduledEvent.getCost())
          .createdOn(scheduledEvent.getCreatedOn())
          .lastUpdatedOn(LocalDateTime.now())
          .build();

      DynamoDbTable<ScheduledEventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(ScheduledEventDto.class));

      dtoDynamoDbTable.putItem(scheduledEventDto);

      return ScheduledEvent.builder()
          .eventScheduleId(scheduledEventDto.getPartitionKey())
          .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(),
              RecordType.SCHEDULED_EVENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .clientId(scheduledEventDto.getClientId())
          .eventId(scheduledEventDto.getEventId())
          .locationId(scheduledEventDto.getLocationId())
          .organizerId(scheduledEventDto.getOrganizerId())
          .name(scheduledEventDto.getName())
          .description(scheduledEventDto.getDescription())
          .category(scheduledEventDto.getCategory())
          .startTime(scheduledEventDto.getStartTime())
          .endTime(scheduledEventDto.getEndTime())
          .scheduledEventType(
              ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
          .scheduledEventInterval(scheduledEventDto.getScheduledEventInterval() != null ?
              ScheduledEventInterval.fromValue(scheduledEventDto.getScheduledEventInterval()) :
              null)
          .scheduledEventDay(DayOfWeekConverter.convert(scheduledEventDto.getScheduledEventDay()))
          .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
          .cost(scheduledEventDto.getCost())
          .createdOn(scheduledEventDto.getCreatedOn())
          .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
          .build();
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
    }
  }

  public void delete(String eventScheduleId, String scheduledEventId) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(eventScheduleId, RecordType.SCHEDULED_EVENT, scheduledEventId);

      DynamoDbTable<ScheduledEventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(ScheduledEventDto.class));

      dtoDynamoDbTable.deleteItem(key);
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
    }
  }
}
