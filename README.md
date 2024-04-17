# services.events-admin-service

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
* Bump the version of the app in the pom
* Update the [change log](./CHANGELOG.md)

## Local Development

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

This is how to create a new build of the Application and package it into a Docker container

```bash
mvn package -Dpackaging=docker
```

Start the Event Admin Service in Docker

```bash
docker run -d --name events-admin-service -e MICRONAUT_ENVIRONMENTS=dev -e AWS_REGION=us-east-1 -p 8080:8080 events-admin-service:latest
```

```bash
docker stop events-admin-service
```

### DynamoDB

**Note** There is an alias assumed if using the `awslocalddb` command below. The alias assumes you have set the
following:

```bash
awslocalddb=aws --profile=local --endpoint-url http://localhost:8000
```

List out the tables created

```bash
awslocalddb dynamodb list-tables
```

List out data in a table

```bash
awslocalddb dynamodb scan --table-name SERVICES_EVENTS_ADMIN_LOCAL
```

### S3

**Note** There is an alias assumed if using the `awslocals3` command below. The alias assumes you have set the
following:

```
awslocals3=aws --profile=local --endpoint-url http://localhost:9090
```

List out the buckets that exits

```bash
awslocals3 s3 ls
```

List out the files in a bucket

```bash
awslocals3 s3 ls event-admin-service-file-storage
```

List out the content of a file in a bucket

```bash
awslocals3 s3 cp s3://event-admin-service-file-storage/4f2d25cc-cb66-4e29-ac36-c20ce83fb28a/2024-04-16T20:13:19.074960.csv - 
```

### IntelliJ

Place the below in the Environment Variables

```bash
MICRONAUT_ENVIRONMENTS=local
```

### Example collapsible section

<details>
  <summary>Click me to expand</summary>

### Heading

1. Foo
2. Bar

* Baz
* Qux

### Some Javascript

  ```js
  function logSomething(something) {
    console.log('Something', something);
  }
  ```

</details>