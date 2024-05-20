Feature: Generate Access Token

  @Authenticate
  Scenario: Verify authentication functionality with valid username and password
    Given Give Username and Password for Access Token
      | username | 101201      |
      | password | Dmart@12345 |
    When Send username and password to get accessToken
    Then verify that status code be equal to "200"