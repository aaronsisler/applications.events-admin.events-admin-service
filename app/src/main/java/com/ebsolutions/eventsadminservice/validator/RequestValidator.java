package com.ebsolutions.eventsadminservice.validator;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.model.*;

public class RequestValidator {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isClientValid(AllowableRequestMethod allowableRequestMethod, Client client) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isClientUpdateValid(client);
            case POST -> RequestValidator.isClientCreateValid(client);
            case GET, DELETE -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isLocationValid(AllowableRequestMethod allowableRequestMethod, String clientId, Location location) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isLocationUpdateValid(clientId, location);
            case POST -> RequestValidator.isLocationCreateValid(clientId, location);
            case GET, DELETE -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isOrganizerValid(AllowableRequestMethod allowableRequestMethod, String clientId, Organizer organizer) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isOrganizerUpdateValid(clientId, organizer);
            case POST -> RequestValidator.isOrganizerCreateValid(clientId, organizer);
            case GET, DELETE -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEventValid(AllowableRequestMethod allowableRequestMethod, String clientId, Event event) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isEventUpdateValid(clientId, event);
            case POST -> RequestValidator.isEventCreateValid(clientId, event);
            case GET, DELETE -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEventScheduleValid(AllowableRequestMethod allowableRequestMethod, String clientId, EventSchedule eventSchedule) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isEventScheduleUpdateValid(clientId, eventSchedule);
            case POST -> RequestValidator.isEventScheduleCreateValid(clientId, eventSchedule);
            case GET, DELETE -> true;
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isScheduledEventValid(AllowableRequestMethod allowableRequestMethod, String clientId, ScheduledEvent scheduledEvent) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isScheduledEventUpdateValid(clientId, scheduledEvent);
            case POST -> RequestValidator.isScheduledEventCreateValid(clientId, scheduledEvent);
            case GET, DELETE -> true;
        };
    }

    private static boolean isClientCreateValid(Client client) {
        return client != null;
    }

    private static boolean isClientUpdateValid(Client client) {
        if (client == null) {
            return false;
        }

        if (StringValidator.isBlank(client.getClientId())) {
            return false;
        }

        if (client.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(client.getCreatedOn());
    }

    private static boolean isLocationCreateValid(String clientId, Location location) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (location == null) {
            return false;
        }

        if (StringValidator.isBlank(location.getClientId())) {
            return false;
        }

        return clientId.equals(location.getClientId());
    }

    private static boolean isLocationUpdateValid(String clientId, Location location) {
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

        if (location.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(location.getCreatedOn());
    }

    private static boolean isOrganizerCreateValid(String clientId, Organizer organizer) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (organizer == null) {
            return false;
        }

        if (StringValidator.isBlank(organizer.getClientId())) {
            return false;
        }

        return clientId.equals(organizer.getClientId());
    }

    private static boolean isOrganizerUpdateValid(String clientId, Organizer organizer) {
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

        if (organizer.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(organizer.getCreatedOn());
    }

    private static boolean isEventCreateValid(String clientId, Event event) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (event == null) {
            return false;
        }

        if (StringValidator.isBlank(event.getClientId())) {
            return false;
        }

        return clientId.equals(event.getClientId());
    }

    private static boolean isEventUpdateValid(String clientId, Event event) {
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

        if (event.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(event.getCreatedOn());
    }

    private static boolean isEventScheduleCreateValid(String clientId, EventSchedule eventSchedule) {
        if (StringValidator.isBlank(clientId)) {
            return false;
        }

        if (eventSchedule == null) {
            return false;
        }

        if (StringValidator.isBlank(eventSchedule.getClientId())) {
            return false;
        }

        return clientId.equals(eventSchedule.getClientId());
    }

    private static boolean isEventScheduleUpdateValid(String clientId, EventSchedule eventSchedule) {
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

        if (eventSchedule.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(eventSchedule.getCreatedOn());
    }

    // TODO
    private static boolean isScheduledEventCreateValid(String eventScheduleId, ScheduledEvent scheduledEvent) {
        if (StringValidator.isBlank(eventScheduleId)) {
            return false;
        }

        if (scheduledEvent == null) {
            return false;
        }

        if (StringValidator.isBlank(scheduledEvent.getEventScheduleId())) {
            return false;
        }

        return eventScheduleId.equals(scheduledEvent.getEventScheduleId());
    }

    // TODO
    private static boolean isScheduledEventUpdateValid(String eventScheduleId, ScheduledEvent scheduledEvent) {
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

        if (scheduledEvent.getCreatedOn() == null) {
            return false;
        }

        return DateValidator.isBeforeNow(scheduledEvent.getCreatedOn());
    }

//    public static boolean isCsvRequestValid(CsvRequest csvRequest) {
//        boolean isYearValid = csvRequest.getYear() >= LocalDate.now().getYear();
//        boolean isMonthValid = (1 <= csvRequest.getMonth() && csvRequest.getMonth() <= 12);
//
//        return isYearValid && isMonthValid;
//    }
//
//    private static boolean isEventValid(Event event) {
//        if (!RequestValidator.isPostBaseEventValid(event)) {
//            return false;
//        }
//
//        return event.getDayOfWeek() != null;
//    }
}
