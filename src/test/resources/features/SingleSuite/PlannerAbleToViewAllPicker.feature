Feature: Verify the functionality of Get TOs

  Background: Generate Bearer Token from Username and Password
    Given Give Username and Password for Access Token
      | username | 101201        |
      | password | Dmart@1234567 |

#  @AllAvailablePickers
  Scenario: Get all the online picker.
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify that Pickers are available in Distribution Center to pick the Articles for wave type deliveries



#    And Verify the Checked-In Pickers details for wave type deliveries

#  @AllAvailablePickers
  Scenario: Call the API after the picker has checked-out
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify that Picker is not present in DC to pick wave type deliveries

#  @AllAvailablePickers
  Scenario: Check all the important fields in response payload
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And verify that schema should be equal "PlannerAbleToViewAllPickerSchema.json"

#  @AllAvailablePickers
  Scenario: Check the profileType field for Forklift picker
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And verify that schema should be equal "PlannerAbleToViewAllPickerSchema.json"
    And Verify the Checked-In Pickers details for wave type deliveries

#  @AllAvailablePickers
  Scenario: Check the profileType field for Manual picker
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And verify that schema should be equal "PlannerAbleToViewAllPickerSchema.json"
    And Verify the Checked-In Pickers details for wave type deliveries

#  @AllAvailablePickers
  Scenario: Check the profileType field for BOPT picker
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And verify that schema should be equal "PlannerAbleToViewAllPickerSchema.json"
    And Verify the Checked-In Pickers details for wave type deliveries

#  @AllAvailablePickers
  Scenario: Check the attendeeStatus field after the picker has checked-in
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And verify that schema should be equal "PlannerAbleToViewAllPickerSchema.json"
    And Verify the Checked-In Pickers details for wave type deliveries

  @AllAvailablePickers
  Scenario: Check the activeSince field after the picker has checked-in.
    When Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers
    Then verify that status code be equal to "200"
    And verify that schema should be equal "PlannerAbleToViewAllPickerSchema.json"
    And Verify that activeSince field value for just checked-in picker is "Just Now"

