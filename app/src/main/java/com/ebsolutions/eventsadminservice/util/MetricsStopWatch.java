package com.ebsolutions.eventsadminservice.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetricsStopWatch {
    /**
     * Stores the start time (in milliseconds) when an object of the StopWatch class is initialized.
     */
    private final long startTime;

    public MetricsStopWatch() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Logs the elapsed time since the time the object of StopWatch was initialized.
     *
     * @param callingCodeBlock Function or location that this method is called in
     */
    public void logElapsedTime(String callingCodeBlock) {
        log.info("ELAPSED TIME: {} :: {}", System.currentTimeMillis() - startTime, callingCodeBlock);
    }
}
