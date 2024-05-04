package com.ebsolutions.eventsadminservice.spec.publishedEventSchedule


import com.ebsolutions.eventsadminservice.constant.PublishedEventScheduleTestConstants
import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.PublishedEventSchedule
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

    def "Get a published event schedule: Get published event schedule: Published event schedule exists"() {
        given: "the url has the correct client id and published event schedule id"
            String publishedEventSchedulesUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getClientId())
                    .append("/published-event-schedules/")
                    .append(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getPublishedEventScheduleId())
                    .toString()

        and: "a published event schedule exists in the database"
            // Data seeded from Database init scripts

        when: "a request is made to retrieve a published event schedule"
            HttpResponse<PublishedEventSchedule> response = httpClient.toBlocking()
                    .exchange(publishedEventSchedulesUrl, PublishedEventSchedule)

        then: "the correct status code is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.code())

        and: "the correct published event schedule is returned"
            PublishedEventSchedule publishedEventSchedule = response.body()

            Assertions.assertEquals(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getClientId(), publishedEventSchedule.getClientId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getPublishedEventScheduleId(), publishedEventSchedule.getPublishedEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getFilename(), publishedEventSchedule.getFilename())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getEventScheduleId(), publishedEventSchedule.getEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getName(), publishedEventSchedule.getName())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.EVENT_SCHEDULE_YEAR, publishedEventSchedule.getEventScheduleYear())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.EVENT_SCHEDULE_MONTH, publishedEventSchedule.getEventScheduleMonth())
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getCreatedOn(), publishedEventSchedule.getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDateAndTimeEqual(PublishedEventScheduleTestConstants.GET_PUBLISHED_EVENT_SCHEDULE.getLastUpdatedOn(), publishedEventSchedule.getLastUpdatedOn()))

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