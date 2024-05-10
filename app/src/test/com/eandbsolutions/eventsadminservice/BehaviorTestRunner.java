package com.eandbsolutions.eventsadminservice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:behaviors")
public class BehaviorTestRunner {
}
