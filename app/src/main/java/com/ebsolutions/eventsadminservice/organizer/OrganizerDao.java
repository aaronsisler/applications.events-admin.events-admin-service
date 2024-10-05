package com.ebsolutions.eventsadminservice.organizer;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.config.DatabaseConfig;
import com.ebsolutions.eventsadminservice.model.Organizer;
import com.ebsolutions.eventsadminservice.shared.MetricsStopwatch;
import com.ebsolutions.eventsadminservice.shared.SortKeyType;
import com.ebsolutions.eventsadminservice.shared.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.shared.util.KeyBuilder;
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
  private final DatabaseConfig databaseConfig;

  public Organizer read(String clientId, String organizerId) throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(clientId, SortKeyType.ORGANIZER, organizerId);

      DynamoDbTable<OrganizerDto> clientDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(databaseConfig.getTableName(),
              TableSchema.fromBean(OrganizerDto.class));

      OrganizerDto organizerDto = clientDtoDynamoDbTable.getItem(key);

      return organizerDto == null
          ? null
          : Organizer.builder()
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
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<Organizer> readAll(String clientId) throws DataProcessingException {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
    try {
      DynamoDbTable<OrganizerDto> organizerDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(databaseConfig.getTableName(),
              TableSchema.fromBean(OrganizerDto.class));

      List<OrganizerDto> organizerDtos = organizerDtoDynamoDbTable
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
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
    }
  }

  public List<Organizer> create(List<Organizer> organizers) {
    MetricsStopwatch metricsStopWatch = new MetricsStopwatch();
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

      DynamoDbTable<OrganizerDto> organizerDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(databaseConfig.getTableName(),
              TableSchema.fromBean(OrganizerDto.class));

      WriteBatch.Builder<OrganizerDto> writeBatchBuilder = WriteBatch.builder(OrganizerDto.class)
          .mappedTableResource(organizerDtoDynamoDbTable);

      organizerDtos.forEach(writeBatchBuilder::addPutItem);

      WriteBatch writeBatch = writeBatchBuilder.build();

      BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest
          .builder()
          .addWriteBatch(writeBatch)
          .build();

      BatchWriteResult batchWriteResult =
          dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);

      if (!batchWriteResult.unprocessedPutItemsForTable(organizerDtoDynamoDbTable).isEmpty()) {
        batchWriteResult.unprocessedPutItemsForTable(organizerDtoDynamoDbTable).forEach(key ->
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
      metricsStopWatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }
}
