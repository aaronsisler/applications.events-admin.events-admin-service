package com.ebsolutions.eventsadminservice.spec

import com.ebsolutions.eventsadminservice.constant.LocationTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.Location
import com.ebsolutions.eventsadminservice.util.CopyObjectUtil
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import spock.lang.Specification

@MicronautTest
class NewLocationSpec extends Specification {
    @Inject
    private HttpClient httpClient

    // Get a location
    // TODO
    def "Get a location: URL Client id is not present"() {
        given: "the client id is not in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append("")
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        when: "a request is made to retrieve a location"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Get a location: URL Client id exists: Location Exists"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.getLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "a location exists in the database"
            // Data seeded from Database init scripts
        when: "a request is made to retrieve a location"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())
        and: "the correct location is returned"
            Location location = response.body()
            Assertions.assertEquals(LocationTestConstants.getLocationClientId, location.getClientId())
            Assertions.assertEquals("Get Mock Location Name", location.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, location.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, location.getLastUpdatedOn()))
    }

    // TODO
    def "Get a location: URL Client id exists: Location does not exist"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.getLocationClientId)
                    .append("/locations/")
                    .append(TestConstants.nonExistentLocationId)
        and: "a location does not exist in the database"
        when: "a request is made to retrieve a location"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    // Get all locations
    // TODO
    def "Get all locations: URL Client id is not present"() {
        given: "the client id is not in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append("")
                    .append("/locations/")
        when: "a request is made to retrieve locations for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Get all locations: URL Client id exists: Locations exist for client"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.getAllLocationsClientId)
                    .append("/locations/")
        and: "locations exist in the database"
            // Data seeded from Database init scripts
        when: "a request is made to retrieve locations for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
        and: "the correct locations are returned"
    }

    // TODO
    def "Get all locations: URL Client id exists: No locations exist for client"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.getAllLocationsClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "no locations exist in the database"
        when: "a request is made to retrieve locations for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    // Delete a location
    // TODO
    def "Delete a location: URL Client id is not present"() {
        given: "the client id is not in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append("")
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        when: "a request is made to delete the location for a client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(locationsUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Delete a location: URL Client id exists: Delete location is successful"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.deleteLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "a location exists in the database"
            // Data seeded from Database init scripts
        when: "a request is made to delete the location for a client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(locationsUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    // Create a location
    // TODO
    def "Create a location: URL Client id is not present"() {
        given: "the client id is not in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append("")
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        when: "a request is made to create a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Create a location: URL Client id exists: Create fails given location client id is blank"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.createLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "the location's client id is blank"
        when: "a request is made to create a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Create a location: URL Client id exists: Create fails given location client id and URL client id do not match"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.createLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "the location's client id does not match the URL client id"
        when: "a request is made to create a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Create a location: URL Client id exists: Create location is successful"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.createLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "the location is valid"
            Location newLocation = Location.builder()
                    .clientId(LocationTestConstants.createLocationClientId)
                    .name("Create Mock Location Name").build()
        when: "a request is made to create a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())
        and: "the correct location is returned"
            Location location = response.body()
            Assertions.assertEquals(LocationTestConstants.createLocationClientId, location.getClientId())
            Assertions.assertNotNull(location.getLocationId())
            Assertions.assertEquals("Create Mock Client Name", location.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(location.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(location.getLastUpdatedOn()))
    }

    // Update a location
    // TODO
    def "Update a location: URL Client id is not present"() {
        given: "the client id is not in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append("")
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        when: "a request is made to update a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Update a location: URL Client id exists: Update fails given location client id is blank"() {
        given: "the client id is in the url"
            StringBuffer locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.updateLocationClientId)
                    .append("/locations/")
        and: "a location exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Location> initResponse = httpClient.toBlocking()
                    .exchange(locationsUrl.append(LocationTestConstants.updateLocationId).toString(), Location)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

        and: "the location's client id is blank"
            Location updatedLocation = CopyObjectUtil.location(initResponse.body())
            updatedLocation.setClientId("")
        when: "a request is made to update a location for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(locationsUrl.toString()), updatedLocation)
            HttpResponse<Location> response = httpClient.toBlocking().exchange(httpRequest, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Update a location: URL Client id exists: Update fails given location client id and URL client id do not match"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.updateLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "the location's client id does not match the URL client id"
        when: "a request is made to update a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Update a location: URL Client id exists: Update fails given create date is empty"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.updateLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "the location's create date is empty"
        when: "a request is made to update a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Update a location: URL Client id exists: Update fails given create date is after 'now'"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.updateLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "the location's create date is after the current date and time"
        when: "a request is made to update a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code())
    }

    // TODO
    def "Update a location: URL Client id exists: Update location is successful"() {
        given: "the client id is in the url"
            String locationsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(LocationTestConstants.updateLocationClientId)
                    .append("/locations/")
                    .append(LocationTestConstants.getLocationId)
        and: "a location exists in the database"
            // Data seeded from Database init scripts
        and: "the location is valid"
        when: "a request is made to update a location for a client"
            HttpResponse<Location> response = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)
        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())
        and: "the updated location is returned"
    }
}