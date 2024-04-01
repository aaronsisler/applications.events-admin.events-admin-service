package com.ebsolutions.eventsadminservice.spec.publishedEventSchedule

import com.ebsolutions.eventsadminservice.constant.PublishedEventScheduleTestConstants
import com.ebsolutions.eventsadminservice.constant.ScheduledEventTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.*
import com.ebsolutions.eventsadminservice.util.CopyObjectUtil
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import spock.lang.Specification

@MicronautTest
class PublishedEventScheduleCreateSpec extends Specification {
    @Inject
    private HttpClient httpClient

    def "GWT Holders"() {


        given: "the event schedule id is in the url"
            String scheduledEventsUrl = new StringBuffer()
                    .append(TestConstants.eventsAdminServiceUrl)
                    .append("/clients/")
                    .append(ScheduledEventTestConstants.CREATE_SCHEDULED_EVENT_SINGLE.getEventScheduleId())
                    .append("/published-event-schedules/")
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

    def "Create a published event schedule: URL Client id exists: Create Published Event Schedule: Create published event schedule is successful"() {
        given: "a client exists in the database"
            // Verify data seeded from Database init scripts correctly
            String clientsUrl = getParentUrl(
                    "clients",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId()
            )

            HttpResponse<Client> clientResponse = httpClient.toBlocking()
                    .exchange(clientsUrl, Client)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, clientResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(), clientResponse.body().getClientId())

        and: "a location for the client exists in the database"
            // Verify data seeded from Database init scripts correctly
            String locationsUrl = getChildUrl(
                    "clients",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(),
                    "locations",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_LOCATION.getLocationId()
            )

            HttpResponse<Location> locationResponse = httpClient.toBlocking()
                    .exchange(locationsUrl, Location)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, locationResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(), locationResponse.body().getClientId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_LOCATION.getLocationId(), locationResponse.body().getLocationId())

        and: "a organizer for the client exists in the database"
            // Verify data seeded from Database init scripts correctly
            String organizersUrl = getChildUrl(
                    "clients",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(),
                    "organizers",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_ORGANIZER.getOrganizerId()
            )

            HttpResponse<Organizer> organizerResponse = httpClient.toBlocking()
                    .exchange(organizersUrl, Organizer)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, organizerResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(), organizerResponse.body().getClientId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_ORGANIZER.getOrganizerId(), organizerResponse.body().getOrganizerId())

        and: "an event for the client with location and organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            String eventsUrl = getChildUrl(
                    "clients",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(),
                    "events",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT.getEventId()
            )

            HttpResponse<Event> eventResponse = httpClient.toBlocking()
                    .exchange(eventsUrl, Event)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, eventResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT.getEventId(), eventResponse.body().getEventId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(), eventResponse.body().getClientId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_LOCATION.getLocationId(), eventResponse.body().getLocationId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_ORGANIZER.getOrganizerId(), eventResponse.body().getOrganizerId())

        and: "an event schedule for the client exists in the database"
            // Verify data seeded from Database init scripts correctly
            String eventSchedulesUrl = getChildUrl(
                    "clients",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(),
                    "event-schedules",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE.getEventScheduleId()
            )

            HttpResponse<EventSchedule> eventScheduleResponse = httpClient.toBlocking()
                    .exchange(eventSchedulesUrl, EventSchedule)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, eventScheduleResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE.getEventScheduleId(), eventScheduleResponse.body().getEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(), eventScheduleResponse.body().getClientId())

        and: "a single scheduled event for the event schedule with no changes to the location and organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            String scheduledEventSingleUrl = getChildUrl(
                    "event-schedules",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE.getEventScheduleId(),
                    "scheduled-events",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE.getScheduledEventId()
            )

            HttpResponse<ScheduledEvent> scheduledEventSingleResponse = httpClient.toBlocking()
                    .exchange(scheduledEventSingleUrl, ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, scheduledEventSingleResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE.getEventScheduleId(), scheduledEventSingleResponse.body().getEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE.getScheduledEventId(), scheduledEventSingleResponse.body().getScheduledEventId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE.getScheduledEventType(), scheduledEventSingleResponse.body().getScheduledEventType())


        and: "a daily reoccurring scheduled event for the event schedule with no changes to the location and organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            String scheduledEventStandardUrl = getChildUrl(
                    "event-schedules",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE.getEventScheduleId(),
                    "scheduled-events",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventId()
            )

            HttpResponse<ScheduledEvent> scheduledEventStandardResponse = httpClient.toBlocking()
                    .exchange(scheduledEventStandardUrl, ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, scheduledEventSingleResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getEventScheduleId(), scheduledEventStandardResponse.body().getEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventId(), scheduledEventStandardResponse.body().getScheduledEventId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getScheduledEventType(), scheduledEventStandardResponse.body().getScheduledEventType())


        and: "a weekly reoccurring scheduled event for the event schedule with no changes to the location and organizer exists in the database"
            // Verify data seeded from Database init scripts correctly
            String scheduledEventWeeklyUrl = getChildUrl(
                    "event-schedules",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE.getEventScheduleId(),
                    "scheduled-events",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventId()
            )

            HttpResponse<ScheduledEvent> scheduledEventWeeklyResponse = httpClient.toBlocking()
                    .exchange(scheduledEventWeeklyUrl, ScheduledEvent)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, scheduledEventSingleResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getEventScheduleId(), scheduledEventWeeklyResponse.body().getEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventId(), scheduledEventWeeklyResponse.body().getScheduledEventId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getScheduledEventType(), scheduledEventWeeklyResponse.body().getScheduledEventType())


        and: "a valid published event schedule for February 2024 with no event blackouts or location blackouts is ready to be published"

        when: "the published event schedule is published"

        then: "the file is published to file storage at a specific file location"

        and: "published event schedule is saved to the database with the correct file location"

        and: "the file is the correct format"

        and: "the file was processed with the correct days (29 since Feb 2024 is a leap year)"

        and: "the file has the correct content"
    }

    def getParentUrl(String basePath, String baseId) {
        return new StringBuffer()
                .append(TestConstants.eventsAdminServiceUrl)
                .append("/${basePath}/")
                .append(baseId)
                .toString()
    }

    def getChildUrl(String parentPath, String parentId, String childPath, String childId) {
        return new StringBuffer()
                .append(TestConstants.eventsAdminServiceUrl)
                .append("/${parentPath}/")
                .append(parentId)
                .append("/${childPath}/")
                .append(childId)
                .toString()
    }
}