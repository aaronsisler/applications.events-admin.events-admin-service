package com.ebsolutions.eventsadminservice.util


import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class DateAndTimeComparisonUtil {
    static boolean areDateAndTimeEqual(LocalDateTime expectedDate, LocalDateTime testResult) {
        return expectedDate.until(testResult, ChronoUnit.SECONDS) == 0
    }

    /**
     * Check that dateTime is close to now (within 1 second for testing purposes)
     */
    static boolean isDateAndTimeNow(LocalDateTime testResult) {
        return testResult.until(LocalDateTime.now(), ChronoUnit.SECONDS) < 1
    }

    static boolean areDatesEqual(LocalDate expectedDate, LocalDate testResult) {
        if (expectedDate == null && testResult == null) {
            return true;
        }

        if (expectedDate == null || testResult == null) {
            return false;
        }

        return expectedDate.until(testResult, ChronoUnit.DAYS) == 0
    }

    static boolean areTimesEqual(LocalTime expectedTime, LocalTime testResult) {
        if (expectedTime == null && testResult == null) {
            return true;
        }

        if (expectedTime == null || testResult == null) {
            return false;
        }

        return expectedTime.until(testResult, ChronoUnit.SECONDS) == 0
    }
}
