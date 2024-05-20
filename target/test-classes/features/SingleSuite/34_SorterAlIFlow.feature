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
      | itemSeq | stoNum     | stoLineNum | ean           | mrp | matNum    | itemDesc                         | matGrp    | catInd | stLoc | batch      | proposedDlvQty | caselot | uom | itemWt | uomWt | itemVol | uomVol | pickStatus | leQty | storageType |
      | 0001    | 5102747694 | 10         | 840222000156  | 182 | 200000346 | LG HING POWDER(100G)             | 2SPOSPXXX | S      | 3000  | 1C40050182 | 100            | 100     | EA  | 0.132  | KG    | 0.012   | FT3    | A          | 20    | HRS         |
      | 0002    | 5102747694 | 20         | 840222000149  | 92  | 200000347 | LG HING POWDER(50G)              | 2SPOSPXXX | S      | 3000  | 1C40050092 | 200            | 200     | EA  | 0.071  | KG    | 0.007   | FT3    | A          | 20    | HRS         |
      | 0003    | 5102747694 | 30         | 8906027120665 | 70  | 200004280 | SAMS WHITE COOKING VINEGAR-700ML | 2FAPRVVIN | S      | 3000  | JC40020070 | 24             | 24      | EA  | 0.75   | KG    | 0.043   | FT3    | A          | 20    | HRS         |
      | 0004    | 5102747694 | 40         | 8901058007220 | 60  | 200005944 | MAGGI MASALA MAGIC(72G)          | 2SPOSPXXX | S      | 3000  | 1B40130060 | 60             | 60      | EA  | 0.1    | KG    | 0.022   | FT3    | A          | 20    | HRS         |
      | 0005    | 5102747694 | 50         | 8906053810264 | 110 | 200006642 | PAAWAK NATURAL JAGGERY JAR-500G  | 2FANSWGUD | S      | 3000  | EC40020110 | 96             | 24      | EA  | 0.5    | KG    | 0.043   | FT3    | A          | 20    | HRS         |
#      | 0006    | 5102747694 | 60         | 8901058007268 | 100 | 200007150 | MAGGI MASALA MAGIC(120G)         | 2SPOSPXXX | S      | 3000  | 1B40070100 | 80             | 40      | EA  | 0.172  | KG    | 0.044   | FT3    | A          | 20    | HRS         |
#      | 0007    | 5102747694 | 70         | 8906027210601 | 80  | 200007347 | KUBAL SPECIAL GARAM MASALA(100G) | 2SPPBLGAM | S      | 3000  | 1C40020080 | 120            | 120     | EA  | 0.119  | KG    | 0.014   | FT3    | A          | 20    | HRS         |
#      | 0008    | 5102747694 | 80         | 6291100213146 | 315 | 200007551 | DATE CROWN FARDH(500G)           | 2NRDRFKHA | S      | 3000  | 1B40030315 | 16             | 16      | EA  | 0.559  | KG    | 0.059   | FT3    | A          | 20    | HRS         |
#      | 0009    | 5102747694 | 90         | 8906021910668 | 162 | 200008080 | G CLASSIC DATES 500G             | 2NRDRFKHA | S      | 3000  | 1C40030162 | 48             | 16      | EA  | 0.59   | KG    | 0.048   | FT3    | A          | 20    | HRS         |
#      | 0010    | 5102747694 | 100        | 8901262150118 | 220 | 400000153 | AMUL FRESH CREAM -1 LTR          | 4LBMPRCRM | D      | 3000  | OB40020220 | 12             | 12      | EA  | 1.133  | KG    | 0.041   | FT3    | A          | 20    | HRS         |

    When User calls the to createDelivery End point to   CreateOneDeliveryWithListOfItemsUsingDTInSoter
    Then verify that status code be equal to "201"


  @VehicleCheckIn2
  Scenario: Assign Vehicle for a Delivery with valid data
    Given Give Username and Password for Access Token
      | username | 1231459   |
      | password | Dmart@123 |
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

  @AssignVehicle3
  Scenario: Assign Vehicle for a Delivery with valid data
    Given Give Username and Password for Access Token
      | username | 1231459   |
      | password | Dmart@123 |
    And Provide dock vehicle type and vehicle number for a delivery to be assigned vehicle
      | dlvNumber     |            |
      | dock          | B2         |
      | vehicleType   | 1109       |
      | vehicleRegNum | AP12AB1234 |
      | currentDriver |            |
    When User calls the assign vehicle's endpoint to assign vehicle for a delivery
    Then verify that status code be equal to "200"


  @OptimizeDeliveryFlow4
  Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
    Given Give Username and Password for Access Token
      | username | 1231459   |
      | password | Dmart@123 |
    When   send AdjustDelivery End point to AdjustDelivery  data in sorter
      | dlvNumber |    |
      | dock      | B2 |
    And add items under vehicles in AdjustDeliveryInSorter
      | vehicleType | vehicleRegNum |
      | 1109        |               |
