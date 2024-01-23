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
    public static LocalDate scheduledEventDate = LocalDate.of(2023, 11, 25)

    public static ScheduledEvent GET_SCHEDULED_EVENT_SINGLE = ScheduledEvent.builder()
            .eventScheduleId("get-scheduled-event-single-event-schedule-id")
            .scheduledEventId("-get-scheduled-event-single-scheduled-event-id")
            .clientId("get-scheduled-event-single-client-id")
            .eventId("get-scheduled-event-single-event-id")
            .locationId("get-scheduled-event-single-location-id")
            .organizerIds(List.of(
                    "get-scheduled-event-single-organizer-1-id",
                    "get-scheduled-event-single-organizer-2-id"
            ))
            .name("Get Scheduled Event Single - Scheduled Event Name")
            .category("Get Scheduled Event Single - Scheduled Event Category")
            .scheduledEventType(ScheduledEventType.SINGLE)
            .scheduledEventDate(scheduledEventDate)
            .startTime(startTime)
            .endTime(endTime)
            .cost(550)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent GET_SCHEDULED_EVENT_REOCCURRING_STANDARD = ScheduledEvent.builder()
            .eventScheduleId("get-scheduled-event-reoccurring-standard-event-schedule-id")
            .scheduledEventId("-get-scheduled-event-reoccurring-standard-scheduled-event-id")
            .clientId("get-scheduled-event-reoccurring-standard-client-id")
            .eventId("get-scheduled-event-reoccurring-standard-event-id")
            .locationId("get-scheduled-event-reoccurring-standard-location-id")
            .organizerIds(List.of(
                    "get-scheduled-event-reoccurring-standard-organizer-1-id",
                    "get-scheduled-event-reoccurring-standard-organizer-2-id"
            ))
            .name("Get Scheduled Event Reoccurring Standard - Scheduled Event Name")
            .description("Get Scheduled Event Reoccurring Standard - Scheduled Event Description")
            .category("Get Scheduled Event Reoccurring Standard - Scheduled Event Category")
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKDAYS)
            .startTime(startTime)
            .endTime(endTime)
            .cost(350)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY = ScheduledEvent.builder()
            .eventScheduleId("get-scheduled-event-reoccurring-weekly-event-schedule-id")
            .scheduledEventId("-get-scheduled-event-reoccurring-weekly-scheduled-event-id")
            .clientId("get-scheduled-event-reoccurring-weekly-client-id")
            .eventId("get-scheduled-event-reoccurring-weekly-event-id")
            .locationId("get-scheduled-event-reoccurring-weekly-location-id")
            .organizerIds(List.of(
                    "get-scheduled-event-reoccurring-weekly-organizer-1-id",
                    "get-scheduled-event-reoccurring-weekly-organizer-2-id"
            ))
            .name("Get Scheduled Event Reoccurring Weekly - Scheduled Event Name")
            .description("Get Scheduled Event Reoccurring Weekly - Scheduled Event Description")
            .category("Get Scheduled Event Reoccurring Weekly - Scheduled Event Category")
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKLY)
            .scheduledEventDay(ScheduledEventDay.TUE)
            .startTime(startTime)
            .endTime(endTime)
            .cost(450)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllScheduledEventsEventScheduleId = "get-all-scheduled-events-event-schedule-id"

    public static ScheduledEvent GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE = ScheduledEvent.builder()
            .eventScheduleId(getAllScheduledEventsEventScheduleId)
            .scheduledEventId("-get-all-scheduled-events-scheduled-event-1-scheduled-event-id")
            .clientId("get-all-scheduled-events-scheduled-event-1-client-id")
            .eventId("get-all-scheduled-events-scheduled-event-1-event-id")
            .locationId("get-all-scheduled-events-scheduled-event-1-location-id")
            .organizerIds(List.of(
                    "get-all-scheduled-events-scheduled-event-1-organizer-1-id",
                    "get-all-scheduled-events-scheduled-event-1-organizer-2-id"
            ))
            .scheduledEventType(ScheduledEventType.SINGLE)
            .name("Get All Scheduled Events - Scheduled Event 1 Name")
            .description("Get All Scheduled Events - Scheduled Event 1 Description")
            .category("Get All Scheduled Events Single - Scheduled Event 1 Category")
            .startTime(startTime)
            .endTime(endTime)
            .scheduledEventDate(scheduledEventDate)
            .cost(550)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO = ScheduledEvent.builder()
            .eventScheduleId(getAllScheduledEventsEventScheduleId)
            .scheduledEventId("-get-all-scheduled-events-scheduled-event-2-scheduled-event-id")
            .clientId("get-all-scheduled-events-reoccurring-weekly-client-id")
            .eventId("get-all-scheduled-events-reoccurring-weekly-event-id")
            .locationId("get-all-scheduled-events-reoccurring-weekly-location-id")
            .organizerIds(List.of(
                    "get-all-scheduled-events-reoccurring-weekly-organizer-1-id",
                    "get-all-scheduled-events-reoccurring-weekly-organizer-2-id"
            ))
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKLY)
            .scheduledEventDay(ScheduledEventDay.TUE)
            .name("Get All Scheduled Events - Scheduled Event 2 Name")
            .description("Get All Scheduled Events - Scheduled Event 2 Description")
            .category("Get All Scheduled Events - Scheduled Event 2 Category")
            .startTime(startTime)
            .endTime(endTime)
            .cost(450)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent DELETE_SCHEDULED_EVENT = ScheduledEvent.builder()
            .eventScheduleId("delete-scheduled-event-event-schedule-id")
            .scheduledEventId("-delete-scheduled-event-scheduled-event-id")
            .clientId("delete-scheduled-event-client-id")
            .eventId("delete-scheduled-event-event-id")
            .name("Delete Scheduled Event - Scheduled Event Name")
            .startTime(startTime)
            .endTime(endTime)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_SCHEDULED_EVENT_SINGLE = ScheduledEvent.builder()
            .eventScheduleId("create-scheduled-event-scheduled-event-single-event-schedule-id")
            .clientId("create-scheduled-event-single-client-id")
            .eventId("create-scheduled-event-single-event-id")
            .locationId("create-scheduled-event-scheduled-event-single-location-id")
            .organizerIds(List.of(
                    "create-scheduled-event-scheduled-event-single-organizer-1-id",
                    "create-scheduled-event-scheduled-event-single-organizer-2-id"
            ))
            .name("Create Scheduled Event - Scheduled Event Single - Scheduled Event Name")
            .description("Create Scheduled Event - Scheduled Event Single - Scheduled Event Description")
            .category("Create Scheduled Event - Scheduled Event Single - Scheduled Event Category")
            .startTime(startTime)
            .endTime(endTime)
            .scheduledEventType(ScheduledEventType.SINGLE)
            .scheduledEventDate(scheduledEventDate)
            .cost(1699)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD = ScheduledEvent.builder()
            .eventScheduleId("create-scheduled-event-scheduled-event-reoccurring-standard-event-schedule-id")
            .clientId("create-scheduled-event-reoccurring-standard-client-id")
            .eventId("create-scheduled-event-reoccurring-standard-event-id")
            .locationId("create-scheduled-event-scheduled-event-reoccurring-standard-location-id")
            .organizerIds(List.of(
                    "create-scheduled-event-scheduled-event-reoccurring-standard-organizer-1-id",
                    "create-scheduled-event-scheduled-event-reoccurring-standard-organizer-2-id"
            ))
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKDAYS)
            .name("Create Scheduled Event - Scheduled Event Reoccurring Standard - Scheduled Event Name")
            .description("Create Scheduled Event - Scheduled Event Reoccurring Standard - Scheduled Event Description")
            .category("Create Scheduled Event - Scheduled Event Reoccurring Standard - Scheduled Event Category")
            .startTime(startTime)
            .endTime(endTime)
            .cost(3999)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY = ScheduledEvent.builder()
            .eventScheduleId("create-scheduled-event-scheduled-event-reoccurring-weekly-event-schedule-id")
            .clientId("create-scheduled-event-reoccurring-weekly-client-id")
            .eventId("create-scheduled-event-reoccurring-weekly-event-id")
            .locationId("create-scheduled-event-scheduled-event-reoccurring-weekly-location-id")
            .organizerIds(List.of(
                    "create-scheduled-event-scheduled-event-reoccurring-weekly-organizer-1-id",
                    "create-scheduled-event-scheduled-event-reoccurring-weekly-organizer-2-id"
            ))
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKLY)
            .scheduledEventDay(ScheduledEventDay.TUE)
            .name("Create Scheduled Event - Scheduled Event Reoccurring Weekly - Scheduled Event Name")
            .description("Create Scheduled Event - Scheduled Event Reoccurring Weekly - Scheduled Event Description")
            .category("Create Scheduled Event - Scheduled Event Reoccurring Weekly - Scheduled Event Category")
            .startTime(startTime)
            .endTime(endTime)
            .cost(3999)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateScheduledEventOrganizerOneId = "update-scheduled-event-organizer-1-id"
    public static String updateScheduledEventOrganizerTwoId = "update-scheduled-event-organizer-2-id"
    public static String updateScheduledEventUpdatedOrganizerThreeId = "update-scheduled-event-organizer-3-id"
    public static String updateScheduledEventUpdatedLocationId = "update-scheduled-event-updated-location-id"
    public static String updateScheduledEventUpdatedName = "Updated Scheduled Event Name"
    public static String updateScheduledEventUpdatedDescription = "Updated Scheduled Event Description"
    public static String updateScheduledEventUpdatedCategory = "Updated Scheduled Event Category"
    public static LocalTime updateScheduledEventStartTime = LocalTime.of(15, 47, 44)
    public static LocalTime updateScheduledEventEndTime = LocalTime.of(22, 32, 27)
    public static LocalDate updatedScheduledEventDate = LocalDate.of(2023, 11, 27)


    public static ScheduledEvent UPDATE_SCHEDULED_EVENT_SINGLE = ScheduledEvent.builder()
            .eventScheduleId("update-scheduled-event-single-event-schedule-id")
            .scheduledEventId("-update-scheduled-event-single-scheduled-event-id")
            .locationId("update-scheduled-event-single-location-id")
            .organizerIds(List.of(updateScheduledEventOrganizerOneId, updateScheduledEventOrganizerTwoId))
            .scheduledEventType(ScheduledEventType.SINGLE)
            .scheduledEventDate(scheduledEventDate)
            .name("Update Scheduled Event Single - Scheduled Event Name")
            .description("Update Scheduled Event Single - Scheduled Event Description")
            .category("Update Scheduled Event Single - Scheduled Event Category")
            .startTime(startTime)
            .endTime(endTime)
            .cost(3999)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD = ScheduledEvent.builder()
            .eventScheduleId("update-scheduled-event-reoccurring-standard-event-schedule-id")
            .scheduledEventId("-update-scheduled-event-reoccurring-standard-scheduled-event-id")
            .locationId("update-scheduled-event-reoccurring-standard-location-id")
            .organizerIds(List.of(updateScheduledEventOrganizerOneId, updateScheduledEventOrganizerTwoId))
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKDAYS)
            .name("Update Scheduled Event Reoccurring Standard - Scheduled Event Name")
            .description("Update Scheduled Event Reoccurring Standard - Scheduled Event Description")
            .category("Update Scheduled Event Reoccurring Standard - Scheduled Event Category")
            .startTime(startTime)
            .endTime(endTime)
            .cost(3999)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY = ScheduledEvent.builder()
            .eventScheduleId("update-scheduled-event-reoccurring-weekly-event-schedule-id")
            .scheduledEventId("-update-scheduled-event-reoccurring-weekly-scheduled-event-id")
            .locationId("update-scheduled-event-reoccurring-weekly-location-id")
            .organizerIds(List.of(updateScheduledEventOrganizerOneId, updateScheduledEventOrganizerTwoId))
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKLY)
            .scheduledEventDay(ScheduledEventDay.TUE)
            .name("Update Scheduled Event Reoccurring Weekly - Scheduled Event Name")
            .description("Update Scheduled Event Reoccurring Weekly - Scheduled Event Description")
            .category("Update Scheduled Event Reoccurring Weekly - Scheduled Event Category")
            .startTime(startTime)
            .endTime(endTime)
            .cost(3999)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
