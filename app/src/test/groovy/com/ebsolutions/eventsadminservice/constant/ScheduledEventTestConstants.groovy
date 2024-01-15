package com.ebsolutions.eventsadminservice.constant


import com.ebsolutions.eventsadminservice.model.ScheduledEvent

/**
 * Holds all of the test values for Event Schedule
 * Leading Dashes on event ids are to help with the DB Setup
 */
class ScheduledEventTestConstants {
    public static ScheduledEvent GET_SCHEDULED_EVENT = ScheduledEvent.builder()
            .clientId("get-event-schedule-client-id")
            .scheduledEventId("-get-event-schedule-event-schedule-id")
            .name("Get Event Schedule - Event Schedule Name")
            .description("Get Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllEventSchedulesClientId = "get-all-event-schedules-client-id"

    public static ScheduledEvent GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE = ScheduledEvent.builder()
            .clientId(getAllEventSchedulesClientId)
            .scheduledEventId("-get-all-event-schedules-event-schedule-1-event-schedule-id")
            .name("Get All Event Schedules - Event Schedule 1 Name")
            .description("Get All Event Schedules - Event Schedule 1 Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO = ScheduledEvent.builder()
            .clientId(getAllEventSchedulesClientId)
            .scheduledEventId("-get-all-event-schedules-event-schedule-2-event-schedule-id")
            .name("Get All Event Schedules - Event Schedule 2 Name")
            .description("Get All Event Schedules - Event Schedule 2 Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent DELETE_SCHEDULED_EVENT = ScheduledEvent.builder()
            .clientId("delete-event-schedule-client-id")
            .scheduledEventId("-delete-event-schedule-event-schedule-id")
            .name("Delete Event Schedule - Event Schedule Name")
            .description("Delete Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_SCHEDULED_EVENT = ScheduledEvent.builder()
            .clientId("create-event-schedule-client-id")
            .name("Create Event Schedule - Event Schedule Name")
            .description("Create Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateEventScheduleUpdatedName = "Updated Event Schedule Name"
    public static String updateEventScheduleUpdatedDescription = "Updated Event Schedule Description"

    public static ScheduledEvent UPDATE_SCHEDULED_EVENT = ScheduledEvent.builder()
            .eve("update-event-schedule-client-id")
            .scheduledEventId("-update-event-schedule-event-schedule-id")
            .name("Update Event Schedule - Event Schedule Name")
            .description("Update Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
