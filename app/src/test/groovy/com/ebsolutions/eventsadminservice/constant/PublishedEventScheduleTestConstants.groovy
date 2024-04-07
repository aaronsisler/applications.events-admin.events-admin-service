package com.ebsolutions.eventsadminservice.constant

import com.ebsolutions.eventsadminservice.model.*

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

/**
 * Holds all of the test values for Published Event Schedule
 * Leading Dashes on event ids are to help with the DB Setup
 */
class PublishedEventScheduleTestConstants {
    // The START_TIME and END_TIME should be logically correct
    public static LocalTime START_TIME = LocalTime.of(13, 45, 42)
    public static LocalTime END_TIME = LocalTime.of(20, 30, 25)

    public static int EVENT_SCHEDULE_YEAR = 2024
    public static int EVENT_SCHEDULE_MONTH = 2
    // Keep this as February 29th, 2024 to make sure leap year testing is conducted
    public static LocalDate SCHEDULED_EVENT_DATE_SINGLE = LocalDate.of(EVENT_SCHEDULE_YEAR, EVENT_SCHEDULE_MONTH, 29)

    public static String CLIENT_ID = "-create-published-event-schedule-client-client-id"
    public static String LOCATION_ID = "-create-published-event-schedule-location-location-id"
    public static String ORGANIZER_ID = "-create-published-event-schedule-organizer-organizer-id"
    public static String EVENT_ID = "-create-published-event-schedule-event-event-id"
    public static String EVENT_SCHEDULE_ID = "-create-published-event-schedule-event-schedule-event-schedule-id"

    public static Client CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT = Client.builder()
            .clientId(CLIENT_ID)
            .name("Create Published Event Schedule - Client - Client Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Location CREATE_PUBLISHED_EVENT_SCHEDULE_LOCATION = Location.builder()
            .clientId(CLIENT_ID)
            .locationId(LOCATION_ID)
            .name("Create Published Event Schedule - Location - Location Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Organizer CREATE_PUBLISHED_EVENT_SCHEDULE_ORGANIZER = Organizer.builder()
            .clientId(CLIENT_ID)
            .organizerId(ORGANIZER_ID)
            .name("Create Published Event Schedule - Organizer - Organizer Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Event CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT = Event.builder()
            .clientId(CLIENT_ID)
            .eventId(EVENT_ID)
            .organizerId(ORGANIZER_ID)
            .locationId(LOCATION_ID)
            .name("Create Published Event Schedule - Event - Event Name")
            .description("Create Published Event Schedule - Event - Event Description")
            .category("Create Published Event Schedule - Event - Event Category")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static EventSchedule CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE = EventSchedule.builder()
            .clientId(CLIENT_ID)
            .eventScheduleId(EVENT_SCHEDULE_ID)
            .name("Create Published Event Schedule - Event Schedule - Event Schedule Name")
            .description("Create Published Event Schedule - Event Schedule - Event Schedule Description")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE = ScheduledEvent.builder()
            .eventScheduleId(EVENT_SCHEDULE_ID)
            .scheduledEventId("-create-published-event-schedule-scheduled-event-single-scheduled-event-id")
            .clientId(CLIENT_ID)
            .eventId(EVENT_ID)
            .locationId(LOCATION_ID)
            .organizerId(ORGANIZER_ID)
            .name("Create Published Event Schedule - Scheduled Event - Single - Scheduled Event Name")
            .description("Create Published Event Schedule - Scheduled Event - Single - Scheduled Event Description")
            .category("Create Published Event Schedule - Scheduled Event - Single - Scheduled Event Category")
            .scheduledEventType(ScheduledEventType.SINGLE)
            .scheduledEventDate(SCHEDULED_EVENT_DATE_SINGLE)
            .startTime(START_TIME)
            .endTime(END_TIME)
            .cost(550)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_STANDARD = ScheduledEvent.builder()
            .eventScheduleId(EVENT_SCHEDULE_ID)
            .scheduledEventId("-create-published-event-schedule-scheduled-event-reoccurring-standard-scheduled-event-id")
            .clientId(CLIENT_ID)
            .eventId(EVENT_ID)
            .locationId(LOCATION_ID)
            .organizerId(ORGANIZER_ID)
            .name("Create Published Event Schedule - Scheduled Event - Reoccurring Standard - Scheduled Event Name")
            .description("Create Published Event Schedule - Scheduled Event - Reoccurring Standard - Scheduled Event Description")
            .category("Create Published Event Schedule - Scheduled Event - Reoccurring Standard - Scheduled Event Category")
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.DAILY)
            .startTime(START_TIME)
            .endTime(END_TIME)
            .cost(350)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static ScheduledEvent CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_WEEKLY = ScheduledEvent.builder()
            .eventScheduleId(EVENT_SCHEDULE_ID)
            .scheduledEventId("-create-published-event-schedule-scheduled-event-reoccurring-weekly-scheduled-event-id")
            .clientId(CLIENT_ID)
            .eventId(EVENT_ID)
            .locationId(LOCATION_ID)
            .organizerId(ORGANIZER_ID)
            .name("Create Published Event Schedule - Scheduled Event - Reoccurring Weekly - Scheduled Event Name")
            .description("Create Published Event Schedule - Scheduled Event - Reoccurring Weekly - Scheduled Event Description")
            .category("Create Published Event Schedule - Scheduled Event - Reoccurring Weekly - Scheduled Event Category")
            .scheduledEventType(ScheduledEventType.REOCCURRING)
            .scheduledEventInterval(ScheduledEventInterval.WEEKLY)
            .scheduledEventDay(DayOfWeek.WEDNESDAY)
            .startTime(START_TIME)
            .endTime(END_TIME)
            .cost(450)
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static PublishedEventSchedule CREATE_PUBLISHED_EVENT_SCHEDULE = PublishedEventSchedule.builder()
            .clientId(CLIENT_ID)
            .eventScheduleId(EVENT_SCHEDULE_ID)
            .eventScheduleYear(EVENT_SCHEDULE_YEAR)
            .eventScheduleMonth(EVENT_SCHEDULE_MONTH)
            .name(String.format("Published Event Schedule for {} {}", EVENT_SCHEDULE_YEAR, EVENT_SCHEDULE_MONTH))
            .locationBlackouts(null)
            .eventBlackouts(null)
            .build()
}
