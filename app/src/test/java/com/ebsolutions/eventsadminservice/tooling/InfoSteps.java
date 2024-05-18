package com.ebsolutions.eventsadminservice.tooling;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InfoSteps {
    @Autowired
    protected MockMvc mvc;

    private ResultActions performedActions;

    @When("the info endpoint is called")
    public void theInfoEndpointIsCalled() throws Exception {
        performedActions = mvc.perform(get("/actuator/info"))
                .andDo(print());
    }

    @Then("the info endpoint replies with the correct status")
    public void theInfoEndpointRepliesWithTheCorrectStatus() throws Exception {
        performedActions.andExpect(status().isOk());
    }

    @And("the info endpoint replies with the correct information")
    public void theInfoEndpointRepliesWithTheCorrectInformation() throws Exception {
        performedActions
                .andExpect(jsonPath("$.build.group", is("com.ebsolutions.eventsadminservice")))
                .andExpect(jsonPath("$.build.artifact", is("events-admin-service")))
                .andExpect(jsonPath("$.build.name", is("Events Admin Service")))
                .andExpect(jsonPath("$.build.version", notNullValue()))
                .andExpect(jsonPath("$.build.time", notNullValue()));
    }
}
