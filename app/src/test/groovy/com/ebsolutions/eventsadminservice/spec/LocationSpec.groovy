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

import java.time.LocalDateTime

@MicronautTest
class LocationSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Get a location: URL Client id exists: Location Exists"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.GET_LOCATION.getClientId())
                    .append("/locations/")
                    .append(LocationTestConstants.GET_LOCATION.getLocationId())
                    .toString()

        and: "a location exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a location"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct location is returned"
            Location location = response.body()
            Assertions.assertEquals(LocationTestConstants.GET_LOCATION.getClientId(), location.getClientId())
            Assertions.assertEquals(LocationTestConstants.GET_LOCATION.getLocationId(), location.getLocationId())
            Assertions.assertEquals(LocationTestConstants.GET_LOCATION.getName(), location.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.GET_LOCATION.getCreatedOn(), location.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.GET_LOCATION.getLastUpdatedOn(), location.getLastUpdatedOn()))
    }

    def "Get a location: URL Client id exists: Location does not exist"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.GET_LOCATION.getClientId())
                    .append("/locations/")
                    .append(TestConstants.nonExistentLocationId)
                    .toString()

        and: "a location does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve a location"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Get all locations: URL Client id exists: Locations exist for client"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.getAllLocationsClientId)
                    .append("/locations/")
                    .toString()

        and: "locations exist in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve locations for a client"
            HttpRequest httpRequest = HttpRequest.GET(locationsUrl)

            HttpResponse<List<Location>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Location.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct locations are returned"
            List<Location> locations = response.body()
            Location firstLocation = locations.get(0)
            Location secondLocation = locations.get(1)

            Assertions.assertEquals(LocationTestConstants.getAllLocationsClientId, firstLocation.getClientId())
            Assertions.assertEquals(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_ONE.getLocationId(), firstLocation.getLocationId())
            Assertions.assertEquals(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_ONE.getName(), firstLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_ONE.getCreatedOn(), firstLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_ONE.getLastUpdatedOn(), firstLocation.getLastUpdatedOn()))

            Assertions.assertEquals(LocationTestConstants.getAllLocationsClientId, secondLocation.getClientId())
            Assertions.assertEquals(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_TWO.getLocationId(), secondLocation.getLocationId())
            Assertions.assertEquals(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_TWO.getName(), secondLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_TWO.getCreatedOn(), secondLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.GET_ALL_LOCATIONS_LOCATION_TWO.getLastUpdatedOn(), secondLocation.getLastUpdatedOn()))

    }

    def "Get all locations: URL Client id exists: No locations exist for client"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(TestConstants.noChildrenClientId)
                    .append("/locations/")
                    .toString()

        and: "no locations exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve locations for a client"
            HttpRequest httpRequest = HttpRequest.GET(locationsUrl)

            HttpResponse<List<Location>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Location.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Delete a location: URL Client id exists: Delete location is successful"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.DELETE_LOCATION.getClientId())
                    .append("/locations/")
                    .append(LocationTestConstants.DELETE_LOCATION.getLocationId())
                    .toString()

        and: "a location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

        when: "a request is made to delete the location for a client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(locationsUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the location no longer exists in the database"
            HttpResponse<Location> finalResponse = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    def "Create a location: URL Client id exists: Create fails given location's client id is blank"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.CREATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "the location's client id is blank"
            Location newLocation = Location.builder()
                    .clientId("")
                    .name(LocationTestConstants.CREATE_LOCATION.getName())
                    .build()

        when: "a request is made to create a location for a client"
            HttpRequest httpRequest = HttpRequest.POST(locationsUrl, newLocation)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a location: URL Client id exists: Create fails given location's client id and URL client id do not match"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.CREATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "the location's client id does not match the URL client id"
            Location newLocation = Location.builder()
                    .clientId("not-the-url-client-id")
                    .name(LocationTestConstants.CREATE_LOCATION.getName())
                    .build()

        when: "a request is made to create a location for a client"
            HttpRequest httpRequest = HttpRequest.POST(locationsUrl, newLocation)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a location: URL Client id exists: Create location is successful"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.CREATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "the location is valid"
            Location newLocation = Location.builder()
                    .clientId(LocationTestConstants.CREATE_LOCATION.getClientId())
                    .name(LocationTestConstants.CREATE_LOCATION.getName())
                    .build()

        when: "a request is made to create a location for a client"
            HttpRequest httpRequest = HttpRequest.POST(locationsUrl, newLocation)
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(httpRequest, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct location is returned"
            Location location = response.body()
            Assertions.assertEquals(LocationTestConstants.CREATE_LOCATION.getClientId(), location.getClientId())
            Assertions.assertNotNull(location.getLocationId())
            Assertions.assertEquals(LocationTestConstants.CREATE_LOCATION.getName(), location.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(location.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(location.getLastUpdatedOn()))

        and: "the new location is correct in the database"
            HttpResponse<Location> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.concat(location.getLocationId()), Location)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Location databaseLocation = checkingDatabaseResponse.body()
            Assertions.assertEquals(LocationTestConstants.CREATE_LOCATION.getClientId(), databaseLocation.getClientId())
            Assertions.assertEquals(location.getLocationId(), databaseLocation.getLocationId())
            Assertions.assertEquals(LocationTestConstants.CREATE_LOCATION.getName(), databaseLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseLocation.getLastUpdatedOn()))

    }

    def "Update a location: URL Client id exists: Update fails given location's client id is blank"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.UPDATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "a location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.concat(LocationTestConstants.UPDATE_LOCATION.getLocationId()), Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(LocationTestConstants.UPDATE_LOCATION.getClientId(), initResponse.body().getClientId())

        and: "the location's client id is blank"
            Location updatedLocation = CopyObjectUtil.location(initResponse.body())
            updatedLocation.setClientId("")

        when: "a request is made to update a location for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(locationsUrl), updatedLocation)
            httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a location: URL Client id exists: Update fails given location's client id and URL client id do not match"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.UPDATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "a location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.concat(LocationTestConstants.UPDATE_LOCATION.getLocationId()), Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(LocationTestConstants.UPDATE_LOCATION.getClientId(), initResponse.body().getClientId())

        and: "the location's client id does not match the URL client id"
            Location updatedLocation = CopyObjectUtil.location(initResponse.body())
            updatedLocation.setClientId("not-the-url-client-id")

        when: "a request is made to update a location for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(locationsUrl), updatedLocation)
            httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a location: URL Client id exists: Update fails given create date is empty"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.UPDATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "a location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.concat(LocationTestConstants.UPDATE_LOCATION.getLocationId()), Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.UPDATE_LOCATION.getCreatedOn(), initResponse.body().getCreatedOn()))

        and: "the location's create date is empty"
            Location updatedLocation = CopyObjectUtil.location(initResponse.body())
            updatedLocation.setCreatedOn(null)

        when: "a request is made to update a location for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(locationsUrl), updatedLocation)
            httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a location: URL Client id exists: Update fails given create date is after 'now'"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.UPDATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "a location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.concat(LocationTestConstants.UPDATE_LOCATION.getLocationId()), Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.UPDATE_LOCATION.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.UPDATE_LOCATION.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "the location's create date is after the current date and time"
            Location updatedLocation = CopyObjectUtil.location(initResponse.body())
            updatedLocation.setCreatedOn(LocalDateTime.now().plusMonths(1))

        when: "a request is made to update a location for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(locationsUrl), updatedLocation)
            httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a location: URL Client id exists: Update location is successful"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.UPDATE_LOCATION.getClientId())
                    .append("/locations/")
                    .toString()

        and: "a location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.concat(LocationTestConstants.UPDATE_LOCATION.getLocationId()), Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.UPDATE_LOCATION.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(LocationTestConstants.UPDATE_LOCATION.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "a valid update is made to location"
            Location updatedLocation = CopyObjectUtil.location(initResponse.body())
            updatedLocation.setName(LocationTestConstants.updateLocationUpdatedName)
            updatedLocation.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to with the updated location"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(locationsUrl), updatedLocation)
            HttpResponse<Location> response = httpClient.toBlocking().exchange(httpRequest, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated location is returned in the response"
            Location returnedLocation = response.body()
            Assertions.assertEquals(LocationTestConstants.UPDATE_LOCATION.getClientId(), returnedLocation.getClientId())
            Assertions.assertEquals(LocationTestConstants.UPDATE_LOCATION.getLocationId(), returnedLocation.getLocationId())
            Assertions.assertEquals(LocationTestConstants.updateLocationUpdatedName, returnedLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, returnedLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(returnedLocation.getLastUpdatedOn()))

        and: "the location is correct in the database"
            HttpResponse<Location> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.concat(returnedLocation.getLocationId()), Location)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Location databaseLocation = checkingDatabaseResponse.body()
            Assertions.assertEquals(LocationTestConstants.UPDATE_LOCATION.getClientId(), databaseLocation.getClientId())
            Assertions.assertEquals(LocationTestConstants.UPDATE_LOCATION.getLocationId(), databaseLocation.getLocationId())
            Assertions.assertEquals(LocationTestConstants.updateLocationUpdatedName, databaseLocation.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, databaseLocation.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseLocation.getLastUpdatedOn()))

    }
}