package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.DatabaseConstants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.EventScheduleDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.EventSchedule;
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
public class EventScheduleDao {
    private final DynamoDbTable<EventScheduleDto> ddbTable;

    public EventScheduleDao(DynamoDbEnhancedClient enhancedClient) {
        this.ddbTable = enhancedClient.table(DatabaseConstants.DATABASE_TABLE_NAME, TableSchema.fromBean(EventScheduleDto.class));
    }

    public EventSchedule read(String clientId, String eventScheduleId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.EVENT_SCHEDULE, eventScheduleId);

            EventScheduleDto eventScheduleDto = ddbTable.getItem(key);

            return eventScheduleDto == null
                    ? null
                    : EventSchedule.builder()
                    .clientId(eventScheduleDto.getPartitionKey())
                    .eventScheduleId(StringUtils.remove(eventScheduleDto.getSortKey(), SortKeyType.EVENT_SCHEDULE.name()))
                    .name(eventScheduleDto.getName())
                    .description(eventScheduleDto.getDescription())
                    .createdOn(eventScheduleDto.getCreatedOn())
                    .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<EventSchedule> readAll(String clientId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            List<EventScheduleDto> eventScheduleDtos = ddbTable
                    .query(r -> r.queryConditional(
                            sortBeginsWith(s
                                    -> s.partitionValue(clientId).sortValue(SortKeyType.EVENT_SCHEDULE.name()).build()))
                    )
                    .items()
                    .stream()
                    .toList();

            return eventScheduleDtos.stream()
                    .map(eventScheduleDto ->
                            EventSchedule.builder()
                                    .clientId(eventScheduleDto.getPartitionKey())
                                    .eventScheduleId(StringUtils.remove(eventScheduleDto.getSortKey(), SortKeyType.EVENT_SCHEDULE.name()))
                                    .scheduleEventIds(eventScheduleDto.getScheduleEventIds())
                                    .name(eventScheduleDto.getName())
                                    .description(eventScheduleDto.getDescription())
                                    .createdOn(eventScheduleDto.getCreatedOn())
                                    .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public EventSchedule create(EventSchedule eventSchedule) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            LocalDateTime now = LocalDateTime.now();

            assert eventSchedule.getClientId() != null;

            EventScheduleDto eventScheduleDto = EventScheduleDto.builder()
                    .partitionKey(eventSchedule.getClientId())
                    .sortKey(SortKeyType.EVENT_SCHEDULE + UniqueIdGenerator.generate())
                    .scheduleEventIds(eventSchedule.getScheduleEventIds())
                    .name(eventSchedule.getName())
                    .description(eventSchedule.getDescription())
                    .createdOn(now)
                    .lastUpdatedOn(now)
                    .build();

            ddbTable.updateItem(eventScheduleDto);

            return EventSchedule.builder()
                    .clientId(eventScheduleDto.getPartitionKey())
                    .eventScheduleId(StringUtils.remove(eventScheduleDto.getSortKey(), SortKeyType.EVENT_SCHEDULE.name()))
                    .scheduleEventIds(eventScheduleDto.getScheduleEventIds())
                    .name(eventScheduleDto.getName())
                    .description(eventScheduleDto.getDescription())
                    .createdOn(eventScheduleDto.getCreatedOn())
                    .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
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
     * @param eventSchedule the object to replace the current database object
     */
    public EventSchedule update(EventSchedule eventSchedule) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            assert eventSchedule.getClientId() != null;
            assert eventSchedule.getEventScheduleId() != null;

            EventScheduleDto eventScheduleDto = EventScheduleDto.builder()
                    .partitionKey(eventSchedule.getClientId())
                    .sortKey(SortKeyType.EVENT_SCHEDULE + eventSchedule.getEventScheduleId())
                    .scheduleEventIds(eventSchedule.getScheduleEventIds())
                    .name(eventSchedule.getName())
                    .description(eventSchedule.getDescription())
                    .createdOn(eventSchedule.getCreatedOn())
                    .lastUpdatedOn(LocalDateTime.now())
                    .build();

            ddbTable.putItem(eventScheduleDto);

            return EventSchedule.builder()
                    .clientId(eventScheduleDto.getPartitionKey())
                    .eventScheduleId(StringUtils.remove(eventScheduleDto.getSortKey(), SortKeyType.EVENT_SCHEDULE.name()))
                    .scheduleEventIds(eventScheduleDto.getScheduleEventIds())
                    .name(eventScheduleDto.getName())
                    .description(eventScheduleDto.getDescription())
                    .createdOn(eventScheduleDto.getCreatedOn())
                    .lastUpdatedOn(eventScheduleDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
        }
    }

    public void delete(String clientId, String eventScheduleId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.EVENT_SCHEDULE, eventScheduleId);

            ddbTable.deleteItem(key);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
        }
    }
}
