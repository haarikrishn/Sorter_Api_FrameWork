Feature: verify the functionality of UpdateDeliveryInSorter in sorter

  @SorterCreateDeliveryAllFlow
  Scenario: Verify the functionality of CreateOneDeliveryWithListOfItemsUsingDT
    Given Give Username and Password for Access Token
      | username  | integrator       |
      | password  | 3poXy$6o29       |
      | requestId |8100000428-3      |
    And Assigning Main Mandatory fields to CreateOneDeliveryWithListOfItemsUsingDTInSorter
      | srcSiteId       | 1034  |
      | dstSiteId       | 4017  |
      | whNumber        | 134   |
      | createdBy       | krish |
      | totalGdsMvtStat | A     |
    And Provide dlvItemNum to CreateOneDeliveryWithListOfItemsUsingDTInSorter
      | itemSeq | stoNum     | stoLineNum | ean          | mrp | matNum    | itemDesc             | matGrp    | catInd | stLoc | batch      | proposedDlvQty | caselot | uom | itemWt | uomWt | itemVol | uomVol | pickStatus | leQty | storageType |
      | 0001    | 5102747694 | 10         | 840222000156 | 182 | 200000346 | LG HING POWDER(100G) | 2SPOSPXXX | S      | 3000  | 1C40050182 | 100            | 100     | EA  | 0.132  | KG    | 0.012   | FT3    | A          | 20    | HRS         |
