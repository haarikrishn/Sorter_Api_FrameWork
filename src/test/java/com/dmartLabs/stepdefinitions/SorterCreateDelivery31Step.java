package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExcelUtils;
import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.GenricUtils;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import com.dmartLabs.pojo.CreateDeliverySorter31Pojo;
import com.dmartLabs.pojo.DlvIemsSorterMain31Pojo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.hu.Ha;
import io.restassured.response.Response;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class SorterCreateDelivery31Step {

    RequestGenerator requestGenerator = new RequestGenerator();
    Response CreateDeliveryResponse;
    public static int ActualTotalBoxes = 0;
    public static String dlvNumber;
    public  static List<String>EanList=new LinkedList<>();
    private static List<HashMap<String, String>> deliveryLists = new ArrayList<>();

    //sorter craete Delivery
    //DlvIemsSorterMain31Pojo dlvIemsSorterMainPojo;
    ArrayList<CreateDeliverySorter31Pojo> createDeliverySorterAl = new ArrayList<>();
    public static CreateDeliverySorter31Pojo createdeliverySorterPojo = new CreateDeliverySorter31Pojo();
    ArrayList<DlvIemsSorterMain31Pojo> dlvItemsSorterAL = new ArrayList<>();

    private List<LinkedHashMap<String, Object>> requestPayload;
    LinkedHashMap<String, Object> deliveryDetails;
    private ExcelUtils excelUtils;

    @And("Assigning Main Mandatory fields to CreateOneDeliveryWithListOfItemsUsingDTInSorter")
    public void assigningMainMandatoryFieldsToCreateOneDeliveryWithListOfItemsUsingDTInSorter(DataTable dataTable) {

        Map<String, String> CreateDeliveryDT = dataTable.asMap(String.class, String.class);
        String dlvNumber = GenricUtils.getRandomDeliveryNumber();
        createdeliverySorterPojo.setDlvNumber(dlvNumber);
        CommonUtilities.setDeliveryNumber(dlvNumber);

     //   System.out.println(dlvNumber + "        random delivey number");
        System.out.println(CommonUtilities.getDeliveryNumber() + "          common utilitites delivery number");

        createdeliverySorterPojo.setProposedDlvDate(GenricUtils.getDate("yyyyMMdd"));
        createdeliverySorterPojo.setSrcSiteId(CreateDeliveryDT.get("srcSiteId"));
        CommonUtilities.setSrcSiteID(createdeliverySorterPojo.getSrcSiteId());

        createdeliverySorterPojo.setDstSiteId(CreateDeliveryDT.get("dstSiteId"));
        CommonUtilities.setDstSiteID(createdeliverySorterPojo.getDstSiteId());

        createdeliverySorterPojo.setWhNumber(CreateDeliveryDT.get("whNumber"));
        CommonUtilities.setWhNumber(createdeliverySorterPojo.getWhNumber());

        createdeliverySorterPojo.setCreatedBy(CreateDeliveryDT.get("createdBy"));

        createdeliverySorterPojo.setCreationDate(GenricUtils.getDate("yyyyMMdd"));
        createdeliverySorterPojo.setCreationTime(GenricUtils.getTime("HHmmss"));
        createdeliverySorterPojo.setTotalGdsMvtStat(CreateDeliveryDT.get("totalGdsMvtStat"));

    }

    //     "leQty": "288.000",
//             "storageType": "FRS",
    @And("Provide dlvItemNum to CreateOneDeliveryWithListOfItemsUsingDTInSorter")
    public void provideDlvItemNumToCreateOneDeliveryWithListOfItemsUsingDTInSorter(DataTable dataTable) {

        List<Map<String, String>> dlvItemsCreateDeliveryDT = dataTable.asMaps();
        for (int i = 0; i < dlvItemsCreateDeliveryDT.size(); i++) {
            Map<String, String> dlvItemOneByOne = dlvItemsCreateDeliveryDT.get(i);//data table

            DlvIemsSorterMain31Pojo dlvIemsSorterMainPojo = new DlvIemsSorterMain31Pojo();

            dlvIemsSorterMainPojo.setItemSeq(dlvItemOneByOne.get("itemSeq"));
            dlvIemsSorterMainPojo.setStoNum(dlvItemOneByOne.get("stoNum"));
            dlvIemsSorterMainPojo.setStoLineNum(dlvItemOneByOne.get("stoLineNum"));

            dlvIemsSorterMainPojo.setEan(dlvItemOneByOne.get("ean"));
            EanList.add(dlvItemOneByOne.get("ean"));
            dlvIemsSorterMainPojo.setMrp(dlvItemOneByOne.get("mrp"));
            dlvIemsSorterMainPojo.setMatNum(dlvItemOneByOne.get("matNum"));
            dlvIemsSorterMainPojo.setItemDesc(dlvItemOneByOne.get("itemDesc"));
            dlvIemsSorterMainPojo.setMatGrp(dlvItemOneByOne.get("matGrp"));
            dlvIemsSorterMainPojo.setCatInd(dlvItemOneByOne.get("catInd"));
            dlvIemsSorterMainPojo.setStLoc(dlvItemOneByOne.get("stLoc"));
            dlvIemsSorterMainPojo.setBatch(dlvItemOneByOne.get("batch"));
            dlvIemsSorterMainPojo.setProposedDlvQty(dlvItemOneByOne.get("proposedDlvQty"));
            dlvIemsSorterMainPojo.setCaselot(dlvItemOneByOne.get("caselot"));
            dlvIemsSorterMainPojo.setLeQty(dlvItemOneByOne.get("leQty"));
            dlvIemsSorterMainPojo.setStorageType(dlvItemOneByOne.get("storageType"));


            dlvIemsSorterMainPojo.setUom(dlvItemOneByOne.get("uom"));
            dlvIemsSorterMainPojo.setItemWt(dlvItemOneByOne.get("itemWt"));
            dlvIemsSorterMainPojo.setUomWt(dlvItemOneByOne.get("uomWt"));
            dlvIemsSorterMainPojo.setItemVol(dlvItemOneByOne.get("itemVol"));
            dlvIemsSorterMainPojo.setUomVol(dlvItemOneByOne.get("uomVol"));
            dlvIemsSorterMainPojo.setPickStatus(dlvItemOneByOne.get("pickStatus"));

            dlvItemsSorterAL.add(dlvIemsSorterMainPojo);

            float proposedDlvQtys = Float.parseFloat(dlvIemsSorterMainPojo.getProposedDlvQty());
            float caselots = Float.parseFloat(dlvIemsSorterMainPojo.getCaselot());
            ActualTotalBoxes = ActualTotalBoxes + (int) (proposedDlvQtys / caselots);
        }
        createdeliverySorterPojo.setDlvItems(dlvItemsSorterAL);
        createDeliverySorterAl.add(createdeliverySorterPojo);
        CommonUtilities.settotalboxescount(ActualTotalBoxes);
        System.out.println(ActualTotalBoxes + "  =========================>ActualTotalBoxes");
    }


    @When("User calls the to createDelivery End point to   CreateOneDeliveryWithListOfItemsUsingDTInSoter")
    public void userCallsTheToCreateDeliveryEndPointToCreateOneDeliveryWithListOfItemsUsingDTInSoter() {
        CreateDeliveryResponse = requestGenerator.getRequest(GenericSteps.userCredential, createDeliverySorterAl).log().all()
                .when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "CreateDeliverySorterEndPoint"));
        CreateDeliveryResponse.then().log().all();
        ExtentReportManager.logJson("Response is " + CreateDeliveryResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + CreateDeliveryResponse.getStatusCode());
        long responseTime = CreateDeliveryResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(CreateDeliveryResponse);

    }

