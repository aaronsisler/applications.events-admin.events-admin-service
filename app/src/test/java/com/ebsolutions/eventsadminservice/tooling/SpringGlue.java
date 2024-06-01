package com.ebsolutions.eventsadminservice.tooling;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@CucumberContextConfiguration
@SpringBootTest(classes = CommonContext.class)
public class SpringGlue {
}