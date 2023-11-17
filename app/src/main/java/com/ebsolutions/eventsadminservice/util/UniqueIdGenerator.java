package com.ebsolutions.eventsadminservice.util;

import java.util.UUID;

public class UniqueIdGenerator {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
