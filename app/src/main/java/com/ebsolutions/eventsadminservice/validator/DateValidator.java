package com.ebsolutions.eventsadminservice.validator;

import java.time.LocalDateTime;

public class DateValidator {
    public static boolean isBeforeNow(LocalDateTime input) {
        return input != null && input.isBefore(LocalDateTime.now());
    }
}
