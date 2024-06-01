Feature: Clients are able to be created, queried, updated, and deleted

  Scenario: Create multiple valid clients
    Given the application is up
    And the first client is valid
    And the second client is valid
    When the client creation endpoint is called
    Then the endpoint replies with the correct status
    And the endpoint replies with the created clients
    And the created clients were saved to the database