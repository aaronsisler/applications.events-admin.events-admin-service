package com.ebsolutions.eventsadminservice.spec.publishedEventSchedule

import com.ebsolutions.eventsadminservice.config.Constants
import com.ebsolutions.eventsadminservice.constant.PublishedEventScheduleTestConstants
import com.ebsolutions.eventsadminservice.constant.TestConstants
import com.ebsolutions.eventsadminservice.model.*
import com.ebsolutions.eventsadminservice.util.DateAndTimeComparisonUtil
import com.ebsolutions.eventsadminservice.util.FileStorageUtil
import de.siegmar.fastcsv.reader.CsvReader
import de.siegmar.fastcsv.reader.CsvRecord
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import spock.lang.Specification

import java.time.LocalDate

@MicronautTest
class PublishedEventScheduleCreateSpec extends Specification {
    @Inject
    private HttpClient httpClient
    @Inject
    private FileStorageUtil fileStorageUtil

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
            Assertions.assertTrue(DateAndTimeComparisonUtil.areDatesEqual(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE.getScheduledEventDate(), scheduledEventSingleResponse.body().getScheduledEventDate()))


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
            PublishedEventSchedule publishedEventSchedule = PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE

        when: "the published event schedule is published"
            String publishedEventScheduleUrl = postChildUrl(
                    "clients",
                    PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(),
                    "published-event-schedules"
            )

            HttpRequest httpRequest = HttpRequest.POST(publishedEventScheduleUrl, publishedEventSchedule)

            HttpResponse<PublishedEventSchedule> publishedEventScheduleHttpResponse = httpClient.toBlocking()
                    .exchange(httpRequest, PublishedEventSchedule)

        then: "the correct response is returned"
            Assertions.assertEquals(HttpURLConnection.HTTP_OK, publishedEventScheduleHttpResponse.code())

