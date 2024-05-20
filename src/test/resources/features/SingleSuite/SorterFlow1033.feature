Feature: verify the functionality of UpdateDeliveryInSorter in sorter

  @SorterCreateDelivery1033
  Scenario: create delivery
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
      | itemSeq | stoNum     | stoLineNum | ean          | mrp    | matNum    | itemDesc             | matGrp    | catInd | stLoc | batch      | proposedDlvQty | caselot | uom | itemWt | uomWt | itemVol | uomVol | pickStatus | storageType | leQty |
      | 000010  | 5102316755 | 000010     | 840222000163 | 356.00 | 200000998 | LG HING POWDER-200 G | 2SPOSPXXX | S      | 3000  | 1G30040356 | 12.000         | 12.00   | EA  | 2.264  | KG    | 0.311   | FT3    | A          | HRS         | 288.000    |
#      | 0002    | 5102747694 | 20         | 840222000149 | 92  | 200000347 | LG HING POWDER(50G)  | 2SPOSPXXX | S      | 3000  | 1C40050092 | 200            | 200     | EA  | 0.071  | KG    | 0.007   | FT3    | A          | HRS         | 20    |
#      | 000030  | 5102747694 | 000030     | 8906027120665 | 70.00  | 200004280 | SAMS WHITE COOKING VINEGAR-700ML | 2FAPRVVIN | S      | 3000  | JC40020070 | 24.000         | 24.00   | EA  | 0.750  | KG    | 0.043   | FT3    | A          | HRS         | 22.000 |
#      | 000040  | 5102747694 | 000040     | 8901058007220 | 60.00  | 200005944 | MAGGI MASALA MAGIC(72G)          | 2SPOSPXXX | S      | 3000  | 1B40130060 | 60.000         | 60.00   | EA  | 0.100  | KG    | 0.022   | FT3    | A          | HRS         | 23.000 |
#      | 000050  | 5102747694 | 000050     | 8906053810264 | 110.00 | 200006642 | PAAWAK NATURAL JAGGERY JAR-500G  | 2FANSWGUD | S      | 3000  | EC40020110 | 96.000         | 24.00   | EA  | 0.500  | KG    | 0.043   | FT3    | A          | HRS         | 24.000 |


    When User calls the to createDelivery End point to   CreateOneDeliveryWithListOfItemsUsingDTInSoter
    Then verify that status code be equal to "201"


  @OptimizeDelivery1133
  Scenario: optimize delivery
    Given Give Username and Password for Access Token
      | username | 1231459   |
      | password | Dmart@123 |
    When   send AdjustDelivery End point to AdjustDelivery  data in sorter
      | dlvNumber |    |
      | dock      | D2 |
    And add items under vehicles in AdjustDeliveryInSorter
      | vehicleType | vehicleRegNum |
      | 1109        |               |
#    | 1109        |               |
#    Then verify data  is Updated or not in AdjustDelivery sorter
    And  verify that status code be equal to "200"

#8100000550

  @ConfirmDelivery1233
  Scenario: confirm delivery
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
#      | username | 1231457   |
#      | password | Dmart@123 |
      | username | 1231459   |
      | password | Dmart@123 |
    When   send confirmDelivery End point to confirmDelivery  data in sorter
      | siteId     | 1034 |
      | deliveries |      |
#    Then verify data  is Updated or not in confirmDelivery sorter
    And  verify that status code be equal to "201"




  @CreateTOForOriginalDelivery1333
  Scenario: create TO

    Given Give Username and Password for Access Token
      | username  | integrator       |
      | password  | 3poXy$6o29       |
      | requestId | 765423           |
    And Assigning Main Mandatory fields to CreateTOWithListOftransferOrderItems
      | whNumber     | 134        |
      | srcPlant     | 1034       |
      | destPlant    | 4017       |
      | grpDlv       | 0018904784 |
      | movementType | 850        |
    And Provide transferOrderItems to CreateTOWithListOftransferOrderItems
      | txfOrdItemSeq | matNumber | matDesc              | ean          | batchNum   | srcStorageType | srcStorageBin | srcQty | caselot | uom | mrp    | itemWeight | itemVolume | itemType | binSeq | bbDate   | storageLoc | destStorageType | palletQty | availableBinQty |
      | 000010        | 200000998 | LG HING POWDER-200 G | 840222000163 | 1G30040356 | HRS            | A01-24-2C     | 12.000 | 12.00   | EA  | 356.00 | 2.264      | 0.311      | ZBGR     | 743    | 20240830 | 3000       | 200             | 600.000   | 12              |
#      | 0002          | 200000347 | LG HING POWDER(50G)  | 840222000149 | 1C40050092 | HRS            | A01-25-1B     | 200.000 | 200.00  | EA  | 92.00  | 0.071      | 0.007      | ZBGR     | 194    | 20240412 | 3000       | 200             | 1600      | 200             |
#      | 0003          | 200004280 | SAMS WHITE COOKING VINEGAR-700ML | 8906027120665 | JC40020070 | HRS            | A014-01-1B    | 24.000  | 24.00   | EA  | 70.00  | 0.750      | 0.043      | ZBGR     | 4682   | 20250910 | 3000       | 200             | 720.000   | 24              |
#      | 0004          | 200005944 | MAGGI MASALA MAGIC(72G)          | 8901058007220 | 1B40130060 | HRS            | A010-06-1B    | 60.000  | 60.00   | EA  | 60.00  | 0.100      | 0.022      | ZBGR     | 3322   | 20241031 | 3000       | 200             | 1680.000  | 60              |
#      | 0005          | 200006642 | PAAWAK NATURAL JAGGERY JAR-500G  | 8906053810264 | EC40020110 | HRS            | A018-23-1A    | 48.000  | 24.00   | EA  | 110.00 | 0.500      | 0.043      | ZBGR     | 6473   | 20250309 | 3000       | 200             | 792.000   | 96              |

#propseddlvqty/caselot*propseddlvqty=avilqty
    When User calls the to create TO End point to CreateTOWithListOftransferOrderItems
    Then verify that status code be equal to "201"
#https://canary.api.meradmart.com/wms/wave-planner/delivery/details/v1?dlvNumber=8111001533

  @AssignVehicle1433
  Scenario: Assign Vehicle for a Delivery with valid data
    Given Give Username and Password for Access Token
      | username | 101201     |
      | password | Dmart@1234567 |

    And Provide dock vehicle type and vehicle number for a delivery to be assigned vehicle
      | dlvNumber     |            |
      | dock          | D1         |
      | vehicleType   | 1109       |
      | vehicleRegNum | MH04AY1109 |
      | currentDriver |            |
    When User calls the assign vehicle's endpoint to assign vehicle for a delivery
    Then verify that status code be equal to "200"