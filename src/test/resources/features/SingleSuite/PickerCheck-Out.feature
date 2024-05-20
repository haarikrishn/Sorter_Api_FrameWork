Feature: Verify the functionality of Picker Check-Out

  Background: Generate Bearer Token from Username and Password
    Given Give Username and Password for Access Token
      | username | 101201        |
      | password | Dmart@1234567 |

  @PickerCheck-Out
  Scenario: Check-Out Picker with valid attendeeId
    And Provide attendeeId to check-out the Picker
      | attendeeId | 65c3a47a1cf18c637f8edaa5 |
    When User calls the Picker Check-Out endpoint to check-out the Picker
    Then verify that status code be equal to "200"

#  @PickerCheck-Out
  Scenario: Check-Out Picker with valid attendeeId
    And Get attendeeId to check-out the Picker
    When User calls the Picker Check-Out endpoint to check-out the Picker
    Then verify that status code be equal to "200"

#  @PickerCheck-Out
  Scenario: Check-Out Picker without Checking-In
    And Provide attendeeId to check-out the Picker
      | attendeeId | 659fe5135c2fd619b403285b |
    When User calls the Picker Check-Out endpoint to check-out the Picker
    Then verify that status code be equal to "404"

#  @PickerCheck-Out
  Scenario Outline: Check-Out Picker with invalid attendeeId's
    And Provide attendeeId to check-out the Picker "<attendeeId>"
    When User calls the Picker Check-Out endpoint to check-out the Picker
    Then verify that status code be equal to "404"
  Examples:
    | attendeeId                   |
    | null                         |
    |                              |
    | 659fe5135c2fd619b403285b@#$& |

#  @PickerCheck-Out Critical Bug
  Scenario: Check-Out Picker of different siteId
    And Provide attendeeId to check-out the Picker
      | attendeeId | 5fbe3a3d5f8051588f3bc46b|
    When User calls the Picker Check-Out endpoint to check-out the Picker
    Then verify that status code be equal to "404"

#  @PickerCheck-Out Critical Bug
  Scenario: Check-Out Picker with Pending tasks
    And Provide attendeeId to check-out the Picker
      | attendeeId | 659fe5135c2fd619b403285b |
    When User calls the Picker Check-Out endpoint to check-out the Picker
    Then verify that status code be equal to "400"