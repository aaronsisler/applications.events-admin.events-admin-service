Feature: Saying Hello!
  Users should be able to submit GET requests to the service and it should response back correctly

  Scenario: User wants to say hello and know the server time
    When a user calls the service to say hello
    Then the server responses
    And the server gives the correct date and time