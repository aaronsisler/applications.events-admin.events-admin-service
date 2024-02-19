# services.events-admin-service

## Task List

https://github.com/users/aaronsisler/projects/9/views/1

## Definition of Done

* Contract created/updated
* Dependencies added to pom(s) are commented with what their usage is for
* DAL layer is created/updated and follows naming conventions
    * DAL
    * DTO
* Controller is created/updated and follows naming conventions
* Integration tests added/updated
    * Test(s) created/updated
    * Data set(s) created/updated
* API collection (Bruno) is updated and committed to api-client repository

## Application Workflow

1. A [client](./app/contracts/client.yaml) is created
   using [client controller](./app/src/main/java/com/ebsolutions/eventsadminservice/controller/ClientController.java).
2. [Location(s)](./app/contracts/location.yaml) for the client are created using
   the [location controller](./app/src/main/java/com/ebsolutions/eventsadminservice/controller/LocationController.java).
3. [Organizer(s)](./app/contracts/organizer.yaml) for the client are created using
   the [organizer controller](./app/src/main/java/com/ebsolutions/eventsadminservice/controller/OrganizerController.java).
4. [Event(s)](./app/contracts/client.yaml) for the client are created using
   the [event controller](./app/src/main/java/com/ebsolutions/eventsadminservice/controller/EventController.java).
5. When a list of events is ready to be grouped together in preparation for future publishing or reuse,
   an [event schedule](./app/contracts/event-schedule.yaml) is created using
   the [event schedule controller](./app/src/main/java/com/ebsolutions/eventsadminservice/controller/EventScheduleController.java).
6. For a given event schedule, [scheduled event(s)](./app/contracts/scheduled-event.yaml) are created and added to the
   event schedule using
   the [scheduled event controller](./app/src/main/java/com/ebsolutions/eventsadminservice/controller/ScheduledEventController.java).
   Scheduled events can be of the type SINGLE or REOCCURRING. REOCCURRING can have different intervals, like WEEKLY or
   DAILY, that each have their own validations. The concept here is the Scheduled Event has no specific date associated
   with it, except in the case of SINGLE scheduled events.
7. When an event schedule is ready to be published for a given year and month,
   the [published event schedule](./app/contracts/published-event-schedule.yaml) is created
   using
   the [published event schedule controller](./app/src/main/java/com/ebsolutions/eventsadminservice/controller/PublishedEventScheduleController.java).
   This takes in a previously created event schedule id, along with year, month, and any event or location blackout
   dates.
8. This hits Orc Service but then what?
9. Seems to be in memory CSV
9. https://www.csvreader.com/java_csv.php
10. Notes
10. https://stackoverflow.com/questions/57361111/can-we-write-csv-file-to-s3-without-creating-a-file-on-local-in-spring-boot

## Running Locally

### Docker

Start the Docker containers

```bash
dockerlocalup
```

```bash
docker compose -f ./docker-compose.local.yml up -d
```

Stop the Docker containers

```bash
dockerlocaldown
```

```bash
docker compose -f ./docker-compose.local.yml down
```

List out the tables created

**Note** There is an alias assumed if using the `awslocal` command below. The alias assumes you have set the following:

```
awslocal=aws --endpoint-url http://localhost:8000
```

```bash
awslocal dynamodb list-tables
```

List out data in a table

```bash
awslocal dynamodb scan --table-name SERVICES_EVENTS_ADMIN_LOCAL
```

### IntelliJ

Place the below in the Environment Variables

```bash
MICRONAUT_ENVIRONMENTS=local
```