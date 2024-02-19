package com.ebsolutions.eventsadminservice.service;

import com.ebsolutions.eventsadminservice.config.Constants;
import com.ebsolutions.eventsadminservice.dal.dao.LocationDao;
import com.ebsolutions.eventsadminservice.dal.dao.OrganizerDao;
import com.ebsolutions.eventsadminservice.dal.dao.PublishedEventScheduleDao;
import com.ebsolutions.eventsadminservice.dal.dao.ScheduledEventDao;
import com.ebsolutions.eventsadminservice.dal.dto.PublishedScheduledEventDto;
import com.ebsolutions.eventsadminservice.model.*;
import com.ebsolutions.eventsadminservice.validator.StringValidator;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

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
    private final OrganizerDao organizerDao;
    private final LocationDao locationDao;
    private final ScheduledEventDao scheduledEventDao;
    private final PublishedEventScheduleDao publishedEventScheduleDao;

    public PublishedEventScheduleOrchestrationService(LocationDao locationDao, OrganizerDao organizerDao, ScheduledEventDao scheduledEventDao, PublishedEventScheduleDao publishedEventScheduleDao) {
        this.locationDao = locationDao;
        this.organizerDao = organizerDao;
        this.scheduledEventDao = scheduledEventDao;
        this.publishedEventScheduleDao = publishedEventScheduleDao;
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
                .map(ScheduledEvent::getLocationId)
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
                                publishedScheduledEvents.add(this.constructPublishedScheduledEvent(localDate, scheduledEvent))));


        publishedScheduledEvents.stream()
                .filter(publishedScheduledEvent -> StringValidator.isBlank(publishedScheduledEvent.getScheduledEvent().getLocationId()))
                .forEach(publishedScheduledEvent -> publishedScheduledEvent.setLocation(locations.get(publishedScheduledEvent.getScheduledEvent().getLocationId())));

        publishedScheduledEvents.stream()
                .filter(publishedScheduledEvent -> StringValidator.isBlank(publishedScheduledEvent.getScheduledEvent().getOrganizerId()))
                .forEach(publishedScheduledEvent -> publishedScheduledEvent.setOrganizer(organizers.get(publishedScheduledEvent.getScheduledEvent().getOrganizerId())));

        // TODO Remove the scheduled events that fall on a location's blackout date
        // TODO Remove the scheduled events that fall on a scheduled event's blackout date

        // Create the data for the CSV
        List<PublishedScheduledEventDto> publishedEventScheduleDtos = publishedScheduledEvents.stream()
                .map(this::constructPublishedScheduledEventDto).toList();

        // Create the CSV
        // Push the CSV to File Storage
        // Add the CSV Location to the Published Event Schedule
        // Save the Published Event Schedule to database
        // Return saved Published Event Schedule to user

        return publishedEventScheduleDao.create(publishedEventSchedule);
    }

    private PublishedScheduledEventDto constructPublishedScheduledEventDto(PublishedScheduledEvent publishedScheduledEvent) {
        return PublishedScheduledEventDto.builder()
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
    }

    private PublishedScheduledEvent constructPublishedScheduledEvent(LocalDate localDate, ScheduledEvent scheduledEvent) {
        // This covers ScheduledEventType.SINGLE
        if (scheduledEvent.getScheduledEventDate() != null) {
            return PublishedScheduledEvent.builder()
                    .scheduledEvent(scheduledEvent)
                    .eventStartDate(scheduledEvent.getScheduledEventDate())
                    .eventStartTime(scheduledEvent.getStartTime())
                    .eventLength(ChronoUnit.MINUTES.between(scheduledEvent.getStartTime(), scheduledEvent.getEndTime()))
                    .eventEndDate(scheduledEvent.getScheduledEventDate())
                    .eventEndTime(scheduledEvent.getEndTime())
                    .eventName(scheduledEvent.getName())
                    .eventDescription(scheduledEvent.getDescription())
                    .eventCategory(scheduledEvent.getCategory())
                    .build();
        }

        // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKLY
        // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKENDS
        // This covers ScheduledEventType.REOCCURRING and ScheduledEventInterval.WEEKDAYS
        // This should cover all remaining cases i.e. ScheduledEventType.REOCCURRING and ScheduledEventInterval.DAILY
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
