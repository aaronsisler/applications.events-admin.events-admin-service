package com.ebsolutions.eventsadminservice.spec

import com.ebsolutions.eventsadminservice.constant.ClientTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.Client
import com.ebsolutions.eventsadminservice.util.CopyObjectUtil
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@MicronautTest
class ClientSpec extends Specification {
    @Inject
    private HttpClient httpClient

    private String clientsUrl = TestConstants.eventsAdminServiceUrl + "/clients"

    def "Get a Client: Client exists"() {
        given: "A client exists in the database"
            // Data seeded from Database init scripts
        when: "a request is made to retrieve the client"
            HttpResponse<Client> response = httpClient.toBlocking()
                    .exchange(this.clientsUrl + "/" + ClientTestConstants.getClientId, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct client is returned"
            Client client = response.body()
            Assertions.assertEquals(ClientTestConstants.getClientId, client.getClientId())
            Assertions.assertEquals("Get Mock Client Name", client.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, client.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, client.getLastUpdatedOn()))
    }

    def "Get a Client: Client does not exist"() {
        given: "A client does not exist in the database"
            // No data seeded from Database init scripts
        when: "a request is made to retrieve the client"
            HttpResponse<Client> response = httpClient.toBlocking()
                    .exchange(this.clientsUrl + "/" + TestConstants.nonExistentClientId, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Create a Client: Create client is successful"() {
        given: "A valid client"
            Client newClient = Client.builder().name("Create Mock Client Name").build()
        when: "a request is made to create a client"
            HttpRequest httpRequest = HttpRequest.POST(URI.create(this.clientsUrl), newClient)
            HttpResponse<Client> response = httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the new client is returned"
            Client client = response.body()
            Assertions.assertNotNull(client.getClientId())
            Assertions.assertEquals("Create Mock Client Name", client.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(client.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(client.getLastUpdatedOn()))
    }

    def "Update a Client: Update fails given invalid Client Id"() {
        given: "A client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(this.clientsUrl + "/" + ClientTestConstants.updateClientId, Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Client initClient = initResponse.body()
            Assertions.assertEquals(ClientTestConstants.updateClientId, initClient.getClientId())
            Assertions.assertEquals("Update Mock Client Name", initClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initClient.getLastUpdatedOn()))

        and: "an update is made to the client id that is invalid"
            Client updatedClient = CopyObjectUtil.client(initClient)
            updatedClient.setClientId("")

        when: "a request is made to update the client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(this.clientsUrl), updatedClient)
            httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a client: Update fails given create date is empty"() {}

    def "Update a client: Update fails given create date is after 'now'"() {
        given: "A client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(this.clientsUrl + "/" + ClientTestConstants.updateClientId, Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Client initClient = initResponse.body()
            Assertions.assertEquals(ClientTestConstants.updateClientId, initClient.getClientId())
            Assertions.assertEquals("Update Mock Client Name", initClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initClient.getLastUpdatedOn()))

        and: "an update is made to the created on date that is invalid"
            Client updatedClient = CopyObjectUtil.client(initClient)
            // Add an extra day to "now" since that is what the controller tests
            updatedClient.setCreatedOn(LocalDateTime.now().plus(1, ChronoUnit.DAYS))

        when: "a request is made to update the client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(this.clientsUrl), updatedClient)
            httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a Client: Update client is successful"() {
        given: "A client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(this.clientsUrl + "/" + ClientTestConstants.updateClientId, Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Client initClient = initResponse.body()
            Assertions.assertEquals(ClientTestConstants.updateClientId, initClient.getClientId())
            Assertions.assertEquals("Update Mock Client Name", initClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initClient.getLastUpdatedOn()))

        and: "an update is made to client"
            Client updatedClient = CopyObjectUtil.client(initClient)
            updatedClient.setName("New Updated Mock Client Name")

        when: "a request is made to update the client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(this.clientsUrl), updatedClient)
            HttpResponse<Client> response = httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated client is returned"
            Client client = response.body()
            Assertions.assertNotNull(client.getClientId())
            Assertions.assertEquals("New Updated Mock Client Name", client.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, client.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(client.getLastUpdatedOn()))
    }

    def "Delete a client: Delete client is successful"() {
        given: "A client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(this.clientsUrl + "/" + ClientTestConstants.deleteClientId, Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Client initClient = initResponse.body()
            Assertions.assertEquals(ClientTestConstants.deleteClientId, initClient.getClientId())
            Assertions.assertEquals("Delete Mock Client Name", initClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initClient.getLastUpdatedOn()))

        when: "a request is made to delete the client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(this.clientsUrl + "/" + ClientTestConstants.deleteClientId))
            HttpResponse<Client> response = httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the client no longer exists in the database"
            HttpResponse<Client> postResponse = httpClient.toBlocking()
                    .exchange(this.clientsUrl + "/" + ClientTestConstants.deleteClientId, Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, postResponse.code())
    }
}