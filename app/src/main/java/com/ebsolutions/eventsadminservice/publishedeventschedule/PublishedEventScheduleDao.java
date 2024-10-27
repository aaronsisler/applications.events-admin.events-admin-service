package com.ebsolutions.eventsadminservice.publishedeventschedule;

import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.RecordType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.shared.util.MetricsStopwatch;
import com.ebsolutions.eventsadminservice.shared.util.UniqueIdGenerator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Slf4j
@Repository
@AllArgsConstructor
public class PublishedEventScheduleDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public PublishedEventSchedule read(String clientId, String publishedEventScheduleId)
      throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();

    try {
      Key key = KeyBuilder.build(clientId, RecordType.PUBLISHED_EVENT_SCHEDULE,
          publishedEventScheduleId);

      DynamoDbTable<PublishedEventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(PublishedEventScheduleDto.class));

      PublishedEventScheduleDto publishedEventScheduleDto = dtoDynamoDbTable.getItem(key);

      return publishedEventScheduleDto == null
          ? null
          : PublishedEventSchedule.builder()
          .clientId(publishedEventScheduleDto.getPartitionKey())
          .publishedEventScheduleId(StringUtils.remove(publishedEventScheduleDto.getSortKey(),
              RecordType.PUBLISHED_EVENT_SCHEDULE.name()))
          .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
          .name(publishedEventScheduleDto.getName())
          .eventScheduleYear(publishedEventScheduleDto.getEventScheduleYear())
          .eventScheduleMonth(publishedEventScheduleDto.getEventScheduleMonth())
          .filename(publishedEventScheduleDto.getFilename())
          .createdOn(publishedEventScheduleDto.getCreatedOn())
          .lastUpdatedOn(publishedEventScheduleDto.getLastUpdatedOn())
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

  public PublishedEventSchedule create(PublishedEventSchedule publishedEventSchedule) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    LocalDateTime now = LocalDateTime.now();

    try {
      PublishedEventScheduleDto publishedEventScheduleDto = PublishedEventScheduleDto.builder()
          .partitionKey(publishedEventSchedule.getClientId())
          .sortKey(RecordType.PUBLISHED_EVENT_SCHEDULE + UniqueIdGenerator.generate())
          .eventScheduleId(publishedEventSchedule.getEventScheduleId())
          .name(publishedEventSchedule.getName())
          .eventScheduleYear(publishedEventSchedule.getEventScheduleYear())
          .eventScheduleMonth(publishedEventSchedule.getEventScheduleMonth())
          .filename(publishedEventSchedule.getFilename())
          .createdOn(now)
          .lastUpdatedOn(now)
          .build();

      DynamoDbTable<PublishedEventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(PublishedEventScheduleDto.class));

      dtoDynamoDbTable.updateItem(publishedEventScheduleDto);

      return PublishedEventSchedule.builder()
          .clientId(publishedEventScheduleDto.getPartitionKey())
          .publishedEventScheduleId(StringUtils.remove(publishedEventScheduleDto.getSortKey(),
              RecordType.PUBLISHED_EVENT_SCHEDULE.name()))
          .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
          .name(publishedEventScheduleDto.getName())
          .eventScheduleYear(publishedEventScheduleDto.getEventScheduleYear())
          .eventScheduleMonth(publishedEventScheduleDto.getEventScheduleMonth())
          .filename(publishedEventScheduleDto.getFilename())
          .createdOn(publishedEventScheduleDto.getCreatedOn())
          .lastUpdatedOn(publishedEventScheduleDto.getLastUpdatedOn())
          .build();
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new DataProcessingException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }
}
