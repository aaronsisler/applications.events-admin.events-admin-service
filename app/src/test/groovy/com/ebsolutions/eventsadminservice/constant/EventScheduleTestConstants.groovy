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
            .scheduledEventIds(List.of(
                    "get-event-schedule-scheduled-event-1-id",
                    "get-event-schedule-scheduled-event-2-id")
            )
            .name("Get Event Schedule - Event Schedule Name")
            .description("Get Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllEventSchedulesClientId = "get-all-event-schedules-client-id"

    public static EventSchedule GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_ONE = EventSchedule.builder()
            .clientId(getAllEventSchedulesClientId)
            .eventScheduleId("-get-all-event-schedules-event-schedule-1-event-schedule-id")
            .scheduledEventIds(List.of(
                    "get-all-event-schedules-event-schedule-1-scheduled-event-1-id",
                    "get-all-event-schedules-event-schedule-1-scheduled-event-2-id"
            ))
            .name("Get All Event Schedules - Event Schedule 1 Name")
            .description("Get All Event Schedules - Event Schedule 1 Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static EventSchedule GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_TWO = EventSchedule.builder()
            .clientId(getAllEventSchedulesClientId)
            .eventScheduleId("-get-all-event-schedules-event-schedule-2-event-schedule-id")
            .scheduledEventIds(List.of(
                    "get-all-event-schedules-event-schedule-2-scheduled-event-1-id",
                    "get-all-event-schedules-event-schedule-2-scheduled-event-2-id"
            ))
            .name("Get All Event Schedules - Event Schedule 2 Name")
            .description("Get All Event Schedules - Event Schedule 2 Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static EventSchedule DELETE_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId("delete-event-schedule-client-id")
            .eventScheduleId("-delete-event-schedule-event-schedule-id")
            .scheduledEventIds(List.of(
                    "delete-event-schedule-scheduled-event-1-id",
                    "delete-event-schedule-scheduled-event-2-id"
            ))
            .name("Delete Event Schedule - Event Schedule Name")
            .description("Delete Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static EventSchedule CREATE_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId("create-event-schedule-client-id")
            .scheduledEventIds(List.of(
                    "create-event-schedule-scheduled-event-1-id",
                    "create-event-schedule-scheduled-event-2-id"
            ))
            .name("Create Event Schedule - Event Schedule Name")
            .description("Create Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateEventScheduleScheduledEventOneId = "update-event-schedule-scheduled-event-1-id"
    public static String updateEventScheduleScheduledEventTwoId = "update-event-schedule-scheduled-event-2-id"
    public static String updateEventScheduleScheduledEventThreeId = "update-event-schedule-scheduled-event-3-id"
    public static String updateEventScheduleUpdatedName = "Updated Event Schedule Name"
    public static String updateEventScheduleUpdatedDescription = "Updated Event Schedule Description"

    public static EventSchedule UPDATE_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId("update-event-schedule-client-id")
            .eventScheduleId("-update-event-schedule-event-schedule-id")
            .scheduledEventIds(List.of(
                    updateEventScheduleScheduledEventOneId,
                    updateEventScheduleScheduledEventTwoId
            ))
            .name("Update Event Schedule - Event Schedule Name")
            .description("Update Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
