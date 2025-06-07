# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Legend

* `Added` for new features.
* `Changed` for changes in existing functionality.
* `Deprecated` for soon-to-be removed features.
* `Removed` for now removed features.
* `Fixed` for any bug fixes.
* `Security` in case of vulnerabilities.

## [0.37.0]

### Fixed

- Changing the User contract object and DTO to have a set of establishmentIds instead of a list.

## [0.36.0]

### Changed

- Split the FileController into a local and a default version based on profile to allow for easier checking of the file
  locally
- Handling the Category being null and replacing with an empty string

## [0.35.0]

### Added

- Logic to returned a presigned url to access S3 files

## [0.34.0]

### Added

- Adding in HTTPS and working through kinks of ECS

## [0.33.0]

### Changed

- Updated compose for local generation
- Update app.yaml for local generation
- Update a messed up usage of id in the file generation process

## [0.32.0]

### Added

- Adding some SSL/HTTPS things

### Changed

- Some port info in the CF stack

## [0.30.0 - 0.31.0]

### Changed

- HTTPS

## [0.26.0 - 0.29.0]

### Changed

- Working through the deployment of the service through ECS. Lots of quick small things

## [0.25.0]

### Added

- Heartbeat logging

## [0.24.0]

### Changed

- Moving around the repo in GH and the folder structure

## [0.23.0]

### Changed

- Changed the prod profile to be default so we don't need to pass the profile for our first tries at deploying to AWS

## [0.22.0]

### Added

- GHA for the Docker image being published to AWS ECR

## [0.21.0]

### Added

- Changes for authentication being needed
- Adding in the flatten plugin for making version bumping easier

## [0.20.0]

### Changed

- Added in Body Kinect specific events for easier schedule creation

## [0.19.0]

### Added

- Published Event Schedule GET ALL

## [0.18.0]

### Changed

- Updating the Published Event Schedule
    - Require event schedule id
    - Shortening the year and month attribute name

## [0.17.0]

### Fixed

- Client: Updating the query for GET ALL given the delimiter
- User: Updating the query for GET ALL given the delimiter

## [0.16.0]

### Changed

- Bumping Spring Boot version from 3.3.X to 3.4.X

### Fixed

- Updating the Record Type to have a trailing delimiter given a bug with EVENT and EVENT_SCHEDULE not playing well
  together on the GET ALL query

## [0.15.0]

### Added

- User GET, GET ALL, POST, PUT, DELETE
- Adding in `dev` profile that will be used in Docker containing database, file storage, and running application
- Adding in a base client and a base user that is associated with that client for UI purposes until AuthN/Z is ready

## [0.14.0]

### Added

- Adding in changes for building and running application on Docker locally

## [0.13.0]

### Changed

- Speeding things up so I can make this project stable for building UI
- Side project for understanding how Cucumber can be utilized is in flight

### Removed

- All tests and dependencies around testing

## [0.12.0]

### Fixed

- Updating the way Maven and project are generating the POJOs from OpenAPI since field validations weren't in place

## [0.11.0]

### Added

- Clients POST

## [0.10.0]

### Changed

- Switching from Micronaut to Spring framework given the delays in dev process.

## [0.9.0]

### Added

- Adding in the File Controller and shifting things so we can pull the S3 CSV out from local storage

## [0.8.0]

### Added

- Added in the configs per environment to allow for Docker and possibly AWS deployment to work

## [0.7.0]

### Added

- Scheduled Events GET, GET ALL, POST, PUT, DELETE
- Event Schedules GET, GET ALL, POST, PUT, DELETE
- Published Event Schedules GET, GET ALL, POST

## [0.6.0] Events APIs

### Added

- Events GET, GET ALL, POST, PUT, DELETE

## [0.5.0] Clients, Locations, Organizer APIs

### Added

- Organizers GET, GET ALL, POST, PUT, DELETE
- Locations GET, GET ALL, POST, PUT, DELETE
- Clients GET, GET ALL, POST