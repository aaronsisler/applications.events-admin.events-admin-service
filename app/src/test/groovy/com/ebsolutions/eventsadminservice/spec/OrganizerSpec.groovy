package com.ebsolutions.eventsadminservice.spec

import com.ebsolutions.eventsadminservice.constant.OrganizerTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.Organizer
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
import spock.lang.Specification

import java.time.LocalDateTime

@MicronautTest
class OrganizerSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Get a organizer: URL Client id exists: Organizer Exists"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.GET_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .append(OrganizerTestConstants.GET_ORGANIZER.getOrganizerId())
                    .toString()

        and: "a organizer exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a organizer"
            HttpResponse<Organizer> response = httpClient.toBlocking()
                    .exchange(organizersUrl, Organizer)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct organizer is returned"
            Organizer organizer = response.body()
            Assertions.assertEquals(OrganizerTestConstants.GET_ORGANIZER.getClientId(), organizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.GET_ORGANIZER.getOrganizerId(), organizer.getOrganizerId())
            Assertions.assertEquals(OrganizerTestConstants.GET_ORGANIZER.getName(), organizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.GET_ORGANIZER.getCreatedOn(), organizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.GET_ORGANIZER.getLastUpdatedOn(), organizer.getLastUpdatedOn()))
    }

    def "Get a organizer: URL Client id exists: Organizer does not exist"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.GET_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .append(TestConstants.nonExistentOrganizerId)
                    .toString()

        and: "a organizer does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve a organizer"
            HttpResponse<Organizer> response = httpClient.toBlocking()
                    .exchange(organizersUrl, Organizer)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Get all organizers: URL Client id exists: Organizers exist for client"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.getAllOrganizersClientId)
                    .append("/organizers/")
                    .toString()

        and: "organizers exist in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve organizers for a client"
            HttpRequest httpRequest = HttpRequest.GET(organizersUrl)

            HttpResponse<List<Organizer>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Organizer.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct organizers are returned"
            List<Organizer> organizers = response.body()
            Organizer firstOrganizer = organizers.get(0)
            Organizer secondOrganizer = organizers.get(1)

            Assertions.assertEquals(OrganizerTestConstants.getAllOrganizersClientId, firstOrganizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_ONE.getOrganizerId(), firstOrganizer.getOrganizerId())
            Assertions.assertEquals(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_ONE.getName(), firstOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_ONE.getCreatedOn(), firstOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_ONE.getLastUpdatedOn(), firstOrganizer.getLastUpdatedOn()))

            Assertions.assertEquals(OrganizerTestConstants.getAllOrganizersClientId, secondOrganizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_TWO.getOrganizerId(), secondOrganizer.getOrganizerId())
            Assertions.assertEquals(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_TWO.getName(), secondOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_TWO.getCreatedOn(), secondOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.GET_ALL_ORGANIZERS_ORGANIZER_TWO.getLastUpdatedOn(), secondOrganizer.getLastUpdatedOn()))

    }

    def "Get all organizers: URL Client id exists: No organizers exist for client"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(TestConstants.noChildrenClientId)
                    .append("/organizers/")
                    .toString()

        and: "no organizers exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve organizers for a client"
            HttpRequest httpRequest = HttpRequest.GET(organizersUrl)

            HttpResponse<List<Organizer>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Organizer.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Delete a organizer: URL Client id exists: Delete organizer is successful"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.DELETE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .append(OrganizerTestConstants.DELETE_ORGANIZER.getOrganizerId())
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl, Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

        when: "a request is made to delete the organizer for a client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(organizersUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the organizer no longer exists in the database"
            HttpResponse<Organizer> finalResponse = httpClient.toBlocking()
                    .exchange(organizersUrl, Organizer)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    def "Create a organizer: URL Client id exists: Create fails given organizer's client id is blank"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.CREATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "the organizer's client id is blank"
            Organizer newOrganizer = Organizer.builder()
                    .clientId("")
                    .name(OrganizerTestConstants.CREATE_ORGANIZER.getName())
                    .build()

        when: "a request is made to create a organizer for a client"
            HttpRequest httpRequest = HttpRequest.POST(organizersUrl, newOrganizer)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a organizer: URL Client id exists: Create fails given organizer's client id and URL client id do not match"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.CREATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "the organizer's client id does not match the URL client id"
            Organizer newOrganizer = Organizer.builder()
                    .clientId("not-the-url-client-id")
                    .name(OrganizerTestConstants.CREATE_ORGANIZER.getName())
                    .build()

        when: "a request is made to create a organizer for a client"
            HttpRequest httpRequest = HttpRequest.POST(organizersUrl, newOrganizer)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a organizer: URL Client id exists: Create organizer is successful"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.CREATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "the organizer is valid"
            Organizer newOrganizer = Organizer.builder()
                    .clientId(OrganizerTestConstants.CREATE_ORGANIZER.getClientId())
                    .name(OrganizerTestConstants.CREATE_ORGANIZER.getName())
                    .build()

        when: "a request is made to create a organizer for a client"
            HttpRequest httpRequest = HttpRequest.POST(organizersUrl, newOrganizer)
            HttpResponse<Organizer> response = httpClient.toBlocking()
                    .exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct organizer is returned"
            Organizer organizer = response.body()
            Assertions.assertEquals(OrganizerTestConstants.CREATE_ORGANIZER.getClientId(), organizer.getClientId())
            Assertions.assertNotNull(organizer.getOrganizerId())
            Assertions.assertEquals(OrganizerTestConstants.CREATE_ORGANIZER.getName(), organizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(organizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(organizer.getLastUpdatedOn()))

        and: "the new organizer is correct in the database"
            HttpResponse<Organizer> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(organizer.getOrganizerId()), Organizer)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Organizer databaseOrganizer = checkingDatabaseResponse.body()
            Assertions.assertEquals(OrganizerTestConstants.CREATE_ORGANIZER.getClientId(), databaseOrganizer.getClientId())
            Assertions.assertEquals(organizer.getOrganizerId(), databaseOrganizer.getOrganizerId())
            Assertions.assertEquals(OrganizerTestConstants.CREATE_ORGANIZER.getName(), databaseOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseOrganizer.getLastUpdatedOn()))

    }

    def "Update a organizer: URL Client id exists: Update fails given organizer's client id is blank"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.UPDATE_ORGANIZER.getOrganizerId()), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId(), initResponse.body().getClientId())

        and: "the organizer's client id is blank"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.setClientId("")

        when: "a request is made to update a organizer for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(organizersUrl), updatedOrganizer)
            httpClient.toBlocking().exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a organizer: URL Client id exists: Update fails given organizer's client id and URL client id do not match"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.UPDATE_ORGANIZER.getOrganizerId()), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId(), initResponse.body().getClientId())

        and: "the organizer's client id does not match the URL client id"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.setClientId("not-the-url-client-id")

        when: "a request is made to update a organizer for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(organizersUrl), updatedOrganizer)
            httpClient.toBlocking().exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a organizer: URL Client id exists: Update fails given create date is empty"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.UPDATE_ORGANIZER.getOrganizerId()), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.UPDATE_ORGANIZER.getCreatedOn(), initResponse.body().getCreatedOn()))

        and: "the organizer's create date is empty"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.setCreatedOn(null)

        when: "a request is made to update a organizer for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(organizersUrl), updatedOrganizer)
            httpClient.toBlocking().exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a organizer: URL Client id exists: Update fails given create date is after 'now'"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.UPDATE_ORGANIZER.getOrganizerId()), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.UPDATE_ORGANIZER.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.UPDATE_ORGANIZER.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "the organizer's create date is after the current date and time"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.setCreatedOn(LocalDateTime.now().plusMonths(1))

        when: "a request is made to update a organizer for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(organizersUrl), updatedOrganizer)
            httpClient.toBlocking().exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a organizer: URL Client id exists: Update organizer is successful"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId())
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.UPDATE_ORGANIZER.getOrganizerId()), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.UPDATE_ORGANIZER.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(OrganizerTestConstants.UPDATE_ORGANIZER.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "a valid update is made to organizer"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.setName(OrganizerTestConstants.updateOrganizerUpdatedName)
            updatedOrganizer.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to with the updated organizer"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(organizersUrl), updatedOrganizer)
            HttpResponse<Organizer> response = httpClient.toBlocking().exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated organizer is returned in the response"
            Organizer returnedOrganizer = response.body()
            Assertions.assertEquals(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId(), returnedOrganizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.UPDATE_ORGANIZER.getOrganizerId(), returnedOrganizer.getOrganizerId())
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerUpdatedName, returnedOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, returnedOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(returnedOrganizer.getLastUpdatedOn()))

        and: "the organizer is correct in the database"
            HttpResponse<Organizer> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(returnedOrganizer.getOrganizerId()), Organizer)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Organizer databaseOrganizer = checkingDatabaseResponse.body()
            Assertions.assertEquals(OrganizerTestConstants.UPDATE_ORGANIZER.getClientId(), databaseOrganizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.UPDATE_ORGANIZER.getOrganizerId(), databaseOrganizer.getOrganizerId())
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerUpdatedName, databaseOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, databaseOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseOrganizer.getLastUpdatedOn()))

    }
}