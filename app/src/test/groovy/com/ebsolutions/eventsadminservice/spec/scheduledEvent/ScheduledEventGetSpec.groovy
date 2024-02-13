package com.ebsolutions.eventsadminservice.spec.scheduledEvent

import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.ScheduledEvent
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
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
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getScheduledEventId())
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
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getScheduledEventId(), eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getClientId(), eventSchedule.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getEventId(), eventSchedule.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getLocationId(), eventSchedule.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getOrganizerId(), eventSchedule.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getName(), eventSchedule.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getDescription(), eventSchedule.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getCategory(), eventSchedule.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getScheduledEventType(), eventSchedule.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getScheduledEventDate(), eventSchedule.getScheduledEventDate())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getScheduledEventDay(), eventSchedule.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getScheduledEventInterval(), eventSchedule.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getStartTime(), eventSchedule.getStartTime())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getEndTime(), eventSchedule.getEndTime())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getCost(), eventSchedule.getCost())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getCreatedOn(), eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getLastUpdatedOn(), eventSchedule.getLastUpdatedOn()))
    }

    def "Get a scheduled event: URL Client id exists: Get Reoccurring Standard Scheduled Event: Scheduled event exists"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventId())
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
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventId(), eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getClientId(), eventSchedule.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventId(), eventSchedule.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getLocationId(), eventSchedule.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getOrganizerId(), eventSchedule.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getName(), eventSchedule.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getDescription(), eventSchedule.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getCategory(), eventSchedule.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventType(), eventSchedule.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventDate(), eventSchedule.getScheduledEventDate())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventDay(), eventSchedule.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventInterval(), eventSchedule.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getStartTime(), eventSchedule.getStartTime())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEndTime(), eventSchedule.getEndTime())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getCost(), eventSchedule.getCost())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getCreatedOn(), eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_STANDARD.getLastUpdatedOn(), eventSchedule.getLastUpdatedOn()))
    }

    def "Get a scheduled event: URL Client id exists: Get Reoccurring Weekly Scheduled Event: Scheduled event exists"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventId())
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
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), eventSchedule.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventId(), eventSchedule.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getClientId(), eventSchedule.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventId(), eventSchedule.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLocationId(), eventSchedule.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getOrganizerId(), eventSchedule.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName(), eventSchedule.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getDescription(), eventSchedule.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCategory(), eventSchedule.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), eventSchedule.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDate(), eventSchedule.getScheduledEventDate())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventDay(), eventSchedule.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventInterval(), eventSchedule.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getStartTime(), eventSchedule.getStartTime())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEndTime(), eventSchedule.getEndTime())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCost(), eventSchedule.getCost())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getCreatedOn(), eventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getLastUpdatedOn(), eventSchedule.getLastUpdatedOn()))
    }

    def "Get a scheduled event: URL Event Schedule id exists: Scheduled event does not exist"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
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
}