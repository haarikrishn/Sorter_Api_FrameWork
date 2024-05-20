Feature: verify the functionality of UpdateDeliveryInSorter in sorter

  @SorterCreateDeliveryAllFlowNew11
  Scenario: Verify the functionality of CreateOneDeliveryWithListOfItemsUsingDT
    Given Give Username and Password for Access Token
      | username  | integrator       |
      | password  | 3poXy$6o29       |
      | requestId |8100000428-3      |
    And Assigning Main Mandatory fields to CreateOneDeliveryWithListOfItemsUsingDTInSorter
      | srcSiteId       | 1033  |
      | dstSiteId       | 4042  |
      | whNumber        | 133   |
      | createdBy       | krish |
      | totalGdsMvtStat | A     |
    And Provide dlvItemNum to CreateOneDeliveryWithListOfItemsUsingDTInSorter
      | itemSeq | stoNum     | stoLineNum | ean          | mrp | matNum    | itemDesc             | matGrp    | catInd | stLoc | batch      | proposedDlvQty | caselot | uom | itemWt | uomWt | itemVol | uomVol | pickStatus | storageType | leQty |
      | 0001    | 5102747694 | 10         | 840222000156 | 182 | 200000346 | LG HING POWDER(100G) | 2SPOSPXXX | S      | 3000  | 1C40050182 | 100            | 100     | EA  | 0.132  | KG    | 0.012   | FT3    | A          | HRS         | 20    |
      | 0002    | 5102747694 | 20         | 840222000149 | 92  | 200000347 | LG HING POWDER(50G)  | 2SPOSPXXX | S      | 3000  | 1C40050092 | 200            | 200     | EA  | 0.071  | KG    | 0.007   | FT3    | A          | HRS         | 20    |
#      | 000030  | 5102747694 | 000030     | 8906027120665 | 70.00  | 200004280 | SAMS WHITE COOKING VINEGAR-700ML | 2FAPRVVIN | S      | 3000  | JC40020070 | 24.000         | 24.00   | EA  | 0.750  | KG    | 0.043   | FT3    | A          | HRS         | 22.000 |
#      | 000040  | 5102747694 | 000040     | 8901058007220 | 60.00  | 200005944 | MAGGI MASALA MAGIC(72G)          | 2SPOSPXXX | S      | 3000  | 1B40130060 | 60.000         | 60.00   | EA  | 0.100  | KG    | 0.022   | FT3    | A          | HRS         | 23.000 |
#      | 000050  | 5102747694 | 000050     | 8906053810264 | 110.00 | 200006642 | PAAWAK NATURAL JAGGERY JAR-500G  | 2FANSWGUD | S      | 3000  | EC40020110 | 96.000         | 24.00   | EA  | 0.500  | KG    | 0.043   | FT3    | A          | HRS         | 24.000 |


    When User calls the to createDelivery End point to   CreateOneDeliveryWithListOfItemsUsingDTInSoter
    Then verify that status code be equal to "201"


  @OptimizeDeliveryFlowNew12
  Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
    Given Give Username and Password for Access Token
      | username | 1231458  |
      | password | Dmart@123 |
    When   send AdjustDelivery End point to AdjustDelivery  data in sorter
      | dlvNumber |    |
      | dock      | D1 |
    And add items under vehicles in AdjustDeliveryInSorter
      | vehicleType | vehicleRegNum |
      | 1109        |               |
#    | 1109        |               |
    Then verify data  is Updated or not in AdjustDelivery sorter
    And  verify that status code be equal to "200"

#8100000550

  @ConfirmDeliveryFlowNew13
  Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
