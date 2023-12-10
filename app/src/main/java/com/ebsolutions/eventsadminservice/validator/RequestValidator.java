package com.ebsolutions.eventsadminservice.validator;

import com.ebsolutions.eventsadminservice.config.AllowableRequestMethod;
import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.model.Location;

public class RequestValidator {
    public static boolean isClientValid(AllowableRequestMethod allowableRequestMethod, Client client) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isClientUpdateValid(client);
            case GET, POST, DELETE -> true;
        };
    }

    public static boolean isLocationValid(AllowableRequestMethod allowableRequestMethod, String clientId, Location location) {
        return switch (allowableRequestMethod) {
            case PUT -> RequestValidator.isLocationUpdateValid(clientId, location);
            case POST -> RequestValidator.isLocationCreateValid(clientId, location);
            case GET, DELETE -> true;
        };
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

//    public static boolean isEventValid(RequestMethod requestMethod, Event event) {
//        return switch (requestMethod) {
//            case POST -> RequestValidator.isEventValid(event);
//            case GET, PUT, DELETE -> true;
//        };
//    }
//
//    public static boolean isWorkshopValid(RequestMethod requestMethod, Workshop workshop) {
//        return switch (requestMethod) {
//            case POST -> RequestValidator.isPostBaseEventValid(workshop);
//            case GET, PUT, DELETE -> true;
//        };
//    }
//
//    private static boolean isPostBaseEventValid(BaseEvent baseEvent) {
//        if (baseEvent.getDuration() <= 0) {
//            return false;
//        }
//
//        return baseEvent.getStartTime() != null;
//    }
//
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
