Feature: verify the functionality of UpdateDeliveryInSorter in sorter

  @SorterCreateDeliveryAllinone1
  Scenario: Verify the functionality of CreateOneDeliveryWithListOfItemsUsingDT
#    Given Give Username and Password for Access Token
#      | username  | integrator |
#      | password  | 3poXy$6o29 |
#      | requestId |            |
##      | requestId | 0000001376364111 |
    Given Give Username and Password and change requestId
      | username  | integrator |
      | password  | 3poXy$6o29 |
      | requestId |            |
#      | requestId | 0000001376364111 |
    And Assigning Main Mandatory fields to CreateOneDeliveryWithListOfItemsUsingDTInSorter
#      | srcSiteId       | 1036  |
#      | dstSiteId       | 4016  |
#      | whNumber        | 136   |
      | srcSiteId       | 1034  |
      | dstSiteId       | 4017  |
      | whNumber        | 134   |
      | createdBy       | krish |
      | totalGdsMvtStat | A     |
    And Provide dlvItemNum to CreateOneDeliveryWithListOfItemsUsingDTInSorter
      | itemSeq | stoNum     | stoLineNum | ean           | mrp    | matNum    | itemDesc                           | matGrp    | catInd | stLoc | batch      | proposedDlvQty | caselot | uom | itemWt | uomWt | itemVol | uomVol | pickStatus | leQty   | storageType |
      | 000010  | 5101334418 | 000010     | 4902430905183 | 50.00  | 500017406 | ORAL B KIDS 2+ YEAR TOOTHBRUSH(3N) | 5OCTBRBAB | F      | 3000  | 2503210050 | 96             | 12.00   | EA  | 0.067  | KG    | 0.021   | FT3    | A          | 288.000 | FRS         |
      | 000020  | 5101334418 | 000020     | 8901314524010 | 170.00 | 500017575 | COLGATE MAXFRESH BLUE GEL TP(300G) | 5OCTTPGEL | F      | 3000  | 0103210170 | 96              | 12.00   | EA  | 0.360  | KG    | 0.027   | FT3    | A          | 288.000 | FRS         |
#      | 000030  | 5101334418 | 000030     | 8903363007247 | 150.00 | 500017602 | GRACE CITRUS PASSI SHOWER GEL 250ML | 5PWLSPBWS | F      | 3000  | 0103210150 | 48             | 36.00   | EA  | 0.369  | KG    | 0.22    | FT3    | A          | 288.000 | FRS         |
#      | 000040  | 5101334418 | 000040     | 8903363007254 | 150.00 | 500017603 | GRACE DEEP IMPACT SHOWER GEL 250M   | 5PWLSPBWS | F      | 3000  | 0103210150 | 94             | 36.00   | EA  | 0.303  | KG    | 0.022   | FT3    | A          | 288.000 | FRS         |
#      | 000050  | 5101334418 | 000050     | 8903363006868 | 149.00 | 500017671 | MEADOWS AIR FRESHENER LAV BLS-240ML | 5AFHFRSPR | G      | 3000  | 0103210149 | 128            | 48.00   | EA  | 0.303  | KG    | 0.022   | FT3    | A          | 288.000 | FRS         |
#      | 000060  | 5101334418 | 000060     | 8901248154031 | 110.00 | 500018038 | BOROPLUS ALOV HAL CH KES GEL(150ML) | 5SKCSLFWS | Q      | 3000  | 0103210110 | 240            | 48.00   | EA  | 0.215  | KG    | 0.028   | FT3    | A          | 288.000 | FRS         |
#      | 000070  | 5101334418 | 000070     | 4987176023476 | 199.00 | 500018258 | PAMPERS ALOE BABY WIPS(72N)         | 5PHICRTSW | Q      | 3000  | 0101210199 | 144            | 48.00   | EA  | 0.250  | KG    | 0.099   | FT3    | A          | 288.000 | FRS         |

    When User calls the to createDelivery End point to   CreateOneDeliveryWithListOfItemsUsingDTInSoter

    Then verify that status code be equal to "201"
#
#==============================================================

  @GetSorterCreateDeliveryAllinone2
  Scenario Outline: Verify the Functionality of Get Delivery by Delivery Number in sorter
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
#      | username | 1231457   |
#      | password | Dmart@123 |
      | username | 1231459   |
      | password | Dmart@123 |
    And Provide delivery number "<dlvNumber>"to get delivery Details in sorter
    When User calls the get delivery by to get delivery details
