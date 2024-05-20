Feature: Create multiple deliveries for a Wave

  Scenario Outline: Create multiple deliveries for a wave using excel
    Given Give Username and Password to get Access Token
      | username  | integrator   |
      | password  | 3poXy$6o29   |
      | requestId | 5001597022-1 |
    And Get the delivery numbers to create multiple deliveries for a wave
    And Provide the proposed delivery date for a delivery
    And Give the source siteId for the delivery "1014"
    And Give the destination siteId for all the deliveries in a wave "<dstPlant>"
    And Give the warehouse number of the source siteId "114"
    And Give the name of a person creating a delivery "yash"
    And Get the date of creation of the delivery
    And Get the time of creation of the delivery
    And Provide the totalGdsMvtStat for the delivery "A"
    And Give the Excel file to get the items for a delivery "<excelFile>"
    And Give the sheet name "<sheetName>" and the number of items required in a delivery 7
    When Requester calls the outbound delivery endpoint to create new delivery for a wave
    Then Verify that status code be equal to 201
  Examples:
    | dstPlant | excelFile                  | sheetName     |
    | 4017     | Sorter_CreateDelivery.xlsx | DeliveryItems |
    | 4021     | Sorter_CreateDelivery.xlsx | DeliveryItems |

  Scenario: Get the dock assigned to destinations for which deliveries are created
    And Provide the DC of which docks are needed "1014"
    When Requester calls the master dock api to get the dock for destinations
    Then Verify that status code be equal to 200
    And Get the docks for destinations for which delivery is created

  Scenario: OptimiRequester calls the master dock api to get the dock for destinationsze the delivery created for a wave
    Given Give Username and Password to get Access Token
      | username | 1231459   |
      | password | Dmart@123 |
    And Get deliveries details and give vehicleType "1109" to create a payload for optimizing delivery
    When Requester calls the optimize delivery endpoint to optimize delivery for a wave
    Then Verify that status code be equal to 200