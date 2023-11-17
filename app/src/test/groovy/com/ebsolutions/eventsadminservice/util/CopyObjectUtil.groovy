package com.ebsolutions.eventsadminservice.util

import com.ebsolutions.eventsadminservice.model.Client

class CopyObjectUtil {

    static Client client(Client client) {
        return Client.builder()
                .clientId(client.getClientId())
                .name(client.getName())
                .createdOn(client.getCreatedOn())
                .lastUpdatedOn(client.getLastUpdatedOn())
                .build()
    }
}
