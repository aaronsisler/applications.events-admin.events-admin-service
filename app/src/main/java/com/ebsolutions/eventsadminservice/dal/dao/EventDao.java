package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.DatabaseConstants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.EventDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Event;
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
public class EventDao {
    private final DynamoDbTable<EventDto> ddbTable;

    public EventDao(DynamoDbEnhancedClient enhancedClient) {
        this.ddbTable = enhancedClient.table(DatabaseConstants.DATABASE_TABLE_NAME, TableSchema.fromBean(EventDto.class));
    }

    public Event read(String clientId, String eventId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.EVENT, eventId);

            EventDto eventDto = ddbTable.getItem(key);

            return eventDto == null
                    ? null
                    : Event.builder()
                    .clientId(eventDto.getPartitionKey())
                    .eventId(StringUtils.remove(eventDto.getSortKey(), SortKeyType.EVENT.name()))
                    .name(eventDto.getName())
                    .description(eventDto.getDescription())
                    .locationId(eventDto.getLocationId())
                    .organizerIds(eventDto.getOrganizerIds())
                    .createdOn(eventDto.getCreatedOn())
                    .lastUpdatedOn(eventDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<Event> readAll(String clientId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            List<EventDto> eventDtos = ddbTable
                    .query(r -> r.queryConditional(
                            sortBeginsWith(s
                                    -> s.partitionValue(clientId).sortValue(SortKeyType.EVENT.name()).build()))
                    )
                    .items()
                    .stream()
                    .toList();

            return eventDtos.stream()
                    .map(eventDto ->
                            Event.builder()
                                    .clientId(eventDto.getPartitionKey())
                                    .eventId(StringUtils.remove(eventDto.getSortKey(), SortKeyType.EVENT.name()))
                                    .name(eventDto.getName())
                                    .description(eventDto.getDescription())
                                    .locationId(eventDto.getLocationId())
                                    .organizerIds(eventDto.getOrganizerIds())
                                    .createdOn(eventDto.getCreatedOn())
                                    .lastUpdatedOn(eventDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public Event create(Event event) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            LocalDateTime now = LocalDateTime.now();

            assert event.getClientId() != null;

            EventDto eventDto = EventDto.builder()
                    .partitionKey(event.getClientId())
                    .sortKey(SortKeyType.EVENT + UniqueIdGenerator.generate())
                    .name(event.getName())
                    .description(event.getDescription())
                    .locationId(event.getLocationId())
                    .organizerIds(event.getOrganizerIds())
                    .createdOn(now)
                    .lastUpdatedOn(now)
                    .build();

            ddbTable.updateItem(eventDto);

            return Event.builder()
                    .clientId(eventDto.getPartitionKey())
                    .eventId(StringUtils.remove(eventDto.getSortKey(), SortKeyType.EVENT.name()))
                    .name(eventDto.getName())
                    .description(eventDto.getDescription())
                    .locationId(eventDto.getLocationId())
                    .organizerIds(eventDto.getOrganizerIds())
                    .createdOn(eventDto.getCreatedOn())
                    .lastUpdatedOn(eventDto.getLastUpdatedOn())
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
     * @param event the object to replace the current database object
     */
    public Event update(Event event) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            assert event.getClientId() != null;
            assert event.getEventId() != null;

            EventDto eventDto = EventDto.builder()
                    .partitionKey(event.getClientId())
                    .sortKey(event.getEventId())
                    .name(event.getName())
                    .description(event.getDescription())
                    .locationId(event.getLocationId())
                    .organizerIds(event.getOrganizerIds())
                    .createdOn(event.getCreatedOn())
                    .lastUpdatedOn(LocalDateTime.now())
                    .build();

            ddbTable.putItem(eventDto);

            return Event.builder()
                    .clientId(eventDto.getPartitionKey())
                    .eventId(StringUtils.remove(eventDto.getSortKey(), SortKeyType.EVENT.name()))
                    .name(eventDto.getName())
                    .description(eventDto.getDescription())
                    .locationId(eventDto.getLocationId())
                    .organizerIds(eventDto.getOrganizerIds())
                    .createdOn(eventDto.getCreatedOn())
                    .lastUpdatedOn(eventDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
        }
    }

    public void delete(String clientId, String eventId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.EVENT, eventId);

            ddbTable.deleteItem(key);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
        }
    }
}
