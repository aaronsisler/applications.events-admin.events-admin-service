package com.ebsolutions.eventsadminservice.constant


import java.time.LocalDateTime

class TestConstants {
    // Generic Test Constants
    public static String eventsAdminServiceUrl = "http://localhost:8080"

    // Does Not Exist Client
    public static String nonExistentClientId = "non-existent-client-id"
    public static String nonExistentLocationId = "non-existent-location-id"
    public static String nonExistentOrganizerId = "non-existent-organizer-id"
    public static LocalDateTime createdOn = LocalDateTime.of(2023, 11, 11, 21, 9, 23)
    public static LocalDateTime lastUpdatedOn = LocalDateTime.of(2023, 11, 11, 21, 9, 23)
    // This is Saturday, September 30, 2028 12:00:00 AM in epoch seconds
    public static long expiryTime = (long) 1853884800
}
