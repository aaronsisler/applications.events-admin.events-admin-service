package com.ebsolutions.eventsadminservice.organizer;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.model.Organizer;
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
public class OrganizerDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public Organizer read(String clientId, String organizerId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(clientId, SortKeyType.ORGANIZER, organizerId);

      DynamoDbTable<OrganizerDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(OrganizerDto.class));

      OrganizerDto organizerDto = dtoDynamoDbTable.getItem(key);

      return organizerDto == null
          ? null
          : Organizer.builder()
          .clientId(organizerDto.getPartitionKey())
          .organizerId(StringUtils.remove(organizerDto.getSortKey(), SortKeyType.ORGANIZER.name()))
          .name(organizerDto.getName())
          .createdOn(organizerDto.getCreatedOn())
//          .lastUpdatedOn(organizerDto.getLastUpdatedOn())
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

  public List<Organizer> readAll(String clientId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      DynamoDbTable<OrganizerDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(OrganizerDto.class));

      List<OrganizerDto> organizerDtos = dtoDynamoDbTable
          .query(r -> r.queryConditional(
              sortBeginsWith(s
                  -> s.partitionValue(clientId).sortValue(SortKeyType.ORGANIZER.name()).build()))
          )
          .items()
          .stream()
          .toList();

      return organizerDtos.stream()
          .map(organizerDto ->
              Organizer.builder()
                  .clientId(organizerDto.getPartitionKey())
                  .organizerId(
                      StringUtils.remove(organizerDto.getSortKey(), SortKeyType.ORGANIZER.name()))
                  .name(organizerDto.getName())
                  .createdOn(organizerDto.getCreatedOn())
                  .lastUpdatedOn(organizerDto.getLastUpdatedOn())
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

  public List<Organizer> create(List<Organizer> organizers) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    List<OrganizerDto> organizerDtos = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();

    try {
      organizers.forEach(organizer ->
          organizerDtos.add(OrganizerDto.builder()
              .partitionKey(organizer.getClientId())
              .sortKey(SortKeyType.ORGANIZER + UniqueIdGenerator.generate())
              .name(organizer.getName())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      DynamoDbTable<OrganizerDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(OrganizerDto.class));

      WriteBatch.Builder<OrganizerDto> writeBatchBuilder = WriteBatch.builder(OrganizerDto.class)
          .mappedTableResource(dtoDynamoDbTable);

      organizerDtos.forEach(writeBatchBuilder::addPutItem);

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

      return organizerDtos.stream().map(organizerDto ->
          Organizer.builder()
              .clientId(organizerDto.getPartitionKey())
              .organizerId(
                  StringUtils.remove(organizerDto.getSortKey(), SortKeyType.ORGANIZER.name()))
              .name(organizerDto.getName())
              .createdOn(organizerDto.getCreatedOn())
              .lastUpdatedOn(organizerDto.getLastUpdatedOn())
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
   * This will replace the entire database object with the input organizer
   *
   * @param organizer the object to replace the current database object
   */
  public Organizer update(Organizer organizer) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();

    try {
      assert organizer.getOrganizerId() != null;

      OrganizerDto organizerDto = OrganizerDto.builder()
          .partitionKey(organizer.getClientId())
          .sortKey(SortKeyType.ORGANIZER + organizer.getOrganizerId())
          .name(organizer.getName())
          .createdOn(organizer.getCreatedOn())
          .lastUpdatedOn(LocalDateTime.now())
          .build();

      DynamoDbTable<OrganizerDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(OrganizerDto.class));

      dtoDynamoDbTable.putItem(organizerDto);

      return Organizer.builder()
          .clientId(organizerDto.getPartitionKey())
          .organizerId(StringUtils.remove(organizerDto.getSortKey(), SortKeyType.ORGANIZER.name()))
          .name(organizerDto.getName())
          .createdOn(organizerDto.getCreatedOn())
          .lastUpdatedOn(organizerDto.getLastUpdatedOn())
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

  public void delete(String clientId, String organizerId) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(clientId, SortKeyType.ORGANIZER, organizerId);

      DynamoDbTable<OrganizerDto> dtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(OrganizerDto.class));

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
