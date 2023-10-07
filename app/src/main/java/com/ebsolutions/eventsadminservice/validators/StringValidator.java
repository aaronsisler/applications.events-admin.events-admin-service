package com.ebsolutions.eventsadminservice.validators;

public class StringValidator {
    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }
}
