package com.ebsolutions.eventsadminservice.filegeneration;

import com.ebsolutions.eventsadminservice.shared.Constants;
import com.ebsolutions.eventsadminservice.shared.exception.CsvGenerationException;
import com.ebsolutions.eventsadminservice.shared.util.MetricsStopwatch;
import de.siegmar.fastcsv.writer.CsvWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CsvGenerator {
  public ByteBuffer create(List<PublishedScheduledEventBus> publishedScheduledEvents)
      throws CsvGenerationException {
    MetricsStopwatch metricsStopwatch = new MetricsStopwatch();
    StringWriter stringWriter = new StringWriter();

    try {
      CsvWriter csvWriter = CsvWriter.builder().build(stringWriter);
      csvWriter.writeRecord(Constants.CSV_COLUMN_HEADERS);
      publishedScheduledEvents
          .forEach(publishedScheduledEvent ->
              csvWriter.writeRecord(
                  this.buildPublishedScheduledEventDtoStringList(publishedScheduledEvent))
          );

      return ByteBuffer.wrap(stringWriter.toString().getBytes());
    } catch (Exception e) {
      log.error("ERROR::{}", this.getClass().getName(), e);
      throw new CsvGenerationException(
          MessageFormat.format("Error in {0}: {1}", this.getClass().getName(), e.getMessage()));
    } finally {
      metricsStopwatch.logElapsedTime(
          MessageFormat.format("{0}::{1}", this.getClass().getName(), "create"));
    }
  }

  private List<String> buildPublishedScheduledEventDtoStringList(
      PublishedScheduledEventBus publishedScheduledEvent) {
    PublishedScheduledEventDto publishedScheduledEventDto = PublishedScheduledEventDto.builder()
        .eventOrganizerName(publishedScheduledEvent.getOrganizer() != null
            ? publishedScheduledEvent.getOrganizer().getName()
            : StringUtils.EMPTY)
        .eventVenueName(publishedScheduledEvent.getLocation() != null
            ? publishedScheduledEvent.getLocation().getName()
            : StringUtils.EMPTY)
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
