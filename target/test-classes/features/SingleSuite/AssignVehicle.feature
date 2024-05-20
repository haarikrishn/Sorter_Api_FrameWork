Feature: Verify the functionality of Assign Vehicle

  Background: Generate Bearer Token from Username and Password
    Given Give Username and Password for Access Token
      | username | 1231459   |
      | password | Dmart@123 |


  @VehicleCheckIn
  Scenario: Assign Vehicle for a Delivery with valid data
    And provide all the driver and truck details
      | driverMobile  | 7654546466               |
      | driverName    | hk                       |
      | goingToCity   | Ambernath                |
      | vehicleNumber | AP12AB1234               |
      | vehicleType   | 1109                     |
      | vendorCode    | 0000405838               |
      | vendorName    | S V ROADLINES            |
      | onContract    |                          |
      | vehicleTypeId | 5f2d6e7364b1a54dd66bec86 |
      | vendorId      | 606c3d4e8079335e616e30df |
      | isManualEntry | true                     |
      | invoiceNumber |                          |
    When User calls the assign vehicle's endpoint to VehicleCheckIn for a delivery
    Then verify that status code be equal to "201"

  @AssignVehicle
  Scenario: Assign Vehicle for a Delivery with valid data
   And Provide dock vehicle type and vehicle number for a delivery to be assigned vehicle
     | dlvNumber     |            |
     | dock          | B2         |
     | vehicleType   | 1109       |
     | vehicleRegNum | AP12AB1234 |
     | currentDriver | krish      |
    When User calls the assign vehicle's endpoint to assign vehicle for a delivery
    Then verify that status code be equal to "200"