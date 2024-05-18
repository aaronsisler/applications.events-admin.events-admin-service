package com.ebsolutions.eventsadminservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class EventsAdminServiceApplicationTest {

    @Autowired
    private EventsAdminServiceApplication application;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
    }
}