package com.ebsolutions.eventsadminservice.spec.scheduledEvent

import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
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

@MicronautTest
class ScheduledEventCreateSpec {
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
}