package com.ebsolutions.eventsadminservice.client;

import com.ebsolutions.eventsadminservice.model.Client;
import com.ebsolutions.eventsadminservice.testing.AssertionUtil;
import com.ebsolutions.eventsadminservice.utils.DateTimeComparisonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientSteps {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    protected DynamoDbTable<ClientDto> clientTable;

    @Autowired
    protected BatchWriteResult batchWriteResult;

    protected ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

    private Client clientOne;
    private Client clientTwo;
    private ResultActions performedActions;
    private LocalDateTime now;
    private List<Client> clients;
    private String requestJson;

    @Before
    public void before() {
        when(dynamoDbEnhancedClient.batchWriteItem(isA(BatchWriteItemEnhancedRequest.class))).thenReturn(batchWriteResult);
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

    @And("the client is invalid")
    public void theClientIsInvalid() throws JsonProcessingException {
        clientOne = Client.builder()
                .name("Create Client: Client Name 1")
                .build();

        String clientsJson = objectWriter.writeValueAsString(List.of(clientOne));
        JsonNode jsonNode = objectMapper.readTree(clientsJson);
        if (!jsonNode.isArray()) {
            fail("List of clients is not an array");
        }

        ArrayNode arrayNode = (ArrayNode) jsonNode;
        ObjectNode firstClient = (ObjectNode) arrayNode.get(0);
        System.out.println(firstClient);
        firstClient.remove("name");
        System.out.println(firstClient);

        requestJson = objectWriter.writeValueAsString(List.of(firstClient));
    }

    @When("the client creation endpoint is called with the clients")
    public void theClientCreationEndpointIsCalledWithTheClients() throws Exception {
        String requestJson = objectWriter.writeValueAsString(List.of(clientOne, clientTwo));
        // Setting the test's time of now right before endpoint invocation
        now = LocalDateTime.now();
        performedActions = mockMvc.perform(
                post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));
    }

    @When("the client creation endpoint is called with the invalid client")
    public void theClientCreationEndpointIsCalledWithTheInvalidClient() throws Exception {
        // Setting the test's time of now right before endpoint invocation
        now = LocalDateTime.now();
        performedActions = mockMvc.perform(
                post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));
    }

    @And("the endpoint replies with the newly created clients")
    public void theEndpointRepliesWithTheNewlyCreatedClients() throws Exception {
        MvcResult mvcResult = performedActions.andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        this.clients = objectMapper.readerForListOf(Client.class).readValue(content);
        assertEquals(2, clients.size());

        Client firstClientResponse = clients.get(0);
        assertEquals(clientOne.getName(), firstClientResponse.getName());
        assertTrue(DateTimeComparisonUtil.areDateTimesEqual(now, firstClientResponse.getCreatedOn()));

        Client secondClientResponse = clients.get(1);
        assertEquals(clientTwo.getName(), secondClientResponse.getName());
        assertTrue(DateTimeComparisonUtil.areDateTimesEqual(now, secondClientResponse.getCreatedOn()));
    }

    @And("the newly created clients were saved to the database")
    public void theNewlyCreatedClientsWereSavedToTheDatabase() {
        ArgumentCaptor<BatchWriteItemEnhancedRequest> savedCaptor = ArgumentCaptor.forClass(BatchWriteItemEnhancedRequest.class);
        verify(dynamoDbEnhancedClient).batchWriteItem(savedCaptor.capture());
        BatchWriteItemEnhancedRequest arg = savedCaptor.getValue();
        List<WriteRequest> writeRequests = arg.writeBatches().stream().flatMap(writeBatch -> writeBatch.writeRequests().stream()).toList();
        assertEquals(2, writeRequests.size());

        AssertionUtil.assertWrittenClient(this.clients.get(0), writeRequests.get(0));
        AssertionUtil.assertWrittenClient(this.clients.get(1), writeRequests.get(1));

    }

    @And("the client was not saved to the database")
    public void theClientWasNotSavedToTheDatabase() {
        verify(dynamoDbEnhancedClient, never()).batchWriteItem(any(BatchWriteItemEnhancedRequest.class));
    }

    @Then("the endpoint replies with the correct success status")
    public void theEndpointRepliesWithTheCorrectSuccessStatus() throws Exception {
        performedActions.andExpect(status().isOk());
    }

    @Then("the endpoint replies with a bad request status")
    public void theEndpointRepliesWithABadRequestStatus() throws Exception {
        performedActions.andExpect(status().isBadRequest());
    }
}