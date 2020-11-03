Feature: Verify Trello Authentication

  Scenario: Verify Key to get authentication page
    Given I have a valid Key
    When I make an http get call with valid Key to get an authentication page
    Then I must receive a status code 200

  Scenario: Verify invalid Key to get authentication page
    Given I have a invalid Key
    When I make an http get call with invalid Key to get an authentication page
    Then I must receive a status code 400
    And Must contain the message "App not found"

  Scenario: Verify valid token
    Given I have a valid token
    When I make an http get call with valid token to validation token
    Then I must receive a status code 200
    And Must contain Json Object

  Scenario: Verify invalid token
    Given I have a invalid token
    When I make an http get call with invalid token to validation token
    Then I must receive a status code 400
    And Must contain the message "invalid token"
