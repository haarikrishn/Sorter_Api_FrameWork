package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.GenricUtils;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.dmartLabs.config.ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH;


public class SorterUpdateDelivery33Step {

    RequestGenerator requestGenerator = new RequestGenerator();

    LinkedHashMap<String, Object> Subobject = new LinkedHashMap<>();
    List<LinkedHashMap<String, Object>> dlvItemsAL = new ArrayList<>();
   public static List<Integer>proposedDlvQtyList=new ArrayList<>();
    Response UpdateDeliveryresponse;

    @When("send UpdateDeliveryInSorter End point to update the data in sorter")
    public void sendUpdateDeliveryInSorterEndPointToUpdateTheDataInSorter(DataTable dataTable) {
        Map<String, String> UpdateDeliveryDT = dataTable.asMap(String.class, String.class);

        String dlvNumberValue = UpdateDeliveryDT.get("dlvNumber");
        //   String deleteDlvValue = UpdateDeliveryDT.get("deleteDlv");
        String itemSeqValue = UpdateDeliveryDT.get("itemSeq");
        String proposedDlvQtyValue = UpdateDeliveryDT.get("proposedDlvQty");
        //String deleteItemValue = UpdateDeliveryDT.get("deleteItem");
        String changedByValue = UpdateDeliveryDT.get("changedBy");
        String totalGdsMvtStatValue = UpdateDeliveryDT.get("totalGdsMvtStat");

//        String random = GenricUtils.getRandomDeliveryNumber();
//        CommonUtilities.setDeliveryNumber(random);
//
        String dlvnumObject = CommonUtilities.getDeliveryNumber();


        try {
            if (dlvNumberValue.equals(null)) {
                Subobject.put("dlvNumber", dlvnumObject);
                // Subobject.put("dlvNumber", dlvNumberValue);
            } else {
                Subobject.put("dlvNumber", dlvNumberValue);
                // Subobject.put("dlvNumber", dlvnumObject);
            }
        } catch (NullPointerException e) {
            Subobject.put("dlvNumber", dlvnumObject);
        }
        //Subobject.put("deleteDlv", deleteDlvValue);
        Subobject.put("changedBy", changedByValue);
        Subobject.put("changedDate", GenricUtils.getDate("yyyyMMdd"));
        Subobject.put("changedTime", GenricUtils.getTime("HHmmss"));
        Subobject.put("totalGdsMvtStat", totalGdsMvtStatValue);
        //============
    }

