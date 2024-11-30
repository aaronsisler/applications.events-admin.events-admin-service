package com.ebsolutions.eventsadminservice.event;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.model.Event;
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
public class EventDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public Event read(String clientId, String eventId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(clientId, RecordType.EVENT, eventId);

      DynamoDbTable<EventDto> eventDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventDto.class));

      EventDto eventDto = eventDtoDynamoDbTable.getItem(key);

      return eventDto == null
          ? null
          : Event.builder()
          .clientId(eventDto.getPartitionKey())
          .eventId(StringUtils.remove(eventDto.getSortKey(),
              RecordType.EVENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .locationId(eventDto.getLocationId())
          .organizerId(eventDto.getOrganizerId())
          .name(eventDto.getName())
          .description(eventDto.getDescription())
          .category(eventDto.getCategory())
          .createdOn(eventDto.getCreatedOn())
          .lastUpdatedOn(eventDto.getLastUpdatedOn())
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

  public List<Event> readAll(String clientId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      DynamoDbTable<EventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventDto.class));

      List<EventDto> eventDtos = dtoDynamoDbTable
          .query(r -> r.queryConditional(
              sortBeginsWith(s
                  -> s.partitionValue(clientId).sortValue(
                      RecordType.EVENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER))
                  .build()))
          )
          .items()
          .stream()
          .toList();

      return eventDtos.stream()
          .map(eventDto ->
              Event.builder()
                  .clientId(eventDto.getPartitionKey())
                  .eventId(StringUtils.remove(eventDto.getSortKey(),
                      RecordType.EVENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
                  .locationId(eventDto.getLocationId())
                  .organizerId(eventDto.getOrganizerId())
                  .name(eventDto.getName())
                  .description(eventDto.getDescription())
                  .category(eventDto.getCategory())
                  .createdOn(eventDto.getCreatedOn())
                  .lastUpdatedOn(eventDto.getLastUpdatedOn())
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

  public List<Event> create(List<Event> events) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      LocalDateTime now = LocalDateTime.now();
      List<EventDto> eventDtos = new ArrayList<>();

      events.forEach(event ->
          eventDtos.add(EventDto.builder()
              .partitionKey(event.getClientId())
              .sortKey(RecordType.EVENT + Constants.DATABASE_RECORD_TYPE_DELIMITER +
                  UniqueIdGenerator.generate())
              .locationId(event.getLocationId())
              .organizerId(event.getOrganizerId())
              .name(event.getName())
              .description(event.getDescription())
              .category(event.getCategory())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      DynamoDbTable<EventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventDto.class));

      WriteBatch.Builder<EventDto> writeBatchBuilder =
          WriteBatch
              .builder(EventDto.class)
              .mappedTableResource(dtoDynamoDbTable);

      eventDtos.forEach(writeBatchBuilder::addPutItem);

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

      return eventDtos.stream().map(eventDto ->
          Event.builder()
              .clientId(eventDto.getPartitionKey())
              .eventId(StringUtils.remove(eventDto.getSortKey(),
                  RecordType.EVENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
              .locationId(eventDto.getLocationId())
              .organizerId(eventDto.getOrganizerId())
              .name(eventDto.getName())
              .description(eventDto.getDescription())
              .category(eventDto.getCategory())
              .createdOn(eventDto.getCreatedOn())
              .lastUpdatedOn(eventDto.getLastUpdatedOn())
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
   * @param event the object to replace the current database object
   */
  public Event update(Event event) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      assert event.getClientId() != null;
      assert event.getEventId() != null;

      EventDto eventDto = EventDto.builder()
          .partitionKey(event.getClientId())
          .sortKey(RecordType.EVENT + Constants.DATABASE_RECORD_TYPE_DELIMITER + event.getEventId())
          .locationId(event.getLocationId())
          .organizerId(event.getOrganizerId())
          .name(event.getName())
          .description(event.getDescription())
          .category(event.getCategory())
          .createdOn(event.getCreatedOn())
          .lastUpdatedOn(LocalDateTime.now())
          .build();

      DynamoDbTable<EventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventDto.class));

      dtoDynamoDbTable.putItem(eventDto);

      return Event.builder()
          .clientId(eventDto.getPartitionKey())
          .eventId(StringUtils.remove(eventDto.getSortKey(),
              RecordType.EVENT.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .locationId(eventDto.getLocationId())
          .organizerId(eventDto.getOrganizerId())
          .name(eventDto.getName())
          .description(eventDto.getDescription())
          .category(eventDto.getCategory())
          .createdOn(eventDto.getCreatedOn())
          .lastUpdatedOn(eventDto.getLastUpdatedOn())
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

  public void delete(String clientId, String eventId) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(clientId, RecordType.EVENT, eventId);

      DynamoDbTable<EventDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(EventDto.class));

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
