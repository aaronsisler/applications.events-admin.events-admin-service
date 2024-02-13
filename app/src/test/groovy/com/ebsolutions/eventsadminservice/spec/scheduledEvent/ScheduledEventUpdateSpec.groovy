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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)

        and: "the scheduled event's event schedule id is blank"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setEventScheduleId("")

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)

        and: "the scheduled event's event schedule id does not match the URL event schedule id"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setEventScheduleId("not-the-url-event schedule-id")

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update fails given scheduled event's start time is after end time"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's start time is after end time"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setStartTime(ScheduledEventTestConstants.startTimeBeforeEndTime)
            newScheduledEvent.setEndTime(ScheduledEventTestConstants.endTimeAfterStartTime)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Standard Scheduled Event: Update fails given scheduled event's start time is after end time"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's start time is after end time"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setStartTime(ScheduledEventTestConstants.startTimeBeforeEndTime)
            newScheduledEvent.setEndTime(ScheduledEventTestConstants.endTimeAfterStartTime)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update fails given scheduled event's start time is after end time"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's start time is after end time"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setStartTime(ScheduledEventTestConstants.startTimeBeforeEndTime)
            newScheduledEvent.setEndTime(ScheduledEventTestConstants.endTimeAfterStartTime)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Single Scheduled Event: Update fails given scheduled event's scheduled event date is null"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's scheduled event date is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setScheduledEventDate(null)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)

        and: "the scheduled event's scheduled event interval is null"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setScheduledEventInterval(null)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
            httpClient.toBlocking().exchange(httpRequest)

        then: "the correct status code is returned"
            HttpClientResponseException ex = thrown()
            assert ex.status == HttpStatus.BAD_REQUEST
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update fails given scheduled event day is not null and scheduled event interval is daily"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)

        and: "the scheduled event's scheduled event day is not null and scheduled event interval is daily"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setScheduledEventDay(DayOfWeek.MONDAY)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.DAILY)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)

        and: "the scheduled event's scheduled event day is not null and scheduled event interval is weekdays"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setScheduledEventDay(DayOfWeek.MONDAY)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKDAYS)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)

        and: "the scheduled event's scheduled event day is not null and scheduled event interval is weekends"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setScheduledEventDay(DayOfWeek.MONDAY)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKENDS)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)

        and: "the scheduled event's scheduled event day is null and scheduled event interval is weekly"
            ScheduledEvent newScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            newScheduledEvent.setScheduledEventDay(null)
            newScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKLY)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, newScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's created on date is empty"
            ScheduledEvent updatedScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedScheduledEvent.createdOn(null)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(scheduledEventsUrl), updatedScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "the scheduled event's created on date is after the current date and time"
            ScheduledEvent updatedScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedScheduledEvent.setCreatedOn(LocalDateTime.now().plusMonths(1))

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(URI.create(scheduledEventsUrl), updatedScheduledEvent)
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

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_SINGLE)

        and: "a valid update is made to the scheduled event"
            ScheduledEvent updatedScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedScheduledEvent.setOrganizerId(ScheduledEventTestConstants.updateScheduledEventUpdatedOrganizerId)
            updatedScheduledEvent.setLocationId(ScheduledEventTestConstants.updateScheduledEventUpdatedLocationId)
            updatedScheduledEvent.setName(ScheduledEventTestConstants.updateScheduledEventUpdatedName)
            updatedScheduledEvent.setDescription(ScheduledEventTestConstants.updateScheduledEventUpdatedDescription)
            updatedScheduledEvent.setCategory(ScheduledEventTestConstants.updateScheduledEventUpdatedCategory)
            updatedScheduledEvent.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, updatedScheduledEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated scheduled event is returned in the response"
            ScheduledEvent scheduledEvent = response.body()

            correctResponseIsReturned(updatedScheduledEvent, scheduledEvent)

        and: "the scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())
            ScheduledEvent databaseScheduledEvent = checkingDatabaseResponse.body()
            correctResponseIsReturned(updatedScheduledEvent, databaseScheduledEvent)
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Standard Scheduled Event: Update scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            // Verify data seeded from Database init scripts correctly
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_STANDARD)

        and: "a valid update is made to the scheduled event"
            ScheduledEvent updatedScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedScheduledEvent.setOrganizerId(ScheduledEventTestConstants.updateScheduledEventUpdatedOrganizerId)
            updatedScheduledEvent.setEventId(ScheduledEventTestConstants.updateScheduledEventUpdatedEventId)
            updatedScheduledEvent.setClientId(ScheduledEventTestConstants.updateScheduledEventUpdatedClientId)
            updatedScheduledEvent.setLocationId(ScheduledEventTestConstants.updateScheduledEventUpdatedLocationId)
            updatedScheduledEvent.setName(ScheduledEventTestConstants.updateScheduledEventUpdatedName)
            updatedScheduledEvent.setDescription(ScheduledEventTestConstants.updateScheduledEventUpdatedDescription)
            updatedScheduledEvent.setCategory(ScheduledEventTestConstants.updateScheduledEventUpdatedCategory)
            updatedScheduledEvent.setStartTime(ScheduledEventTestConstants.updateScheduledEventStartTime)
            updatedScheduledEvent.setEndTime(ScheduledEventTestConstants.updateScheduledEventEndTime)
            updatedScheduledEvent.setScheduledEventInterval(ScheduledEventInterval.WEEKENDS)
            updatedScheduledEvent.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, updatedScheduledEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking().exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated scheduled event is returned in the response"
            ScheduledEvent scheduledEvent = response.body()

            correctResponseIsReturned(updatedScheduledEvent, scheduledEvent)

        and: "the scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseScheduledEvent = checkingDatabaseResponse.body()

            correctResponseIsReturned(updatedScheduledEvent, databaseScheduledEvent)
    }

    def "Update a scheduled event: URL Event Schedule id exists: Update Reoccurring Weekly Scheduled Event: Update scheduled event is successful"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .toString()

        and: "a scheduled event exists in the database"
            HttpResponse<ScheduledEvent> initResponse = checkDatabaseSeeding(scheduledEventsUrl, ScheduledEventTestConstants.UPDATE_SCHEDULED_EVENT_REOCCURRING_WEEKLY)

        and: "a valid update is made to the scheduled event"
            ScheduledEvent updatedScheduledEvent = CopyObjectUtil.scheduledEvent(initResponse.body())
            updatedScheduledEvent.setOrganizerId(ScheduledEventTestConstants.updateScheduledEventUpdatedOrganizerId)
            updatedScheduledEvent.setLocationId(ScheduledEventTestConstants.updateScheduledEventUpdatedLocationId)
            updatedScheduledEvent.setName(ScheduledEventTestConstants.updateScheduledEventUpdatedName)
            updatedScheduledEvent.setDescription(ScheduledEventTestConstants.updateScheduledEventUpdatedDescription)
            updatedScheduledEvent.setCategory(ScheduledEventTestConstants.updateScheduledEventUpdatedCategory)
            updatedScheduledEvent.setScheduledEventDay(DayOfWeek.FRIDAY)
            updatedScheduledEvent.setCreatedOn(TestConstants.updateCreatedOn)

        when: "a request is made to update a scheduled event for an event schedule"
            HttpRequest httpRequest = HttpRequest.PUT(scheduledEventsUrl, updatedScheduledEvent)
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking().exchange(httpRequest, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the updated scheduled event is returned in the response"
            ScheduledEvent scheduledEvent = response.body()
            correctResponseIsReturned(updatedScheduledEvent, scheduledEvent)

        and: "the scheduled event is correct in the database"
            HttpResponse<ScheduledEvent> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())

            ScheduledEvent databaseScheduledEvent = checkingDatabaseResponse.body()
            correctResponseIsReturned(updatedScheduledEvent, databaseScheduledEvent)
    }


    private HttpResponse<ScheduledEvent> checkDatabaseSeeding(String scheduledEventsUrl, ScheduledEvent scheduledEvent) {
        // Verify data seeded from Database init scripts correctly
        HttpResponse<ScheduledEvent> initResponse = httpClient.toBlocking()
                .exchange(scheduledEventsUrl.concat(scheduledEvent.getScheduledEventId()), ScheduledEvent)
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, initResponse.code())
        Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(scheduledEvent.getCreatedOn(), initResponse.body().getCreatedOn()))
        Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(scheduledEvent.getLastUpdatedOn(), initResponse.body().getLastUpdatedOn()))

        return initResponse
    }

    private void correctResponseIsReturned(ScheduledEvent expectedScheduledEvent, ScheduledEvent scheduledEvent) {
        Assertions.assertEquals(expectedScheduledEvent.getEventScheduleId(), scheduledEvent.getEventScheduleId())
        Assertions.assertEquals(expectedScheduledEvent.getScheduledEventId(), scheduledEvent.getScheduledEventId())
        Assertions.assertEquals(expectedScheduledEvent.getClientId(), scheduledEvent.getClientId())
        Assertions.assertEquals(expectedScheduledEvent.getEventId(), scheduledEvent.getEventId())
        Assertions.assertEquals(expectedScheduledEvent.getLocationId(), scheduledEvent.getLocationId())
        Assertions.assertEquals(expectedScheduledEvent.getOrganizerId(), scheduledEvent.getOrganizerId())
        Assertions.assertEquals(expectedScheduledEvent.getName(), scheduledEvent.getName())
        Assertions.assertEquals(expectedScheduledEvent.getDescription(), scheduledEvent.getDescription())
        Assertions.assertEquals(expectedScheduledEvent.getCategory(), scheduledEvent.getCategory())
        Assertions.assertEquals(expectedScheduledEvent.getScheduledEventType(), scheduledEvent.getScheduledEventType())
        Assertions.assertEquals(expectedScheduledEvent.getScheduledEventInterval(), scheduledEvent.getScheduledEventInterval())
        Assertions.assertEquals(expectedScheduledEvent.getScheduledEventDay(), scheduledEvent.getScheduledEventDay())
        Assertions.assertEquals(expectedScheduledEvent.getCost(), scheduledEvent.getCost())
        // Date and time comparisons
        Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(expectedScheduledEvent.getScheduledEventDate(), scheduledEvent.getScheduledEventDate()))
        Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(expectedScheduledEvent.getStartTime(), scheduledEvent.getStartTime()))
        Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(expectedScheduledEvent.getEndTime(), scheduledEvent.getEndTime()))
        Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(TestConstants.updateCreatedOn, scheduledEvent.getCreatedOn()))
        Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(scheduledEvent.getLastUpdatedOn()))
    }

}