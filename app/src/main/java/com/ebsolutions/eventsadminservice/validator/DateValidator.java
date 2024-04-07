package com.ebsolutions.eventsadminservice.validator;

import java.time.DayOfWeek;
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

    public static boolean isWeekend(LocalDate localDate) {
        return switch (localDate.getDayOfWeek()) {
            case SATURDAY, SUNDAY -> true;
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> false;
        };
    }

    public static boolean areDayOfWeekEqual(LocalDate localDate, DayOfWeek dayOfWeek) {
        if (localDate == null) {
            return false;
        }

        if (dayOfWeek == null) {
            return false;
        }

        return localDate.getDayOfWeek().equals(dayOfWeek);
    }
}
