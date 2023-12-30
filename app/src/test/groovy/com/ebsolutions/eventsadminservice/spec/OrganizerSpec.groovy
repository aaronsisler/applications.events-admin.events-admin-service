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

    // Get a organizer
    def "Get a organizer: URL Client id exists: Organizer Exists"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.getOrganizerClientId)
                    .append("/organizers/")
                    .append(OrganizerTestConstants.getOrganizerId)
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
            Assertions.assertEquals(OrganizerTestConstants.getOrganizerClientId, organizer.getClientId())
            Assertions.assertEquals("Get Mock Organizer Name", organizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, organizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, organizer.getLastUpdatedOn()))
    }

    def "Get a organizer: URL Client id exists: Organizer does not exist"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.getOrganizerClientId)
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

    // Get all organizers
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
            Assertions.assertEquals(OrganizerTestConstants.getAllOrganizersIdOne, firstOrganizer.getOrganizerId())
            Assertions.assertEquals("Get All Mock Organizer Name 1", firstOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, firstOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, firstOrganizer.getLastUpdatedOn()))

            Assertions.assertEquals(OrganizerTestConstants.getAllOrganizersClientId, secondOrganizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.getAllOrganizersIdTwo, secondOrganizer.getOrganizerId())
            Assertions.assertEquals("Get All Mock Organizer Name 2", secondOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, secondOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, secondOrganizer.getLastUpdatedOn()))
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

    // Delete a organizer
    def "Delete a organizer: URL Client id exists: Delete organizer is successful"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.deleteOrganizerClientId)
                    .append("/organizers/")
                    .append(OrganizerTestConstants.deleteOrganizerId)
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
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> finalResponse = httpClient.toBlocking()
                    .exchange(organizersUrl, Organizer)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    // Create a organizer
    def "Create a organizer: URL Client id exists: Create fails given organizer client id is blank"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.createOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "the organizer's client id is blank"
            Organizer newOrganizer = Organizer.builder()
                    .clientId("")
                    .name("Create Mock Organizer Name")
                    .build()

        when: "a request is made to create a organizer for a client"
            HttpRequest httpRequest = HttpRequest.POST(organizersUrl, newOrganizer)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a organizer: URL Client id exists: Create fails given organizer client id and URL client id do not match"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.createOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "the organizer's client id does not match the URL client id"
            Organizer newOrganizer = Organizer.builder()
                    .clientId("not-the-url-client-id")
                    .name("Create Mock Organizer Name")
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
                    .append(OrganizerTestConstants.createOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "the organizer is valid"
            Organizer newOrganizer = Organizer.builder()
                    .clientId(OrganizerTestConstants.createOrganizerClientId)
                    .name("Create Mock Organizer Name")
                    .build()

        when: "a request is made to create a organizer for a client"
            HttpRequest httpRequest = HttpRequest.POST(organizersUrl, newOrganizer)
            HttpResponse<Organizer> response = httpClient.toBlocking()
                    .exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct organizer is returned"
            Organizer organizer = response.body()
            Assertions.assertEquals(OrganizerTestConstants.createOrganizerClientId, organizer.getClientId())
            Assertions.assertNotNull(organizer.getOrganizerId())
            Assertions.assertEquals("Create Mock Organizer Name", organizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(organizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(organizer.getLastUpdatedOn()))

        and: "the new organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> checkingResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(organizer.getOrganizerId()), Organizer)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingResponse.code())
    }

    // Update a organizer
    def "Update a organizer: URL Client id exists: Update fails given organizer client id is blank"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.updateOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.updateOrganizerId), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerClientId, initResponse.body().getClientId())

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

    def "Update a organizer: URL Client id exists: Update fails given organizer client id and URL client id do not match"() {
        given: "the client id is in the url"
            String organizersUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(OrganizerTestConstants.updateOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.updateOrganizerId), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerClientId, initResponse.body().getClientId())

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
                    .append(OrganizerTestConstants.updateOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.updateOrganizerId), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initResponse.body().getCreatedOn()))

        and: "the organizer's create date is empty"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.createdOn(null)

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
                    .append(OrganizerTestConstants.updateOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.updateOrganizerId), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initResponse.body().getLastUpdatedOn()))

        and: "the organizer's create date is after the current date and time"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.createdOn(null)

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
                    .append(OrganizerTestConstants.updateOrganizerClientId)
                    .append("/organizers/")
                    .toString()

        and: "a organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Organizer> initResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(OrganizerTestConstants.updateOrganizerId), Organizer)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initResponse.body().getLastUpdatedOn()))

        and: "a valid update is made to organizer"
            Organizer updatedOrganizer = CopyObjectUtil.organizer(initResponse.body())
            updatedOrganizer.setName("New Updated Organizer Name")
            updatedOrganizer.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to with the updated organizer"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(organizersUrl), updatedOrganizer)
            HttpResponse<Organizer> response = httpClient.toBlocking().exchange(httpRequest, Organizer)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated organizer is returned in the response"
            Organizer returnedOrganizer = response.body()
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerClientId, returnedOrganizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerId, returnedOrganizer.getOrganizerId())
            Assertions.assertEquals("New Updated Organizer Name", returnedOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, returnedOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(returnedOrganizer.getLastUpdatedOn()))

        and: "the organizer is correct in the database"
            HttpResponse<Organizer> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(organizersUrl.concat(returnedOrganizer.getOrganizerId()), Organizer)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Organizer databaseOrganizer = checkingDatabaseResponse.body()
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerClientId, databaseOrganizer.getClientId())
            Assertions.assertEquals(OrganizerTestConstants.updateOrganizerId, databaseOrganizer.getOrganizerId())
            Assertions.assertEquals("New Updated Organizer Name", databaseOrganizer.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, databaseOrganizer.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocalDateTime.now(), databaseOrganizer.getLastUpdatedOn()))
    }
}