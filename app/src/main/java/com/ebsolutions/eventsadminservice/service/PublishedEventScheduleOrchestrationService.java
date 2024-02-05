package com.ebsolutions.eventsadminservice.service;

import com.ebsolutions.eventsadminservice.dal.dao.PublishedEventScheduleDao;
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule;
import io.micronaut.context.annotation.Prototype;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Prototype
public class PublishedEventScheduleOrchestrationService {
    private PublishedEventScheduleDao publishedEventScheduleDao;

    public PublishedEventScheduleOrchestrationService(PublishedEventScheduleDao publishedEventScheduleDao) {
        this.publishedEventScheduleDao = publishedEventScheduleDao;
    }

    public PublishedEventSchedule publishEventSchedule(PublishedEventSchedule publishedEventSchedule) {
        // Retrieve the scheduled events using the eventScheduleId
        // Get distinct list of location ids from Scheduled Events
        // Get distinct list of organizer ids from Scheduled Events
        // Get list of Locations for list of location ids
        // Get list of Organizers for list of organizer ids
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
