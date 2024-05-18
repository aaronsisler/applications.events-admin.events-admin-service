Feature: Application provides build information

  Scenario: Receive build information when requested
    Given the application is up
    When the info endpoint is called
    Then the info endpoint replies with the correct status
    And the info endpoint replies with the correct information