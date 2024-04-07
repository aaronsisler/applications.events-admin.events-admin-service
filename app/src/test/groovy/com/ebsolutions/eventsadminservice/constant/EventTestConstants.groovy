package com.ebsolutions.eventsadminservice.constant

import com.ebsolutions.eventsadminservice.model.Event

/**
 * Holds all of the test values for Event
 * Leading Dashes on event ids are to help with the DB Setup
 */
class EventTestConstants {
    public static Event GET_EVENT = Event.builder()
            .clientId("get-event-client-id")
            .eventId("-get-event-event-id")
            .locationId("get-event-location-id")
            .organizerId("get-event-organizer-id")
            .name("Get Event - Event Name")
            .description("Get Event - Event Description")
            .category("Get Event - Event Category")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllEventsClientId = "get-all-events-client-id"
    public static String getAllEventsOrganizerId = "get-all-events-organizer-id"

    public static Event GET_ALL_EVENTS_EVENT_ONE = Event.builder()
            .clientId(getAllEventsClientId)
            .eventId("-get-all-events-event-1-event-id")
            .locationId("get-all-events-event-1-location-id")
            .organizerId(getAllEventsOrganizerId)
            .name("Get All Events - Event 1 Name")
            .description("Get All Events - Event 1 Description")
            .category("Get All Events - Event 1 Category")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Event GET_ALL_EVENTS_EVENT_TWO = Event.builder()
            .clientId(getAllEventsClientId)
            .eventId("-get-all-events-event-2-event-id")
            .locationId("get-all-events-event-2-location-id")
            .organizerId(getAllEventsOrganizerId)
            .name("Get All Events - Event 2 Name")
            .description("Get All Events - Event 2 Description")
            .category("Get All Events - Event 2 Category")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Event DELETE_EVENT = Event.builder()
            .clientId("delete-event-client-id")
            .eventId("-delete-event-event-id")
            .locationId("delete-event-location-id")
            .organizerId("delete-event-organizer-id")
            .name("Delete Event - Event Name")
            .description("Delete Event - Event Description")
            .category("Delete Event - Event Category")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Event CREATE_EVENT = Event.builder()
            .clientId("create-event-client-id")
            .locationId("create-event-location-id")
            .organizerId("create-event-organizer-id")
            .name("Create Event - Event Name")
            .description("Create Event - Event Description")
            .category("Create Event - Event Category")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateEventOrganizerId = "update-event-organizer-id"
    public static String updateEventUpdateOrganizerId = "update-event-updated-organizer-id"
    public static String updateEventUpdatedLocationId = "update-event-updated-location-id"
    public static String updateEventUpdatedCategory = "Updated Event Category"
    public static String updateEventUpdatedName = "Updated Event Name"
    public static String updateEventUpdatedDescription = "Updated Event Description"

    public static Event UPDATE_EVENT = Event.builder()
            .clientId("update-event-client-id")
            .eventId("-update-event-event-id")
            .locationId("update-event-location-id")
            .organizerId(updateEventOrganizerId)
            .name("Update Event - Event Name")
            .description("Update Event - Event Description")
            .category("Update Event - Event Category")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
