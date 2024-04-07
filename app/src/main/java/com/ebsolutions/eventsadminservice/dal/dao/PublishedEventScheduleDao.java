package com.ebsolutions.eventsadminservice.dal.dao;

import com.ebsolutions.eventsadminservice.config.Constants;
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

@Slf4j
@Prototype
public class PublishedEventScheduleDao {
    private final DynamoDbTable<PublishedEventScheduleDto> ddbTable;

    public PublishedEventScheduleDao(DynamoDbEnhancedClient enhancedClient) {
        this.ddbTable = enhancedClient.table(Constants.DATABASE_TABLE_NAME, TableSchema.fromBean(PublishedEventScheduleDto.class));
    }

    public PublishedEventSchedule read(String clientId, String publishedEventScheduleId) throws DataProcessingException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            Key key = KeyBuilder.build(clientId, SortKeyType.PUBLISHED_EVENT_SCHEDULE, publishedEventScheduleId);

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
//                    .locationBlackouts(publishedEventScheduleDto.getLocationBlackouts())
//                    .eventBlackouts(publishedEventScheduleDto.getEventBlackouts())
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
//                    .locationBlackouts(publishedEventSchedule.getLocationBlackouts())
//                    .eventBlackouts(publishedEventSchedule.getEventBlackouts())
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
//                    .locationBlackouts(publishedEventScheduleDto.getLocationBlackouts())
//                    .eventBlackouts(publishedEventScheduleDto.getEventBlackouts())
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
}
