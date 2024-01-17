package com.ebsolutions.eventsadminservice.spec

import com.ebsolutions.eventsadminservice.constant.EventScheduleTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.EventSchedule
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
class EventScheduleSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Get an event schedule: URL Client id exists: Event Schedule Exists"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .append(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getEventScheduleId())
                    .toString()

        and: "an event schedule exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve an event schedule"
            HttpResponse<EventSchedule> response = httpClient.toBlocking()
                    .exchange(eventsUrl, EventSchedule)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct event schedule is returned"
            EventSchedule eventSchedule = response.body()
            Assertions.assertEquals(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getClientId(), eventSchedule.getClientId())
            Assertions.assertEquals(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertEquals(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getName(), eventSchedule.getName())
            Assertions.assertEquals(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getDescription(), eventSchedule.getDescription())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getCreatedOn(), eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getLastUpdatedOn(), eventSchedule.getLastUpdatedOn()))
    }

    def "Get an event schedule: URL Client id exists: Event Schedule does not exist"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.GET_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .append(TestConstants.nonExistentEventId)
                    .toString()

        and: "an event schedule does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve an event schedule"
            HttpResponse<EventSchedule> response = httpClient.toBlocking()
                    .exchange(eventsUrl, EventSchedule)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Get all event schedules: URL Client id exists: Events exist for client"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.getAllEventSchedulesClientId)
                    .append("/event-schedules/")
                    .toString()

        and: "event schedules exist in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve event schedules for a client"
            HttpRequest httpRequest = HttpRequest.GET(eventsUrl)

            HttpResponse<List<EventSchedule>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(EventSchedule.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct event schedules are returned"
            List<EventSchedule> events = response.body()
            EventSchedule firstEventSchedule = events.get(0)
            EventSchedule secondEventSchedule = events.get(1)

            Assertions.assertEquals(EventScheduleTestConstants.getAllEventSchedulesClientId, firstEventSchedule.getClientId())
            Assertions.assertEquals(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_ONE.getEventScheduleId(), firstEventSchedule.getEventScheduleId())
            Assertions.assertEquals(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_ONE.getName(), firstEventSchedule.getName())
            Assertions.assertEquals(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_ONE.getDescription(), firstEventSchedule.getDescription())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_ONE.getCreatedOn(), firstEventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_ONE.getLastUpdatedOn(), firstEventSchedule.getLastUpdatedOn()))

            Assertions.assertEquals(EventScheduleTestConstants.getAllEventSchedulesClientId, secondEventSchedule.getClientId())
            Assertions.assertEquals(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_TWO.getEventScheduleId(), secondEventSchedule.getEventScheduleId())
            Assertions.assertEquals(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_TWO.getName(), secondEventSchedule.getName())
            Assertions.assertEquals(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_TWO.getDescription(), secondEventSchedule.getDescription())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_TWO.getCreatedOn(), secondEventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.GET_ALL_EVENT_SCHEDULES_EVENT_SCHEDULE_TWO.getLastUpdatedOn(), secondEventSchedule.getLastUpdatedOn()))

    }

    def "Get all event schedules: URL Client id exists: No events exist for client"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(TestConstants.noChildrenClientId)
                    .append("/event-schedules/")
                    .toString()

        and: "no event schedules exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve event schedules for a client"
            HttpRequest httpRequest = HttpRequest.GET(eventsUrl)

            HttpResponse<List<EventSchedule>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(EventSchedule.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Delete an event schedule: URL Client id exists: Delete event is successful"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.DELETE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .append(EventScheduleTestConstants.DELETE_EVENT_SCHEDULE.getEventScheduleId())
                    .toString()

        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<EventSchedule> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl, EventSchedule)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

        when: "a request is made to delete the event for a client"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(eventsUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the event no longer exists in the database"
            HttpResponse<EventSchedule> finalResponse = httpClient.toBlocking()
                    .exchange(eventsUrl, EventSchedule)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    def "Create an event: URL Client id exists: Create fails given event client id is blank"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "the event's client id is blank"
            EventSchedule newEventSchedule = EventSchedule.builder()
                    .clientId("")
                    .name("Create Mock Event ScheduleName")
                    .build()

        when: "a request is made to create an event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEventSchedule)
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
                    .append(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "the event's client id does not match the URL client id"
            EventSchedule newEventSchedule = EventSchedule.builder()
                    .clientId("not-the-url-client-id")
                    .name("Create Mock Event Name")
                    .build()

        when: "a request is made to create an event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEventSchedule)
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
                    .append(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "the event is valid"
            EventSchedule newEventSchedule = EventSchedule.builder()
                    .clientId(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getClientId())
                    .name(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getName())
                    .description(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getDescription())
                    .build()

        when: "a request is made to create an event for a client"
            HttpRequest httpRequest = HttpRequest.POST(eventsUrl, newEventSchedule)
            HttpResponse<EventSchedule> response = httpClient.toBlocking()
                    .exchange(httpRequest, EventSchedule)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct event is returned"
            EventSchedule eventSchedule = response.body()
            Assertions.assertEquals(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getClientId(), eventSchedule.getClientId())
            Assertions.assertNotNull(eventSchedule.getEventScheduleId())
            Assertions.assertEquals(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getName(), eventSchedule.getName())
            Assertions.assertEquals(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getDescription(), eventSchedule.getDescription())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getLastUpdatedOn()))

        and: "the new event is correct in the database"
            HttpResponse<EventSchedule> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(eventSchedule.getEventScheduleId()), EventSchedule)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            EventSchedule databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(eventSchedule.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getName(), databaseEvent.getName())
            Assertions.assertEquals(EventScheduleTestConstants.CREATE_EVENT_SCHEDULE.getDescription(), databaseEvent.getDescription())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))

    }

    def "Update an event: URL Client id exists: Update fails given event client id is blank"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<EventSchedule> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getEventScheduleId()), EventSchedule)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId(), initResponse.body().getClientId())

        and: "the event's client id is blank"
            EventSchedule updatedEventSchedule = CopyObjectUtil.eventSchedule(initResponse.body())
            updatedEventSchedule.setClientId("")

        when: "a request is made to update an event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEventSchedule)
            httpClient.toBlocking().exchange(httpRequest, EventSchedule)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event schedule: URL Client id exists: Update fails given event client id and URL client id do not match"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "an event schedule exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<EventSchedule> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getEventScheduleId()), EventSchedule)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertEquals(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId(), initResponse.body().getClientId())

        and: "the event schedule's client id does not match the URL client id"
            EventSchedule updatedEventSchedule = CopyObjectUtil.eventSchedule(initResponse.body())
            updatedEventSchedule.setClientId("not-the-url-client-id")

        when: "a request is made to update an event schedule for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEventSchedule)
            httpClient.toBlocking().exchange(httpRequest, EventSchedule)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event schedule: URL Client id exists: Update fails given create date is empty"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "an event schedule exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<EventSchedule> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getEventScheduleId()), EventSchedule)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getCreatedOn(), initResponse.body().getCreatedOn()))

        and: "the event schedule's create date is empty"
            EventSchedule updatedEventSchedule = CopyObjectUtil.eventSchedule(initResponse.body())
            updatedEventSchedule.setCreatedOn(null)

        when: "a request is made to update an event schedule for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEventSchedule)
            httpClient.toBlocking().exchange(httpRequest, EventSchedule)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event schedule: URL Client id exists: Update fails given create date is after 'now'"() {
        given: "the client id is in the url"
            String eventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "an event schedule exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<EventSchedule> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getEventScheduleId()), EventSchedule)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "the event schedule's create date is after the current date and time"
            EventSchedule updatedEvent = CopyObjectUtil.eventSchedule(initResponse.body())
            updatedEvent.setCreatedOn(LocalDateTime.now().plusMonths(1))

        when: "a request is made to update an event for a client"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, EventSchedule)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update an event schedule: URL Client id exists: Update event schedule is successful"() {
        given: "the client id is in the url"
            String eventSchedulesUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId())
                    .append("/event-schedules/")
                    .toString()

        and: "an event schedule exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<EventSchedule> initResponse = httpClient.toBlocking()
                    .exchange(eventSchedulesUrl.concat(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getEventScheduleId()), EventSchedule)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "a valid update is made to event schedule"
            EventSchedule updatedEventSchedule = CopyObjectUtil.eventSchedule(initResponse.body())
            updatedEventSchedule.setName(EventScheduleTestConstants.updateEventScheduleUpdatedName)
            updatedEventSchedule.setDescription(EventScheduleTestConstants.updateEventScheduleUpdatedDescription)
            updatedEventSchedule.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to with the updated event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(eventSchedulesUrl), updatedEventSchedule)
            HttpResponse<EventSchedule> response = httpClient.toBlocking().exchange(httpRequest, EventSchedule)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated event schedule is returned in the response"
            EventSchedule returnedEventSchedule = response.body()
            Assertions.assertEquals(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId(), returnedEventSchedule.getClientId())
            Assertions.assertEquals(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getEventScheduleId(), returnedEventSchedule.getEventScheduleId())
            Assertions.assertEquals(EventScheduleTestConstants.updateEventScheduleUpdatedName, returnedEventSchedule.getName())
            Assertions.assertEquals(EventScheduleTestConstants.updateEventScheduleUpdatedDescription, returnedEventSchedule.getDescription())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, returnedEventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(returnedEventSchedule.getLastUpdatedOn()))

        and: "the event schedule is correct in the database"
            HttpResponse<EventSchedule> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(eventSchedulesUrl.concat(returnedEventSchedule.getEventScheduleId()), EventSchedule)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            EventSchedule databaseEventSchedule = checkingDatabaseResponse.body()
            Assertions.assertEquals(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getClientId(), databaseEventSchedule.getClientId())
            Assertions.assertEquals(EventScheduleTestConstants.UPDATE_EVENT_SCHEDULE.getEventScheduleId(), databaseEventSchedule.getEventScheduleId())
            Assertions.assertEquals(EventScheduleTestConstants.updateEventScheduleUpdatedName, databaseEventSchedule.getName())
            Assertions.assertEquals(EventScheduleTestConstants.updateEventScheduleUpdatedDescription, databaseEventSchedule.getDescription())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, databaseEventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEventSchedule.getLastUpdatedOn()))

    }
}