# And verify delivery number is present or not in  after creating sorter
   And verify delivery number is present or not in  after creating sorter change

    Then verify that status code be equal to "200"
   And validating JsonSchema Of object in sorter "SorterGetDeliveryByDlvNumber.json"
    Examples:
      | dlvNumber |
      |           |
#9580622046

#=================================================================

  @UpdateDeliveryInSorterWithoutDeleteAllinOne3
  Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
#    Given Give Username and Password for Access Token
#      | username  | integrator   |
#      | password  | 3poXy$6o29   |
#      | requestId | 8100000437-11 |
    Given Give Username and Password and change requestId
      | username  | integrator |
      | password  | 3poXy$6o29 |
      | requestId |            |
    When   send UpdateDeliveryInSorter End point to update the data in sorter
      | dlvNumber       |         |
      | changedBy       | krishna |
      | totalGdsMvtStat | A       |
    And add items under dlvItems in UpdateDeliveryInSorter
      | itemSeq | proposedDlvQty |
      | 000010  | 48             |
      | 000020  | 48              |
#      | 000030  | 48              |
#      | 000040  | 144            |
    And  verify that status code be equal to "201"
#=====================================================
  @getSorterUpdateDeliveryWithoutDeleteItemAllinone4
  Scenario Outline: Verify the Functionality of Get Delivery by Delivery Number in sorter
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
      | username | 1231459   |
      | password | Dmart@123 |
    And Provide delivery number "<dlvNumber>"to get delivery Details in sorter
    When User calls the get delivery by to get delivery details
    And  verify delivery  is updated  or not in sorter
    Then verify that status code be equal to "200"


    Examples:
      | dlvNumber |
      |           |
#    ==============================================================================================
  @UpdateDeliveryInSorterWithDeleteAllinone5
  Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
#    Given Give Username and Password for Access Token
#      | username  | integrator   |
#      | password  | 3poXy$6o29   |
#      | requestId | 8100000437-11 |
    Given Give Username and Password and change requestId
      | username  | integrator |
      | password  | 3poXy$6o29 |
      | requestId |            |
    When   send UpdateDeliveryInSorter End point to update the data in sorter with deleting  the item
      | dlvNumber       |         |
      | deleteDlv       | 1       |
      | changedBy       | krishna |
      | totalGdsMvtStat | A       |
    And add items under dlvItems in UpdateDeliveryInSorter with deleting the item
      | itemSeq | proposedDlvQty | deleteItem |
      | 000010  | 48.00          | 1          |
      | 000020  | 48.00          | 1          |
    And  verify that status code be equal to "201"
#===
  @getSorterUpdateDeliveryWithDeleteItemAllinone6
  Scenario Outline: Verify the Functionality of Get Delivery by Delivery Number in sorter
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
      | username | 1231459   |
      | password | Dmart@123 |
    And Provide delivery number "<dlvNumber>"to get delivery Details in sorter
    When User calls the get delivery by to get delivery details
    And  verify delivery  is updated  or not in sorter
    Then verify that status code be equal to "200"

    Examples:
      | dlvNumber |
      |           |
#    =====================================================================================================

  @AdjustDeliveryAllinOne7
Scenario: verify the functionality of UpdateDeliveryInSorter in sorter
  Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
#      | username | 1231457   |
#      | password | Dmart@123 |
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

  @ConfirmDeliveryAllinOne8
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

  @GetSorterConfirmDeliveryAllinone9
  Scenario Outline: Verify the Functionality of Get Delivery by Delivery Number in sorter
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
#      | username | 1231457   |
#      | password | Dmart@123 |
      | username | 1231459   |
      | password | Dmart@123 |
    And Provide delivery number "<dlvNumber>"to get delivery Details in sorter
    When User calls the get delivery by to get delivery details
    And verify delivery number is present or not in  after Confirming the sorter delivery
#    And verify delivery number is present or not in  after creating sorter change

    Then verify that status code be equal to "200"
