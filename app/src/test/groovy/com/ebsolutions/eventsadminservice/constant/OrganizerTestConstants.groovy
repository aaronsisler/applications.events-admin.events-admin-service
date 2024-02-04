package com.ebsolutions.eventsadminservice.constant


import com.ebsolutions.eventsadminservice.model.Organizer

/**
 * Holds all of the test values for Organizer
 * Leading Dashes on organizer ids are to help with the DB Setup
 */
class OrganizerTestConstants {
    public static Organizer GET_ORGANIZER = Organizer.builder()
            .clientId("get-organizer-client-id")
            .organizerId("-get-organizer-organizer-id")
            .name("Get Organizer - Organizer Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String getAllOrganizersClientId = "get-all-organizers-client-id"

    public static Organizer GET_ALL_ORGANIZERS_ORGANIZER_ONE = Organizer.builder()
            .clientId(getAllOrganizersClientId)
            .organizerId("-get-all-organizers-organizer-1-organizer-id")
            .name("Get All Organizers - Organizer 1 Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Organizer GET_ALL_ORGANIZERS_ORGANIZER_TWO = Organizer.builder()
            .clientId(getAllOrganizersClientId)
            .organizerId("-get-all-organizers-organizer-2-organizer-id")
            .name("Get All Organizers - Organizer 2 Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Organizer DELETE_ORGANIZER = Organizer.builder()
            .clientId("delete-organizer-client-id")
            .organizerId("-delete-organizer-organizer-id")
            .name("Delete Organizer - Organizer Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Organizer CREATE_ORGANIZER = Organizer.builder()
            .clientId("create-organizer-client-id")
            .name("Create Organizer - Organizer Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateOrganizerUpdatedName = "Updated Organizer Name"

    public static Organizer UPDATE_ORGANIZER = Organizer.builder()
            .clientId("update-organizer-client-id")
            .organizerId("-update-organizer-organizer-id")
            .name("Update Organizer - Organizer Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
