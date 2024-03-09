package com.ebsolutions.eventsadminservice.dal.util;

import com.ebsolutions.eventsadminservice.config.Constants;
import com.ebsolutions.eventsadminservice.dal.dto.PublishedScheduledEventDto;
import com.ebsolutions.eventsadminservice.exception.CsvGenerationException;
import com.ebsolutions.eventsadminservice.model.PublishedScheduledEvent;
import com.ebsolutions.eventsadminservice.util.MetricsStopWatch;
import de.siegmar.fastcsv.writer.CsvWriter;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Prototype
public class CsvFileGenerator {
    public ByteBuffer create(List<PublishedScheduledEvent> publishedScheduledEvents) throws IOException {
        MetricsStopWatch metricsStopWatch = new MetricsStopWatch();
        try {
            StringWriter stringWriter = new StringWriter();
            CsvWriter csvWriter = CsvWriter.builder().build(stringWriter);
            csvWriter.writeRecord(Constants.CSV_COLUMN_HEADERS);
            publishedScheduledEvents
                    .forEach(publishedScheduledEvent ->
                            csvWriter.writeRecord(this.buildPublishedScheduledEventDtoStringList(publishedScheduledEvent))
                    );

            return ByteBuffer.wrap(stringWriter.toString().getBytes());
        } catch (Exception e) {
            log.error("ERROR::{}", this.getClass().getName(), e);
            throw new CsvGenerationException(MessageFormat.format("Error in {0}", this.getClass().getName()), e);
        } finally {
            metricsStopWatch.logElapsedTime(MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
        }
    }

    private List<String> buildPublishedScheduledEventDtoStringList(PublishedScheduledEvent publishedScheduledEvent) {
        PublishedScheduledEventDto publishedScheduledEventDto = PublishedScheduledEventDto.builder()
                .eventOrganizerName(publishedScheduledEvent.getOrganizer() != null
                        ? publishedScheduledEvent.getOrganizer().getName()
                        : Constants.EMPTY_STRING)
                .eventVenueName(publishedScheduledEvent.getLocation() != null
                        ? publishedScheduledEvent.getLocation().getName()
                        : Constants.EMPTY_STRING)
                .eventStartDate(publishedScheduledEvent.getEventStartDate().toString())
                .eventStartTime(publishedScheduledEvent.getEventStartTime().toString())
                .eventLength(String.valueOf(publishedScheduledEvent.getEventLength()))
                .eventEndDate(publishedScheduledEvent.getEventEndDate().toString())
                .eventEndTime(publishedScheduledEvent.getEventEndTime().toString())
                .eventName(publishedScheduledEvent.getEventName())
                .eventCategory(publishedScheduledEvent.getEventCategory())
                .eventDescription(publishedScheduledEvent.getEventDescription())
                .build();

        return List.of(
                publishedScheduledEventDto.getEventOrganizerName(),
                publishedScheduledEventDto.getEventStartDate(),
                publishedScheduledEventDto.getEventStartTime(),
                publishedScheduledEventDto.getEventLength(),
                publishedScheduledEventDto.getEventEndDate(),
                publishedScheduledEventDto.getEventEndTime(),
                publishedScheduledEventDto.getEventName(),
                publishedScheduledEventDto.getEventCategory(),
                publishedScheduledEventDto.getEventVenueName(),
                publishedScheduledEventDto.getEventDescription()
        );
    }
}
