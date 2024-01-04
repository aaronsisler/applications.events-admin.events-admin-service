package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.DatabaseConstants;
import com.ebsolutions.eventsadminservice.dal.SortKeyType;
import com.ebsolutions.eventsadminservice.dal.dto.OrganizerDto;
import com.ebsolutions.eventsadminservice.dal.util.KeyBuilder;
import com.ebsolutions.eventsadminservice.exception.DataProcessingException;
import com.ebsolutions.eventsadminservice.model.Organizer;
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
public class OrganizerDao {
    private final DynamoDbTable<OrganizerDto> ddbTable;

    public OrganizerDao(DynamoDbEnhancedClient enhancedClient) {
        this.ddbTable = enhancedClient.table(DatabaseConstants.DATABASE_TABLE_NAME, TableSchema.fromBean(OrganizerDto.class));
    }

    public Organizer read(String clientId, String organizerId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.ORGANIZER, organizerId);

            OrganizerDto organizerDto = ddbTable.getItem(key);

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
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public List<Organizer> readAll(String clientId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            List<OrganizerDto> organizerDtos = ddbTable
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
                                    .organizerId(StringUtils.remove(organizerDto.getSortKey(), SortKeyType.ORGANIZER.name()))
                                    .name(organizerDto.getName())
                                    .createdOn(organizerDto.getCreatedOn())
                                    .lastUpdatedOn(organizerDto.getLastUpdatedOn())
                                    .build()
                    ).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "read"));
        }
    }

    public Organizer create(Organizer organizer) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            LocalDateTime now = LocalDateTime.now();

            assert organizer.getClientId() != null;

            OrganizerDto organizerDto = OrganizerDto.builder()
                    .partitionKey(organizer.getClientId())
                    .sortKey(SortKeyType.ORGANIZER + UniqueIdGenerator.generate())
                    .name(organizer.getName())
                    .createdOn(now)
                    .lastUpdatedOn(now)
                    .build();

            ddbTable.updateItem(organizerDto);

            return Organizer.builder()
                    .clientId(organizerDto.getPartitionKey())
                    .organizerId(StringUtils.remove(organizerDto.getSortKey(), SortKeyType.ORGANIZER.name()))
                    .name(organizerDto.getName())
                    .createdOn(organizerDto.getCreatedOn())
                    .lastUpdatedOn(organizerDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }

    /**
     * This will replace the entire database object with the input organizer
     *
     * @param organizer the object to replace the current database object
     */
    public Organizer update(Organizer organizer) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            assert organizer.getClientId() != null;
            assert organizer.getOrganizerId() != null;

            OrganizerDto organizerDto = OrganizerDto.builder()
                    .partitionKey(organizer.getClientId())
                    .sortKey(SortKeyType.ORGANIZER + organizer.getOrganizerId())
                    .name(organizer.getName())
                    .createdOn(organizer.getCreatedOn())
                    .lastUpdatedOn(LocalDateTime.now())
                    .build();

            ddbTable.putItem(organizerDto);

            return Organizer.builder()
                    .clientId(organizerDto.getPartitionKey())
                    .organizerId(StringUtils.remove(organizerDto.getSortKey(), SortKeyType.ORGANIZER.name()))
                    .name(organizerDto.getName())
                    .createdOn(organizerDto.getCreatedOn())
                    .lastUpdatedOn(organizerDto.getLastUpdatedOn())
                    .build();
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "update"));
        }
    }

    public void delete(String clientId, String organizerId) {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.ORGANIZER, organizerId);

            ddbTable.deleteItem(key);
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new DataProcessingException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "delete"));
        }
    }
}
