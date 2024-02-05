package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.DatabaseConstants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.PublishedEventScheduleDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import com.ebsolutions.eventsadminservice.util.UniqueIdGenerator;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.sortBeginsWith;

@Slf4j
@Prototype
public class PublishedEventScheduleDao {
    private final DynamoDbTable<PublishedEventScheduleDto> ddbTable;

    public PublishedEventScheduleDao(DynamoDbEnhancedClient enhancedClient) {
        this.ddbTable = enhancedClient.table(DatabaseConstants.DATABASE_TABLE_NAME, TableSchema.fromBean(PublishedEventScheduleDto.class));
    }

    public PublishedEventSchedule read(String clientId, String publisehdEventScheduleId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.PUBLISHED_EVENT_SCHEDULE, publisehdEventScheduleId);

            PublishedEventScheduleDto publishedEventScheduleDto = ddbTable.getItem(key);

            return publishedEventScheduleDto == null
                    ? null
                    : PublishedEventSchedule.builder()
                    .clientId(publishedEventScheduleDto.getPartitionKey())
                    .publishedEventScheduleId(StringUtils.remove(publishedEventScheduleDto.getSortKey(), SortKeyType.PUBLISHED_EVENT_SCHEDULE.name()))
                    .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
                    .name(publishedEventScheduleDto.getName())
                    .eventScheduleYear(publishedEventScheduleDto.getEventScheduleYear())
                    .eventScheduleMonth(publishedEventScheduleDto.getEventScheduleMonth())
                    .fileLocation(publishedEventScheduleDto.getFileLocation())
                    .locationBlackouts(publishedEventScheduleDto.getLocationBlackouts())
                    .eventBlackouts(publishedEventScheduleDto.getEventBlackouts())
                    .createdOn(publishedEventScheduleDto.getCreatedOn())
                    .lastUpdatedOn(publishedEventScheduleDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<PublishedEventSchedule> readAll(String clientId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            List<PublishedEventScheduleDto> publishedEventScheduleDtos = ddbTable
                    .query(r -> r.queryConditional(
                            sortBeginsWith(s
                                    -> s.partitionValue(clientId).sortValue(SortKeyType.PUBLISHED_EVENT_SCHEDULE.name()).build()))
                    )
                    .items()
                    .stream()
                    .toList();

            return publishedEventScheduleDtos.stream()
                    .map(publishedEventScheduleDto ->
                            PublishedEventSchedule.builder()
                                    .clientId(publishedEventScheduleDto.getPartitionKey())
                                    .publishedEventScheduleId(StringUtils.remove(publishedEventScheduleDto.getSortKey(), SortKeyType.PUBLISHED_EVENT_SCHEDULE.name()))
                                    .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
                                    .name(publishedEventScheduleDto.getName())
                                    .eventScheduleYear(publishedEventScheduleDto.getEventScheduleYear())
                                    .eventScheduleMonth(publishedEventScheduleDto.getEventScheduleMonth())
                                    .fileLocation(publishedEventScheduleDto.getFileLocation())
                                    .locationBlackouts(publishedEventScheduleDto.getLocationBlackouts())
                                    .eventBlackouts(publishedEventScheduleDto.getEventBlackouts())
                                    .createdOn(publishedEventScheduleDto.getCreatedOn())
                                    .lastUpdatedOn(publishedEventScheduleDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public PublishedEventSchedule create(PublishedEventSchedule publishedEventSchedule) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            LocalDateTime now = LocalDateTime.now();

            PublishedEventScheduleDto publishedEventScheduleDto = PublishedEventScheduleDto.builder()
                    .partitionKey(publishedEventSchedule.getClientId())
                    .sortKey(SortKeyType.PUBLISHED_EVENT_SCHEDULE + UniqueIdGenerator.generate())
                    .eventScheduleId(publishedEventSchedule.getEventScheduleId())
                    .name(publishedEventSchedule.getName())
                    .eventScheduleYear(publishedEventSchedule.getEventScheduleYear())
                    .eventScheduleMonth(publishedEventSchedule.getEventScheduleMonth())
                    .fileLocation(publishedEventSchedule.getFileLocation())
                    .locationBlackouts(publishedEventSchedule.getLocationBlackouts())
                    .eventBlackouts(publishedEventSchedule.getEventBlackouts())
                    .createdOn(now)
                    .lastUpdatedOn(now)
                    .build();

            ddbTable.updateItem(publishedEventScheduleDto);

            return PublishedEventSchedule.builder()
                    .clientId(publishedEventScheduleDto.getPartitionKey())
                    .publishedEventScheduleId(StringUtils.remove(publishedEventScheduleDto.getSortKey(), SortKeyType.PUBLISHED_EVENT_SCHEDULE.name()))
                    .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
                    .name(publishedEventScheduleDto.getName())
                    .eventScheduleYear(publishedEventScheduleDto.getEventScheduleYear())
                    .eventScheduleMonth(publishedEventScheduleDto.getEventScheduleMonth())
                    .fileLocation(publishedEventScheduleDto.getFileLocation())
                    .locationBlackouts(publishedEventScheduleDto.getLocationBlackouts())
                    .eventBlackouts(publishedEventScheduleDto.getEventBlackouts())
                    .createdOn(publishedEventScheduleDto.getCreatedOn())
                    .lastUpdatedOn(publishedEventScheduleDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }

    /**
     * This will replace the entire database object with the input event
     *
     * @param publishedEventSchedule the object to replace the current database object
     */
    public PublishedEventSchedule update(PublishedEventSchedule publishedEventSchedule) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            assert publishedEventSchedule.getPublishedEventScheduleId() != null;

            PublishedEventScheduleDto publishedEventScheduleDto = PublishedEventScheduleDto.builder()
                    .partitionKey(publishedEventSchedule.getClientId())
                    .sortKey(SortKeyType.PUBLISHED_EVENT_SCHEDULE + publishedEventSchedule.getPublishedEventScheduleId())
                    .eventScheduleId(publishedEventSchedule.getEventScheduleId())
                    .name(publishedEventSchedule.getName())
                    .eventScheduleYear(publishedEventSchedule.getEventScheduleYear())
                    .eventScheduleMonth(publishedEventSchedule.getEventScheduleMonth())
                    .fileLocation(publishedEventSchedule.getFileLocation())
                    .locationBlackouts(publishedEventSchedule.getLocationBlackouts())
                    .eventBlackouts(publishedEventSchedule.getEventBlackouts())
                    .createdOn(publishedEventSchedule.getCreatedOn())
                    .lastUpdatedOn(LocalDateTime.now())
                    .build();

            ddbTable.putItem(publishedEventScheduleDto);

            return PublishedEventSchedule.builder()
                    .clientId(publishedEventScheduleDto.getPartitionKey())
                    .publishedEventScheduleId(StringUtils.remove(publishedEventScheduleDto.getSortKey(), SortKeyType.PUBLISHED_EVENT_SCHEDULE.name()))
                    .eventScheduleId(publishedEventScheduleDto.getEventScheduleId())
                    .name(publishedEventScheduleDto.getName())
                    .eventScheduleYear(publishedEventScheduleDto.getEventScheduleYear())
                    .eventScheduleMonth(publishedEventScheduleDto.getEventScheduleMonth())
                    .fileLocation(publishedEventScheduleDto.getFileLocation())
                    .locationBlackouts(publishedEventScheduleDto.getLocationBlackouts())
                    .eventBlackouts(publishedEventScheduleDto.getEventBlackouts())
                    .createdOn(publishedEventScheduleDto.getCreatedOn())
                    .lastUpdatedOn(publishedEventScheduleDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
        }
    }

    public void delete(String clientId, String publisehdEventScheduleId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.PUBLISHED_EVENT_SCHEDULE, publisehdEventScheduleId);

            ddbTable.deleteItem(key);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
        }
    }
}
