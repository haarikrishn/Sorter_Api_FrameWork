Feature: Get  delivery using GetListofDeliveriesEndPoint
  Background: access token id by
    Given Give Username and Password for Access Token
      | username | 123457      |
      | password | Welcome@123 |

  @verifyDelivernumberPresentOrNotInSorter
  Scenario: verify the functionality of GetListofDeliveriesEndpoint in sorter
    When   send GetDelivery End point to get the data in sorter
      | srcSiteId | 1039    |
      | dlvStatus | CREATED |
    Then verify delivernumber is present or not in sorter
    And  verify that status code be equal to "200"

