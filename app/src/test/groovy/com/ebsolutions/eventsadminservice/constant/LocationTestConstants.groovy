package com.ebsolutions.eventsadminservice.constant


import com.ebsolutions.eventsadminservice.model.Location

/**
 * Holds all of the test values for Location
 * Leading Dashes on location ids are to help with the DB Setup
 */
class LocationTestConstants {
    public static Location GET_LOCATION = Location.builder()
            .clientId("get-location-client-id")
            .locationId("-get-location-location-id")
            .name("Get Location - Location Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllLocationsClientId = "get-all-locations-client-id"

    public static Location GET_ALL_LOCATIONS_LOCATION_ONE = Location.builder()
            .clientId(getAllLocationsClientId)
            .locationId("-get-all-locations-location-1-location-id")
            .name("Get All Locations - Location 1 Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Location GET_ALL_LOCATIONS_LOCATION_TWO = Location.builder()
            .clientId(getAllLocationsClientId)
            .locationId("-get-all-locations-location-2-location-id")
            .name("Get All Locations - Location 2 Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Location DELETE_LOCATION = Location.builder()
            .clientId("delete-location-client-id")
            .locationId("-delete-location-location-id")
            .name("Delete Location - Location Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Location CREATE_LOCATION = Location.builder()
            .clientId("create-location-client-id")
            .name("Create Location - Location Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateLocationUpdatedName = "Updated Location Name"

    public static Location UPDATE_LOCATION = Location.builder()
            .clientId("update-location-client-id")
            .locationId("-update-location-location-id")
            .name("Update Location - Location Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
