package com.ebsolutions.eventsadminservice.spec


import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

@MicronautTest
class NewLocationSpec extends Specification {
    // DONE: Get a location
    def "Get a location: URL Client id does not exist"() {
        given: "the client id is not in the url"
        when: "a request is made to retrieve a location"
        then: "the correct status code is returned"
    }

    def "Get a location: URL Client id exists: Location Exists"() {
        given: "the client id is in the url"
        and: "a location exists in the database"
        when: "a request is made to retrieve a location"
        then: "the correct status code is returned"
        and: "the correct location is returned"
    }

    def "Get a location: URL Client id exists: Location does not exist"() {
        given: "the client id is in the url"
        and: "a location does not exist in the database"
        when: "a request is made to retrieve a location"
        then: "the correct status code is returned"
    }

    // TODO
    // Get all locations
    def "Get all locations: URL Client id does not exist"() {}

    def "Get all locations: URL Client id exists: Locations exist for client"() {}

    def "Get all locations: URL Client id exists: No locations exist for client"() {}

    def "Get all Locations: Locations exist for client"() {
        given: "A set of locations exist in the database for a given client"
        when: "a request is made to retrieve the locations"
        then: "the correct status code is returned"
        and: "the correct locations are returned"
    }

    def "Get all Locations: No locations exist for client"() {
        given: "No locations exist in the database for a given client"
        when: "a request is made to retrieve the locations"
        then: "the correct status code is returned"
    }

    // TODO
    // Delete a location
    def "Delete a location: URL Client id does not exist"() {}

    def "Delete a location: URL Client id exists: Delete location is successful"() {}


    def "Delete a Location"() {
        given: "A location exists in the database"
        when: "a request is made to delete the location"
        then: "the correct status code is returned"
        and: "the location no longer exists in the database"
    }

    // TODO
    // Create a location
    def "Create a location: URL Client id does not exist"() {}

    def "Create a location: URL Client id exists: Create fails given location client id is blank"() {}

    def "Create a location: URL Client id exists: Create fails given location client id and URL client id do not match"() {
    }

    def "Create a location: URL Client id exists: Create location is successful"() {}

    def "Create a Location: Success"() {
        given: "A valid location"
        when: "a request is made to create a location"
        then: "the correct status code is returned"
        and: "the new location is returned"
    }

    // TODO
    // Update a location
    def "Update a location: URL Client id does not exist"() {}

    def "Update a location: URL Client id exists: Update fails given location client id is blank"() {}

    def "Update a location: URL Client id exists: Update fails given location client id and URL client id do not match"() {
    }

    def "Update a location: URL Client id exists: Update fails given create date is empty"() {}

    def "Update a location: URL Client id exists: Update fails given create date is after 'now'"() {}

    def "Update a location: URL Client id exists: Update location is successful"() {}

    def "Update a Location: Fails given invalid Location Id"() {
        given: "A location exists in the database"
        and: "an update is made to the location id that is invalid"
        when: "a request is made to update the location"
        then: "the correct status code is returned"
    }

    def "Update a Location: Fails given create date is after 'now'"() {
        given: "A location exists in the database"
        and: "an update is made to the created on date that is invalid"
        when: "a request is made to update the location"
        then: "the correct status code is returned"
    }

    def "Update a Location: Success"() {
        given: "A location exists in the database"
        and: "an update is made to location"
        when: "a request is made to update the location"
        then: "the correct status code is returned"
        and: "the updated location is returned"
    }


}