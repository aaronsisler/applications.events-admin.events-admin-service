package com.ebsolutions.eventsadminservice.util

import com.ebsolutions.eventsadminservice.model.Client
import com.ebsolutions.eventsadminservice.model.Event
import com.ebsolutions.eventsadminservice.model.Location
import com.ebsolutions.eventsadminservice.model.Organizer

class CopyObjectUtil {

    static Client client(Client client) {
        return Client.builder()
                .clientId(client.getClientId())
                .name(client.getName())
                .createdOn(client.getCreatedOn())
                .lastUpdatedOn(client.getLastUpdatedOn())
                .build()
    }

    static Location location(Location location) {
        return Location.builder()
                .clientId(location.getClientId())
                .locationId(location.getLocationId())
                .name(location.getName())
                .createdOn(location.getCreatedOn())
                .lastUpdatedOn(location.getLastUpdatedOn())
                .build()
    }

    static Organizer organizer(Organizer organizer) {
        return Organizer.builder()
                .clientId(organizer.getClientId())
                .organizerId(organizer.getOrganizerId())
                .name(organizer.getName())
                .createdOn(organizer.getCreatedOn())
                .lastUpdatedOn(organizer.getLastUpdatedOn())
                .build()
    }

    static Event event(Event event) {
        return Event.builder()
                .clientId(event.getClientId())
                .eventId(event.getEventId())
                .name(event.getName())
                .description(event.getDescription())
                .locationId(event.getLocationId())
                .organizerIds(event.getOrganizerIds())
                .createdOn(event.getCreatedOn())
                .lastUpdatedOn(event.getLastUpdatedOn())
                .build()
    }
}
