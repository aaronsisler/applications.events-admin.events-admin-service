package com.ebsolutions.eventsadminservice.util

import com.ebsolutions.eventsadminservice.model.Client
import com.fasterxml.jackson.databind.ObjectMapper

class CopyObjectUtil {

    static Client old_client(Client client) {
        return Client.builder()
                .clientId(client.getClientId())
                .name(client.getName())
                .createdOn(client.getCreatedOn())
                .lastUpdatedOn(client.getLastUpdatedOn())
                .build()
    }

    static Client client(Client client) {
        ObjectMapper objectMapper = new ObjectMapper();
        println(client)
        println(client.toJson())
        String clientString = objectMapper.writeValueAsString(client)
        println clientString

        Client newClient = objectMapper.readValue(clientString, Client.class)
        return newClient
    }
}