#      | username | 1231457   |
#      | password | Dmart@123 |
      | username | 1231458   |
      | password | Dmart@123 |
    When   send confirmDelivery End point to confirmDelivery  data in sorter
      | siteId     | 1033 |
      | deliveries |      |
    Then verify data  is Updated or not in confirmDelivery sorter
    And  verify that status code be equal to "201"


  @AssignVehicleNew14
  Scenario: Assign Vehicle for a Delivery with valid data
    Given Give Username and Password for Access Token
      | username | 1231458   |
      | password | Dmart@123 |

    And Provide dock vehicle type and vehicle number for a delivery to be assigned vehicle
      | dlvNumber     |            |
      | dock          | D1         |
      | vehicleType   | 1109       |
      | vehicleRegNum | AP12V3475 |
      | currentDriver |            |
    When User calls the assign vehicle's endpoint to assign vehicle for a delivery
    Then verify that status code be equal to "200"

  @CreateTOForOriginalDeliveryFlowNew15
  Scenario: Verify the functionality of CreateTOWithListOftransferOrderItems

    Given Give Username and Password for Access Token
      | username  | integrator       |
      | password  | 3poXy$6o29       |
      | requestId | 765423           |
    And Assigning Main Mandatory fields to CreateTOWithListOftransferOrderItems
      | whNumber     | 133        |
      | srcPlant     | 1033       |
      | destPlant    | 4042       |
      | grpDlv       | 0009035909 |
      | movementType | 850        |
    And Provide transferOrderItems to CreateTOWithListOftransferOrderItems
      | txfOrdItemSeq | matNumber | matDesc              | ean          | batchNum   | srcStorageType | srcStorageBin | srcQty  | caselot | uom | mrp    | itemWeight | itemVolume | itemType | binSeq | bbDate   | storageLoc | destStorageType | palletQty | availableBinQty |
      | 0001          | 200000346 | LG HING POWDER(100G) | 840222000156 | 1C40050182 | HRS            | A01-24-2C     | 100.000 | 100.00  | EA  | 182.00 | 0.132      | 0.012      | ZBGR     | 191    | 20240412 | 3000       | 200             | 600       | 100             |
      | 0002          | 200000347 | LG HING POWDER(50G)  | 840222000149 | 1C40050092 | HRS            | A01-25-1B     | 200.000 | 200.00  | EA  | 92.00  | 0.071      | 0.007      | ZBGR     | 194    | 20240412 | 3000       | 200             | 1600      | 200             |
#      | 0003          | 200004280 | SAMS WHITE COOKING VINEGAR-700ML | 8906027120665 | JC40020070 | HRS            | A014-01-1B    | 24.000  | 24.00   | EA  | 70.00  | 0.750      | 0.043      | ZBGR     | 4682   | 20250910 | 3000       | 200             | 720.000   | 24              |
#      | 0004          | 200005944 | MAGGI MASALA MAGIC(72G)          | 8901058007220 | 1B40130060 | HRS            | A010-06-1B    | 60.000  | 60.00   | EA  | 60.00  | 0.100      | 0.022      | ZBGR     | 3322   | 20241031 | 3000       | 200             | 1680.000  | 60              |
#      | 0005          | 200006642 | PAAWAK NATURAL JAGGERY JAR-500G  | 8906053810264 | EC40020110 | HRS            | A018-23-1A    | 48.000  | 24.00   | EA  | 110.00 | 0.500      | 0.043      | ZBGR     | 6473   | 20250309 | 3000       | 200             | 792.000   | 96              |


    When User calls the to create TO End point to CreateTOWithListOftransferOrderItems
    Then verify that status code be equal to "201"
#https://canary.api.meradmart.com/wms/wave-planner/delivery/details/v1?dlvNumber=8111001533


  @getDeliveryAllFlow5
  Scenario Outline: Verify the Functionality of Get Delivery by Delivery Number in sorter
    Given Give Username and Password for Access Token
      | username | 1231459   |
      | password | Dmart@123 |
    And Provide delivery number "<dlvNumber>"to get delivery Details in sorter
    When User calls the get delivery by to get delivery details
    Then verify that status code be equal to "200"
    Examples:
      | dlvNumber |
      |           |