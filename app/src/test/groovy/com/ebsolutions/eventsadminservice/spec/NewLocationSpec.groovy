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

    // Get all locations
    def "Get all locations: URL Client id does not exist"() {
        given: "the client id is not in the url"
        when: "a request is made to retrieve locations for a client"
        then: "the correct status code is returned"
    }

    def "Get all locations: URL Client id exists: Locations exist for client"() {
        given: "the client id is in the url"
        when: "a request is made to retrieve locations for a client"
        then: "the correct status code is returned"
        and: "the correct locations are returned"
    }

    def "Get all locations: URL Client id exists: No locations exist for client"() {
        given: "the client id is in the url"
        when: "a request is made to retrieve locations for a client"
        then: "the correct status code is returned"
    }

    // Delete a location
    def "Delete a location: URL Client id does not exist"() {
        given: "the client id is not in the url"
        when: "a request is made to delete the location for a client"
        then: "the correct status code is returned"
    }

    def "Delete a location: URL Client id exists: Delete location is successful"() {
        given: "the client id is in the url"
        when: "a request is made to delete the location for a client"
        then: "the correct status code is returned"
    }

    // Create a location
    def "Create a location: URL Client id does not exist"() {
        given: "the client id is not in the url"
        when: "a request is made to create a location for a client"
        then: "the correct status code is returned"
    }

    def "Create a location: URL Client id exists: Create fails given location client id is blank"() {
        given: "the client id is in the url"
        and: "the location's client id is blank"
        when: "a request is made to create a location for a client"
        then: "the correct status code is returned"
    }

    def "Create a location: URL Client id exists: Create fails given location client id and URL client id do not match"() {
        given: "the client id is in the url"
        and: "the location's client id does not match the URL client id"
        when: "a request is made to create a location for a client"
        then: "the correct status code is returned"
    }

    def "Create a location: URL Client id exists: Create location is successful"() {
        given: "the client id is in the url"
        and: "the location is valid"
        when: "a request is made to create a location for a client"
        then: "the correct status code is returned"
        and: "the correct location is returned"
    }

    // Update a location
    def "Update a location: URL Client id does not exist"() {
        given: "the client id is not in the url"
        when: "a request is made to update a location for a client"
        then: "the correct status code is returned"
    }

    def "Update a location: URL Client id exists: Update fails given location client id is blank"() {
        given: "the client id is in the url"
        and: "the location's client id is blank"
        when: "a request is made to update a location for a client"
        then: "the correct status code is returned"
    }

    def "Update a location: URL Client id exists: Update fails given location client id and URL client id do not match"() {
        given: "the client id is in the url"
        and: "the location's client id does not match the URL client id"
        when: "a request is made to update a location for a client"
        then: "the correct status code is returned"
    }

    def "Update a location: URL Client id exists: Update fails given create date is empty"() {
        given: "the client id is in the url"
        and: "the location's create date is empty"
        when: "a request is made to update a location for a client"
        then: "the correct status code is returned"
    }

    def "Update a location: URL Client id exists: Update fails given create date is after 'now'"() {
        given: "the client id is in the url"
        and: "the location's create date is after the current date and time"
        when: "a request is made to update a location for a client"
        then: "the correct status code is returned"
    }

    def "Update a location: URL Client id exists: Update location is successful"() {
        given: "the client id is in the url"
        and: "the location is valid"
        when: "a request is made to update a location for a client"
        then: "the correct status code is returned"
        and: "the updated location is returned"
    }
}