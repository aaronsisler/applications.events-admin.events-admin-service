package com.ebsolutions.eventsadminservice.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateValidator {
    public static boolean isBeforeNow(LocalDateTime localDateTime) {
        return localDateTime != null && localDateTime.isBefore(LocalDateTime.now());
    }

    public static boolean isWeekday(LocalDate localDate) {
        return switch (localDate.getDayOfWeek()) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> true;
            case SATURDAY, SUNDAY -> false;
        };
    }
}
