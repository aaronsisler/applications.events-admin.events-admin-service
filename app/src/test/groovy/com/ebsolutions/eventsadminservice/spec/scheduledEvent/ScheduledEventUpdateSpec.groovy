package com.ebsolutions.eventsadminservice.spec.scheduledEvent

import com.ebsolutions.eventsadminservice.constant.EventTestConstants
import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.Event
import com.ebsolutions.eventsadminservice.model.ScheduledEvent
import com.ebsolutions.eventsadminservice.model.ScheduledEventDay
import com.ebsolutions.eventsadminservice.model.ScheduledEventInterval
import com.ebsolutions.eventsadminservice.util.CopyObjectUtil
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
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
class ScheduledEventUpdateSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update fails given scheduled event's event schedule id is blank"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's scheduled event date is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setScheduledEventDate(null)

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's scheduled event date is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDate(null)

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.DAILY)

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKDAYS)

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO
        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDay(ScheduledEventDay.MON)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKENDS)

        when: "a request is made to update a scheduled event for an event schedule"
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "an event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<Event> initResponse = httpClient.toBlocking()
                    .exchange(eventsUrl.concat(EventTestConstants.UPDATE_EVENT.getEventId()), Event)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(EventTestConstants.UPDATE_EVENT.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

            // TODO
        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDay(null)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKLY)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update fails given created on date is empty"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()


            // TODO
        and: "a scheduled event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<ScheduledEvent> initResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getScheduledEventId()), ScheduledEvent)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getCreatedOn(), initResponse.body().getCreatedOn()))

        and: "the scheduled event's created on date is empty"
            ScheduledEvent updatedEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedEvent.createdOn(null)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(scheduledEventsUrl), updatedEvent)
            httpClient.toBlocking().exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update fails given created on date is after 'now'"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "a scheduled event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<ScheduledEvent> initResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getScheduledEventId()), ScheduledEvent)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        and: "the scheduled event's created on date is after the current date and time"
            ScheduledEvent updatedEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedEvent.setCreatedOn(LocalDateTime.now().plusMonths(1))

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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

            // TODO
        and: "a scheduled event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<ScheduledEvent> initResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getScheduledEventId()), ScheduledEvent)
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getCreatedOn(), initResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))


        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, updatedEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated scheduled event is returned in the response"
            ScheduledEvent scheduledEvent = response.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), scheduledEvent.getEventScheduleId())
            Assertions.assertNotNull(scheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), scheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), scheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), scheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(0), scheduledEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(1), scheduledEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName(), scheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getDescription(), scheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCategory(), scheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), scheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventInterval(), scheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDay(), scheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCost(), scheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDate(), scheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getStartTime(), scheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEndTime(), scheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getLastUpdatedOn()))

        and: "the scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(scheduledEvent.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName(), databaseEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getDescription(), databaseEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCategory(), databaseEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), databaseEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventInterval(), databaseEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDay(), databaseEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCost(), databaseEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDate(), databaseEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getStartTime(), databaseEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEndTime(), databaseEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Standard Scheduled Event: Update scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .name(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated scheduled event is returned in the response"
            ScheduledEvent scheduledEvent = response.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), scheduledEvent.getEventScheduleId())
            Assertions.assertNotNull(scheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), scheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), scheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), scheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(0), scheduledEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(1), scheduledEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName(), scheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getDescription(), scheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCategory(), scheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), scheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventInterval(), scheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDay(), scheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCost(), scheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDate(), scheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getStartTime(), scheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEndTime(), scheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getLastUpdatedOn()))

        and: "the scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(scheduledEvent.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName(), databaseEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getDescription(), databaseEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCategory(), databaseEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), databaseEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventInterval(), databaseEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDay(), databaseEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCost(), databaseEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDate(), databaseEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getStartTime(), databaseEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEndTime(), databaseEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newEvent = ScheduledEvent.builder()
                    .eventScheduleId(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING.getEventScheduleId())
                    .name(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING.getName())
                    .startTime(ScheduledEventTestConstants.startTime)
                    .endTime(ScheduledEventTestConstants.endTime)
                    .build()

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated scheduled event is returned in the response"
            ScheduledEvent scheduledEvent = response.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), scheduledEvent.getEventScheduleId())
            Assertions.assertNotNull(scheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), scheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), scheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), scheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(0), scheduledEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(1), scheduledEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName(), scheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getDescription(), scheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCategory(), scheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), scheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventInterval(), scheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDay(), scheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCost(), scheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDate(), scheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getStartTime(), scheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEndTime(), scheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getLastUpdatedOn()))

        and: "the scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(scheduledEvent.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(0), databaseEvent.getOrganizerIds().get(0))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerIds().get(1), databaseEvent.getOrganizerIds().get(1))
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName(), databaseEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getDescription(), databaseEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCategory(), databaseEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), databaseEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventInterval(), databaseEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDay(), databaseEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCost(), databaseEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDate(), databaseEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getStartTime(), databaseEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEndTime(), databaseEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }
}