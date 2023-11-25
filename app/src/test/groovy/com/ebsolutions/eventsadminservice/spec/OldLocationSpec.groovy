package com.ebsolutions.eventsadminservice.spec

import com.ebsolutions.eventsadminservice.constant.LocationTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.Location
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

import java.text.MessageFormat
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@MicronautTest
class OldLocationSpec extends Specification {
    @Inject
    private HttpClient httpClient

    private String locationsUrl = TestConstants.eventsAdminServiceUrl + "/locations"

    def "Get a Location: Given location exists"() {
        given: "A location exists in the database"
            // Data seeded from Database init scripts
        when: "a request is made to retrieve the location"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(this.locationsUrl + "/" + LocationTestConstants.getLocationId, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct location is returned"
            Location location = response.body()
            Assertions.assertEquals(LocationTestConstants.getLocationClientId, location.getClientId())
            Assertions.assertEquals("Get Mock Location Name", location.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, location.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, location.getLastUpdatedOn()))
    }

    def "Get a Location: Given location does not exist"() {
        given: "A location does not exist in the database"
            // No data seeded from Database init scripts
        when: "a request is made to retrieve the location"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(this.locationsUrl + "/" + TestConstants.nonExistentLocationId, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Get all Locations: Locations exist for client"() {
        given: "A set of locations exist in the database for a given client"
            // Data seeded from Database init scripts
        when: "a request is made to retrieve the locations"
            HttpRequest httpRequest = HttpRequest.GET(this.locationsUrl)

            HttpResponse<List<Location>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Location.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct locations are returned"
            List<Location> locations = response.body()
            Location firstLocation = locations.get(0)
            Location secondLocation = locations.get(1)

            Assertions.assertEquals(LocationTestConstants.getAllLocationsClientId, firstLocation.getClientId())
            Assertions.assertEquals(LocationTestConstants.getAllLocationsIdOne, firstLocation.getLocationId())
            Assertions.assertEquals("Get All Mock Location Name 1", firstLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, firstLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, firstLocation.getLastUpdatedOn()))

            Assertions.assertEquals(LocationTestConstants.getAllLocationsClientId, secondLocation.getClientId())
            Assertions.assertEquals(LocationTestConstants.getAllLocationsIdTwo, secondLocation.getLocationId())
            Assertions.assertEquals("Get All Mock Location Name 2", secondLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, secondLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, secondLocation.getLastUpdatedOn()))
    }

    def "Get all Locations: No locations exist for client"() {
        given: "No locations exist in the database for a given client"
            // No data seeded from Database init scripts
        when: "a request is made to retrieve the locations"
            String incorrectUrl = MessageFormat.format("{0}/{1}/locations",
                    TestConstants.clientsUrl,
                    TestConstants.nonExistentClientId)

            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(incorrectUrl, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Delete a Location"() {
        given: "A location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(this.locationsUrl + "/" + LocationTestConstants.deleteLocationClientId, Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Location initClient = initResponse.body()
            Assertions.assertEquals(LocationTestConstants.deleteLocationClientId, initClient.getClientId())
            Assertions.assertEquals("Delete Mock Client Name", initClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initClient.getLastUpdatedOn()))

        when: "a request is made to delete the location"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(this.locationsUrl + "/" + LocationTestConstants.deleteLocationId))
            HttpResponse<Location> response = httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the location no longer exists in the database"
            HttpResponse<Location> postResponse = httpClient.toBlocking()
                    .exchange(this.locationsUrl + "/" + LocationTestConstants.deleteLocationClientId, Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, postResponse.code())
    }

    def "Create a Location: Success"() {
        given: "A valid location"
            Location newLocation = Location.builder()
                    .clientId(LocationTestConstants.createLocationClientId)
                    .name("Create Mock Location Name").build()
        when: "a request is made to create a location"
            HttpRequest httpRequest = HttpRequest.POST(URI.create(this.locationsUrl), newLocation)
            HttpResponse<Location> response = httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the new location is returned"
            Location location = response.body()
            Assertions.assertEquals(LocationTestConstants.createLocationClientId, location.getClientId())
            Assertions.assertNotNull(location.getLocationId())
            Assertions.assertEquals("Create Mock Client Name", location.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(location.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(location.getLastUpdatedOn()))
    }

    def "Update a Location: Fails given invalid Location Id"() {
        given: "A location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(this.locationsUrl + "/" + LocationTestConstants.updateLocationId, Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Location initLocation = initResponse.body()
            Assertions.assertEquals(LocationTestConstants.updateLocationClientId, initLocation.getClientId())
            Assertions.assertEquals("Update Mock Location Name", initLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initLocation.getLastUpdatedOn()))

        and: "an update is made to the location id that is invalid"
            Location updatedLocation = CopyObjectUtil.location(initLocation)
            updatedLocation.setClientId("")

        when: "a request is made to update the location"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(this.locationsUrl), updatedLocation)
            httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a Location: Fails given create date is after 'now'"() {
        given: "A location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(this.locationsUrl + "/" + LocationTestConstants.updateClientId, Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Location initClient = initResponse.body()
            Assertions.assertEquals(LocationTestConstants.updateClientId, initClient.getClientId())
            Assertions.assertEquals("Update Mock Client Name", initClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initClient.getLastUpdatedOn()))

        and: "an update is made to the created on date that is invalid"
            Location updatedClient = CopyObjectUtil.client(initClient)
            // Add an extra day to "now" since that is what the controller tests
            updatedClient.setCreatedOn(LocalDateTime.now().plus(1, ChronoUnit.DAYS))

        when: "a request is made to update the location"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(this.locationsUrl), updatedClient)
            httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a Location: Success"() {
        given: "A location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(this.locationsUrl + "/" + LocationTestConstants.updateClientId, Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

            Location initClient = initResponse.body()
            Assertions.assertEquals(LocationTestConstants.updateClientId, initClient.getClientId())
            Assertions.assertEquals("Update Mock Client Name", initClient.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initClient.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initClient.getLastUpdatedOn()))

        and: "an update is made to location"
            Location updatedClient = CopyObjectUtil.client(initClient)
            updatedClient.setName("New Updated Mock Client Name")

        when: "a request is made to update the location"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(this.locationsUrl), updatedClient)
            HttpResponse<Location> response = httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated location is returned"
            Location client = response.body()
            Assertions.assertNotNull(client.getClientId())
            Assertions.assertEquals("New Updated Mock Client Name", client.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, client.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(client.getLastUpdatedOn()))
    }


}