package com.ebsolutions.eventsadminservice.spec

import com.ebsolutions.eventsadminservice.constant.EventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.Event
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

@MicronautTest
class EventSpec extends Specification {
    @Inject
    private HttpClient httpClient

    // Get a event
    def "Get a event: URL Client id exists: Event Exists"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.getEventClientId)
                    .append("/events/")
                    .append(EventTestConstants.getEventId)
                    .toString()

        and: "a event exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a event"
            HttpResponse<Event> response = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct event is returned"
            Event event = response.body()
            Assertions.assertEquals(EventTestConstants.getEventClientId, event.getClientId())
            Assertions.assertEquals("Get Mock Event Name", event.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, event.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, event.getLastUpdatedOn()))
    }

    def "Get a event: URL Client id exists: Event does not exist"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.getEventClientId)
                    .append("/events/")
                    .append(TestConstants.nonExistentEventId)
                    .toString()

        and: "a event does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve a event"
            HttpResponse<Event> response = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    // Get all events
    def "Get all events: URL Client id exists: Events exist for client"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.getAllEventsClientId)
                    .append("/events/")
                    .toString()

        and: "events exist in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve events for a client"
            HttpRequest httpRequest = HttpRequest.GET(eventsUrl)

            HttpResponse<List<Event>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Event.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct events are returned"
            List<Event> events = response.body()
            Event firstEvent = events.get(0)
            Event secondEvent = events.get(1)

            Assertions.assertEquals(EventTestConstants.getAllEventsClientId, firstEvent.getClientId())
            Assertions.assertEquals(EventTestConstants.getAllEventsIdOne, firstEvent.getEventId())
            Assertions.assertEquals("Get All Events Mock Event Name 1", firstEvent.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, firstEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, firstEvent.getLastUpdatedOn()))

            Assertions.assertEquals(EventTestConstants.getAllEventsClientId, secondEvent.getClientId())
            Assertions.assertEquals(EventTestConstants.getAllEventsIdTwo, secondEvent.getEventId())
            Assertions.assertEquals("Get All Events Mock Event Name 2", secondEvent.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, secondEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, secondEvent.getLastUpdatedOn()))
    }

    def "Get all events: URL Client id exists: No events exist for client"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(TestConstants.noChildrenClientId)
                    .append("/events/")
                    .toString()

        and: "no events exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve events for a client"
            HttpRequest httpRequest = HttpRequest.GET(eventsUrl)

            HttpResponse<List<Event>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(Event.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    // Delete a event
    def "Delete a event: URL Client id exists: Delete event is successful"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.deleteEventClientId)
                    .append("/events/")
                    .append(EventTestConstants.deleteEventId)
                    .toString()

        and: "a event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

        when: "a request is made to delete the event for a client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(eventsUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the event no longer exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> finalResponse = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    // Create a event
    def "Create a event: URL Client id exists: Create fails given event client id is blank"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.createEventClientId)
                    .append("/events/")
                    .toString()

        and: "the event's client id is blank"
            Event newEvent = Event.builder()
                    .clientId("")
                    .name("Create Mock Event Name")
                    .build()

        when: "a request is made to create a event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a event: URL Client id exists: Create fails given event client id and URL client id do not match"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.createEventClientId)
                    .append("/events/")
                    .toString()

        and: "the event's client id does not match the URL client id"
            Event newEvent = Event.builder()
                    .clientId("not-the-url-client-id")
                    .name("Create Mock Event Name")
                    .build()

        when: "a request is made to create a event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a event: URL Client id exists: Create event is successful"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.createEventClientId)
                    .append("/events/")
                    .toString()

        and: "the event is valid"
            Event newEvent = Event.builder()
                    .clientId(EventTestConstants.createEventClientId)
                    .name("Create Mock Event Name")
                    .build()

        when: "a request is made to create a event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEvent)
            HttpResponse<Event> response = httpClient.toBlocking()
                    .exchange(httpRequest, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct event is returned"
            Event event = response.body()
            Assertions.assertEquals(EventTestConstants.createEventClientId, event.getClientId())
            Assertions.assertNotNull(event.getEventId())
            Assertions.assertEquals("Create Mock Event Name", event.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(event.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(event.getLastUpdatedOn()))

        and: "the new event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> checkingResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(event.getEventId()), Event)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingResponse.code())
    }

    // Update a event
    def "Update a event: URL Client id exists: Update fails given event client id is blank"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.updateEventClientId)
                    .append("/events/")
                    .toString()

        and: "a event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.updateEventId), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(EventTestConstants.updateEventClientId, initResponse.body().getClientId())

        and: "the event's client id is blank"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.setClientId("")

        when: "a request is made to update a event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a event: URL Client id exists: Update fails given event client id and URL client id do not match"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.updateEventClientId)
                    .append("/events/")
                    .toString()

        and: "a event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.updateEventId), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(EventTestConstants.updateEventClientId, initResponse.body().getClientId())

        and: "the event's client id does not match the URL client id"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.setClientId("not-the-url-client-id")

        when: "a request is made to update a event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a event: URL Client id exists: Update fails given create date is empty"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.updateEventClientId)
                    .append("/events/")
                    .toString()

        and: "a event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.updateEventId), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initResponse.body().getCreatedOn()))

        and: "the event's create date is empty"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.createdOn(null)

        when: "a request is made to update a event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a event: URL Client id exists: Update fails given create date is after 'now'"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.updateEventClientId)
                    .append("/events/")
                    .toString()

        and: "a event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.updateEventId), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initResponse.body().getLastUpdatedOn()))

        and: "the event's create date is after the current date and time"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.createdOn(null)

        when: "a request is made to update a event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a event: URL Client id exists: Update event is successful"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.updateEventClientId)
                    .append("/events/")
                    .toString()

        and: "a event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.updateEventId), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.createdOn, initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.lastUpdatedOn, initResponse.body().getLastUpdatedOn()))

        and: "the event is valid"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.name("New Updated Event Name")

        when: "a request is made to update a event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            HttpResponse<Event> response = httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated event is returned"
            Event returnedEvent = response.body()
            Assertions.assertEquals(EventTestConstants.updateEventClientId, returnedEvent.getClientId())
            Assertions.assertEquals(EventTestConstants.updateEventId, returnedEvent.getEventId())

        and: "the event is correct in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> checkingResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(returnedEvent.getEventId()), Event)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingResponse.code())
            Assertions.assertEquals("New Updated Event Name", updatedEvent.getName())
    }
}