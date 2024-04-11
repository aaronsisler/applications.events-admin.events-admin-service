package com.ebsolutions.eventsadminservice.spec.publishedEventSchedule

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
class PublishedEventScheduledGetSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "Get a published event schedule: URL Client id exists: Get published event schedule: Published event schedule exists"() {
        given: "the client id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getScheduledEventId())
                    .toString()

        and: "a published event schedule exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a published event schedule"
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct published event schedule is returned"
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

    def "Get a published event schedule: URL Client id exists: Published event schedule does not exist"() {
        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/event-schedules/")
                    .append(ScheduledEventTestConstants.GET_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/scheduled-events/")
                    .append(TestConstants.nonExistentEventScheduleId)
                    .toString()

        and: "a published event schedule does not exist in the database"
            // Data was not seeded for this test scenario

        when: "a request is made to retrieve a published event schedule"
            HttpResponse<ScheduledEvent> response = httpClient.toBlocking()
                    .exchange(scheduledEventsUrl, ScheduledEvent)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.code())
    }
}