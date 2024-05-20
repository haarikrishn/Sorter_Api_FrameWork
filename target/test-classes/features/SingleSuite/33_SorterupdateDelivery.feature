Feature: verify the functionality of UpdateDeliveryInSorter in sorter
  Background: access token id by
    Given Give Username and Password for Access Token
      | username  | integrator       |
      | password  | 3poXy$6o29       |
      | requestId | 8100000437-02    |

  @UpdateDeliveryInSorter
  Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
    When   send UpdateDeliveryInSorter End point to update the data in sorter
      | dlvNumber       |         |
      | deleteDlv       | 1       |
      | changedBy       | krishna |
      | totalGdsMvtStat | A       |
    And add items under dlvItems in UpdateDeliveryInSorter
      | itemSeq | proposedDlvQty | deleteItem |
      | 000010  | 15             |            |
      | 000020  | 15             |            |
#      | 000030  | 20             | 1          |
#      | 000040  | 10             | 1          |
#      | 000050  | 10             | 1          |

#    Then verify data  is Updated or not in sorter in updateDelivery
    And  verify that status code be equal to "201"


  @getSorterUpdateDeliveryWithoutDeleteItemAllinone
  Scenario Outline: Verify the Functionality of Get Delivery by Delivery Number in sorter
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
      | username | 1231459   |
      | password | Dmart@123 |
    And Provide delivery number "<dlvNumber>"to get delivery Details in sorterOne
    When User calls the get delivery by to get delivery detailsOne
    And  verify delivery  is updated  or not in sorterOne
#    Then verify that status code be equal to "200"
    Examples:
      | dlvNumber |
      |           |
