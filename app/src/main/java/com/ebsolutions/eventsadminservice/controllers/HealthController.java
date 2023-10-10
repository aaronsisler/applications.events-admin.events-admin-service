package com.ebsolutions.eventsadminservice.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Controller("/health")
public class HealthController {

    @Get(produces = "text/plain")
    public String index() {
        return "Service is alive and the time is " + LocalDateTime.now();
    }
}