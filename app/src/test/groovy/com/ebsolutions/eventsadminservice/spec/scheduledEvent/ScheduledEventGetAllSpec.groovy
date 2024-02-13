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
            ScheduledEvent thirdScheduledEvent = events.get(2)

            Assertions.assertEquals(ScheduledEventTestConstants.getAllScheduledEventsEventScheduleId, firstScheduledEvent.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getScheduledEventId(), firstScheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getClientId(), firstScheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getEventId(), firstScheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getLocationId(), firstScheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getOrganizerId(), firstScheduledEvent.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getName(), firstScheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getDescription(), firstScheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getCategory(), firstScheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getScheduledEventType(), firstScheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getScheduledEventInterval(), firstScheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getScheduledEventDay(), firstScheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getCost(), firstScheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getScheduledEventDate(), firstScheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getStartTime(), firstScheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getEndTime(), firstScheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getCreatedOn(), firstScheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_ONE.getLastUpdatedOn(), firstScheduledEvent.getLastUpdatedOn()))

            Assertions.assertEquals(ScheduledEventTestConstants.getAllScheduledEventsEventScheduleId, secondScheduledEvent.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getScheduledEventId(), secondScheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getClientId(), secondScheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getEventId(), secondScheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getLocationId(), secondScheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getOrganizerId(), secondScheduledEvent.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getName(), secondScheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getDescription(), secondScheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getCategory(), secondScheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getScheduledEventType(), secondScheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getScheduledEventInterval(), secondScheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getScheduledEventDay(), secondScheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getCost(), secondScheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getScheduledEventDate(), secondScheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getStartTime(), secondScheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getEndTime(), secondScheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getCreatedOn(), secondScheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_TWO.getLastUpdatedOn(), secondScheduledEvent.getLastUpdatedOn()))

            Assertions.assertEquals(ScheduledEventTestConstants.getAllScheduledEventsEventScheduleId, thirdScheduledEvent.getEventScheduleId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getScheduledEventId(), thirdScheduledEvent.getScheduledEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getClientId(), thirdScheduledEvent.getClientId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getEventId(), thirdScheduledEvent.getEventId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getLocationId(), thirdScheduledEvent.getLocationId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getOrganizerId(), thirdScheduledEvent.getOrganizerId())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getName(), thirdScheduledEvent.getName())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getDescription(), thirdScheduledEvent.getDescription())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getCategory(), thirdScheduledEvent.getCategory())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getScheduledEventType(), thirdScheduledEvent.getScheduledEventType())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getScheduledEventInterval(), thirdScheduledEvent.getScheduledEventInterval())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getScheduledEventDay(), thirdScheduledEvent.getScheduledEventDay())
            Assertions.assertEquals(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getCost(), thirdScheduledEvent.getCost())
            // Date and time comparisons
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getScheduledEventDate(), thirdScheduledEvent.getScheduledEventDate()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getStartTime(), thirdScheduledEvent.getStartTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areTimesEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getEndTime(), thirdScheduledEvent.getEndTime()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getCreatedOn(), thirdScheduledEvent.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(ScheduledEventTestConstants.GET_ALL_SCHEDULED_EVENTS_SCHEDULED_EVENT_THREE.getLastUpdatedOn(), thirdScheduledEvent.getLastUpdatedOn()))

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