#    And validating JsonSchema Of object in sorter "SorterGetDeliveryByDlvNumber.json"
    Examples:
      | dlvNumber |
      |           |

  @CreateTOForOriginalDelivery
  Scenario: Verify the functionality of CreateTOWithListOftransferOrderItems
    Given Give Username and Password and change requestId
      | username  | integrator |
      | password  | 3poXy$6o29 |
      | requestId |            |
    And Assigning Main Mandatory fields to CreateTOWithListOftransferOrderItems
      | whNumber     | 134        |
      | srcPlant     | 1034       |
      | destPlant    | 4017       |
      | grpDlv       | 0004362630 |
      | movementType | 850        |
    And Provide transferOrderItems to CreateTOWithListOftransferOrderItems
#      | txfOrdItemSeq | matNumber | matDesc                             | ean           | batchNum   | srcStorageType | srcStorageBin | srcQty | caselot | uom | mrp    | itemWeight | itemVolume | itemType | binSeq | bbDate | storageLoc | destStorageType | palletQty |
#      | 000010        | 5101334418 |  ORAL B KIDS 2+ YEAR TOOTHBRUSH(3N)  | 5OCTBRBAB | F | 3000 | 2503210050 | 96 | 12.00 | EA | 0.067 | KG | 0.021 | FT3 | A | 288.000 | FRS |
#      | 000020        | 5101334418 | COLGATE MAXFRESH BLUE GEL TP(300G)  | 5OCTTPGEL | F | 3000 | 0103210170 | 96 | 12.00 | EA | 0.360 | KG | 0.027 | FT3 | A | 288.000 | FRS |
#      | 000030        | 5101334418 |  GRACE CITRUS PASSI SHOWER GEL 250ML | 5PWLSPBWS | F | 3000 | 0103210150 | 48 | 36.00 | EA | 0.369 | KG | 0.22  | FT3 | A | 288.000 | FRS |
  #    | 0001          | 500017406 | ORAL B KIDS 2+ YEAR TOOTHBRUSH(3N)  | 4902430905183 | 2503210050 | FRS            | A030-14-1B    | 96.000 | 48.00   | EA  | 50.00  | 0.067      | 0.021      | ZFNF     | 3982   |        | 3000       | 200             | 1728.000  |
#      | 0002          | 500017575 | COLGATE MAXFRESH BLUE GEL TP(300G)  | 8901314524010 | 0103210170 | FRS            | A030-14-1B    | 96.000 | 48.00   | EA  | 170.00 | 0.360      | 0.027      | ZFNF     | 3904   |        | 3000       | 200             | 1440.000  |
#      | 0003          | 500017602 | GRACE CITRUS PASSI SHOWER GEL 250ML | 8903363007247 | 0103210150 | FRS            | A033-07-1A    | 72.000 | 36.00   | EA  | 150.00 | 0.369      | 0.022      | ZFNF     | 4143   |        | 3000       | 200             | 1440.000  |
#      | 0004          | 500017603 | GRACE DEEP IMPACT SHOWER GEL 250M   | 8903363007254 | 0103210150 | FRS            | A033-29-1A    | 72.000 | 36.00   | EA  | 150.00 | 0.303      | 0.022      | ZFNF     | 4319   |        | 3000       | 200             | 1440.000  |
#      | 0005          | 500017671 | MEADOWS AIR FRESHENER LAV BLS-240ML | 8903363006868 | 0103210149 | FRS            | A032-09-1B    | 96.000 | 48.00   | EA  | 149.00 | 0.242      | 0.032      | ZFNF     | 4161   |        | 3000       | 200             | 1152.000  |
#    When User calls the to create TO End point to CreateTOWithListOftransferOrderItems
    Then verify that status code be equal to "201"

#  000010| 4902430905183 | 50.00  | 500017406
#  000020| 8901314524010 | 170.00 | 500017575 |
#  000030 | 8903363007247 | 150.00 | 500017602




#    ========================================================================================
  @verifyDelivernumberPresentOrNotInSorterAllinOne
  Scenario: verify the functionality of GetListofDeliveriesEndpoint in sorter
    Given Give Username and Password for Access Token
#      | username | 123457      |
#      | password | Welcome@123 |
#      | username | 1231457   |
#      | password | Dmart@123 |
      | username | 1231459   |
      | password | Dmart@123 |
    When   send GetDelivery End point to get the data in sorter
#      | srcSiteId | 1036    |
#      | dlvStatus | CREATED |
      | srcSiteId | 1034    |
      | dlvStatus | CREATED |
    Then verify delivernumber is present or not in sorter
    And  verify that status code be equal to "200"