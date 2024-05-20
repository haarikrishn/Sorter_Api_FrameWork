Feature: Verify the functionality of Get TOs

  Background: Generate Bearer Token from Username and Password
    Given Give Username and Password for Access Token
      | username | 101201      |
      | password | Dmart@12345 |

  @PickerPresence
  Scenario: Verify the functionality of Picker Presence
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify the Checked-In Picker details

#  @PickerPresenceForNegScenario
  Scenario: Verify the functionality of Picker Presence
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify that Picker is not Checked-In to DC