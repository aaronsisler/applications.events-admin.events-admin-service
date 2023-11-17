package com.ebsolutions.eventsadminservice.validator;

public class StringValidator {
    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }
}
