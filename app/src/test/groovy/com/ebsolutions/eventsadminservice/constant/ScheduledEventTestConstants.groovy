package com.ebsolutions.eventsadminservice.constant

import com.ebsolutions.eventsadminservice.model.ScheduledEvent
import com.ebsolutions.eventsadminservice.model.ScheduledEventDay
import com.ebsolutions.eventsadminservice.model.ScheduledEventInterval
import com.ebsolutions.eventsadminservice.model.ScheduledEventType

import java.time.LocalDate
import java.time.LocalTime

/**
 * Holds all of the test values for Scheduled Event
 * Leading Dashes on event ids are to help with the DB Setup
 */
class ScheduledEventTestConstants {
    public static LocalTime startTime = LocalTime.of(13, 45, 42)
    public static LocalTime endTime = LocalTime.of(20, 30, 25)
    public static LocalDate scheduledEventDate = LocalDate.of(20, 30, 25)

    public static ScheduledEvent GET_SCHEDULED_EVENT = ScheduledEvent.builder()
            .eventScheduleId("get-scheduled-event-event-schedule-id")
            .scheduledEventId("-get-scheduled-event-scheduled-event-id")
            .locationId("get-scheduled-event-location-id")
            .organizerIds(List.of(
                    "get-scheduled-event-organizer-1-id",
                    "get-scheduled-event-organizer-2-id"
            ))
            .name("Get Scheduled Event - Scheduled Event Name")
            .startTime(startTime)
            .endTime(endTime)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllScheduledEventsEventScheduleId = "get-all-scheduled-events-event-schedule-id"

    public static ScheduledEvent GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE = ScheduledEvent.builder()
            .eventScheduleId(getAllScheduledEventsEventScheduleId)
            .scheduledEventId("-get-all-scheduled-events-scheduled-event-1-scheduled-event-id")
            .locationId("get-scheduled-event-scheduled-event-1-location-id")
            .organizerIds(List.of(
                    "get-scheduled-event-scheduled-event-1-organizer-1-id",
                    "get-scheduled-event-scheduled-event-1-organizer-2-id"
            ))
            .name("Get All Scheduled Events - Scheduled Event 1 Name")
            .startTime(startTime)
            .endTime(endTime)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO = ScheduledEvent.builder()
            .eventScheduleId(getAllScheduledEventsEventScheduleId)
            .scheduledEventId("-get-all-scheduled-events-scheduled-event-2-scheduled-event-id")
            .locationId("get-scheduled-event-scheduled-event-2-location-id")
            .organizerIds(List.of(
                    "get-scheduled-event-scheduled-event-2-organizer-1-id",
                    "get-scheduled-event-scheduled-event-2-organizer-2-id"
            ))
            .name("Get All Scheduled Events - Scheduled Event 2 Name")
            .startTime(startTime)
            .endTime(endTime)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent DELETE_SCHEDULED_EVENT = ScheduledEvent.builder()
            .eventScheduleId("delete-scheduled-event-event-schedule-id")
            .scheduledEventId("-delete-scheduled-event-scheduled-event-id")
            .name("Delete Scheduled Event - Scheduled Event Name")
            .startTime(startTime)
            .endTime(endTime)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_SCHEDULED_EVENT_SINGLE = ScheduledEvent.builder()
            .eventScheduleId("create-scheduled-event-scheduled-event-single-event-schedule-id")
            .locationId("create-scheduled-event-scheduled-event-single-location-id")
            .organizerIds(List.of(
                    "create-scheduled-event-scheduled-event-single-organizer-1-id",
                    "create-scheduled-event-scheduled-event-single-organizer-2-id"
            ))
            .name("Create Scheduled Event - Scheduled Event Single - Scheduled Event Name")
            .startTime(startTime)
            .endTime(endTime)
            .scheduledEventType(ScheduledEventType.SINGLE)
            .scheduledEventDate(scheduledEventDate)
            .cost(1699)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_SCHEDULED_EVENT_REOCCURRING = ScheduledEvent.builder()
            .eventScheduleId("create-scheduled-event-scheduled-event-reoccurring-event-schedule-id")
            .locationId("create-scheduled-event-scheduled-event-reoccurring-location-id")
            .organizerIds(List.of(
                    "create-scheduled-event-scheduled-event-reoccurring-organizer-1-id",
                    "create-scheduled-event-scheduled-event-reoccurring-organizer-2-id"
            ))
            .name("Create Scheduled Event - Scheduled Event Reoccurring - Scheduled Event Name")
            .startTime(startTime)
            .endTime(endTime)
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKLY)
            .scheduledEventDay(ScheduledEventDay.TUE)
            .cost(3999)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateScheduledEventOrganizerOneId = "update-scheduled-event-organizer-1-id"
    public static String updateScheduledEventOrganizerTwoId = "update-scheduled-event-organizer-2-id"
    public static String updateScheduledEventUpdatedOrganizerThreeId = "update-scheduled-event-organizer-3-id"
    public static String updateScheduledEventUpdatedLocationId = "update-scheduled-event-updated-location-id"
    public static String updateScheduledEventUpdatedName = "Updated Scheduled Event Name"


    public static ScheduledEvent UPDATE_SCHEDULED_EVENT = ScheduledEvent.builder()
            .eventScheduleId("update-scheduled-event-event-schedule-id")
            .scheduledEventId("-update-scheduled-event-scheduled-event-id")
            .locationId("update-scheduled-event-location-id")
            .organizerIds(List.of(updateScheduledEventOrganizerOneId, updateScheduledEventOrganizerTwoId))
            .name("Update Scheduled Event - Scheduled Event Name")
            .startTime(startTime)
            .endTime(endTime)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
