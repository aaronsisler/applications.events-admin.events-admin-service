package com.ebsolutions.eventsadminservice.constant

import java.time.LocalDateTime

class TestConstants {
    // Generic Test Constants
    public static String eventsAdminServiceUrl = "http://localhost:8080"

    // Does Not Exist Client
    public static String nonExistentClientId = "non-existent-client-id"
    public static String nonExistentLocationId = "non-existent-location-id"
    public static String nonExistentOrganizerId = "non-existent-organizer-id"
    public static String nonExistentEventId = "non-existent-event-id"
    public static String nonExistentEventScheduleId = "non-existent-event-schedule-id"
    public static String nonExistentScheduledEventId = "non-existent-scheduled-event-id"

    // No children Ids
    public static String noChildrenClientId = "get-all-no-children-client-mock-client-id"
    public static String noChildrenEventScheduleId = "get-all-no-children-event-schedule-id"

    // This must be before the updateCreatedOn variable
    public static LocalDateTime createdOn =
            LocalDateTime.of(2023, 7, 4, 1, 2, 34)
    // This must be after the createdOn variable
    public static LocalDateTime updateCreatedOn =
            LocalDateTime.of(2023, 12, 25, 7, 4, 00)
    public static LocalDateTime lastUpdatedOn =
            LocalDateTime.of(2023, 11, 5, 12, 34, 56)

    // This is Saturday, September 30, 2028 12:00:00 AM in epoch seconds
    public static long expiryTime = (long) 1853884800
}
