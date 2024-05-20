Feature: Verify the functionality of Picker Check-In

  Background: Generate Bearer Token from Username and Password
    Given Give Username and Password for Access Token
      | username | 101202      |
      | password | Dmart@12345 |

##  @PickerCheckIn
#  Scenario: Check-In Picker with valid data
#    And Get Picker's details to Check-In Picker into DC
#    When User calls the Picker Check-In's endpoint to check-in the Picker
#    Then verify that status code be equal to "200"

  @PickerCheckIn
  Scenario: Check-In Picker with valid data
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 5fbe3a3d5f8051588f3bc46b |
      | checkinTime        | 03-04-2024 14:09         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1014                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"

#  @PickerCheckIn
#  Scenario: Check-In assigned picker to another siteId
#    And Provide Picker's details using DataTable to Check-In Picker into DC
#      | attendeeId         | 659fe5135c2fd619b403285b |
#      | checkinTime        | 10-01-2024 18:55         |
#      | deviceSerialNumber | 1234567890               |
#      | siteId             | 1039                     |
#    When User calls the Picker Check-In's endpoint to check-in the Picker
#    Then verify that status code be equal to "200"

#  @PickerCheckIn
  Scenario Outline: Check-In Picker with invalid attendeeId
    And Provide Picker's details to Check-In Picker into DC "<attendeeId>" "<checkinTime>" "<deviceSerialNumber>" "<siteId>"
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "500"
    Examples:
      | attendeeId                    | checkinTime      | deviceSerialNumber | siteId |
      | null                          | 04-01-2024 16:13 | 1234567890         | 1014   |
      |                               | 04-01-2024 16:13 | 1234567890         | 1014   |
#      | 6577f98130e33d33903e7876@#$%* | 04-01-2024 16:13 | 1234567890         | 1014   |

#  @PickerCheckIn
#  Scenario: Check-In Picker with attendeeId not created
#    And Provide Picker's details using DataTable to Check-In Picker into DC
#      | attendeeId         | 659fd2585c2fd619b4032812@#$ |
#      | checkinTime        | 10-01-2024 17:11            |
#      | deviceSerialNumber | 1234567890                  |
#      | siteId             | 1014                        |
#    When User calls the Picker Check-In's endpoint to check-in the Picker
#    Then verify that status code be equal to "404"

#  @PickerCheckIn
  Scenario Outline: Check-In Picker with invalid checkinTime
    And Provide Picker's details to Check-In Picker into DC "<attendeeId>" "<checkinTime>" "<deviceSerialNumber>" "<siteId>"
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "400"
    Examples:
      | attendeeId               | checkinTime         | deviceSerialNumber | siteId |
      | 659fd2585c2fd619b4032812 | 10-01-2024 16:45@#$ | 1234567890         | 1014   |

#  @PickerCheckIn
  Scenario Outline: Check-In Picker with invalid siteId
    And Provide Picker's details to Check-In Picker into DC "<attendeeId>" "<checkinTime>" "<deviceSerialNumber>" "<siteId>"
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "500"
    Examples:
      | attendeeId               | checkinTime      | deviceSerialNumber | siteId  |
      | 65c3a47a1cf18c637f8edaa5 | 09-01-2024 18:45 | 1234567890         |         |
      | 65c3a47a1cf18c637f8edaa5 | 09-01-2024 18:45 | 1234567890         | null    |
      | 65c3a47a1cf18c637f8edaa5 | 09-01-2024 18:45 | 1234567890         | 1014@#$ |

#  @PickerCheckIn
  Scenario: Check-In Picker to different siteId(bearer token 1014, Checkin-Officer 1014 picker 1014, siteId 1039)
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 6600ffcaeccaa054e14cbdc3 |
      | checkinTime        | 12-01-2024 12:30         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1034                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"
    Given Give Username and Password for Access Token
      | username | 101201     |
      | password | Dmart@12345 |
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify that Picker is not Checked-In to DC

#  @PickerCheckIn
  Scenario: Check-In Picker of different siteID(bearer token 1014, siteId 1014, picker 1039)
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 5fbe3a3d5f8051588f3bc46b |
      | checkinTime        | 16-01-2024 14:17         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1014                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"

#  @PickerCheckIn
  Scenario: Check-In Picker without attendeeId
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | checkinTime        | 11-01-2024 18:14 |
      | deviceSerialNumber | 1234567890       |
      | siteId             | 1014             |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "500"

  #  @PickerCheckIn doubt
#  Scenario: Check-In Picker without checkinTime
#    And Provide Picker's details using DataTable to Check-In Picker into DC
#      | attendeeId         | 6577f98130e33d33903e7876 |
#      | checkinTime        | 10-01-2024 17:11         |
#      | deviceSerialNumber | 1234567890               |
#      | siteId             | 1014                     |
#    When User calls the Picker Check-In's endpoint to check-in the Picker
#    Then verify that status code be equal to "400 0r 500"

#  @PickerCheckIn
  Scenario: Check-In Picker without deviceSerialNumber
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId  | 659fd2585c2fd619b4032812 |
      | checkinTime | 04-01-2024 18:15         |
      | siteId      | 1014                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "500"

#  @PickerCheckIn
  Scenario: Check-In Picker without siteId
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 659fd2585c2fd619b4032812 |
      | checkinTime        | 04-01-2024 16:13         |
      | deviceSerialNumber | 1234567890               |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "500"

#  @PickerCheckIn
  Scenario: Check-In already Checked-in picker
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 6577f98130e33d33903e7876 |
      | checkinTime        | 11-01-2024 18:32         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1014                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify the Checked-In Picker details
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 6577f98130e33d33903e7876 |
      | checkinTime        | 11-01-2024 18:32         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1014                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "409"

#  @PickerCheckIn
  Scenario: Check-In same Picker to Two different Distribution Centers without Checking-Out
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 6600ffcaeccaa054e14cbdc3 |
      | checkinTime        | 11-01-2024 18:37         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1014                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify the Checked-In Picker details
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 6600ffcaeccaa054e14cbdc3 |
      | checkinTime        | 11-01-2024 18:37         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1034                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Given Give Username and Password for Access Token
      | username | 101201      |
      | password | Dmart@12345 |
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify that Picker is not Checked-In to DC

#  @PickerCheckIn
  Scenario: Check-In Picker with pending Task to other siteId
    And Get dcId to get the checked-in Picker's details
    When User calls the Get Picker endpoint to get checked-in Pickers
    Then verify that status code be equal to "200"
    And Provide Picker's details using DataTable to Check-In Picker into DC
      | attendeeId         | 659f934f5c2fd619b4032780 |
      | checkinTime        | 11-01-2024 17:11         |
      | deviceSerialNumber | 1234567890               |
      | siteId             | 1039                     |
    When User calls the Picker Check-In's endpoint to check-in the Picker
    Then verify that status code be equal to "200"
    Given Give Username and Password for Access Token
      | username | 102202    |
      | password | Dmart@123 |
    When User calls the Picker Presence endpoint to get the checked-in Pickers
    Then verify that status code be equal to "200"
    And Verify that Picker is not Checked-In to DC
