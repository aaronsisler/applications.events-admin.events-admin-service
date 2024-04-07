package com.ebsolutions.eventsadminservice.service;

import com.ebsolutions.eventsadminservice.dal.dao.*;
import com.ebsolutions.eventsadminservice.dal.util.CsvFileGenerator;
import com.ebsolutions.eventsadminservice.model.*;
import com.ebsolutions.eventsadminservice.validator.DateValidator;
import com.ebsolutions.eventsadminservice.validator.StringValidator;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Prototype
public class PublishedEventScheduleOrchestrationService {
    private final ScheduledEventDao scheduledEventDao;
    private final OrganizerDao organizerDao;
    private final LocationDao locationDao;
    private final CsvFileGenerator csvFileGenerator;
    private final PublishedEventScheduleDao publishedEventScheduleDao;
    private final FileStorageDao fileStorageDao;

    public PublishedEventScheduleOrchestrationService(ScheduledEventDao scheduledEventDao, OrganizerDao organizerDao, LocationDao locationDao, CsvFileGenerator csvFileGenerator, PublishedEventScheduleDao publishedEventScheduleDao, FileStorageDao fileStorageDao) {
        this.scheduledEventDao = scheduledEventDao;
        this.organizerDao = organizerDao;
        this.locationDao = locationDao;
        this.csvFileGenerator = csvFileGenerator;
        this.publishedEventScheduleDao = publishedEventScheduleDao;
        this.fileStorageDao = fileStorageDao;
    }

    public PublishedEventSchedule publishEventSchedule(PublishedEventSchedule publishedEventSchedule) {
        // Retrieve the scheduled events using the eventScheduleId
        List<ScheduledEvent> scheduledEvents = scheduledEventDao.readAll(publishedEventSchedule.getEventScheduleId());

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

        // Get list of Organizers for list of organizer ids
        Map<String, Organizer> organizers = organizerDao.read(publishedEventSchedule.getClientId(), organizerIds)
                .stream().collect(Collectors.toMap(Organizer::getOrganizerId, Function.identity()));

        // Get list of Locations for list of location ids
        Map<String, Location> locations = locationDao.read(publishedEventSchedule.getClientId(), locationIds)
                .stream().collect(Collectors.toMap(Location::getLocationId, Function.identity()));

        // Create the list of dates from 1st of month to the end of month for given year/month in request
        LocalDate startOfMonth = LocalDate.of(publishedEventSchedule.getEventScheduleYear(), publishedEventSchedule.getEventScheduleMonth(), 1);
        LocalDate startOfNextMonth = startOfMonth.plusMonths(1);
        List<LocalDate> spanOfDates = startOfMonth.datesUntil(startOfNextMonth).toList();

        // Loop through the days and see which scheduledEvents should be placed
        List<PublishedScheduledEvent> publishedScheduledEvents = new ArrayList<>();
        spanOfDates
                .forEach(localDate -> scheduledEvents
                        .forEach(scheduledEvent ->
                                publishedScheduledEvents.addAll(this.populatePublishedScheduledEvents(localDate, scheduledEvent))));

        // TODO Remove the scheduled events that fall on a location's blackout date
        // TODO Remove the scheduled events that fall on a scheduled event's blackout date

        publishedScheduledEvents.stream()
                .filter(publishedScheduledEvent -> !StringValidator.isBlank(publishedScheduledEvent.getScheduledEvent().getLocationId())).toList()
                .forEach(publishedScheduledEvent -> publishedScheduledEvent.setLocation(locations.get(publishedScheduledEvent.getScheduledEvent().getLocationId())));

        publishedScheduledEvents.stream()
                .filter(publishedScheduledEvent -> !StringValidator.isBlank(publishedScheduledEvent.getScheduledEvent().getOrganizerId()))
                .forEach(publishedScheduledEvent -> publishedScheduledEvent.setOrganizer(organizers.get(publishedScheduledEvent.getScheduledEvent().getOrganizerId())));

        // Create the CSV bytes
        ByteBuffer byteBuffer = this.csvFileGenerator.create(publishedScheduledEvents);
        // Push the CSV bytes to File Storage
        String fileLocation = this.fileStorageDao.create(publishedEventSchedule.getClientId(), byteBuffer);
        // Add the CSV Location to the Published Event Schedule
        publishedEventSchedule.setFileLocation(fileLocation);
        // Save and return the Published Event Schedule to database
        return publishedEventScheduleDao.create(publishedEventSchedule);
    }

    private List<PublishedScheduledEvent> populatePublishedScheduledEvents(LocalDate localDate, ScheduledEvent scheduledEvent) {
        List<PublishedScheduledEvent> returnedList = new ArrayList<>();

        // This covers ScheduledEventType.SINGLE
        if (ScheduledEventType.SINGLE.equals(scheduledEvent.getScheduledEventType())
                && scheduledEvent.getScheduledEventDate() != null
                && localDate.isEqual(scheduledEvent.getScheduledEventDate())) {
            returnedList.add(this.publishedScheduledEventBuilder(localDate, scheduledEvent));

            return returnedList;
        }

        // This covers ScheduledEventType.REOCCURRING
        if (ScheduledEventType.REOCCURRING.equals(scheduledEvent.getScheduledEventType())) {
            // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.DAILY
            if (ScheduledEventInterval.DAILY.equals(scheduledEvent.getScheduledEventInterval())) {
                returnedList.add(this.publishedScheduledEventBuilder(localDate, scheduledEvent));

                return returnedList;
            }

            // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKLY
            if (ScheduledEventInterval.WEEKLY.equals(scheduledEvent.getScheduledEventInterval())
                    && DateValidator.areDayOfWeekEqual(localDate, scheduledEvent.getScheduledEventDay())) {
                returnedList.add(this.publishedScheduledEventBuilder(localDate, scheduledEvent));
            }

            // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKDAYS
            if (ScheduledEventInterval.WEEKDAYS.equals(scheduledEvent.getScheduledEventInterval())
                    && DateValidator.isWeekday(localDate)) {
                returnedList.add(this.publishedScheduledEventBuilder(localDate, scheduledEvent));
            }

            // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKENDS
            if (ScheduledEventInterval.WEEKENDS.equals(scheduledEvent.getScheduledEventInterval())
                    && DateValidator.isWeekend(localDate)) {
                returnedList.add(this.publishedScheduledEventBuilder(localDate, scheduledEvent));
            }
        }

        return returnedList;
    }

    private PublishedScheduledEvent publishedScheduledEventBuilder(LocalDate localDate, ScheduledEvent scheduledEvent) {
        return PublishedScheduledEvent.builder()
                .scheduledEvent(scheduledEvent)
                .eventStartDate(localDate)
                .eventStartTime(scheduledEvent.getStartTime())
                .eventLength(ChronoUnit.MINUTES.between(scheduledEvent.getStartTime(), scheduledEvent.getEndTime()))
                .eventEndDate(localDate)
                .eventEndTime(scheduledEvent.getEndTime())
                .eventName(scheduledEvent.getName())
                .eventDescription(scheduledEvent.getDescription())
                .eventCategory(scheduledEvent.getCategory())
                .build();
    }
}
