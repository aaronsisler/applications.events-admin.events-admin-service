package com.ebsolutions.eventsadminservice.spec.scheduledEvent

import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.ScheduledEvent
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

import java.time.DayOfWeek

@MicronautTest
class ScheduledEventCreateSpec extends Specification {
    @Inject
    private HttpClient httpClient

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
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
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
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)
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
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
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
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Single Scheduled Event: Create fails given scheduled event's start time is after end time"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's start time is after the end time"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)
            newScheduledEvent.setStartTime(ScheduledEventTestConstants.endTime.plusMinutes(2))

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create fails given scheduled event's start time is after end time"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's start time is after the end time"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setStartTime(ScheduledEventTestConstants.endTime.plusMinutes(2))

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Weekly Scheduled Event: Create fails given scheduled event's start time is after end time"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's start time is after the end time"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)
            newScheduledEvent.setStartTime(ScheduledEventTestConstants.endTime.plusMinutes(2))

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
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
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event interval is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventInterval(null)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create fails given scheduled event day is not null and scheduled event interval is daily"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event day is not null and scheduled event interval is daily"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDay(DayOfWeek.MONDAY)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.DAILY)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create fails given scheduled event day is not null and scheduled event interval is weekdays"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event day is not null and scheduled event interval is weekdays"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDay(DayOfWeek.MONDAY)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKDAYS)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create fails given scheduled event day is not null and scheduled event interval is weekends"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event day is not null and scheduled event interval is weekends"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)
            newScheduledEvent.setScheduledEventDay(DayOfWeek.MONDAY)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKENDS)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Weekly Scheduled Event: Create fails given scheduled event day is null and scheduled event interval is weekly"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event's scheduled event day is null and scheduled event interval is weekly"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)
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
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent scheduledEvent = response.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), scheduledEvent.getEventScheduleId())
            Assertions.assertNotNull(scheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getClientId(), scheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventId(), scheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getLocationId(), scheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getOrganizerId(), scheduledEvent.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName(), scheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getDescription(), scheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getCategory(), scheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventType(), scheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventInterval(), scheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventDay(), scheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getCost(), scheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventDate(), scheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getStartTime(), scheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEndTime(), scheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(scheduledEvent.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getOrganizerId(), databaseEvent.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getName(), databaseEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getDescription(), databaseEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getCategory(), databaseEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventType(), databaseEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventInterval(), databaseEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventDay(), databaseEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getCost(), databaseEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getScheduledEventDate(), databaseEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getStartTime(), databaseEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEndTime(), databaseEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Standard Scheduled Event: Create scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent scheduledEvent = response.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId(), scheduledEvent.getEventScheduleId())
            Assertions.assertNotNull(scheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getClientId(), scheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventId(), scheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getLocationId(), scheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getOrganizerId(), scheduledEvent.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getName(), scheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getDescription(), scheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getCategory(), scheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventType(), scheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventInterval(), scheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventDay(), scheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getCost(), scheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventDate(), scheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getStartTime(), scheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEndTime(), scheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getLastUpdatedOn()))

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(scheduledEvent.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getOrganizerId(), databaseEvent.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getName(), databaseEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getDescription(), databaseEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getCategory(), databaseEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventType(), databaseEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventInterval(), databaseEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventDay(), databaseEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getCost(), databaseEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventDate(), databaseEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getStartTime(), databaseEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEndTime(), databaseEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(databaseEvent.getLastUpdatedOn()))
    }

    def "Create a scheduled event: URL Event Schedule id exists: Create Reoccurring Weekly Scheduled Event: Create scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "the scheduled event is valid"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)

        when: "a request is made to create a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.POST(scheduledEventsUrl, newScheduledEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct scheduled event is returned"
            ScheduledEvent scheduledEvent = response.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), scheduledEvent.getEventScheduleId())
            Assertions.assertNotNull(scheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), scheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), scheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), scheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerId(), scheduledEvent.getOrganizerId())
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

        and: "the new scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseEvent = checkingDatabaseResponse.body()

            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), databaseEvent.getEventScheduleId())
            Assertions.assertEquals(scheduledEvent.getScheduledEventId(), databaseEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), databaseEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), databaseEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), databaseEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerId(), databaseEvent.getOrganizerId())
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