    @And("add items under dlvItems in UpdateDeliveryInSorter")
    public void addItemsUnderDlvItemsInUpdateDeliveryInSorter(DataTable dataTable) {

        List<Map<String, String>> dlvItemsUpdateDeliveryDT = dataTable.asMaps();
        for (int i = 0; i < dlvItemsUpdateDeliveryDT.size(); i++) {
            Map<String, String> dlvItemOneByOne = dlvItemsUpdateDeliveryDT.get(i);//data table
            LinkedHashMap<String, Object> dlvItemsObj = new LinkedHashMap<>();
            dlvItemsObj.put("itemSeq", dlvItemOneByOne.get("itemSeq"));
            dlvItemsObj.put("proposedDlvQty", dlvItemOneByOne.get("proposedDlvQty"));
            proposedDlvQtyList.add(Integer.valueOf(dlvItemOneByOne.get("proposedDlvQty")));
            //    dlvItemsObj.put("deleteItem", dlvItemOneByOne.get("deleteItem"));
            dlvItemsAL.add(dlvItemsObj);
        }
        Subobject.put("dlvItems", dlvItemsAL);


        // ExtentReportManager.logInfoDetails("User calls the Confirm Delivery's endpoint to confirm the Delivery Request");
        RequestSpecification requestSpecification = requestGenerator.getRequest(GenericSteps.userCredential, Subobject).log().all();

        UpdateDeliveryresponse = requestSpecification.put(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "UpdateDeliverySorterEndPoint")).then().log().all().extract().response();
        ExtentReportManager.logJson("Response is " + UpdateDeliveryresponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + UpdateDeliveryresponse.getStatusCode());
        long responseTime = UpdateDeliveryresponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(UpdateDeliveryresponse);


    }


    //================================================================================================
    //delete the item

    @When("send UpdateDeliveryInSorter End point to update the data in sorter with deleting  the item")
    public void sendUpdateDeliveryInSorterEndPointToUpdateTheDataInSorterWithDeletingTheItem(DataTable dataTable) {

        Map<String, String> UpdateDeliveryDT = dataTable.asMap(String.class, String.class);

        String dlvNumberValue = UpdateDeliveryDT.get("dlvNumber");
        String deleteDlvValue = UpdateDeliveryDT.get("deleteDlv");
        String itemSeqValue = UpdateDeliveryDT.get("itemSeq");
        String proposedDlvQtyValue = UpdateDeliveryDT.get("proposedDlvQty");
        String deleteItemValue = UpdateDeliveryDT.get("deleteItem");
        String changedByValue = UpdateDeliveryDT.get("changedBy");
        String totalGdsMvtStatValue = UpdateDeliveryDT.get("totalGdsMvtStat");

//        String random = GenricUtils.getRandomDeliveryNumber();
//        CommonUtilities.setDeliveryNumber(random);
//
        String dlvnumObject = CommonUtilities.getDeliveryNumber();
        try {
            if (dlvNumberValue.equals(null)) {
                Subobject.put("dlvNumber", dlvnumObject);
                // Subobject.put("dlvNumber", dlvNumberValue);
            } else {
                Subobject.put("dlvNumber", dlvNumberValue);
                // Subobject.put("dlvNumber", dlvnumObject);
            }
        } catch (NullPointerException e) {
            Subobject.put("dlvNumber", dlvnumObject);
        }
        Subobject.put("deleteDlv", deleteDlvValue);
        Subobject.put("changedBy", changedByValue);
        Subobject.put("changedDate", GenricUtils.getDate("yyyyMMdd"));
        Subobject.put("changedTime", GenricUtils.getTime("HHmmss"));
        Subobject.put("totalGdsMvtStat", totalGdsMvtStatValue);
        //============
    }


    @And("add items under dlvItems in UpdateDeliveryInSorter with deleting the item")
    public void addItemsUnderDlvItemsInUpdateDeliveryInSorterWithDeletingTheItem(DataTable dataTable) {

        List<Map<String, String>> dlvItemsUpdateDeliveryDT = dataTable.asMaps();
        for (int i = 0; i < dlvItemsUpdateDeliveryDT.size(); i++) {
            Map<String, String> dlvItemOneByOne = dlvItemsUpdateDeliveryDT.get(i);//data table
            LinkedHashMap<String, Object> dlvItemsObj = new LinkedHashMap<>();
            dlvItemsObj.put("itemSeq", dlvItemOneByOne.get("itemSeq"));
            dlvItemsObj.put("proposedDlvQty", dlvItemOneByOne.get("proposedDlvQty"));
            dlvItemsObj.put("deleteItem", dlvItemOneByOne.get("deleteItem"));
            dlvItemsAL.add(dlvItemsObj);
        }
        Subobject.put("dlvItems", dlvItemsAL);


        // ExtentReportManager.logInfoDetails("User calls the Confirm Delivery's endpoint to confirm the Delivery Request");
        RequestSpecification requestSpecification = requestGenerator.getRequest(GenericSteps.totalObj, Subobject).log().all();

        UpdateDeliveryresponse = requestSpecification.put(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "UpdateDeliverySorterEndPoint")).then().log().all().extract().response();
        ExtentReportManager.logJson("Response is " + UpdateDeliveryresponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + UpdateDeliveryresponse.getStatusCode());
        long responseTime = UpdateDeliveryresponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(UpdateDeliveryresponse);

    }

    //=================================================================================================
