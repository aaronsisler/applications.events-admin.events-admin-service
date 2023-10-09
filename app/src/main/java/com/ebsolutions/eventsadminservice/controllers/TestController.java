package com.ebsolutions.eventsadminservice.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/test")
public class TestController {

    @Get(produces = "text/plain")
    public String index() {
        return "Example Response";
    }
}