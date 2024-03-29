package com.ebsolutions.eventsadminservice.spec

import com.ebsolutions.eventsadminservice.constant.ClientTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.Client
import com.ebsolutions.eventsadminservice.util.CopyObjectUtil
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDateTime

@MicronautTest
class ClientSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Get a client: Client Exists"() {
        given: "the client id is in the url"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(ClientTestConstants.GET_CLIENT.getClientId())
                    .toString()

        and: "a client exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a client"
            HttpResponse<Client> response = httpClient.toBlocking()
                    .exchange(clientsUrl, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct client is returned"
            Client client = response.body()
            Assertions.assertEquals(ClientTestConstants.GET_CLIENT.getClientId(), client.getClientId())
            Assertions.assertEquals(ClientTestConstants.GET_CLIENT.getName(), client.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.GET_CLIENT.getCreatedOn(), client.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.GET_CLIENT.getLastUpdatedOn(), client.getLastUpdatedOn()))
    }

    def "Get a client: Client does not exist"() {
        given: "the client id is in the url"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(TestConstants.nonExistentClientId)
                    .toString()

        and: "a client does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve a client"
            HttpResponse<Client> response = httpClient.toBlocking()
                    .exchange(clientsUrl, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Get all clients: Clients exist"() {
        given: "the url is correct"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .toString()

        and: "clients exist in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve clients"
            HttpRequest httpRequest = HttpRequest.GET(clientsUrl)

            HttpResponse<List<Client>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Client.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct clients are returned"
            List<Client> allClients = response.body()
            println allClients
            List<Client> getAllClients = List.copyOf(allClients).stream()
                    .filter(client -> client.getClientId().contains("-get-all-clients"))
                    .toList()

            Client firstClient = getAllClients.get(0)
            Client secondClient = getAllClients.get(1)

            Assertions.assertEquals(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_ONE.getClientId(), firstClient.getClientId())
            Assertions.assertEquals(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_ONE.getName(), firstClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_ONE.getCreatedOn(), firstClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_ONE.getLastUpdatedOn(), firstClient.getLastUpdatedOn()))

            Assertions.assertEquals(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_TWO.getClientId(), secondClient.getClientId())
            Assertions.assertEquals(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_TWO.getName(), secondClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_TWO.getCreatedOn(), secondClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.GET_ALL_CLIENTS_CLIENT_TWO.getLastUpdatedOn(), secondClient.getLastUpdatedOn()))

    }

    @Ignore("Disabled until the database can be empty of clients during this test")
    def "Get all clients: No clients exist"() {
        given: "the client id is in the url"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .toString()

        and: "no clients exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve clients"
            HttpRequest httpRequest = HttpRequest.GET(clientsUrl)

            HttpResponse<List<Client>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Client.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Delete a client: Delete client is successful"() {
        given: "the client id is in the url"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(ClientTestConstants.DELETE_CLIENT.getClientId())
                    .toString()

        and: "a client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(clientsUrl, Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

        when: "a request is made to delete the client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(clientsUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the client no longer exists in the database"
            HttpResponse<Client> finalResponse = httpClient.toBlocking()
                    .exchange(clientsUrl, Client)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    def "Create a client: Create client is successful"() {
        given: "the url is correct"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .toString()

        and: "the client is valid"
            Client newClient = Client.builder()
                    .name(ClientTestConstants.CREATE_CLIENT.getName())
                    .build()

        when: "a request is made to create a client for a client"
            HttpRequest httpRequest = HttpRequest.POST(clientsUrl, newClient)
            HttpResponse<Client> response = httpClient.toBlocking()
                    .exchange(httpRequest, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct client is returned"
            Client client = response.body()
            Assertions.assertNotNull(client.getClientId())
            Assertions.assertEquals(ClientTestConstants.CREATE_CLIENT.getName(), client.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(client.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(client.getLastUpdatedOn()))

        and: "the new client is correct in the database"
            HttpResponse<Client> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(clientsUrl.concat(client.getClientId()), Client)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Client databaseClient = checkingDatabaseResponse.body()
            Assertions.assertEquals(client.getClientId(), databaseClient.getClientId())
            Assertions.assertEquals(ClientTestConstants.CREATE_CLIENT.getName(), databaseClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseClient.getLastUpdatedOn()))

    }

    def "Update a client: Update fails given client's client id is blank"() {
        given: "the url is correct"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .toString()

        and: "a client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(clientsUrl.concat(ClientTestConstants.UPDATE_CLIENT.getClientId()), Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(ClientTestConstants.UPDATE_CLIENT.getClientId(), initResponse.body().getClientId())

        and: "the client's client id is blank"
            Client updatedClient = CopyObjectUtil.client(initResponse.body())
            updatedClient.setClientId("")

        when: "a request is made to update a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(clientsUrl), updatedClient)
            httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a client: Update fails given create date is empty"() {
        given: "the client id is in the url"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .toString()

        and: "a client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(clientsUrl.concat(ClientTestConstants.UPDATE_CLIENT.getClientId()), Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.UPDATE_CLIENT.getCreatedOn(), initResponse.body().getCreatedOn()))

        and: "the client's create date is empty"
            Client updatedClient = CopyObjectUtil.client(initResponse.body())
            updatedClient.setCreatedOn(null)

        when: "a request is made to update a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(clientsUrl), updatedClient)
            httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a client: Update fails given create date is after 'now'"() {
        given: "the url is correct"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .toString()

        and: "a client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(clientsUrl.concat(ClientTestConstants.UPDATE_CLIENT.getClientId()), Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.UPDATE_CLIENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.UPDATE_CLIENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "the client's create date is after the current date and time"
            Client updatedClient = CopyObjectUtil.client(initResponse.body())
            updatedClient.setCreatedOn(LocalDateTime.now().plusMonths(1))

        when: "a request is made to update a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(clientsUrl), updatedClient)
            httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a client: Update client is successful"() {
        given: "the url is correct"
            String clientsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .toString()

        and: "a client exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Client> initResponse = httpClient.toBlocking()
                    .exchange(clientsUrl.concat(ClientTestConstants.UPDATE_CLIENT.getClientId()), Client)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.UPDATE_CLIENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ClientTestConstants.UPDATE_CLIENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "a valid update is made to client"
            Client updatedClient = CopyObjectUtil.client(initResponse.body())
            updatedClient.setName(ClientTestConstants.updateClientUpdatedName)
            updatedClient.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to with the updated client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(clientsUrl), updatedClient)
            HttpResponse<Client> response = httpClient.toBlocking().exchange(httpRequest, Client)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated client is returned in the response"
            Client returnedClient = response.body()
            Assertions.assertEquals(ClientTestConstants.UPDATE_CLIENT.getClientId(), returnedClient.getClientId())
            Assertions.assertEquals(ClientTestConstants.updateClientUpdatedName, returnedClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, returnedClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(returnedClient.getLastUpdatedOn()))

        and: "the client is correct in the database"
            HttpResponse<Client> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(clientsUrl.concat(returnedClient.getClientId()), Client)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Client databaseClient = checkingDatabaseResponse.body()
            Assertions.assertEquals(ClientTestConstants.UPDATE_CLIENT.getClientId(), databaseClient.getClientId())
            Assertions.assertEquals(ClientTestConstants.updateClientUpdatedName, databaseClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, databaseClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseClient.getLastUpdatedOn()))
    }
}