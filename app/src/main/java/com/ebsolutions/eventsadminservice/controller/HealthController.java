package com.ebsolutions.eventsadminservice.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Controller("/health")
public class HealthController {
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> index() {
        return HttpResponse.ok("Service is alive and the time is " + LocalDateTime.now());
    }
}