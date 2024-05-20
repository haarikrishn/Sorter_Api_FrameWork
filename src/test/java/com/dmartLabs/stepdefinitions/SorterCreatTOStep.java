package com.dmartLabs.stepdefinitions;


import com.dmartLabs.commonutils.ExcelUtils;
import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.GenricUtils;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import com.dmartLabs.pojo.CreateToMainPojo;
import com.dmartLabs.pojo.TransferOrderItemsToPojo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SorterCreatTOStep {

    RequestGenerator requestGenerator=new RequestGenerator();

    ArrayList<CreateToMainPojo> createToMainpojoArrayList=new ArrayList<>();
    public  static  CreateToMainPojo createToMainPojo=new CreateToMainPojo();
    ArrayList<TransferOrderItemsToPojo>transferOrderItemsToPojoArrayList=new ArrayList<>();
    Response CreateTOResponse;


    @And("Assigning Main Mandatory fields to CreateTOWithListOftransferOrderItems")
    public void assigningMainMandatoryFieldsToCreateTOWithListOftransferOrderItems(DataTable dataTable) {
        Map<String, String> CreateTODT = dataTable.asMap(String.class, String.class);
        createToMainPojo.setWhNumber(CreateTODT.get("whNumber"));
        CommonUtilities.setWhNumber(CreateTODT.get("whNumber"));
        createToMainPojo.setSrcPlant(CreateTODT.get("srcPlant"));
        createToMainPojo.setDestPlant(CreateTODT.get("destPlant"));
        createToMainPojo.setGrpDlv(CreateTODT.get("grpDlv"));
      //  String actualtxfOrdNumber = GenricUtils.gettxfOrdNumber();
        //============================================================================
        String actualtxfOrdNumber = CommonUtilities.getDeliveryNumber();
      //  String actualtxfOrdNumber = "9280561887";
       createToMainPojo.setTxfOrdNumber(actualtxfOrdNumber);
      //  CommonUtilities.settxfOrdNumber(createToMainPojo.getTxfOrdNumber());
        createToMainPojo.setMovementType(CreateTODT.get("movementType"));
        createToMainPojo.setCreationDate(GenricUtils.getDate("yyyyMMdd"));
        createToMainPojo.setCreationTime(GenricUtils.getTime("HHmmss"));
        //===============================================================================
       String originalDlv1 = CommonUtilities.getDeliveryNumber();
       // String originalDlv1 = "9280561887";
        System.out.println(originalDlv1+"================>originalDlv1");
        List<String> dlvnumberAl=new ArrayList<>();
        dlvnumberAl.add(originalDlv1);
        System.out.println(dlvnumberAl+"===============>dlvnumberAl");
        createToMainPojo.setDlvNumbers(dlvnumberAl);
    }

    @And("Provide transferOrderItems to CreateTOWithListOftransferOrderItems")
    public void
    provideTransferOrderItemsToCreateTOWithListOftransferOrderItems(DataTable dataTable) {

        List<Map<String, String>> transferOrderItemsDT = dataTable.asMaps();
        int ActualTransferOrderItemsCount = transferOrderItemsDT.size();
        CommonUtilities.setTransferOrderItemsCount(ActualTransferOrderItemsCount);
        List<String> txfOrdItemSeq = new ArrayList<>();

        for(int i=0;i<transferOrderItemsDT.size();i++)
        {
            Map<String, String> transferOrderItemsDTOneByOne = transferOrderItemsDT.get(i);

            TransferOrderItemsToPojo transferOrderItemsToPojo=new TransferOrderItemsToPojo();
            transferOrderItemsToPojo.setTxfOrdItemSeq(transferOrderItemsDTOneByOne.get("txfOrdItemSeq"));
            txfOrdItemSeq.add(transferOrderItemsDTOneByOne.get("txfOrdItemSeq"));
            transferOrderItemsToPojo.setMatNumber(transferOrderItemsDTOneByOne.get("matNumber"));
            transferOrderItemsToPojo.setMatDesc(transferOrderItemsDTOneByOne.get("matDesc"));
            transferOrderItemsToPojo.setEan(transferOrderItemsDTOneByOne.get("ean"));
            transferOrderItemsToPojo.setBatchNum(transferOrderItemsDTOneByOne.get("batchNum"));
            transferOrderItemsToPojo.setSrcStorageType(transferOrderItemsDTOneByOne.get("srcStorageType"));
            transferOrderItemsToPojo.setSrcStorageBin(transferOrderItemsDTOneByOne.get("srcStorageBin"));
            transferOrderItemsToPojo.setSrcQty(transferOrderItemsDTOneByOne.get("srcQty"));
            transferOrderItemsToPojo.setCaselot(transferOrderItemsDTOneByOne.get("caselot"));
            transferOrderItemsToPojo.setUom(transferOrderItemsDTOneByOne.get("uom"));
            transferOrderItemsToPojo.setMrp(transferOrderItemsDTOneByOne.get("mrp"));
            transferOrderItemsToPojo.setItemWeight(transferOrderItemsDTOneByOne.get("itemWeight"));
            transferOrderItemsToPojo.setItemVolume(transferOrderItemsDTOneByOne.get("itemVolume"));
            transferOrderItemsToPojo.setItemType(transferOrderItemsDTOneByOne.get("itemType"));
            transferOrderItemsToPojo.setBinSeq(transferOrderItemsDTOneByOne.get("binSeq"));
            transferOrderItemsToPojo.setBbDate(GenricUtils.getDate("yyyyMMdd"));
            transferOrderItemsToPojo.setStorageLoc(transferOrderItemsDTOneByOne.get("storageLoc"));
            transferOrderItemsToPojo.setDestStorageType(transferOrderItemsDTOneByOne.get("destStorageType"));
            transferOrderItemsToPojo.setPalletQty(transferOrderItemsDTOneByOne.get("palletQty"));
            transferOrderItemsToPojo.setAvailableBinQty(transferOrderItemsDTOneByOne.get("availableBinQty"));
            transferOrderItemsToPojoArrayList.add(transferOrderItemsToPojo);
        }
        CommonUtilities.setTxfOrdItemSeq(txfOrdItemSeq);
        createToMainPojo.setTransferOrderItems(transferOrderItemsToPojoArrayList);
        createToMainpojoArrayList.add(createToMainPojo);

    }

    @When("User calls the to create TO End point to CreateTOWithListOftransferOrderItems")
    public void userCallsTheToCreateTOEndPointToCreateTOWithListOftransferOrderItems() throws InterruptedException {

        Thread.sleep(7000);
        System.out.println("waiting 6 seconds");
        CreateTOResponse = requestGenerator.getRequest(GenericSteps.userCredential, createToMainpojoArrayList).log().all()
                .when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "CreateTOEndPoint"));
        CreateTOResponse.then().log().all();
        ExtentReportManager.logJson("Response is " +   CreateTOResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " +   CreateTOResponse.getStatusCode());
        long responseTime =    CreateTOResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is "+responseTime+" ms");
        CommonUtilities.setResponseInstance(  CreateTOResponse);


    }

    private List<LinkedHashMap<String, Object>> requestPayload;
    private LinkedHashMap<String, Object> transfer_orderDetails;
    private ExcelUtils excelUtils;
    @And("Provide the warehouse number of a site {string}")
    public void provideTheWarehouseNumberOfASite(String whNumber) {
        ExtentReportManager.logInfoDetails("Provide the warehouse number of a site");
        requestPayload = new ArrayList<>();
        transfer_orderDetails = new LinkedHashMap<>();
        transfer_orderDetails.put("whNumber", whNumber);
    }

    @And("Give the source plant id for which transfer order has to be created {string}")
    public void giveTheSourceSiteIdForWhichTransferOrderHasToBeCreated(String srcPlantId) {
        ExtentReportManager.logInfoDetails("Give the source site id for which transfer order has to be created");
        transfer_orderDetails.put("srcPlant", srcPlantId);
    }

    @And("Give the destination plant id to which items has to be delivered {string}")
    public void giveTheDestinationIdToWhichItemsHasToBeDelivered(String destPlantId) {
        ExtentReportManager.logInfoDetails("Give the destination plant id to which items has to be delivered");
        transfer_orderDetails.put("destPlant", destPlantId);
    }

    @And("Give the group delivery number for the transfer order {string}")
    public void giveTheGroupDeliveryNumberForTheTransferOrder(String groupDlvNumber) {
        ExtentReportManager.logInfoDetails("Give the group delivery number for the transfer order");
        transfer_orderDetails.put("grpDlv", groupDlvNumber);
    }

    @And("Get the transfer order number to create TO for a delivery")
    public void getTheTransferOrderNumberToCreateTOForADelivery() {
        ExtentReportManager.logInfoDetails("Get the transfer order number to create TO for a delivery");
        transfer_orderDetails.put("txfOrdNumber", CommonUtilities.getDeliveryNumber());
    }

    @And("Give the movement type for a transfer order {string}")
    public void giveTheMovementTypeForATransferOrder(String movementType) {
        ExtentReportManager.logInfoDetails("Give the movement type for a transfer order");
        transfer_orderDetails.put("movementType", movementType);
    }

    @And("Get the date of creation of the transfer order")
    public void getTheDateOfCreationOfTheTransferOrder() {
        ExtentReportManager.logInfoDetails("Get the date of creation of the transfer order");
        transfer_orderDetails.put("creationDate", GenricUtils.getDate(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "YEAR_MONTH_DATE")));
    }

    @And("Get the time of creation of the transfer order")
    public void getTheTimeOfCreationOfTheTransferOrder() {
        ExtentReportManager.logInfoDetails("Get the time of creation of the transfer order");
        transfer_orderDetails.put("creationTime", GenricUtils.getTime(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "HOUR_MINUTE_SECOND")));
    }

    @And("Get the delivery number for which transfer has to be created")
    public void getTheDeliveryNumberForWhichTransferHasToBeCreated() {
        ExtentReportManager.logInfoDetails("Get the delivery number for which transfer has to be created");
        ArrayList<String> dlvNumbers = new ArrayList<>();
        dlvNumbers.add(CommonUtilities.getDeliveryNumber());
        transfer_orderDetails.put("dlvNumbers", dlvNumbers);
    }

    @And("Give the Excel file to get the items for a transfer order {string}")
    public void giveTheExcelFileToGetTheItemsForATransferOrder(String excelFile) {
        ExtentReportManager.logInfoDetails("Give the Excel file to get the items for a transfer order");
        excelUtils = new ExcelUtils(excelFile);
    }

    @And("Give the sheet name to get the items for which transfer order has to be created {string}")
    public void giveTheSheetNameToGetTheItemsForWhichTransferOrderHasToBeCreated(String sheetName) {
        ExtentReportManager.logInfoDetails("Give the sheet name to get the items for which transfer order has to be created");
        List<LinkedHashMap<String, Object>> trasnferOrderItems = excelUtils.getTransferOrderItems(sheetName);
        transfer_orderDetails.put("transferOrderItems", trasnferOrderItems);
        requestPayload.add(transfer_orderDetails);
    }

    @And("Give the sheet name {string} and the number of items required in a transfer order {int}")
    public void giveTheSheetNameAndTheNumberOfItemsRequiredInATransferOrder(String sheetName, int requiredItems) {
        ExtentReportManager.logInfoDetails("Give the sheet name and the number of items required in a transfer order");
        List<LinkedHashMap<String, Object>> transferOrderItems = excelUtils.getTransferOrderItems(sheetName, requiredItems);
        transfer_orderDetails.put("transferOrderItems", transferOrderItems);
        requestPayload.add(transfer_orderDetails);
    }

    @When("Requester calls the outbound transfer order endpoint to create new delivery for a wave")
    public void requesterCallsTheOutboundTransferOrderEndpointToCreateNewDeliveryForAWave() {
        ExtentReportManager.logInfoDetails("Requester calls the outbound transfer order endpoint to create new delivery for a wave");
        RequestSpecification requestSpecification = RequestGenerator.getRequest(GenericSteps.userCredential, requestPayload).log().all();
        Response response = requestSpecification.post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "CreateTOEndPoint"));
        response.then().log().all();
        ExtentReportManager.logInfoDetails("Response is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + response.getStatusCode());
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
    }

}
