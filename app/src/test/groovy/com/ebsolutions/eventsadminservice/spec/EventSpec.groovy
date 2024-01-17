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

import java.time.LocalDateTime

@MicronautTest
class EventSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Get an event: URL Client id exists: Event Exists"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.GET_EVENT.getClientId())
                    .append("/events/")
                    .append(EventTestConstants.GET_EVENT.getEventId())
                    .toString()

        and: "an event exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve an event"
            HttpResponse<Event> response = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct event is returned"
            Event event = response.body()
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getClientId(), event.getClientId())
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getEventId(), event.getEventId())
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getLocationId(), event.getLocationId())
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getOrganizerIds().get(0), event.getOrganizerIds().get(0))
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getOrganizerIds().get(1), event.getOrganizerIds().get(1))
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getName(), event.getName())
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getDescription(), event.getDescription())
            Assertions.assertEquals(EventTestConstants.GET_EVENT.getCategory(), event.getCategory())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.GET_EVENT.getCreatedOn(), event.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.GET_EVENT.getLastUpdatedOn(), event.getLastUpdatedOn()))
    }

    def "Get an event: URL Client id exists: Event does not exist"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.GET_EVENT.getClientId())
                    .append("/events/")
                    .append(TestConstants.nonExistentEventId)
                    .toString()

        and: "an event does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve an event"
            HttpResponse<Event> response = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

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
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_ONE.getEventId(), firstEvent.getEventId())
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_ONE.getLocationId(), firstEvent.getLocationId())
            Assertions.assertEquals(EventTestConstants.getAllEventsOrganizerOneId, firstEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(EventTestConstants.getAllEventsOrganizerTwoId, firstEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_ONE.getName(), firstEvent.getName())
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_ONE.getDescription(), firstEvent.getDescription())
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_ONE.getCategory(), firstEvent.getCategory())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.GET_ALL_EVENTS_EVENT_ONE.getCreatedOn(), firstEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.GET_ALL_EVENTS_EVENT_ONE.getLastUpdatedOn(), firstEvent.getLastUpdatedOn()))

            Assertions.assertEquals(EventTestConstants.getAllEventsClientId, secondEvent.getClientId())
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_TWO.getEventId(), secondEvent.getEventId())
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_TWO.getLocationId(), secondEvent.getLocationId())
            Assertions.assertEquals(EventTestConstants.getAllEventsOrganizerOneId, secondEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(EventTestConstants.getAllEventsOrganizerTwoId, secondEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_TWO.getName(), secondEvent.getName())
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_TWO.getDescription(), secondEvent.getDescription())
            Assertions.assertEquals(EventTestConstants.GET_ALL_EVENTS_EVENT_TWO.getCategory(), secondEvent.getCategory())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.GET_ALL_EVENTS_EVENT_TWO.getCreatedOn(), secondEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.GET_ALL_EVENTS_EVENT_TWO.getLastUpdatedOn(), secondEvent.getLastUpdatedOn()))

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

    def "Delete an event: URL Client id exists: Delete event is successful"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.DELETE_EVENT.getClientId())
                    .append("/events/")
                    .append(EventTestConstants.DELETE_EVENT.getEventId())
                    .toString()

        and: "an event exists in the database"
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
            HttpResponse<Event> finalResponse = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    def "Create an event: URL Client id exists: Create fails given event client id is blank"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.CREATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "the event's client id is blank"
            Event newEvent = Event.builder()
                    .clientId("")
                    .name("Create Mock Event Name")
                    .build()

        when: "a request is made to create an event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create an event: URL Client id exists: Create fails given event client id and URL client id do not match"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.CREATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "the event's client id does not match the URL client id"
            Event newEvent = Event.builder()
                    .clientId("not-the-url-client-id")
                    .name("Create Mock Event Name")
                    .build()

        when: "a request is made to create an event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create an event: URL Client id exists: Create event is successful"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.CREATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "the event is valid"
            Event newEvent = Event.builder()
                    .clientId(EventTestConstants.CREATE_EVENT.getClientId())
                    .locationId(EventTestConstants.CREATE_EVENT.getLocationId())
                    .organizerIds(EventTestConstants.CREATE_EVENT.getOrganizerIds())
                    .name(EventTestConstants.CREATE_EVENT.getName())
                    .description(EventTestConstants.CREATE_EVENT.getDescription())
                    .category(EventTestConstants.CREATE_EVENT.getCategory())
                    .build()

        when: "a request is made to create an event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEvent)
            HttpResponse<Event> response = httpClient.toBlocking()
                    .exchange(httpRequest, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct event is returned"
            Event event = response.body()
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getClientId(), event.getClientId())
            Assertions.assertNotNull(event.getEventId())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getLocationId(), event.getLocationId())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getOrganizerIds().get(0), event.getOrganizerIds().get(0))
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getOrganizerIds().get(1), event.getOrganizerIds().get(1))
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getName(), event.getName())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getDescription(), event.getDescription())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getCategory(), event.getCategory())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(event.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(event.getLastUpdatedOn()))

        and: "the new event is correct in the database"
            HttpResponse<Event> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(event.getEventId()), Event)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Event databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(event.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getName(), databaseEvent.getName())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getDescription(), databaseEvent.getDescription())
            Assertions.assertEquals(EventTestConstants.CREATE_EVENT.getCategory(), databaseEvent.getCategory())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))

    }

    def "Update an event: URL Client id exists: Update fails given event client id is blank"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.UPDATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(EventTestConstants.UPDATE_EVENT.getClientId(), initResponse.body().getClientId())

        and: "the event's client id is blank"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.setClientId("")

        when: "a request is made to update an event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event: URL Client id exists: Update fails given event client id and URL client id do not match"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.UPDATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(EventTestConstants.UPDATE_EVENT.getClientId(), initResponse.body().getClientId())

        and: "the event's client id does not match the URL client id"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.setClientId("not-the-url-client-id")

        when: "a request is made to update an event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event: URL Client id exists: Update fails given create date is empty"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.UPDATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))

        and: "the event's create date is empty"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.setCreatedOn(null)

        when: "a request is made to update an event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event: URL Client id exists: Update fails given create date is after 'now'"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.UPDATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "the event's create date is after the current date and time"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.setCreatedOn(LocalDateTime.now().plusMonths(1))

        when: "a request is made to update an event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event: URL Client id exists: Update event is successful"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventTestConstants.UPDATE_EVENT.getClientId())
                    .append("/events/")
                    .toString()

        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "a valid update is made to event"
            Event updatedEvent = CopyObjectUtil.event(initResponse.body())
            updatedEvent.getOrganizerIds().remove(1)
            updatedEvent.getOrganizerIds().add(EventTestConstants.updateEventUpdatedOrganizerThreeId)
            updatedEvent.setLocationId(EventTestConstants.updateEventUpdatedLocationId)
            updatedEvent.setName(EventTestConstants.updateEventUpdatedName)
            updatedEvent.setDescription(EventTestConstants.updateEventUpdatedDescription)
            updatedEvent.setCategory(EventTestConstants.updateEventUpdatedCategory)
            updatedEvent.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to with the updated event"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            HttpResponse<Event> response = httpClient.toBlocking().exchange(httpRequest, Event)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated event is returned in the response"
            Event returnedEvent = response.body()
            Assertions.assertEquals(EventTestConstants.UPDATE_EVENT.getClientId(), returnedEvent.getClientId())
            Assertions.assertEquals(EventTestConstants.UPDATE_EVENT.getEventId(), returnedEvent.getEventId())
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedLocationId, returnedEvent.getLocationId())
            Assertions.assertEquals(EventTestConstants.updateEventOrganizerOneId, returnedEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedOrganizerThreeId, returnedEvent.getOrganizerIds().get(1))
            Assertions.assertFalse(returnedEvent.getOrganizerIds().contains(EventTestConstants.updateEventOrganizerTwoId))
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedName, returnedEvent.getName())
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedDescription, returnedEvent.getDescription())
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedCategory, returnedEvent.getCategory())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, returnedEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(returnedEvent.getLastUpdatedOn()))

        and: "the event is correct in the database"
            HttpResponse<Event> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(returnedEvent.getEventId()), Event)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            Event databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(EventTestConstants.UPDATE_EVENT.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(EventTestConstants.UPDATE_EVENT.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedLocationId, databaseEvent.getLocationId())
            Assertions.assertEquals(EventTestConstants.updateEventOrganizerOneId, databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedOrganizerThreeId, databaseEvent.getOrganizerIds().get(1))
            Assertions.assertFalse(databaseEvent.getOrganizerIds().contains(EventTestConstants.updateEventOrganizerTwoId))
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedName, databaseEvent.getName())
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedDescription, databaseEvent.getDescription())
            Assertions.assertEquals(EventTestConstants.updateEventUpdatedCategory, databaseEvent.getCategory())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))

    }
}