#    | 1109        |               |
    Then verify data  is Updated or not in AdjustDelivery sorter
    And  verify that status code be equal to "200"

#8100000550

  @ConfirmDeliveryFlow5
  Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
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
    Then verify data  is Updated or not in confirmDelivery sorter
    And  verify that status code be equal to "201"

  @CreateTOForOriginalDeliveryFlow6
  Scenario: Verify the functionality of CreateTOWithListOftransferOrderItems

    Given Give Username and Password for Access Token
      | username  | integrator       |
      | password  | 3poXy$6o29       |
      | requestId | 765423           |
    And Assigning Main Mandatory fields to CreateTOWithListOftransferOrderItems
      | whNumber     | 134        |
      | srcPlant     | 1034       |
      | destPlant    | 4017       |
      | grpDlv       | 0004362630 |
      | movementType | 850        |
    And Provide transferOrderItems to CreateTOWithListOftransferOrderItems
      | txfOrdItemSeq | matNumber | matDesc                          | ean           | batchNum   | srcStorageType | srcStorageBin | srcQty | caselot | uom | mrp | itemWeight | itemVolume | itemType | binSeq | bbDate   | storageLoc | destStorageType | palletQty | availableBinQty |
      | 0001          | 200000346 | LG HING POWDER(100G)             | 840222000156  | 1C40050182 | HRS            | A01-24-2C     | 100    | 100     | EA  | 182 | 0.132      | 0.012      | ZBGR     | 191    | 20250501 | 3000       | 200             | 600.000   | 1               |
      | 0002          | 200000347 | LG HING POWDER(50G)              | 840222000149  | 1C40050092 | HRS            | A01-25-1B     | 200    | 200     | EA  | 92  | 0.071      | 0.007      | ZBGR     | 194    | 20250525 | 3000       | 200             | 1600.000  | 1               |
      | 0003          | 200004280 | SAMS WHITE COOKING VINEGAR-700ML | 8906027120665 | JC40020070 | HRS            | A014-01-1B    | 24     | 24      | EA  | 70  | 0.75       | 0.043      | ZBGR     | 4682   | 20250910 | 3000       | 200             | 720.000   | 1               |
      | 0004          | 200005944 | MAGGI MASALA MAGIC(72G)          | 8901058007220 | 1B40130060 | HRS            | A010-06-1B    | 60     | 60      | EA  | 60  | 0.1        | 0.022      | ZBGR     | 3322   | 20241031 | 3000       | 200             | 1680.000  | 1               |
      | 0005          | 200006642 | PAAWAK NATURAL JAGGERY JAR-500G  | 8906053810264 | EC40020110 | HRS            | A018-23-1A    | 96     | 24      | EA  | 110 | 0.5        | 0.043      | ZBGR     | 6473   | 20250309 | 3000       | 200             | 792.000   | 2               |
#      | 0006          | 200007150 | MAGGI MASALA MAGIC(120G)         | 8901058007268 | 1B40070100 | HRS            | A013-26-1A    | 80.000  | 40.00   | EA  | 100.00 | 0.172      | 0.044      | ZBGR     | 4369   | 20241028 | 3000       | 200             | 840.000   | 2               |
#      | 0007          | 200007347 | KUBAL SPECIAL GARAM MASALA(100G) | 8906027210601 | 1C40020080 | HRS            | A01-07-1A     | 120.000 | 120.00  | EA  | 80.00  | 0.119      | 0.014      | ZBGR     | 49     | 20250224 | 3000       | 200             | 480.000   | 1               |
#      | 0008          | 200007551 | DATE CROWN FARDH(500G)           | 6291100213146 | 1B40030315 | HRS            | A01-06-2C     | 16.000  | 16.00   | EA  | 315.00 | 0.559      | 0.059      | ZBGR     | 47     | 20250703 | 3000       | 200             | 192.000   | 1               |
#      | 0009          | 200008080 | G CLASSIC DATES 500G             | 8906021910668 | 1C40030162 | HRS            | A01-15-1C     | 32.000  | 16.00   | EA  | 162.00 | 0.590      | 0.048      | ZBGR     | 115    | 20250224 | 3000       | 200             | 160.000   | 2               |
#      | 0010          | 400000153 | AMUL FRESH CREAM -1 LTR          | 8901262150118 | OB40020220 | HRS            | A006-07-1B    | 12.000  | 12.00   | EA  | 220.00 | 1.133      | 0.041      | ZFFO     | 1898   | 20240623 | 3000       | 200             | 720.000   | 1               |

    When User calls the to create TO End point to CreateTOWithListOftransferOrderItems
    Then verify that status code be equal to "201"
#https://canary.api.meradmart.com/wms/wave-planner/delivery/details/v1?dlvNumber=8111001533
  @getDeliveryAllFlow
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