package com.ebsolutions.eventsadminservice.constant


import com.ebsolutions.eventsadminservice.model.Client

/**
 * Holds all of the test values for Client
 * Leading Dashes on client ids are to help with the DB Setup
 */
class ClientTestConstants {
    public static Client GET_CLIENT = Client.builder()
            .clientId("-get-client-client-id")
            .name("Get Client - Client Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Client GET_ALL_CLIENTS_CLIENT_ONE = Client.builder()
            .clientId("-get-all-clients-client-1-client-id")
            .name("Get All Clients - Client 1 Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Client GET_ALL_CLIENTS_CLIENT_TWO = Client.builder()
            .clientId("-get-all-clients-client-2-client-id")
            .name("Get All Clients - Client 2 Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Client DELETE_CLIENT = Client.builder()
            .clientId("-delete-client-client-id")
            .name("Delete Client - Client Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static Client CREATE_CLIENT = Client.builder()
            .clientId("-create-client-client-id")
            .name("Create Client - Client Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()

    public static String updateClientUpdatedName = "Updated Client Name"

    public static Client UPDATE_CLIENT = Client.builder()
            .clientId("-update-client-client-id")
            .name("Update Client - Client Name")
            .createdOn(TestConstants.createdOn)
            .lastUpdatedOn(TestConstants.lastUpdatedOn)
            .build()
}
