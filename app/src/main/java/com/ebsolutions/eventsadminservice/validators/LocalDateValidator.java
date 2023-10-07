package com.ebsolutions.eventsadminservice.validators;

import java.time.LocalDateTime;

public class LocalDateValidator {
    public static boolean isBeforeNow(LocalDateTime localDateTime) {
        return localDateTime != null && localDateTime.isBefore(LocalDateTime.now());
    }
}
