package com.ebsolutions.eventsadminservice.spec.scheduledEvent

import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.ScheduledEvent
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions

@MicronautTest
class ScheduledEventDeleteSpec {
    @Inject
    private HttpClient httpClient

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
}