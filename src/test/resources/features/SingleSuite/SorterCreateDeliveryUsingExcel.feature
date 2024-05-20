Feature: Create delivery using excel file

#  Background: Generate access token from username and password
#    Given Give Username and Password to get Access Token
#      | username | 101201        |
#      | password | Dmart@1234567 |

#  Scenario: Create delivery for sorter using excel
#    Given Give Username and Password to get Access Token
#      | username  | integrator   |
#      | password  | 3poXy$6o29   |
#      | requestId | 5001597022-1 |
#    And Get the delivery number to create new delivery
#    And Provide the proposed delivery date for a delivery
#    And Give the source siteId for the delivery "1014"
#    And Give the destination siteId for the delivery "4017"
#    And Give the warehouse number of the source siteId "114"
#    And Give the name of a person creating a delivery "yash"
#    And Get the date of creation of the delivery
#    And Get the time of creation of the delivery
#    And Provide the totalGdsMvtStat for the delivery "A"
#    And Give the Excel file to get the items for a delivery "SorterDeliveryItems.xlsx"
#    And Give the sheet name to get the items for which delivery has to be created "DeliveryItems"
#    When Requester calls the outbound delivery endpoint to create new delivery for a wave
#    Then Verify that status code be equal to 201

  Scenario: Create delivery for sorter using excel
    Given Give Username and Password to get Access Token
      | username  | integrator   |
      | password  | 3poXy$6o29   |
      | requestId | 5001597022-1 |
    And Get the delivery number to create new delivery
    And Provide the proposed delivery date for a delivery
    And Give the source siteId for the delivery "1031"
    And Give the destination siteId for the delivery "4035"
    And Give the warehouse number of the source siteId "131"
    And Give the name of a person creating a delivery "yash"
    And Get the date of creation of the delivery
    And Get the time of creation of the delivery
    And Provide the totalGdsMvtStat for the delivery "A"
    And Give the Excel file to get the items for a delivery "Sorter_CreateDelivery.xlsx"
    And Give the sheet name "DeliveryItems" and the number of items required in a delivery 30
    When Requester calls the outbound delivery endpoint to create new delivery for a wave
    Then Verify that status code be equal to 201

  Scenario: Optimize the delivery created for a wave
    Given Give Username and Password to get Access Token
      | username | 444555    |
      | password | Dmart@123 |
    And Get the delivery number to optimize the delivery based on vehicle type
    And Give the dock number for the delivery to be optimized
    And Provide the vehicle type "1109" and vehicle registration number "" for the delivery to be optimized
    When Requester calls the optimize delivery endpoint to optimize delivery for a wave
    Then Verify that status code be equal to 200

  Scenario: Confirm deliveries for a Wave
    Given Give Username and Password to get Access Token
      | username | 444555    |
      | password | Dmart@123 |
    And Get the response from the optimize delivery API to confirm the wave
    And Give the status for confirming the wave "PLANNED"
    When Requester calls the confirm wave endpoint to optimize delivery for a wave
    Then Verify that status code be equal to 201

  Scenario: Assign vehicles for a delivery
    Given Give Username and Password to get Access Token
      | username | 444555    |
      | password | Dmart@123 |
    And Get the delivery number to assign vehicle to the delivery
    And Get the dock number from which items will be loaded into a vehicle
    And Provide the current driver for the vehicle
    And Give the vehicle type "1109" and vehicle registration number "VH543211" to assign vehicle to delivery
    When Requester calls the assign vehicle endpoint to assign vehicle for a delivery
    Then Verify that status code be equal to 200

#  Scenario: Create transfer order for a delivery
#    Given Give Username and Password to get Access Token
#      | username  | integrator   |
#      | password  | 3poXy$6o29   |
#      | requestId | 5001597022-1 |
#    And Provide the warehouse number of a site "114"
#    And Give the source plant id for which transfer order has to be created "1014"
#    And Give the destination plant id to which items has to be delivered "4017"
#    And Give the group delivery number for the transfer order "8144031899"
#    And Get the transfer order number to create TO for a delivery
#    And Give the movement type for a transfer order "850"
#    And Get the date of creation of the transfer order
#    And Get the time of creation of the transfer order
#    And Get the delivery number for which transfer has to be created
#    And Give the Excel file to get the items for a transfer order "Sorter_CreateTransferOrder.xlsx"
#    And Give the sheet name to get the items for which transfer order has to be created "TransferOrderItems"
#    When Requester calls the outbound transfer order endpoint to create new delivery for a wave
#    Then Verify that status code be equal to 201

  Scenario: Create transfer order for a delivery
    Given Give Username and Password to get Access Token
      | username  | integrator   |
      | password  | 3poXy$6o29   |
      | requestId | 5001597022-1 |
    And Provide the warehouse number of a site "131"
    And Give the source plant id for which transfer order has to be created "1031"
    And Give the destination plant id to which items has to be delivered "4035"
    And Give the group delivery number for the transfer order "8144031899"
    And Get the transfer order number to create TO for a delivery
    And Give the movement type for a transfer order "850"
    And Get the date of creation of the transfer order
    And Get the time of creation of the transfer order
    And Get the delivery number for which transfer has to be created
    And Give the Excel file to get the items for a transfer order "Sorter_CreateTransferOrder.xlsx"
    And Give the sheet name "TransferOrderItems" and the number of items required in a transfer order 30
    When Requester calls the outbound transfer order endpoint to create new delivery for a wave
    Then Verify that status code be equal to 200

  Scenario: Get all items in a Wave
    Given Give Username and Password to get Access Token
      | username | 444555    |
      | password | Dmart@123 |
    And Provide the wave id to get the list of items
    When Requester calls the wave-pick-planner all wave items api to get the list of items
    Then Verify that status code be equal to 200
    And Get the items present in a wave to assign them to pickers

  Scenario: Check-In forklift Picker with valid data
    Given Give Username and Password for Access Token
      | username | 444555    |
      | password | Dmart@123 |
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 663ca26694b1a330949cd19d |
      | checkinTime        | 06-02-2024 11:24         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1035                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"

  Scenario: Verify that forklift Picker is checked-in
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify the Checked-In Picker details

  Scenario: Check-In BOPT Picker with valid data
    Given Give Username and Password for Access Token
      | username | 444555    |
      | password | Dmart@123 |
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 661cbb7b7c3529634aa79b11 |
      | checkinTime        | 06-02-2024 11:24         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1035                      |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"

  Scenario: Verify that manual Picker is checked-in
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify the Checked-In Picker details

  Scenario: Assign items in a wave to picker
    Given Give Username and Password to get Access Token
      | username | 10311031  |
      | password | Dmart@123 |
    And Get the siteId for which wave is created
    And Get the current time at which items are assigned to picker
    And Get the waveId from which items has to be assigned to picker
    And Get the list of conveyable and non-conveyable items to be assigned to pickers
    When Requester calls wave pick planner endpoint to assign items to picker
    Then Verify that status code be equal to 200








