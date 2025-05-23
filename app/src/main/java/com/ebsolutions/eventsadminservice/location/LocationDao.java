package com.ebsolutions.eventsadminservice.location;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

import com.ebsolutions.eventsadminservice.model.Location;
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
public class LocationDao {
  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  public Location read(String establishmentId, String locationId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(establishmentId, RecordType.LOCATION, locationId);

      DynamoDbTable<LocationDto> clientDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(LocationDto.class));

      LocationDto locationDto = clientDtoDynamoDbTable.getItem(key);

      return locationDto == null
          ? null
          : Location.builder()
          .establishmentId(locationDto.getPartitionKey())
          .locationId(StringUtils.remove(locationDto.getSortKey(),
              RecordType.LOCATION.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .name(locationDto.getName())
          .createdOn(locationDto.getCreatedOn())
          .lastUpdatedOn(locationDto.getLastUpdatedOn())
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

  public List<Location> readAll(String establishmentId) throws DataProcessingException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      DynamoDbTable<LocationDto> locationDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(LocationDto.class));

      List<LocationDto> locationDtos = locationDtoDynamoDbTable
          .query(r -> r.queryConditional(
              sortBeginsWith(s
                  -> s.partitionValue(establishmentId).sortValue(
                      RecordType.LOCATION.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER))
                  .build()))
          )
          .items()
          .stream()
          .toList();

      return locationDtos.stream()
          .map(locationDto ->
              Location.builder()
                  .establishmentId(locationDto.getPartitionKey())
                  .locationId(
                      StringUtils.remove(locationDto.getSortKey(), RecordType.LOCATION.name()
                          .concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
                  .name(locationDto.getName())
                  .createdOn(locationDto.getCreatedOn())
                  .lastUpdatedOn(locationDto.getLastUpdatedOn())
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

  public List<Location> create(List<Location> locations) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    List<LocationDto> locationDtos = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();

    try {
      locations.forEach(location ->
          locationDtos.add(LocationDto.builder()
              .partitionKey(location.getEstablishmentId())
              .sortKey(RecordType.LOCATION +
                  Constants.DATABASE_RECORD_TYPE_DELIMITER +
                  UniqueIdGenerator.generate())
              .name(location.getName())
              .createdOn(now)
              .lastUpdatedOn(now)
              .build())
      );

      DynamoDbTable<LocationDto> locationDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(LocationDto.class));

      WriteBatch.Builder<LocationDto> writeBatchBuilder = WriteBatch.builder(
              LocationDto.class)
          .mappedTableResource(locationDtoDynamoDbTable);

      locationDtos.forEach(writeBatchBuilder::addPutItem);

      WriteBatch writeBatch = writeBatchBuilder.build();

      BatchWriteItemEnhancedRequest batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest
          .builder()
          .addWriteBatch(writeBatch)
          .build();

      BatchWriteResult batchWriteResult =
          dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest);

      if (!batchWriteResult.unprocessedPutItemsForTable(locationDtoDynamoDbTable).isEmpty()) {
        batchWriteResult.unprocessedPutItemsForTable(locationDtoDynamoDbTable).forEach(key ->
            log.info(key.toString()));
      }

      return locationDtos.stream().map(locationDto ->
          Location.builder()
              .establishmentId(locationDto.getPartitionKey())
              .locationId(
                  StringUtils.remove(locationDto.getSortKey(),
                      RecordType.LOCATION.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
              .name(locationDto.getName())
              .createdOn(locationDto.getCreatedOn())
              .lastUpdatedOn(locationDto.getLastUpdatedOn())
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
   * This will replace the entire database object with the input location
   *
   * @param location the object to replace the current database object
   */
  public Location update(Location location) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();

    try {
      assert location.getLocationId() != null;

      LocationDto
          locationDto = LocationDto.builder()
          .partitionKey(location.getEstablishmentId())
          .sortKey(RecordType.LOCATION +
              Constants.DATABASE_RECORD_TYPE_DELIMITER +
              location.getLocationId())
          .name(location.getName())
          .createdOn(location.getCreatedOn())
          .lastUpdatedOn(LocalDateTime.now())
          .build();

      DynamoDbTable<LocationDto> locationDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(LocationDto.class));

      locationDtoDynamoDbTable.putItem(locationDto);

      return Location.builder()
          .establishmentId(locationDto.getPartitionKey())
          .locationId(StringUtils.remove(locationDto.getSortKey(),
              RecordType.LOCATION.name().concat(Constants.DATABASE_RECORD_TYPE_DELIMITER)))
          .name(locationDto.getName())
          .createdOn(locationDto.getCreatedOn())
          .lastUpdatedOn(locationDto.getLastUpdatedOn())
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

  public void delete(String establishmentId, String locationId) {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    try {
      Key key = KeyBuilder.build(establishmentId, RecordType.LOCATION, locationId);

      DynamoDbTable<LocationDto> locationDtoDynamoDbTable =
          dynamoDbEnhancedClient.table(Constants.TABLE_NAME,
              TableSchema.fromBean(LocationDto.class));

      locationDtoDynamoDbTable.deleteItem(key);
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
