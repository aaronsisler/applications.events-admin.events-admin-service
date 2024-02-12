package com.ebsolutions.eventsadminservice.service;

import com.ebsolutions.eventsadminservice.dal.dao.LocationDao;
import com.ebsolutions.eventsadminservice.dal.dao.OrganizerDao;
import com.ebsolutions.eventsadminservice.dal.dao.PublishedEventScheduleDao;
import com.ebsolutions.eventsadminservice.dal.dao.ScheduledEventDao;
import com.ebsolutions.eventsadminservice.dal.dto.ScheduledEventBusDto;
import com.ebsolutions.eventsadminservice.model.*;
import com.ebsolutions.eventsadminservice.validator.DateValidator;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Prototype
public class PublishedEventScheduleOrchestrationService {
    private final OrganizerDao organizerDao;
    private final LocationDao locationDao;
    private final ScheduledEventDao scheduledEventDao;
    private final PublishedEventScheduleDao publishedEventScheduleDao;
    private final List<ScheduledEventBusDto> scheduledEventBusDtos = new ArrayList<>();
    private final List<ScheduledEvent> reoccurringEventsStandard = new ArrayList<>();
    private final List<ScheduledEvent> reoccurringEventsWeekly = new ArrayList<>();

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
        List<Organizer> organizers = organizerDao.read(publishedEventSchedule.getClientId(), organizerIds);

        // Get list of Locations for list of location ids
        List<Location> locations = locationDao.read(publishedEventSchedule.getClientId(), locationIds);

//        Map<ScheduledEventDay, ScheduledEvent> dayScheduledEventMap = scheduledEvents.stream()
//                .collect(Collectors.toMap(this::figureOutDay, Function.identity()));

        // Filter out the ScheduledEventType.SINGLE then null check the schedule event interval
//        Map<ScheduledEventInterval, List<ScheduledEvent>> scheduledEventIntervalListHashMap = scheduledEvents.stream()
//                .filter(scheduledEvent -> ScheduledEventType.SINGLE.equals(scheduledEvent.getScheduledEventType()))
//                .filter(scheduledEvent -> scheduledEvent.getScheduledEventInterval() != null)
//                .collect(Collectors.groupingBy(ScheduledEvent::getScheduledEventInterval));

        this.reoccurringEventsStandard.stream()
                .filter(scheduledEvent -> List.of(ScheduledEventInterval.DAILY, ScheduledEventInterval.WEEKDAYS).contains(scheduledEvent.getScheduledEventInterval()));

        // Create the list of dates from 1st of month to the end of month for given year/month in request
        LocalDate startOfMonth = LocalDate.of(publishedEventSchedule.getEventScheduleYear(), publishedEventSchedule.getEventScheduleMonth(), 1);
        LocalDate startOfNextMonth = startOfMonth.plusMonths(1);
        List<LocalDate> spanOfDates = startOfMonth.datesUntil(startOfNextMonth).toList();

        // TODO Go through the list and think of a way to break it into a KVP such that when going through the scheduled events, we can create a "hash" that can be found in the KVP

        // Loop through the days and see which scheduledEvents should be placed
        spanOfDates.forEach(this::figureItOut);

        // Create the CSV
        // Push the CSV to File Storage
        // Add the CSV Location to the Published Event Schedule
        // Save the Published Event Schedule to database
        // Return saved Published Event Schedule to user

        // Not sure when/where to do the below but don't forget
        // Remove the scheduled events that fall on a location's blackout date
        // Remove the scheduled events that fall on a scheduled event's blackout date
        // Add in the Single Scheduled Events
        return publishedEventScheduleDao.create(publishedEventSchedule);
    }

    private void figureItOut(LocalDate localDate) {
        // Each date we need to see if it matches a weekday
        if (DateValidator.isWeekday(localDate)) {
            // If it is a weekday?
        } else {
            // Not a WeekDay
        }
        // Each date we need to see if it matches a weekend
        // Each date we need to see if it matches a daily
        // Each date we need to see if it matches a weekly value i.e. MON, TUE
        this.scheduledEventBusDtos.add(
                ScheduledEventBusDto.builder().build()
        );
    }
}
