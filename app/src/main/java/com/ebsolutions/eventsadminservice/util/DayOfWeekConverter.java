package com.ebsolutions.eventsadminservice.util;

import com.ebsolutions.eventsadminservice.validator.StringValidator;

import java.time.DayOfWeek;

public class DayOfWeekConverter {
    public static String convert(DayOfWeek dayOfWeek) {
        return dayOfWeek.name();
    }

    public static DayOfWeek convert(String dayOfWeek) {
        if (StringValidator.isBlank(dayOfWeek)) {
            return null;
        }

        return DayOfWeek.valueOf(dayOfWeek);
    }
}
