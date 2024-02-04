package com.ebsolutions.eventsadminservice.validator;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.model.*;

import java.util.List;

public class RequestValidator {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isClientValid(AllowableRequestMethod allowableRequestMethod, Client client) {
        if (client == null) {
            return false;
        }

        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isClientUpdateValid(client);
            case POST, GET, DELETE -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isLocationValid(AllowableRequestMethod allowableRequestMethod, String clientId, Location location) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (location == null) {
            return false;
        }

        if (StringValidator.isBlank(location.getClientId())) {
            return false;
        }

        if (!clientId.equals(location.getClientId())) {
            return false;
        }

        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isLocationUpdateValid(location);
            case POST, GET, DELETE -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isOrganizerValid(AllowableRequestMethod allowableRequestMethod, String clientId, Organizer organizer) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (organizer == null) {
            return false;
        }

        if (StringValidator.isBlank(organizer.getClientId())) {
            return false;
        }

        if (!clientId.equals(organizer.getClientId())) {
            return false;
        }

        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isOrganizerUpdateValid(organizer);
            case GET, DELETE, POST -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEventValid(AllowableRequestMethod allowableRequestMethod, String clientId, Event event) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (event == null) {
            return false;
        }

        if (StringValidator.isBlank(event.getClientId())) {
            return false;
        }

        if (!clientId.equals(event.getClientId())) {
            return false;
        }

        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isEventUpdateValid(event);
            case GET, DELETE, POST -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEventScheduleValid(AllowableRequestMethod allowableRequestMethod, String clientId, EventSchedule eventSchedule) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (eventSchedule == null) {
            return false;
        }

        if (StringValidator.isBlank(eventSchedule.getClientId())) {
            return false;
        }

        if (!clientId.equals(eventSchedule.getClientId())) {
            return false;
        }

        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isEventScheduleUpdateValid(eventSchedule);
            case GET, DELETE, POST -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isScheduledEventValid(AllowableRequestMethod allowableRequestMethod, String eventScheduleId, ScheduledEvent scheduledEvent) {
        if (!RequestValidator.isScheduledEventValid(eventScheduleId, scheduledEvent)) {
            return false;
        }

        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isScheduledEventUpdateValid(scheduledEvent);
            case GET, DELETE, POST -> true;
        };
    }

    private static boolean isClientUpdateValid(Client client) {
        if (StringValidator.isBlank(client.getClientId())) {
            return false;
        }

        if (client.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(client.getCreatedOn());
    }

    private static boolean isLocationUpdateValid(Location location) {
        if (location.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(location.getCreatedOn());
    }

    private static boolean isOrganizerUpdateValid(Organizer organizer) {
        if (organizer.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(organizer.getCreatedOn());
    }

    private static boolean isEventUpdateValid(Event event) {
        if (event.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(event.getCreatedOn());
    }

    private static boolean isEventScheduleUpdateValid(EventSchedule eventSchedule) {
        if (eventSchedule.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(eventSchedule.getCreatedOn());
    }

    private static boolean isScheduledEventValid(String eventScheduleId, ScheduledEvent scheduledEvent) {
        if (StringValidator.isBlank(eventScheduleId)) {
            return false;
        }

        if (scheduledEvent == null) {
            return false;
        }

        if (StringValidator.isBlank(scheduledEvent.getEventScheduleId())) {
            return false;
        }

        if (!eventScheduleId.equals(scheduledEvent.getEventScheduleId())) {
            return false;
        }

        if (scheduledEvent.getStartTime().isAfter(scheduledEvent.getEndTime())) {
            return false;
        }

        if (scheduledEvent.getScheduledEventType() == ScheduledEventType.SINGLE) {
            if (scheduledEvent.getScheduledEventDate() == null) {
                return false;
            }
        }

        if (scheduledEvent.getScheduledEventType() == ScheduledEventType.REOCCURRING) {
            if (scheduledEvent.getScheduledEventInterval() == null) {
                return false;
            }

            if (scheduledEvent.getScheduledEventDay() != null
                    && List.of(
                            ScheduledEventInterval.DAILY,
                            ScheduledEventInterval.WEEKDAYS,
                            ScheduledEventInterval.WEEKENDS)
                    .contains(scheduledEvent.getScheduledEventInterval())
            ) {
                return false;
            }

            return !(scheduledEvent.getScheduledEventDay() == null
                    && ScheduledEventInterval.WEEKLY.equals(scheduledEvent.getScheduledEventInterval()));
        }

        return true;
    }

    private static boolean isScheduledEventUpdateValid(ScheduledEvent scheduledEvent) {
        if (scheduledEvent.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(scheduledEvent.getCreatedOn());
    }
}