        and: "the correct published event schedule is returned"
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_CLIENT.getClientId(), publishedEventScheduleHttpResponse.body().getClientId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE.getEventScheduleId(), publishedEventScheduleHttpResponse.body().getEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.EVENT_SCHEDULE_YEAR, publishedEventScheduleHttpResponse.body().getEventScheduleYear())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.EVENT_SCHEDULE_MONTH, publishedEventScheduleHttpResponse.body().getEventScheduleMonth())

        and: "the file is published to file storage at a specific file location"
            List<String> fileContents = fileStorageUtil.getFileContents(publishedEventScheduleHttpResponse.body().getFileLocation())
            // Greater than 1 means at least the headers and one row of data is there
            Assertions.assertTrue(fileContents.size() > 1)

        and: "published event schedule is saved to the database with the correct file location"
            String getPublishedEventScheduleUrl = publishedEventScheduleUrl.concat(publishedEventScheduleHttpResponse.body().getPublishedEventScheduleId())

            HttpResponse<PublishedEventSchedule> checkingDatabaseResponse = httpClient.toBlocking()
                    .exchange(getPublishedEventScheduleUrl, PublishedEventSchedule)

            Assertions.assertEquals(HttpURLConnection.HTTP_OK, checkingDatabaseResponse.code())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_EVENT_SCHEDULE.getEventScheduleId(), checkingDatabaseResponse.body().getEventScheduleId())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.EVENT_SCHEDULE_YEAR, checkingDatabaseResponse.body().getEventScheduleYear())
            Assertions.assertEquals(PublishedEventScheduleTestConstants.EVENT_SCHEDULE_MONTH, checkingDatabaseResponse.body().getEventScheduleMonth())
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(checkingDatabaseResponse.body().getCreatedOn()))
            Assertions.assertTrue(DateAndTimeComparisonUtil.isDateAndTimeNow(checkingDatabaseResponse.body().getLastUpdatedOn()))

        and: "the file is the correct format"
            try (CsvReader<CsvRecord> csv = CsvReader.builder().ofCsvRecord(fileStorageUtil.getFileReader(checkingDatabaseResponse.body().getFileLocation()))) {
                csv.forEach(it -> Assertions.assertEquals(Constants.CSV_COLUMN_HEADERS.size(), it.fieldCount))
            }

        and: "the file has the correct content for the single event including the fact Feb 2024 is a leap year"
            try (CsvReader<CsvRecord> csv = CsvReader.builder().ofCsvRecord(fileStorageUtil.getFileReader(checkingDatabaseResponse.body().getFileLocation()))) {
                checkForSingleEventContent(csv)
            }

        and: "the file has the correct content for the daily reoccurring event including the fact Feb 2024 is a leap year"
            try (CsvReader<CsvRecord> csv = CsvReader.builder().ofCsvRecord(fileStorageUtil.getFileReader(checkingDatabaseResponse.body().getFileLocation()))) {
                checksForDailyReoccurringEventContent(csv)
            }

        and: "the file has the correct content for the weekly reoccurring event including the fact Feb 2024 is a leap year"
            try (CsvReader<CsvRecord> csv = CsvReader.builder().ofCsvRecord(fileStorageUtil.getFileReader(checkingDatabaseResponse.body().getFileLocation()))) {
                checkForWeeklyReoccurringEventContent(csv)
            }
    }

    def checksForDailyReoccurringEventContent(CsvReader<CsvRecord> csv) {
        int eventNameIndex = Constants.CSV_COLUMN_HEADERS.indexOf(Constants.CsvColumnHeaderNames.EVENT_NAME.label)
        int eventStartDateColumnIndex = Constants.CSV_COLUMN_HEADERS.indexOf(Constants.CsvColumnHeaderNames.EVENT_START_DATE.label)
        List<Integer> expectedDaysOfTheMonth = 1..29

        List<CsvRecord> csvRecords = csv.findAll {
            it.getField(eventNameIndex) == PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_STANDARD.getName()
        } as List<CsvRecord>

        Assertions.assertEquals(csvRecords.size(), expectedDaysOfTheMonth.size(), "Correct number of records not found between CSV line items and dates in Year and Month")

        csvRecords.forEach {
            Assertions.assertTrue(expectedDaysOfTheMonth.contains(LocalDate.parse(it.getField(eventStartDateColumnIndex)).getDayOfMonth()),
                    "Correct days not found for compared to the year (2024) and month (February) i.e. 1 through 29")
        }
    }

    def checkForWeeklyReoccurringEventContent(CsvReader<CsvRecord> csv) {
        int eventNameIndex = Constants.CSV_COLUMN_HEADERS.indexOf(Constants.CsvColumnHeaderNames.EVENT_NAME.label)
        int eventStartDateColumnIndex = Constants.CSV_COLUMN_HEADERS.indexOf(Constants.CsvColumnHeaderNames.EVENT_START_DATE.label)
        List<CsvRecord> csvRecords = csv.findAll {
            it.getField(eventNameIndex) == PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_REOCCURRING_WEEKLY.getName()
        } as List<CsvRecord>

        List<LocalDate> expectedReoccurringWeeklyDates = List.of(
                LocalDate.of(2024, 2, 7),
                LocalDate.of(2024, 2, 14),
                LocalDate.of(2024, 2, 21),
                LocalDate.of(2024, 2, 28),
        )

        Assertions.assertEquals(csvRecords.size(), expectedReoccurringWeeklyDates.size(), "Correct number of records not found between CSV line items and dates in Year and Month")

        csvRecords.forEach {
            Assertions.assertTrue(expectedReoccurringWeeklyDates.contains(LocalDate.parse(it.getField(eventStartDateColumnIndex))),
                    "Correct dates not found for day of the week (Wednesday) compared to the year (2024) and month (February)")
        }
    }

    def checkForSingleEventContent(CsvReader<CsvRecord> csv) {
        int eventNameIndex = Constants.CSV_COLUMN_HEADERS.indexOf(Constants.CsvColumnHeaderNames.EVENT_NAME.label)

        CsvRecord foundObject = csv.find {
            it.getField(eventNameIndex) == PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE.getName()
        } as CsvRecord

        Assertions.assertTrue(foundObject != null, "There is no record within the CSV that correlates with the correct single scheduled event's name")
        int eventStartDateColumnIndex = Constants.CSV_COLUMN_HEADERS.indexOf(Constants.CsvColumnHeaderNames.EVENT_START_DATE.label)
        LocalDate objectEventStartDate = LocalDate.parse(foundObject.getField(eventStartDateColumnIndex))

        Assertions.assertTrue(
                DateAndTimeComparisonUtil.areDatesEqual(
                        objectEventStartDate,
                        PublishedEventScheduleTestConstants.CREATE_PUBLISHED_EVENT_SCHEDULE_SCHEDULED_EVENT_SINGLE.getScheduledEventDate()),
                "The record that matches the single scheduled event's name does not have the correct date.")
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

    def postChildUrl(String parentPath, String parentId, String childPath) {
        return new StringBuffer()
                .append(TestConstants.eventsAdminServiceUrl)
                .append("/${parentPath}/")
                .append(parentId)
                .append("/${childPath}/")
                .toString()
    }
}