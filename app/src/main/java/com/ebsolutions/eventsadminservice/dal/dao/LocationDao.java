package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.Constants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.LocationDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Location;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import com.ebsolutions.eventsadminservice.util.UniqueIdGenerator;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchGetResultPage;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchGetResultPageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ReadBatch;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

@Slf4j
@Prototype
public class LocationDao {
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<LocationDto> ddbTable;

    public LocationDao(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        this.ddbTable = enhancedClient.table(Constants.DATABASE_TABLE_NAME, TableSchema.fromBean(LocationDto.class));
    }

    public Location read(String clientId, String locationId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.LOCATION, locationId);

            LocationDto locationDto = ddbTable.getItem(key);

            return locationDto == null
                    ? null
                    : Location.builder()
                    .clientId(locationDto.getPartitionKey())
                    .locationId(StringUtils.remove(locationDto.getSortKey(), SortKeyType.LOCATION.name()))
                    .name(locationDto.getName())
                    .createdOn(locationDto.getCreatedOn())
                    .lastUpdatedOn(locationDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<Location> read(String clientId, List<String> locationIds) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            // TODO check if there are 100 or more locationIds
            ReadBatch.Builder<LocationDto> locationBatchBuilder = ReadBatch.builder(LocationDto.class)
                    .mappedTableResource(ddbTable);

            locationIds.forEach(locationId ->
                    locationBatchBuilder.addGetItem(b -> b.key(KeyBuilder.build(clientId, SortKeyType.LOCATION, locationId)))
            );

            ReadBatch organizerBatch = locationBatchBuilder.build();

            BatchGetResultPageIterable resultPages = enhancedClient.batchGetItem(b -> b.readBatches(organizerBatch));
            List<LocationDto> locationDtos = resultPages.resultsForTable(ddbTable).stream().toList();

            List<String> unprocessedLocationIds =
                    resultPages.stream().flatMap((BatchGetResultPage pageResult) ->
                            pageResult.unprocessedKeysForTable(ddbTable).stream().map(Object::toString)
                    ).toList();

            log.info("Unprocessed Location Ids: {}", unprocessedLocationIds);

            return locationDtos.stream()
                    .map(locationDto ->
                            Location.builder()
                                    .clientId(locationDto.getPartitionKey())
                                    .locationId(StringUtils.remove(locationDto.getSortKey(), SortKeyType.LOCATION.name()))
                                    .name(locationDto.getName())
                                    .createdOn(locationDto.getCreatedOn())
                                    .lastUpdatedOn(locationDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<Location> readAll(String clientId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            List<LocationDto> locationDtos = ddbTable
                    .query(r -> r.queryConditional(
                            sortBeginsWith(s
                                    -> s.partitionValue(clientId).sortValue(SortKeyType.LOCATION.name()).build()))
                    )
                    .items()
                    .stream()
                    .toList();

            return locationDtos.stream()
                    .map(locationDto ->
                            Location.builder()
                                    .clientId(locationDto.getPartitionKey())
                                    .locationId(StringUtils.remove(locationDto.getSortKey(), SortKeyType.LOCATION.name()))
                                    .name(locationDto.getName())
                                    .createdOn(locationDto.getCreatedOn())
                                    .lastUpdatedOn(locationDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public Location create(Location location) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            LocalDateTime now = LocalDateTime.now();

            LocationDto locationDto = LocationDto.builder()
                    .partitionKey(location.getClientId())
                    .sortKey(SortKeyType.LOCATION + UniqueIdGenerator.generate())
                    .name(location.getName())
                    .createdOn(now)
                    .lastUpdatedOn(now)
                    .build();

            ddbTable.updateItem(locationDto);

            return Location.builder()
                    .clientId(locationDto.getPartitionKey())
                    .locationId(StringUtils.remove(locationDto.getSortKey(), SortKeyType.LOCATION.name()))
                    .name(locationDto.getName())
                    .createdOn(locationDto.getCreatedOn())
                    .lastUpdatedOn(locationDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }

    /**
     * This will replace the entire database object with the input location
     *
     * @param location the object to replace the current database object
     */
    public Location update(Location location) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            assert location.getLocationId() != null;

            LocationDto locationDto = LocationDto.builder()
                    .partitionKey(location.getClientId())
                    .sortKey(SortKeyType.LOCATION + location.getLocationId())
                    .name(location.getName())
                    .createdOn(location.getCreatedOn())
                    .lastUpdatedOn(LocalDateTime.now())
                    .build();

            ddbTable.putItem(locationDto);

            return Location.builder()
                    .clientId(locationDto.getPartitionKey())
                    .locationId(StringUtils.remove(locationDto.getSortKey(), SortKeyType.LOCATION.name()))
                    .name(locationDto.getName())
                    .createdOn(locationDto.getCreatedOn())
                    .lastUpdatedOn(locationDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
        }
    }

    public void delete(String clientId, String locationId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.LOCATION, locationId);

            ddbTable.deleteItem(key);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
        }
    }
}
