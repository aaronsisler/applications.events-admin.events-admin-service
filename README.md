# services.events-admin-service

## Task List

https://github.com/users/aaronsisler/projects/9/views/1

## Running Locally

### Docker

Start the Docker containers

```bash
docker compose -f ./docker-compose.local.yml up -d
```

Stop the Docker containers

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
awslocal dynamodb scan --table-name SERVICES_ADMIN_DUTIES_LOCAL
```

### IntelliJ

Place the below in the Environment Variables

```bash
MICRONAUT_ENVIRONMENTS=local
```
