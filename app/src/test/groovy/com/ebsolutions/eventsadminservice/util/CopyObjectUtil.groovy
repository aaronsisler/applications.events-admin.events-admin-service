package com.ebsolutions.eventsadminservice.util

import com.ebsolutions.eventsadminservice.model.*

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
                .locationId(event.getLocationId())
                .organizerIds(event.getOrganizerIds())
                .name(event.getName())
                .description(event.getDescription())
                .category(event.getCategory())
                .createdOn(event.getCreatedOn())
                .lastUpdatedOn(event.getLastUpdatedOn())
                .build()
    }

    static EventSchedule eventSchedule(EventSchedule eventSchedule) {
        return EventSchedule.builder()
                .clientId(eventSchedule.getClientId())
                .eventScheduleId(eventSchedule.getEventScheduleId())
                .name(eventSchedule.getName())
                .description(eventSchedule.getDescription())
                .createdOn(eventSchedule.getCreatedOn())
                .lastUpdatedOn(eventSchedule.getLastUpdatedOn())
                .build()
    }

    static ScheduledEvent scheduledEvent(ScheduledEvent scheduledEvent) {
        return ScheduledEvent.builder()
                .eventScheduleId(scheduledEvent.getEventScheduleId())
                .scheduledEventId(scheduledEvent.getScheduledEventId())
                .locationId(scheduledEvent.getLocationId())
                .organizerIds(scheduledEvent.getOrganizerIds())
                .name(scheduledEvent.getName())
                .createdOn(scheduledEvent.getCreatedOn())
                .lastUpdatedOn(scheduledEvent.getLastUpdatedOn())
                .build()
    }
}
