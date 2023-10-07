package com.ebsolutions.eventsadminservice.utils;

import java.util.UUID;

public class UniqueIdGenerator {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
