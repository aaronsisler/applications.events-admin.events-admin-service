package com.ebsolutions.eventsadminservice.spec.scheduledEvent

import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.ScheduledEvent
import com.ebsolutions.eventsadminservice.model.ScheduledEventDay
import com.ebsolutions.eventsadminservice.model.ScheduledEventInterval
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
class ScheduledEventGetSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Get a scheduled event: URL Client id exists: Get Single Scheduled Event: Scheduled event exists"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getScheduledEventId())
                    .toString()

        and: "a scheduled event exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a scheduled event"
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getScheduledEventId(), eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getLocationId(), eventSchedule.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getOrganizerIds().get(0), eventSchedule.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getOrganizerIds().get(1), eventSchedule.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getCreatedOn(), eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getLastUpdatedOn(), eventSchedule.getLastUpdatedOn()))
    }

    def "Get a scheduled event: URL Client id exists: Get Reoccurring Standard Scheduled Event: Scheduled event exists"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getScheduledEventId())
                    .toString()

        and: "a scheduled event exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a scheduled event"
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getScheduledEventId(), eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getLocationId(), eventSchedule.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getOrganizerIds().get(0), eventSchedule.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getOrganizerIds().get(1), eventSchedule.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getCreatedOn(), eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getLastUpdatedOn(), eventSchedule.getLastUpdatedOn()))
    }

    def "Get a scheduled event: URL Client id exists: Get Reoccurring Weekly Scheduled Event: Scheduled event exists"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getScheduledEventId())
                    .toString()

        and: "a scheduled event exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a scheduled event"
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getScheduledEventId(), eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getLocationId(), eventSchedule.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getOrganizerIds().get(0), eventSchedule.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getOrganizerIds().get(1), eventSchedule.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getCreatedOn(), eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getLastUpdatedOn(), eventSchedule.getLastUpdatedOn()))
    }

    def "Get a scheduled event: URL Event Schedule id exists: Scheduled event does not exist"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(TestConstants.nonExistentEventScheduleId)
                    .toString()

        and: "a scheduled event does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve a scheduled event"
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Get all scheduled events: URL Event Schedule id exists: Scheduled events exist for event schedule"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.getAllScheduledEventsEventScheduleId)
                    .append("/scheduled-events/")
                    .toString()

        and: "scheduled events exist in the database for an event schedule"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve scheduled events for an event schedule"
            HttpRequest httpRequest = HttpRequest.GET(scheduledEventsUrl)

            HttpResponse<List<ScheduledEvent>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(ScheduledEvent.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled events are returned"
            List<ScheduledEvent> events = response.body()
            ScheduledEvent firstScheduledEvent = events.get(0)
            ScheduledEvent secondScheduledEvent = events.get(1)

            Assertions.assertEquals(ScheduledEventTestConstants.getAllScheduledEventsEventScheduleId, firstScheduledEvent.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getScheduledEventId(), firstScheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getLocationId(), firstScheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getOrganizerIds().get(0), firstScheduledEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getOrganizerIds().get(1), firstScheduledEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getName(), firstScheduledEvent.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getCreatedOn(), firstScheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getLastUpdatedOn(), firstScheduledEvent.getLastUpdatedOn()))

            Assertions.assertEquals(ScheduledEventTestConstants.getAllScheduledEventsEventScheduleId, secondScheduledEvent.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getScheduledEventId(), secondScheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getLocationId(), secondScheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getOrganizerIds().get(0), secondScheduledEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getOrganizerIds().get(1), secondScheduledEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getName(), secondScheduledEvent.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getCreatedOn(), secondScheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getLastUpdatedOn(), secondScheduledEvent.getLastUpdatedOn()))

    }

    def "Get all scheduled events: URL Event Schedule id exists: No scheduled events exist for event schedule"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(TestConstants.noChildrenEventScheduleId)
                    .append("/scheduled-events/")
                    .toString()

        and: "no scheduled events exist in the database for an event schedule"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve scheduled events for an event schedule"
            HttpRequest httpRequest = HttpRequest.GET(scheduledEventsUrl)

            HttpResponse<List<ScheduledEvent>> response = httpClient.toBlocking()
                    .exchange(httpRequest, Argument.listOf(ScheduledEvent.class))

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }

    def "Delete a scheduled event: URL Event Schedule id exists: Delete scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.DELETE_SCHEDULED_EVENT.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.DELETE_SCHEDULED_EVENT.getScheduledEventId())
                    .toString()

        and: "a scheduled event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<ScheduledEvent> initResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())

        when: "a request is made to delete the scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.DELETE(URI.create(scheduledEventsUrl))
            HttpResponse response = httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())

        and: "the scheduled event no longer exists in the database"
            HttpResponse<ScheduledEvent> finalResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, finalResponse.code())
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Single Scheduled Event: Create fails given scheduled event's event schedule id is blank"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create fails given scheduled event's event schedule id is blank"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Weekly Scheduled Event: Create fails given scheduled event's event schedule id is blank"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Single Scheduled Event: Create fails given scheduled event's event schedule id and URL event schedule id do not match"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create fails given scheduled event's event schedule id and URL event schedule id do not match"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Weekly Scheduled Event: Create fails given scheduled event's event schedule id and URL event schedule id do not match"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Single Scheduled Event: Create fails given scheduled event's start time is after end time"() {
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create fails given scheduled event's start time is after end time"() {
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Weekly Scheduled Event: Create fails given scheduled event's start time is after end time"() {
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Single Scheduled Event: Create fails given scheduled event's scheduled event date is null"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event date is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setScheduledEventDate(null)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Scheduled Event: Create fails given scheduled event's scheduled event interval is null"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event date is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDate(null)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Scheduled Event: Create fails given scheduled event day is not null and scheduled event interval is daily"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.DAILY)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Scheduled Event: Create fails given scheduled event day is not null and scheduled event interval is weekdays"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKDAYS)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Scheduled Event: Create fails given scheduled event day is not null and scheduled event interval is weekends"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKENDS)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Scheduled Event: Create fails given scheduled event day is null and scheduled event interval is weekly"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(null)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKLY)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Single Scheduled Event: Create scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .name(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertNotNull(eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(eventSchedule.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(eventSchedule.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName(), databaseEvent.getName())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .name(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertNotNull(eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(eventSchedule.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(eventSchedule.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), databaseEvent.getName())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Weekly Scheduled Event: Create scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .name(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertNotNull(eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(eventSchedule.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(eventSchedule.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), databaseEvent.getName())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update fails given scheduled event's event schedule id is blank"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Standard Scheduled Event: Update fails given scheduled event's event schedule id is blank"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update fails given scheduled event's event schedule id is blank"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update fails given scheduled event's event schedule id and URL event schedule id do not match"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Standard Scheduled Event: Update fails given scheduled event's event schedule id and URL event schedule id do not match"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update fails given scheduled event's event schedule id and URL event schedule id do not match"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update fails given scheduled event's start time is after end time"() {
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Standard Scheduled Event: Update fails given scheduled event's start time is after end time"() {
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update fails given scheduled event's start time is after end time"() {
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update fails given scheduled event's scheduled event date is null"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event date is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setScheduledEventDate(null)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Scheduled Event: Update fails given scheduled event's scheduled event interval is null"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event date is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDate(null)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Scheduled Event: Update fails given scheduled event day is not null and scheduled event interval is daily"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.DAILY)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Scheduled Event: Update fails given scheduled event day is not null and scheduled event interval is weekdays"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKDAYS)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Scheduled Event: Update fails given scheduled event day is not null and scheduled event interval is weekends"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKENDS)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Scheduled Event: Update fails given scheduled event day is null and scheduled event interval is weekly"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setScheduledEventDay(null)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKLY)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update fails given create date is empty"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<ScheduledEvent> initResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT.getScheduledEventId()), ScheduledEvent)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))

        and: "the scheduled event's create date is empty"
            ScheduledEvent updatedEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedEvent.createdOn(null)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(scheduledEventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update fails given create date is after 'now'"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<ScheduledEvent> initResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT.getScheduledEventId()), ScheduledEvent)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "the scheduled event's create date is after the current date and time"
            ScheduledEvent updatedEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedEvent.createdOn(null)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(scheduledEventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .name(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertNotNull(eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(eventSchedule.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(eventSchedule.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName(), databaseEvent.getName())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Standard Scheduled Event: Update scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .name(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertNotNull(eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(eventSchedule.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(eventSchedule.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), databaseEvent.getName())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .name(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent eventSchedule = response.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertNotNull(eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), eventSchedule.getName())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(eventSchedule.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(eventSchedule.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(eventSchedule.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING.getName(), databaseEvent.getName())

            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }
}