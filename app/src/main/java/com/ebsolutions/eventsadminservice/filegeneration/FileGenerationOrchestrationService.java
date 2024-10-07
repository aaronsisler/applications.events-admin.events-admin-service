package com.ebsolutions.eventsadminservice.filegeneration;

import com.ebsolutions.eventsadminservice.location.LocationDao;
import com.ebsolutions.eventsadminservice.model.Location;
import com.ebsolutions.eventsadminservice.model.Organizer;
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
import com.ebsolutions.eventsadminservice.model.ScheduledEventInterval;
import com.ebsolutions.eventsadminservice.model.ScheduledEventType;
import com.ebsolutions.eventsadminservice.organizer.OrganizerDao;
import com.ebsolutions.eventsadminservice.publishedeventschedule.PublishedEventScheduleDao;
import com.ebsolutions.eventsadminservice.scheduledevent.ScheduledEventDao;
import com.ebsolutions.eventsadminservice.shared.util.DateValidator;
import com.ebsolutions.eventsadminservice.shared.util.FileLocationUtil;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FileGenerationOrchestrationService {
  private final CsvGenerator csvGenerator;
  private final FileDao fileDao;
  private final LocationDao locationDao;
  private final OrganizerDao organizerDao;
  private final PublishedEventScheduleDao publishedEventScheduleDao;
  private final ScheduledEventDao scheduledEventDao;

  public PublishedEventSchedule publishEventSchedule(
      PublishedEventSchedule publishedEventSchedule) {
    // Retrieve the scheduled events using the eventScheduleId
    List<ScheduledEvent> scheduledEvents =
        scheduledEventDao.readAll(publishedEventSchedule.getEventScheduleId());

    // Get distinct list of location ids from Scheduled Events
    List<String> locationIds = scheduledEvents.stream()
        .map(ScheduledEvent::getLocationId)
        .distinct()
        .toList();

    // Get distinct list of organizer ids from Scheduled Events
    List<String> organizerIds = scheduledEvents.stream()
        .map(ScheduledEvent::getOrganizerId)
        .distinct()
        .toList();

    // Get list of all Organizers for client id and then filter out Organizers not needed
    Map<String, Organizer>
        organizers = organizerDao.readAll(publishedEventSchedule.getClientId())
        .stream()
        .filter(organizer -> organizerIds.contains(organizer.getOrganizerId()))
        .collect(Collectors.toMap(Organizer::getOrganizerId, Function.identity()));

    // Get list of all Locations for client id and then filter out Locations not needed
    Map<String, Location> locations = locationDao.readAll(publishedEventSchedule.getClientId())
        .stream()
        .filter(location -> locationIds.contains(location.getLocationId()))
        .collect(Collectors.toMap(Location::getLocationId, Function.identity()));

    // Create the list of dates from 1st of month to the end of month for given year/month in request
    LocalDate startOfMonth = LocalDate.of(publishedEventSchedule.getEventScheduleYear(),
        publishedEventSchedule.getEventScheduleMonth(), 1);
    LocalDate startOfNextMonth = startOfMonth.plusMonths(1);
    List<LocalDate> spanOfDates = startOfMonth.datesUntil(startOfNextMonth).toList();

    // Loop through the days and see which scheduledEvents should be placed
    List<PublishedScheduledEventBus> publishedScheduledEvents = new ArrayList<>();
    spanOfDates
        .forEach(localDate -> scheduledEvents
            .forEach(scheduledEvent ->
                publishedScheduledEvents.addAll(
                    this.populatePublishedScheduledEvents(localDate, scheduledEvent))));

    // TODO Remove the scheduled events that fall on a location's blackout date
    // TODO Remove the scheduled events that fall on a scheduled event's blackout date

    publishedScheduledEvents.stream()
        .filter(publishedScheduledEvent -> !StringUtils.isBlank(
            publishedScheduledEvent.getScheduledEvent().getLocationId())).toList()
        .forEach(publishedScheduledEvent -> publishedScheduledEvent.setLocation(
            locations.get(publishedScheduledEvent.getScheduledEvent().getLocationId())));

    publishedScheduledEvents.stream()
        .filter(publishedScheduledEvent -> !StringUtils.isBlank(
            publishedScheduledEvent.getScheduledEvent().getOrganizerId()))
        .forEach(publishedScheduledEvent -> publishedScheduledEvent.setOrganizer(
            organizers.get(publishedScheduledEvent.getScheduledEvent().getOrganizerId())));

    // Create the CSV bytes
    ByteBuffer byteBuffer = this.csvGenerator.create(publishedScheduledEvents);

    // Format the CSV into bytes and push to File Storage
    String filename = MessageFormat.format("{0}.csv", LocalDateTime.now().toString());
    String fileLocation =
        FileLocationUtil.build(publishedEventSchedule.getClientId(), filename);
    this.fileDao.create(fileLocation, byteBuffer);
    // Add the CSV Location to the Published Event Schedule
    publishedEventSchedule.setFilename(filename);
    // Save and return the Published Event Schedule to database
    return publishedEventScheduleDao.create(publishedEventSchedule);
  }

  private List<PublishedScheduledEventBus> populatePublishedScheduledEvents(LocalDate localDate,
                                                                            ScheduledEvent scheduledEvent) {
    List<PublishedScheduledEventBus> returnedList = new ArrayList<>();

    // This covers ScheduledEventType.SINGLE
    if (ScheduledEventType.SINGLE.equals(scheduledEvent.getScheduledEventType())
        && scheduledEvent.getScheduledEventDate() != null
        && localDate.isEqual(scheduledEvent.getScheduledEventDate())) {
      returnedList.add(this.hydratePublishedScheduledEventBus(localDate, scheduledEvent));

      return returnedList;
    }

    // This covers ScheduledEventType.REOCCURRING
    if (ScheduledEventType.RECURRING.equals(scheduledEvent.getScheduledEventType())) {
      // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.DAILY
      if (ScheduledEventInterval.DAILY.equals(scheduledEvent.getScheduledEventInterval())) {
        returnedList.add(this.hydratePublishedScheduledEventBus(localDate, scheduledEvent));

        return returnedList;
      }

      // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKLY
      if (ScheduledEventInterval.WEEKLY.equals(scheduledEvent.getScheduledEventInterval())
          && DateValidator.areDayOfWeekEqual(localDate, scheduledEvent.getScheduledEventDay())) {
        returnedList.add(this.hydratePublishedScheduledEventBus(localDate, scheduledEvent));
      }

      // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKDAYS
      if (ScheduledEventInterval.WEEKDAYS.equals(scheduledEvent.getScheduledEventInterval())
          && DateValidator.isWeekday(localDate)) {
        returnedList.add(this.hydratePublishedScheduledEventBus(localDate, scheduledEvent));
      }

      // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKENDS
      if (ScheduledEventInterval.WEEKENDS.equals(scheduledEvent.getScheduledEventInterval())
          && DateValidator.isWeekend(localDate)) {
        returnedList.add(this.hydratePublishedScheduledEventBus(localDate, scheduledEvent));
      }
    }

    return returnedList;
  }

  private PublishedScheduledEventBus hydratePublishedScheduledEventBus(LocalDate localDate,
                                                                       ScheduledEvent scheduledEvent) {
    return PublishedScheduledEventBus.builder()
        .scheduledEvent(scheduledEvent)
        .eventStartDate(localDate)
        .eventStartTime(scheduledEvent.getStartTime())
        .eventEndDate(localDate)
        .eventEndTime(scheduledEvent.getEndTime())
        .eventName(scheduledEvent.getName())
        .eventDescription(scheduledEvent.getDescription())
        .eventCategory(scheduledEvent.getCategory())
        .build();
  }
}
