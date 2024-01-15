package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.DatabaseConstants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.ScheduledEventDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
import com.ebsolutions.eventsadminservice.model.ScheduledEventDay;
import com.ebsolutions.eventsadminservice.model.ScheduledEventInterval;
import com.ebsolutions.eventsadminservice.model.ScheduledEventType;
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
public class ScheduledEventDao {
    private final DynamoDbTable<ScheduledEventDto> ddbTable;

    public ScheduledEventDao(DynamoDbEnhancedClient enhancedClient) {
        this.ddbTable = enhancedClient.table(DatabaseConstants.DATABASE_TABLE_NAME, TableSchema.fromBean(ScheduledEventDto.class));
    }

    public ScheduledEvent read(String clientId, String scheduledEventId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.SCHEDULED_EVENT, scheduledEventId);

            ScheduledEventDto scheduledEventDto = ddbTable.getItem(key);

            return scheduledEventDto == null
                    ? null
                    : ScheduledEvent.builder()
                    .eventScheduleId(scheduledEventDto.getPartitionKey())
                    .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(), SortKeyType.SCHEDULED_EVENT.name()))
                    .locationId(scheduledEventDto.getLocationId())
                    .organizerIds(scheduledEventDto.getOrganizerIds())
                    .name(scheduledEventDto.getName())
                    .startTime(scheduledEventDto.getStartTime())
                    .endTime(scheduledEventDto.getEndTime())
                    .scheduledEventType(ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
                    .scheduledEventInterval(ScheduledEventInterval.fromValue(scheduledEventDto.getScheduledEventInterval()))
                    .scheduledEventDay(ScheduledEventDay.fromValue(scheduledEventDto.getScheduledEventDay()))
                    .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
                    .cost(scheduledEventDto.getCost())
                    .createdOn(scheduledEventDto.getCreatedOn())
                    .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<ScheduledEvent> readAll(String clientId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            List<ScheduledEventDto> scheduledEventDtos = ddbTable
                    .query(r -> r.queryConditional(
                            sortBeginsWith(s
                                    -> s.partitionValue(clientId).sortValue(SortKeyType.SCHEDULED_EVENT.name()).build()))
                    )
                    .items()
                    .stream()
                    .toList();

            return scheduledEventDtos.stream()
                    .map(scheduledEventDto ->
                            ScheduledEvent.builder()
                                    .eventScheduleId(scheduledEventDto.getPartitionKey())
                                    .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(), SortKeyType.SCHEDULED_EVENT.name()))
                                    .locationId(scheduledEventDto.getLocationId())
                                    .organizerIds(scheduledEventDto.getOrganizerIds())
                                    .name(scheduledEventDto.getName())
                                    .startTime(scheduledEventDto.getStartTime())
                                    .endTime(scheduledEventDto.getEndTime())
                                    .scheduledEventType(ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
                                    .scheduledEventInterval(ScheduledEventInterval.fromValue(scheduledEventDto.getScheduledEventInterval()))
                                    .scheduledEventDay(ScheduledEventDay.fromValue(scheduledEventDto.getScheduledEventDay()))
                                    .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
                                    .cost(scheduledEventDto.getCost())
                                    .createdOn(scheduledEventDto.getCreatedOn())
                                    .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public ScheduledEvent create(ScheduledEvent scheduledEvent) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            LocalDateTime now = LocalDateTime.now();

            ScheduledEventDto scheduledEventDto = ScheduledEventDto.builder()
                    .partitionKey(scheduledEvent.getEventScheduleId())
                    .sortKey(SortKeyType.SCHEDULED_EVENT + UniqueIdGenerator.generate())
                    .locationId(scheduledEvent.getLocationId())
                    .organizerIds(scheduledEvent.getOrganizerIds())
                    .name(scheduledEvent.getName())
                    .startTime(scheduledEvent.getStartTime())
                    .endTime(scheduledEvent.getEndTime())
                    .scheduledEventType(scheduledEvent.getScheduledEventType().getValue())
                    .scheduledEventInterval(scheduledEvent.getScheduledEventInterval() != null ? scheduledEvent.getScheduledEventInterval().getValue() : null)
                    .scheduledEventDay(scheduledEvent.getScheduledEventDay() != null ? scheduledEvent.getScheduledEventDay().getValue() : null)
                    .scheduledEventDate(scheduledEvent.getScheduledEventDate())
                    .cost(scheduledEvent.getCost())
                    .createdOn(now)
                    .lastUpdatedOn(now)
                    .build();

            ddbTable.updateItem(scheduledEventDto);

            return ScheduledEvent.builder()
                    .eventScheduleId(scheduledEventDto.getPartitionKey())
                    .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(), SortKeyType.SCHEDULED_EVENT.name()))
                    .locationId(scheduledEventDto.getLocationId())
                    .organizerIds(scheduledEventDto.getOrganizerIds())
                    .name(scheduledEventDto.getName())
                    .startTime(scheduledEventDto.getStartTime())
                    .endTime(scheduledEventDto.getEndTime())
                    .scheduledEventType(ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
                    .scheduledEventInterval(ScheduledEventInterval.fromValue(scheduledEventDto.getScheduledEventInterval()))
                    .scheduledEventDay(ScheduledEventDay.fromValue(scheduledEventDto.getScheduledEventDay()))
                    .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
                    .cost(scheduledEventDto.getCost())
                    .createdOn(scheduledEventDto.getCreatedOn())
                    .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
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
     * @param scheduledEvent the object to replace the current database object
     */
    public ScheduledEvent update(ScheduledEvent scheduledEvent) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            assert scheduledEvent.getScheduledEventId() != null;

            ScheduledEventDto scheduledEventDto = ScheduledEventDto.builder()
                    .partitionKey(scheduledEvent.getEventScheduleId())
                    .sortKey(SortKeyType.SCHEDULED_EVENT + scheduledEvent.getScheduledEventId())
                    .locationId(scheduledEvent.getLocationId())
                    .organizerIds(scheduledEvent.getOrganizerIds())
                    .name(scheduledEvent.getName())
                    .startTime(scheduledEvent.getStartTime())
                    .endTime(scheduledEvent.getEndTime())
                    .scheduledEventType(scheduledEvent.getScheduledEventType().getValue())
                    .scheduledEventInterval(scheduledEvent.getScheduledEventInterval() != null ? scheduledEvent.getScheduledEventInterval().getValue() : null)
                    .scheduledEventDay(scheduledEvent.getScheduledEventDay() != null ? scheduledEvent.getScheduledEventDay().getValue() : null)
                    .scheduledEventDate(scheduledEvent.getScheduledEventDate())
                    .cost(scheduledEvent.getCost())
                    .createdOn(scheduledEvent.getCreatedOn())
                    .lastUpdatedOn(LocalDateTime.now())
                    .build();

            ddbTable.putItem(scheduledEventDto);

            return ScheduledEvent.builder()
                    .eventScheduleId(scheduledEventDto.getPartitionKey())
                    .scheduledEventId(StringUtils.remove(scheduledEventDto.getSortKey(), SortKeyType.SCHEDULED_EVENT.name()))
                    .locationId(scheduledEventDto.getLocationId())
                    .organizerIds(scheduledEventDto.getOrganizerIds())
                    .name(scheduledEventDto.getName())
                    .startTime(scheduledEventDto.getStartTime())
                    .endTime(scheduledEventDto.getEndTime())
                    .scheduledEventType(ScheduledEventType.fromValue(scheduledEventDto.getScheduledEventType()))
                    .scheduledEventInterval(ScheduledEventInterval.fromValue(scheduledEventDto.getScheduledEventInterval()))
                    .scheduledEventDay(ScheduledEventDay.fromValue(scheduledEventDto.getScheduledEventDay()))
                    .scheduledEventDate(scheduledEventDto.getScheduledEventDate())
                    .cost(scheduledEventDto.getCost())
                    .createdOn(scheduledEventDto.getCreatedOn())
                    .lastUpdatedOn(scheduledEventDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
        }
    }

    public void delete(String clientId, String scheduledEventId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.SCHEDULED_EVENT, scheduledEventId);

            ddbTable.deleteItem(key);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
        }
    }
}
