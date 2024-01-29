package com.ebsolutions.eventsadminservice.spec.scheduledEvent

import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.ScheduledEvent
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import spock.lang.Specification

@MicronautTest
class ScheduledEventGetAllSpec extends Specification {
    @Inject
    private HttpClient httpClient

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
}