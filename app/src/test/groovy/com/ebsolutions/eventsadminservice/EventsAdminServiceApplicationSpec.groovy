package com.ebsolutions.eventsadminservice

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class EventsAdminServiceApplicationSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    void 'Application is running correctly'() {
        expect:
            application.running
    }
}
