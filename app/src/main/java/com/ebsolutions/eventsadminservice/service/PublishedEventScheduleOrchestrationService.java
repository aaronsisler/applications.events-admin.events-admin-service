package com.ebsolutions.eventsadminservice.service;

import com.ebsolutions.eventsadminservice.dal.dao.LocationDao;
import com.ebsolutions.eventsadminservice.dal.dao.OrganizerDao;
import com.ebsolutions.eventsadminservice.dal.dao.PublishedEventScheduleDao;
import com.ebsolutions.eventsadminservice.dal.dao.ScheduledEventDao;
import com.ebsolutions.eventsadminservice.model.Location;
import com.ebsolutions.eventsadminservice.model.Organizer;
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import com.ebsolutions.eventsadminservice.model.ScheduledEvent;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
        List<Organizer> organizers = organizerDao.readAll(publishedEventSchedule.getClientId(), organizerIds);
        // Get list of Locations for list of location ids
        List<Location> locations = locationDao.readAll(publishedEventSchedule.getClientId(), locationIds);
        // Group the reoccurring Scheduled Events by type (Standard and Weekly)
        // Create the list of dates from 1st of month to the end of month for given year/month in request
        // Go through the list and think of a way to break it into a KVP such that when
        // going through the scheduled events, we can create a "hash" that can be found in the KVP

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
}
