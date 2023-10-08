package com.ebsolutions.eventsadminservice;

import io.micronaut.http.annotation.*;

@Controller("/eventsAdminService")
public class EventsAdminServiceController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}