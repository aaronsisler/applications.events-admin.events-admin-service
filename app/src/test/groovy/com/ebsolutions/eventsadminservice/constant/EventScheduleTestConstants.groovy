package com.ebsolutions.eventsadminservice.constant


import com.ebsolutions.eventsadminservice.model.EventSchedule

/**
 * Holds all of the test values for Event Schedule
 * Leading Dashes on event ids are to help with the DB Setup
 */
class EventScheduleTestConstants {
    public static EventSchedule GET_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId("get-event-schedule-client-id")
            .eventScheduleId("-get-event-schedule-event-schedule-id")
            .name("Get Event Schedule - Event Schedule Name")
            .description("Get Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllEventSchedulesClientId = "get-all-event-schedules-client-id"

    public static EventSchedule GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_ONE = EventSchedule.builder()
            .clientId(getAllEventSchedulesClientId)
            .eventScheduleId("-get-all-event-schedules-event-schedule-1-event-schedule-id")
            .name("Get All Event Schedules - Event Schedule 1 Name")
            .description("Get All Event Schedules - Event Schedule 1 Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static EventSchedule GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_TWO = EventSchedule.builder()
            .clientId(getAllEventSchedulesClientId)
            .eventScheduleId("-get-all-event-schedules-event-schedule-2-event-schedule-id")
            .name("Get All Event Schedules - Event Schedule 2 Name")
            .description("Get All Event Schedules - Event Schedule 2 Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static EventSchedule DELETE_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId("delete-event-schedule-client-id")
            .eventScheduleId("-delete-event-schedule-event-schedule-id")
            .name("Delete Event Schedule - Event Schedule Name")
            .description("Delete Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static EventSchedule CREATE_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId("create-event-schedule-client-id")
            .name("Create Event Schedule - Event Schedule Name")
            .description("Create Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateEventScheduleUpdatedName = "Updated Event Schedule Name"
    public static String updateEventScheduleUpdatedDescription = "Updated Event Schedule Description"

    public static EventSchedule UPDATE_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId("update-event-schedule-client-id")
            .eventScheduleId("-update-event-schedule-event-schedule-id")
            .name("Update Event Schedule - Event Schedule Name")
            .description("Update Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