//===================================================================================================================

    //creating multiple deliveries
    DlvIemsSorterMain31Pojo dlvIemsSorterMainPojo = new DlvIemsSorterMain31Pojo();

    @When("User calls the sorter  Create Delivery endPoint {string} {string} {string}	{string} {string} {string} {string} 	{string}	{string} {string} {string} {string} {string}  {string} {string}	{string}	{string} {string}	{string}	{string}	{string}	{string}	{string}	{string}	{string}	{string} {string} {string}")
    public void userCallsTheSorterCreateDeliveryEndPoint(String dlvNumber, String srcSiteId, String dstSiteId, String whNumber, String createdBy, String creationDate, String totalGdsMvtStat, String itemSeq, String stoNum, String stoLineNum, String ean, String mrp, String matNum, String itemDesc, String matGrp, String catInd, String stLoc, String batch, String proposedDlvQty, String caselot, String uom, String itemWt, String uomWt, String itemVol, String uomVol, String pickStatus, String leQty, String storageType) {

        //    System.out.println("current scenario is"+"   "  +CurrentScenarioIs);
        // System.out.println(dlvNumber+"============>dlvNumber");
        //    System.out.println(GenricUtils.getRandomDeliveryNumber());
        String RandomdlvNumber = GenricUtils.getRandomDeliveryNumber();

        if (!(dlvNumber.equals(""))) {
            //  createdeliverySorterPojo.setDlvNumber(GenricUtils.getRandomDeliveryNumber());
            createdeliverySorterPojo.setDlvNumber(dlvNumber);
            CommonUtilities.setDeliveryNumber(dlvNumber);
        } else {
            createdeliverySorterPojo.setDlvNumber(RandomdlvNumber);
            CommonUtilities.setDeliveryNumber(RandomdlvNumber);
            //  createdeliverySorterPojo.setDlvNumber(dlvNumber);
        }
        // createdeliverySorterPojo.setDlvNumber(dlvNumber);


        createdeliverySorterPojo.setProposedDlvDate(GenricUtils.getDate("yyyyMMdd"));
        createdeliverySorterPojo.setSrcSiteId(srcSiteId);
        CommonUtilities.setSrcSiteID(createdeliverySorterPojo.getSrcSiteId());
        createdeliverySorterPojo.setDstSiteId(dstSiteId);
        CommonUtilities.setDstSiteID(createdeliverySorterPojo.getDstSiteId());
        createdeliverySorterPojo.setWhNumber(whNumber);
        CommonUtilities.setWhNumber(createdeliverySorterPojo.getWhNumber());
        createdeliverySorterPojo.setCreatedBy(createdBy);

        String date = GenricUtils.getDate("yyyyMMdd");
        if (!(creationDate.equals(""))) {
            createdeliverySorterPojo.setCreationDate(date);
        } else {
            createdeliverySorterPojo.setCreationDate(date);
        }

        String Time = GenricUtils.getTime("HHmmss");
        createdeliverySorterPojo.setCreationTime(Time);
        createdeliverySorterPojo.setTotalGdsMvtStat(totalGdsMvtStat);

        dlvIemsSorterMainPojo.setItemSeq(itemSeq);//itemSeq
        dlvIemsSorterMainPojo.setStoNum(stoNum);
        dlvIemsSorterMainPojo.setStoLineNum(stoLineNum);
        dlvIemsSorterMainPojo.setEan(ean);
        dlvIemsSorterMainPojo.setMrp(mrp);
        dlvIemsSorterMainPojo.setMatNum(matNum);
        dlvIemsSorterMainPojo.setItemDesc(itemDesc);
        dlvIemsSorterMainPojo.setMatGrp(matGrp);
        dlvIemsSorterMainPojo.setCatInd(catInd);
        dlvIemsSorterMainPojo.setStLoc(stLoc);
        dlvIemsSorterMainPojo.setBatch(batch);
        dlvIemsSorterMainPojo.setProposedDlvQty(proposedDlvQty);
        dlvIemsSorterMainPojo.setCaselot(caselot);
        dlvIemsSorterMainPojo.setLeQty(leQty);
        dlvIemsSorterMainPojo.setStorageType(storageType);
        dlvIemsSorterMainPojo.setUom(uom);
        dlvIemsSorterMainPojo.setItemWt(itemWt);
        dlvIemsSorterMainPojo.setUomWt(uomWt);
        dlvIemsSorterMainPojo.setItemVol(itemVol);
        dlvIemsSorterMainPojo.setUomVol(uomVol);
        dlvIemsSorterMainPojo.setPickStatus(pickStatus);

        dlvItemsSorterAL.add(dlvIemsSorterMainPojo);

        createdeliverySorterPojo.setDlvItems(dlvItemsSorterAL);
        createDeliverySorterAl.add(createdeliverySorterPojo);

        float proposedDlvQtys = Float.parseFloat(dlvIemsSorterMainPojo.getProposedDlvQty());
        float caselots = Float.parseFloat(dlvIemsSorterMainPojo.getCaselot());
        ActualTotalBoxes = ActualTotalBoxes + (int) (proposedDlvQtys / caselots);

        CommonUtilities.settotalboxescount(ActualTotalBoxes);
        System.out.println(ActualTotalBoxes + "  =========================>ActualTotalBoxes");


        CreateDeliveryResponse = requestGenerator.getRequest(GenericSteps.userCredential, createDeliverySorterAl).log().all()
                .when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "CreateDeliverySorterEndPoint"));
        CreateDeliveryResponse.then().log().all();
        ExtentReportManager.logJson("Response is " + CreateDeliveryResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + CreateDeliveryResponse.getStatusCode());
        long responseTime = CreateDeliveryResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(CreateDeliveryResponse);

    }


    //========================================================================================
    //sorter all flow
    @When("User calls the sorter  Create Delivery endPoint for all flow {string} {string} {string}	{string} {string} {string} {string} 	{string}	{string} {string} {string} {string} {string}  {string} {string}	{string}	{string} {string}	{string}	{string}	{string}	{string}	{string}	{string}	{string}	{string} {string} {string} {string} {string}")
    public void userCallsTheSorterCreateDeliveryEndPointForAllFlow(String dlvNumber, String srcSiteId, String dstSiteId, String whNumber, String createdBy, String creationDate, String totalGdsMvtStat, String itemSeq, String stoNum, String stoLineNum, String ean, String mrp, String matNum, String itemDesc, String matGrp, String catInd, String stLoc, String batch, String proposedDlvQty, String caselot, String uom, String itemWt, String uomWt, String itemVol, String uomVol, String pickStatus, String leQty, String storageType, String itemSeq1, String proposedDlvQty1) {
        String RandomdlvNumber = GenricUtils.getRandomDeliveryNumber();

        if (!(dlvNumber.equals(""))) {
            //  createdeliverySorterPojo.setDlvNumber(GenricUtils.getRandomDeliveryNumber());
            createdeliverySorterPojo.setDlvNumber(dlvNumber);
            CommonUtilities.setDeliveryNumber(dlvNumber);
        } else {
            createdeliverySorterPojo.setDlvNumber(RandomdlvNumber);
            CommonUtilities.setDeliveryNumber(RandomdlvNumber);
            //  createdeliverySorterPojo.setDlvNumber(dlvNumber);
        }
        // createdeliverySorterPojo.setDlvNumber(dlvNumber);


        createdeliverySorterPojo.setProposedDlvDate(GenricUtils.getDate("yyyyMMdd"));
        createdeliverySorterPojo.setSrcSiteId(srcSiteId);
        CommonUtilities.setSrcSiteID(createdeliverySorterPojo.getSrcSiteId());
        createdeliverySorterPojo.setDstSiteId(dstSiteId);
        CommonUtilities.setDstSiteID(createdeliverySorterPojo.getDstSiteId());
        createdeliverySorterPojo.setWhNumber(whNumber);
        CommonUtilities.setWhNumber(createdeliverySorterPojo.getWhNumber());
        createdeliverySorterPojo.setCreatedBy(createdBy);

        String date = GenricUtils.getDate("yyyyMMdd");
        if (!(creationDate.equals(""))) {
            createdeliverySorterPojo.setCreationDate(date);
        } else {
            createdeliverySorterPojo.setCreationDate(date);
        }

        String Time = GenricUtils.getTime("HHmmss");
        createdeliverySorterPojo.setCreationTime(Time);
        createdeliverySorterPojo.setTotalGdsMvtStat(totalGdsMvtStat);

        dlvIemsSorterMainPojo.setItemSeq(itemSeq);//itemSeq
        dlvIemsSorterMainPojo.setStoNum(stoNum);
        dlvIemsSorterMainPojo.setStoLineNum(stoLineNum);
        dlvIemsSorterMainPojo.setEan(ean);
        dlvIemsSorterMainPojo.setMrp(mrp);
        dlvIemsSorterMainPojo.setMatNum(matNum);
        dlvIemsSorterMainPojo.setItemDesc(itemDesc);
        dlvIemsSorterMainPojo.setMatGrp(matGrp);
        dlvIemsSorterMainPojo.setCatInd(catInd);
        dlvIemsSorterMainPojo.setStLoc(stLoc);
        dlvIemsSorterMainPojo.setBatch(batch);
        dlvIemsSorterMainPojo.setProposedDlvQty(proposedDlvQty);
        dlvIemsSorterMainPojo.setCaselot(caselot);
        dlvIemsSorterMainPojo.setLeQty(leQty);
        dlvIemsSorterMainPojo.setStorageType(storageType);
        dlvIemsSorterMainPojo.setUom(uom);
        dlvIemsSorterMainPojo.setItemWt(itemWt);
        dlvIemsSorterMainPojo.setUomWt(uomWt);
        dlvIemsSorterMainPojo.setItemVol(itemVol);
        dlvIemsSorterMainPojo.setUomVol(uomVol);
        dlvIemsSorterMainPojo.setPickStatus(pickStatus);

        dlvItemsSorterAL.add(dlvIemsSorterMainPojo);

        createdeliverySorterPojo.setDlvItems(dlvItemsSorterAL);
        createDeliverySorterAl.add(createdeliverySorterPojo);

        float proposedDlvQtys = Float.parseFloat(dlvIemsSorterMainPojo.getProposedDlvQty());
        float caselots = Float.parseFloat(dlvIemsSorterMainPojo.getCaselot());
        ActualTotalBoxes = ActualTotalBoxes + (int) (proposedDlvQtys / caselots);

        CommonUtilities.settotalboxescount(ActualTotalBoxes);
        System.out.println(ActualTotalBoxes + "  =========================>ActualTotalBoxes");


        CreateDeliveryResponse = requestGenerator.getRequest(GenericSteps.userCredential, createDeliverySorterAl).log().all()
                .when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "CreateDeliverySorterEndPoint"));
        CreateDeliveryResponse.then().log().all();
        ExtentReportManager.logJson("Response is " + CreateDeliveryResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + CreateDeliveryResponse.getStatusCode());
        long responseTime = CreateDeliveryResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(CreateDeliveryResponse);


    }

    @And("Get the delivery number to create new delivery")
    public void getTheDeliveryNumberToCreateNewDelivery() {
        ExtentReportManager.logInfoDetails("Get the delivery number to create new delivery");
        requestPayload = new ArrayList<>();
        deliveryDetails = new LinkedHashMap<>();
        String dlvNumber = GenricUtils.getRandomDeliveryNumber();
        deliveryDetails.put("dlvNumber", dlvNumber);
        CommonUtilities.setDeliveryNumber(dlvNumber);
    }

    private static List<String> dlvNumbers = new ArrayList<>();
    @And("Get the delivery numbers to create multiple deliveries for a wave")
    public void getTheDeliveryNumbersToCreateMultipleDeliveriesForAWave() {
        ExtentReportManager.logInfoDetails("Get the delivery numbers to create multiple deliveries for a wave");
        requestPayload = new ArrayList<>();
        deliveryDetails = new LinkedHashMap<>();
        dlvNumber = GenricUtils.getRandomDeliveryNumber();
        deliveryDetails.put("dlvNumber", dlvNumber);
        dlvNumbers.add(dlvNumber);
        CommonUtilities.setMultipleDlvNumbers(dlvNumbers);
    }

    @And("Provide the proposed delivery date for a delivery")
    public void provideTheProposedDeliveryDateForADelivery() {
        ExtentReportManager.logInfoDetails("Provide the proposed delivery date for a delivery");
        deliveryDetails.put("proposedDlvDate", GenricUtils.getDate(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "YEAR_MONTH_DATE")));
    }

    @And("Give the source siteId for the delivery {string}")
    public void giveTheSourceSiteIdForTheDelivery(String srcSiteID) {
        ExtentReportManager.logInfoDetails("Give the source siteId for the delivery");
        deliveryDetails.put("srcSiteId", srcSiteID);
    }

    @And("Give the destination siteId for the delivery {string}")
    public void giveTheDestinationSiteIdForTheDelivery(String dstSiteId) {
        ExtentReportManager.logInfoDetails("Give the destination siteId for the delivery");
        deliveryDetails.put("dstSiteId", dstSiteId);
    }

    @And("Give the destination siteId for all the deliveries in a wave {string}")
    public void giveTheDestinationSiteIdForAllTheDeliveriesInAWave(String dstSiteId) {
        ExtentReportManager.logInfoDetails("Give the destination siteId for all the deliveries in a wave");
        deliveryDetails.put("dstSiteId", dstSiteId);
        HashMap<String, String> destinationWithDeliveryNumber = new HashMap<>();
        destinationWithDeliveryNumber.put(dlvNumber, dstSiteId);
        deliveryLists.add(destinationWithDeliveryNumber);
        CommonUtilities.setDestinationBasedOnDeliveryNumbers(deliveryLists);
    }

    @And("Give the warehouse number of the source siteId {string}")
    public void giveTheWarehouseNumberOfTheSourceSiteId(String whNumber) {
        ExtentReportManager.logInfoDetails("Give the warehouse number of the source siteId");
        deliveryDetails.put("whNumber", whNumber);
    }

    @And("Give the name of a person creating a delivery {string}")
    public void giveTheNameOfAPersonCreatingADelivery(String createdBy) {
        ExtentReportManager.logInfoDetails("Give the name of a person creating a delivery");
        deliveryDetails.put("createdBy", createdBy);
    }

    @And("Get the date of creation of the delivery")
    public void getTheDateOfCreationOfTheDelivery() {
        ExtentReportManager.logInfoDetails("Get the date of creation of the delivery");
        deliveryDetails.put("creationDate", GenricUtils.getDate(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "YEAR_MONTH_DATE")));
    }

    @And("Get the time of creation of the delivery")
    public void getTheTimeOfCreationOfTheDelivery() {
        ExtentReportManager.logInfoDetails("Get the time of creation of the delivery");
        deliveryDetails.put("creationTime", GenricUtils.getTime(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "HOUR_MINUTE_SECOND")));
    }

    @And("Provide the totalGdsMvtStat for the delivery {string}")
    public void provideTheTotalGdsMvtStatForTheDelivery(String totalGdsMvtStat) {
        ExtentReportManager.logInfoDetails("Provide the totalGdsMvtStat for the delivery");
        deliveryDetails.put("totalGdsMvtStat", totalGdsMvtStat);
    }

    @And("Give the Excel file to get the items for a delivery {string}")
    public void giveTheExcelFileToGetTheItemsForADelivery(String excelFileName) {
        ExtentReportManager.logInfoDetails("Give the Excel file to get the items for a delivery");
        excelUtils = new ExcelUtils(excelFileName);
    }

    @And("Give the sheet name to get the items for which delivery has to be created {string}")
    public void giveTheSheetNameToGetTheItemsForWhichDeliveryHasToBeCreated(String sheetName) {
        ExtentReportManager.logInfoDetails("Give the sheet name to get the items for which delivery has to be created");
        List<LinkedHashMap<String, Object>> dlvItems = excelUtils.getDeliveryItems(sheetName);
        deliveryDetails.put("dlvItems", dlvItems);
        requestPayload.add(deliveryDetails);
      //  System.out.println(requestPayload);
    }

    @And("Give the sheet name {string} and the number of items required in a delivery {int}")
    public void giveTheSheetNameAndTheNumberOfRequiredItemsForWhichDeliveryHasToBeCreated(String sheetName, int requiredItems) {
        ExtentReportManager.logInfoDetails("Give the sheet name and the number of items required in a delivery");
        List<LinkedHashMap<String, Object>> dlvItems = excelUtils.getDeliveryItems(sheetName, requiredItems);
        deliveryDetails.put("dlvItems",dlvItems);
        requestPayload.add(deliveryDetails);
    }

    @When("Requester calls the outbound delivery endpoint to create new delivery for a wave")
    public void requesterCallsTheOutboundDeliveryEndpointToCreateNewDeliveryForAWave() {
        ExtentReportManager.logInfoDetails("Requester calls the outbound delivery endpoint to create new delivery for a wave");
        CreateDeliveryResponse = requestGenerator.getRequest(GenericSteps.userCredential, requestPayload).log().all()
                .when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "CreateDeliverySorterEndPoint"));
        CreateDeliveryResponse.then().log().all();
        ExtentReportManager.logInfoDetails("Response is - ");
        ExtentReportManager.logJson(CreateDeliveryResponse.prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + CreateDeliveryResponse.getStatusCode());
        long responseTime = CreateDeliveryResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(CreateDeliveryResponse);
    }

}
