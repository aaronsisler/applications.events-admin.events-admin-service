Feature: Clients are able to be created

  Scenario: Create multiple valid clients
    Given the application is up
    And the first client is valid
    And the second client is valid
    When the client creation endpoint is called with the clients
    Then the endpoint replies with the correct success status
    And the endpoint replies with the newly created clients
    And the newly created clients were saved to the database

  @Disabled
  Scenario: Create client with invalid data
    Given the application is up
    And the client is invalid
    When the client creation endpoint is called with the invalid client
    Then the endpoint replies with a bad request status
    And the client was not saved to the database