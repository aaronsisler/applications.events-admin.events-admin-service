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