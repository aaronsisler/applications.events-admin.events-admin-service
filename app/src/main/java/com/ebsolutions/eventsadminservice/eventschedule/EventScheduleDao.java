package com.ebsolutions.eventsadminservice.eventschedule;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.model.EventSchedule;
import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.RecordType;
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
public class EventScheduleDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public EventSchedule read(String clientId, String eventScheduleId)
      throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();

    try {
      Key key = KeyBuilder.build(clientId, RecordType.EVENT_SCHEDULE, eventScheduleId);

      DynamoDbTable<EventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventScheduleDto.class));

      EventScheduleDto eventScheduleDto = dtoDynamoDbTable.getItem(key);

      return eventScheduleDto == null
          ? null
          : EventSchedule.builder()
          .clientId(eventScheduleDto.getPartitionKey())
          .eventScheduleId(
              StringUtils.remove(eventScheduleDto.getSortKey(), RecordType.EVENT_SCHEDULE.name()))
          .name(eventScheduleDto.getName())
          .description(eventScheduleDto.getDescription())
          .createdOn(eventScheduleDto.getCreatedOn())
          .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
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

  public List<EventSchedule> readAll(String clientId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      DynamoDbTable<EventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventScheduleDto.class));

      List<EventScheduleDto> eventScheduleDtos = dtoDynamoDbTable
          .query(r -> r.queryConditional(
              sortBeginsWith(s
                  -> s.partitionValue(clientId).sortValue(RecordType.EVENT_SCHEDULE.name())
                  .build()))
          )
          .items()
          .stream()
          .toList();

      return eventScheduleDtos.stream()
          .map(eventScheduleDto ->
              EventSchedule.builder()
                  .clientId(eventScheduleDto.getPartitionKey())
                  .eventScheduleId(
                      StringUtils.remove(eventScheduleDto.getSortKey(),
                          RecordType.EVENT_SCHEDULE.name()))
                  .name(eventScheduleDto.getName())
                  .description(eventScheduleDto.getDescription())
                  .createdOn(eventScheduleDto.getCreatedOn())
                  .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
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

  public List<EventSchedule> create(List<EventSchedule> eventSchedules) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    List<EventScheduleDto> eventScheduleDtos = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();

    try {
      eventSchedules.forEach(eventSchedule ->
          eventScheduleDtos.add(
              EventScheduleDto.builder()
                  .partitionKey(eventSchedule.getClientId())
                  .sortKey(RecordType.EVENT_SCHEDULE + UniqueIdGenerator.generate())
                  .name(eventSchedule.getName())
                  .description(eventSchedule.getDescription())
                  .createdOn(now)
                  .lastUpdatedOn(now)
                  .build())
      );

      DynamoDbTable<EventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventScheduleDto.class));

      WriteBatch.Builder<EventScheduleDto> writeBatchBuilder = WriteBatch.builder(
              EventScheduleDto.class)
          .mappedTableResource(dtoDynamoDbTable);

      eventScheduleDtos.forEach(writeBatchBuilder::addPutItem);

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

      return eventScheduleDtos.stream().map(eventScheduleDto ->
          EventSchedule.builder()
              .clientId(eventScheduleDto.getPartitionKey())
              .eventScheduleId(
                  StringUtils.remove(eventScheduleDto.getSortKey(),
                      RecordType.EVENT_SCHEDULE.name()))
              .name(eventScheduleDto.getName())
              .description(eventScheduleDto.getDescription())
              .createdOn(eventScheduleDto.getCreatedOn())
              .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
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
   * This will replace the entire database object with the input eventSchedule
   *
   * @param eventSchedule the object to replace the current database object
   */
  public EventSchedule update(EventSchedule eventSchedule) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();

    try {
      assert eventSchedule.getEventScheduleId() != null;

      EventScheduleDto
          eventScheduleDto = EventScheduleDto.builder()
          .partitionKey(eventSchedule.getClientId())
          .sortKey(RecordType.EVENT_SCHEDULE + eventSchedule.getEventScheduleId())
          .name(eventSchedule.getName())
          .description(eventSchedule.getDescription())
          .createdOn(eventSchedule.getCreatedOn())
          .lastUpdatedOn(LocalDateTime.now())
          .build();

      DynamoDbTable<EventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventScheduleDto.class));

      dtoDynamoDbTable.putItem(eventScheduleDto);

      return EventSchedule.builder()
          .clientId(eventScheduleDto.getPartitionKey())
          .eventScheduleId(
              StringUtils.remove(eventScheduleDto.getSortKey(), RecordType.EVENT_SCHEDULE.name()))
          .name(eventScheduleDto.getName())
          .createdOn(eventScheduleDto.getCreatedOn())
          .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
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

  public void delete(String clientId, String eventScheduleId) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(clientId, RecordType.EVENT_SCHEDULE, eventScheduleId);

      DynamoDbTable<EventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventScheduleDto.class));

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
