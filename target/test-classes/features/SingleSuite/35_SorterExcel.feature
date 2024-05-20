Feature: Verify the functionality of Create delivery

  @SorterCreateDeliveryExcelDT
  Scenario: Verify the functionality of CreateOneDeliveryWithListOfItemsUsingDT
    Given Give Username and Password for Access Token
      | username  | integrator       |
      | password  | 3poXy$6o29       |
      | requestId | 0000001376364111 |
    And Assigning Main Mandatory fields to CreateOneDeliveryWithListOfItemsUsingDTInSorter using ExcelDataTable
      | srcSiteId       | 1034    |
      | dstSiteId       | 4017    |
      | whNumber        | 134     |
      | createdBy       | krishna |
      | totalGdsMvtStat | A       |
    And Provide dlvItemNum to CreateOneDeliveryWithListOfItemsUsingDTInSorter using excel sheetDT
#      | fileName  | ./src/test/resources/TestData.Canary/Sorter3data.xlsx |
    | fileName  | ./src/test/resources/TestData.Canary/Sorter2DT.xlsx |
#      | fileName  | ./src/test/resources/TestData.Canary/Sorter2DT (1).xlsx |
      | sheetName | TestDataDT                                              |
#      | fileName  | ./src/test/resources/features/TestData/Canary/refill.xlsx |
#      | sheetName | refillSheet                                               |
    Then verify that status code be equal to "201"

