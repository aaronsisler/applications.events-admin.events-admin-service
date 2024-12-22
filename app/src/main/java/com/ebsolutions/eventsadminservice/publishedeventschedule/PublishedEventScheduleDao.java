package com.ebsolutions.eventsadminservice.publishedeventschedule;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.RecordType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.shared.util.MetricsStopwatch;
import com.ebsolutions.eventsadminservice.shared.util.UniqueIdGenerator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
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
              RecordType.PUBLISHED_EVENT_SCHEDULE.name()
                  .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
          .name(publishedEventScheduleDto.getName())
          .targetYear(publishedEventScheduleDto.getTargetYear())
          .targetMonth(publishedEventScheduleDto.getTargetMonth())
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

  public List<PublishedEventSchedule> readAll(String clientId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      DynamoDbTable<PublishedEventScheduleDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(PublishedEventScheduleDto.class));

      List<PublishedEventScheduleDto> publishedEventScheduleDtos = dtoDynamoDbTable
          .query(r -> r.queryConditional(
              sortBeginsWith(s
                  -> s.partitionValue(clientId).sortValue(
                      RecordType.PUBLISHED_EVENT_SCHEDULE.name()
                          .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER))
                  .build()))
          )
          .items()
          .stream()
          .toList();

      return publishedEventScheduleDtos.stream()
          .map(publishedEventScheduleDto ->
              PublishedEventSchedule.builder()
                  .clientId(publishedEventScheduleDto.getPartitionKey())
                  .publishedEventScheduleId(
                      StringUtils.remove(publishedEventScheduleDto.getSortKey(),
                          RecordType.PUBLISHED_EVENT_SCHEDULE.name()
                              .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
                  .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
                  .targetYear(publishedEventScheduleDto.getTargetYear())
                  .targetMonth(publishedEventScheduleDto.getTargetMonth())
                  .name(publishedEventScheduleDto.getName())
                  .filename(publishedEventScheduleDto.getFilename())
                  .createdOn(publishedEventScheduleDto.getCreatedOn())
                  .lastUpdatedOn(publishedEventScheduleDto.getLastUpdatedOn())
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

  public PublishedEventSchedule create(PublishedEventSchedule publishedEventSchedule) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    LocalDateTime now = LocalDateTime.now();

    try {
      PublishedEventScheduleDto publishedEventScheduleDto = PublishedEventScheduleDto.builder()
          .partitionKey(publishedEventSchedule.getClientId())
          .sortKey(RecordType.PUBLISHED_EVENT_SCHEDULE +
              Constants.DATABASE_RECORD_TYPE_DELIMITER +
              UniqueIdGenerator.generate())
          .eventScheduleId(publishedEventSchedule.getEventScheduleId())
          .name(publishedEventSchedule.getName())
          .targetYear(publishedEventSchedule.getTargetYear())
          .targetMonth(publishedEventSchedule.getTargetMonth())
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
              RecordType.PUBLISHED_EVENT_SCHEDULE.name()
                  .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
          .name(publishedEventScheduleDto.getName())
          .targetYear(publishedEventScheduleDto.getTargetYear())
          .targetMonth(publishedEventScheduleDto.getTargetMonth())
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
