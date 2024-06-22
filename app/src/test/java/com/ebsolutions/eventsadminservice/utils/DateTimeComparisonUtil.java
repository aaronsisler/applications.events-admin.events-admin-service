package com.ebsolutions.eventsadminservice.utils;

import com.ebsolutions.eventsadminservice.testing.TestConfigConstants;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeComparisonUtil {
    /**
     * Checks the absolute value of the time between two LocalDateTime values in milliseconds.
     * Method then returns if that value is less than a test range interval.
     * The range is for the delay between the service call and the possible 'now' within the test.
     *
     * @return Returns if time between two LocalDateTime values is less than a test range interval.
     */
    public static boolean areDateTimesEqual(LocalDateTime firstLocalDateTime, LocalDateTime secondLocalDateTime) {
        return Math.abs(ChronoUnit.MILLIS.between(firstLocalDateTime, secondLocalDateTime))
                < TestConfigConstants.TIME_INTERVAL_FOR_COMPARISON_OF_TWO_TIMES;
    }
}