//
//
//
//    get SorterUpdateDelivery
    Response GetDeliveryresponse;
    HashMap<String, String> queryParams = new HashMap<>();

    @And("Provide delivery number {string}to get delivery Details in sorterOne")
    public void provideDeliveryNumberToGetDeliveryDetailsInSorterOne(String dlvNumber) {
        //get delivery by delivery number


        String dlvobject = CommonUtilities.getDeliveryNumber();
        System.out.println(CommonUtilities.getDeliveryNumber() + "========>CommonUtilities.getDeliveryNumber()");
        try {
            if (dlvNumber.equals("")) {

                queryParams.put("dlvNumber", dlvobject);

            } else {
                queryParams.put("dlvNumber", dlvNumber);
                // Subobject.put("dlvNumber", dlvnumObject);
            }
        } catch (NullPointerException e) {
            queryParams.put("dlvNumber", dlvobject);
        }

    }


    @When("User calls the get delivery by to get delivery detailsOne")
    public void userCallsTheGetDeliveryByToGetDeliveryDetailsOne() {
        ExtentReportManager.logInfoDetails("User calls the get delivery by dlvNumber and truckType to get the confirmed delivery");
        RequestSpecification requestSpecification = RequestGenerator.getRequestWithQueryParam(CommonUtilities.genericHeader(), queryParams);


        GetDeliveryresponse = requestSpecification.get(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "GetDliveryByDlvnumberSorter"));
        //   GetDeliveryresponse.then().log().all();
        ExtentReportManager.logInfoDetails("Get Delivery Response Status Code is : " + GetDeliveryresponse.getStatusCode());
        ExtentReportManager.logInfoDetails("Get Delivery Response Payload is - ");
        ExtentReportManager.logJson(GetDeliveryresponse.prettyPrint());
        ExtentReportManager.logInfoDetails("Response Time is : " + GetDeliveryresponse.getTimeIn(TimeUnit.MILLISECONDS) + " ms");
        CommonUtilities.setResponseInstance(GetDeliveryresponse);


    }

    @And("verify delivery  is updated  or not in sorterOne")
    public void verifyDeliveryIsUpdatedOrNotInSorterOne() {


        boolean flag = true;
        int ActualTotalBoxCount = SorterCreateDelivery31Step.ActualTotalBoxes;
        String ActualDeliveryNumber;
        ActualDeliveryNumber = GetDeliveryresponse.jsonPath().get("dlvNumber");
        String expectedDeliveryNumber = CommonUtilities.getDeliveryNumber();

        String ActualdstSiteId = GetDeliveryresponse.jsonPath().get("dstSiteId");
        String expectedSiteid = CommonUtilities.getDstSiteId();
        String ActualsrcSiteId = GetDeliveryresponse.jsonPath().get("srcSiteId");
        String expectedsrcSiteId = CommonUtilities.getSrcSiteId();
        String ExpectedStatus = "CREATED";
        String ActualdeliveryStatus = GetDeliveryresponse.jsonPath().get("deliveryStatus");
        String Actualdock = GetDeliveryresponse.jsonPath().get("dock");
        int totalOriginalItems = Integer.parseInt(GetDeliveryresponse.jsonPath().get("metrics.totalOriginalItems").toString());
        int totalOriginalBoxes = Integer.parseInt(GetDeliveryresponse.jsonPath().get("metrics.totalOriginalBoxes").toString());
        int totalAdjustedBoxes = Integer.parseInt(GetDeliveryresponse.jsonPath().get("metrics.totalAdjustedBoxes").toString());

        //jsonPath.get("metrics.totalOriginalBoxes");
//dlv number,srcsiteid,dstid,status, original boxes,
        //ean//original dlv quantity/proposed dlv qty,
        if (ActualDeliveryNumber.equals(expectedDeliveryNumber)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("Expected delivery number is " + expectedDeliveryNumber + " and the actual deliveryNumber is " + ActualDeliveryNumber);
            Assert.assertEquals(expectedDeliveryNumber, ActualDeliveryNumber);
            System.out.println(expectedDeliveryNumber + " ===================>" + "deliveryNumber is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("Expected delivery number is " + expectedDeliveryNumber + " and the actual deliveryNumber is " + ActualDeliveryNumber);
            System.out.println(expectedDeliveryNumber + " ===================>" + "deliveryNumber is not present and validation is fail");
        }
        //===========
        if (expectedSiteid.equals(ActualdstSiteId)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("expectedSiteid is " + expectedSiteid + " and the ActualdstSiteId is " + ActualdstSiteId);
            Assert.assertEquals(expectedSiteid, ActualdstSiteId);
            System.out.println(expectedSiteid + " ===================>" + "ActualdstSiteId is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expectedSiteid " + expectedSiteid + " and the ActualdstSiteId is " + ActualdstSiteId);
            System.out.println(expectedSiteid + " ===================>" + "ActualdstSiteId is not present and validation is fail");
        }

        if (expectedsrcSiteId.equals(ActualsrcSiteId)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("expectedsrcSiteId is " + expectedsrcSiteId + " and the ActualsrcSiteId is " + ActualsrcSiteId);
            Assert.assertEquals(expectedsrcSiteId, ActualsrcSiteId);
            System.out.println(expectedsrcSiteId + " ===================>" + "ActualsrcSiteId is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expectedSiteid " + expectedSiteid + " and the ActualsrcSiteId is " + ActualsrcSiteId);
            System.out.println(expectedSiteid + " ===================>" + "ActualsrcSiteId is not present and validation is fail");
        }

        if (ExpectedStatus.equals(ActualdeliveryStatus)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("ExpectedStatus is " + ExpectedStatus + " and the ActualdeliveryStatus is " + ActualdeliveryStatus);
            Assert.assertEquals(ExpectedStatus, ActualdeliveryStatus);
            System.out.println(ExpectedStatus + " ===================>" + "ActualdeliveryStatus is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expectedSiteid " + expectedSiteid + " and the ActualdeliveryStatus is " + ActualdeliveryStatus);
            System.out.println(expectedSiteid + " ===================>" + "ActualdeliveryStatus is not present and validation is fail");
        }


        //==============================
        if (ActualTotalBoxCount == totalOriginalBoxes) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("Expected delivery number is " + totalOriginalBoxes + " and the actual total box count is " + totalOriginalBoxes);
            Assert.assertEquals(ActualTotalBoxCount, totalOriginalBoxes);
            System.out.println(totalOriginalBoxes + " ===================>" + "totalOriginalBoxes is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("Expected delivery number is " + totalOriginalBoxes + " and the actual deliveryNumber is " + ActualTotalBoxCount);
            System.out.println(ActualTotalBoxCount + " ===================>" + "ActualTotalBoxCount is not present and validation is fail");
        }

        System.out.println("Actualdock is" + "========>" + Actualdock);
        ExtentReportManager.logInfoDetails("Actualdock is" + "========>" + Actualdock);
        System.out.println("totalOriginalItems is" + "========>" + totalOriginalItems);
        ExtentReportManager.logInfoDetails("totalOriginalItems is" + "========>" + totalOriginalItems);
        System.out.println("totalOriginalBoxes is" + "========>" + totalOriginalBoxes);
        ExtentReportManager.logInfoDetails("totalOriginalBoxes is" + "========>" + totalOriginalBoxes);
        System.out.println("totalAdjustedBoxes is" + "========>" + totalAdjustedBoxes);
        ExtentReportManager.logInfoDetails("totalAdjustedBoxes is" + "========>" + totalAdjustedBoxes);


    }

    @When("User calls the sorter  Update Delivery endPoint for all flow {string} {string} {string}	{string} {string} {string} {string} 	{string}	{string} {string} {string} {string} {string}  {string} {string}	{string}	{string} {string}	{string}	{string}	{string}	{string}	{string}	{string}	{string}	{string} {string} {string} {string} {string}")
    public void userCallsTheSorterUpdateDeliveryEndPointForAllFlow(String dlvNumber, String srcSiteId, String dstSiteId, String whNumber, String createdBy, String creationDate, String totalGdsMvtStat, String itemSeq, String stoNum, String stoLineNum, String ean, String mrp, String matNum, String itemDesc, String matGrp, String catInd, String stLoc, String batch, String proposedDlvQty, String caselot, String uom, String itemWt, String uomWt, String itemVol, String uomVol, String pickStatus, String leQty, String storageType, String itemSeq1, String proposedDlvQty1) {



            LinkedHashMap<String, Object> dlvItemsObj = new LinkedHashMap<>();
            dlvItemsObj.put("itemSeq", itemSeq1);
            dlvItemsObj.put("proposedDlvQty",proposedDlvQty1);
            dlvItemsAL.add(dlvItemsObj);
        Subobject.put("dlvItems", dlvItemsAL);


        // ExtentReportManager.logInfoDetails("User calls the Confirm Delivery's endpoint to confirm the Delivery Request");
        RequestSpecification requestSpecification = requestGenerator.getRequest(GenericSteps.totalObj, Subobject).log().all();

        UpdateDeliveryresponse = requestSpecification.put(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "UpdateDeliverySorterEndPoint")).then().log().all().extract().response();
        ExtentReportManager.logJson("Response is " + UpdateDeliveryresponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + UpdateDeliveryresponse.getStatusCode());
        long responseTime = UpdateDeliveryresponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(UpdateDeliveryresponse);

    }


}




