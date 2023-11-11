package com.ebsolutions.eventsadminservice.validator;

import java.time.Instant;

public class DateValidator {
    public static boolean isBeforeNow(Instant input) {
        return input != null && input.isBefore(Instant.now());
    }
}
