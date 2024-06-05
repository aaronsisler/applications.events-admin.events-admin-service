package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.config.DatabaseConfig;
import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.utils.DateTimeComparisonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientSteps {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected DynamoDbTable<?> dynamoDbTable;

    @Autowired
    protected DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    protected DatabaseConfig databaseConfig;

    @Autowired
    protected BatchWriteResult batchWriteResult;

    protected ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    private Client clientOne;
    private Client clientTwo;
    private ResultActions performedActions;

    @Before
    public void before() {
        System.out.println("Before All");
        // TODO Fix IDE yelling
        when(dynamoDbEnhancedClient.batchWriteItem((Consumer<BatchWriteItemEnhancedRequest.Builder>) any())).thenReturn(batchWriteResult);
        when(batchWriteResult.unprocessedPutItemsForTable(any())).thenReturn(Collections.emptyList());
    }

    @And("the first client is valid")
    public void theFirstClientIsValid() {
        clientOne = Client.builder()
                .name("Create Client: Client Name 1")
                .build();
    }

    @And("the second client is valid")
    public void theSecondClientIsValid() {
        clientTwo = Client.builder()
                .name("Create Client: Client Name 2")
                .build();
    }

    @When("the client creation endpoint is called")
    public void theClientCreationEndpointIsCalled() throws Exception {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(List.of(clientOne, clientTwo));
        performedActions = mockMvc.perform(
                post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));
    }

    @Then("the endpoint replies with the correct status")
    public void theEndpointRepliesWithTheCorrectStatus() throws Exception {
        performedActions.andExpect(status().isOk());
    }

    @And("the endpoint replies with the created clients")
    public void theEndpointRepliesWithTheCreatedClients() throws Exception {
        MvcResult mvcResult = performedActions.andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        List<Client> clients = mapper.readerForListOf(Client.class).readValue(content);
        assertEquals(2, clients.size());

        LocalDateTime now = LocalDateTime.now();
        Client firstClientResponse = clients.get(0);
        assertEquals(clientOne.getName(), firstClientResponse.getName());
        assertTrue(DateTimeComparisonUtil.areDateTimesEqual(now, firstClientResponse.getCreatedOn()));

        Client secondClientResponse = clients.get(1);
        assertEquals(clientTwo.getName(), secondClientResponse.getName());
        assertTrue(DateTimeComparisonUtil.areDateTimesEqual(now, secondClientResponse.getCreatedOn()));
    }

    @And("the created clients were saved to the database")
    public void theCreatedClientsWereSavedToTheDatabase() {
        ArgumentCaptor<Consumer<BatchWriteItemEnhancedRequest.Builder>> savedCaptor = ArgumentCaptor.forClass(Consumer.class);
        verify(dynamoDbEnhancedClient).batchWriteItem(savedCaptor.capture());
        assertTrue(savedCaptor.getValue().contains("substring I want to find");
    }
}
