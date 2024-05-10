package com.eandbsolutions.eventsadminservice;

public class HelloBehaviorStepDefinition {
    @io.cucumber.java.en.When("a user calls the service to say hello")
    public void aUserCallsTheServiceToSayHello() {
    }

    @io.cucumber.java.en.Then("the server responses")
    public void theServerResponses() {
        assert 1 == 1;
    }

    @io.cucumber.java.en.And("the server gives the correct date and time")
    public void theServerGivesTheCorrectDateAndTime() {
    }
}
