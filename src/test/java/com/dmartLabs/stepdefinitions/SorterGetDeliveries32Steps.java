package com.dmartLabs.stepdefinitions;


import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import com.dmartLabs.pojo.DlvIemsSorterMain31Pojo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.dmartLabs.stepdefinitions.SorterCreateDelivery31Step.EanList;
import static com.dmartLabs.stepdefinitions.SorterUpdateDelivery33Step.proposedDlvQtyList;


public class SorterGetDeliveries32Steps implements ConStants {
    RequestGenerator requestGenerator = new RequestGenerator();
    Response GetDeliveryResponse;
    String dlvStatusValue;
    String srcSiteIdValue;
    public int totalOriginalBoxes;
    public int ActualTotalBoxCount;

    @When("send GetDelivery End point to get the data in sorter")
    public void sendGetDeliveryEndPointToGetTheDataInSorter(DataTable dataTable) {
        Map<String, String> GetdataDT = dataTable.asMap(String.class, String.class);
        srcSiteIdValue = GetdataDT.get("srcSiteId");
        dlvStatusValue = GetdataDT.get("dlvStatus");

        LinkedHashMap<String, String> MapData = new LinkedHashMap<>();
        MapData.put("srcSiteId", srcSiteIdValue);
        MapData.put("dlvStatus", dlvStatusValue);


        GetDeliveryResponse = requestGenerator.getRequest(CommonUtilities.genericHeader(), MapData)
                .when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "GetSorterDeliveryendPoint"));
        //  GetDeliveryResponse.then().log().all();
        ExtentReportManager.logJson("Response is " + GetDeliveryResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + GetDeliveryResponse.getStatusCode());
        long responseTime = GetDeliveryResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(GetDeliveryResponse);


    }

    @Then("verify delivernumber is present or not in sorter")
    public void verifyDelivernumberIsPresentOrNotInSorter() {
        boolean flag = true;
        ActualTotalBoxCount = SorterCreateDelivery31Step.ActualTotalBoxes;

        List<Map<String, String>> GetdeliverySchemabody = GetDeliveryResponse.jsonPath().get();
        //  System.out.println(CommonUtilities.getDeliveryNumber()+"=======>CommonUtilities.getDeliveryNumber()");
        for (int i = 0; i < GetdeliverySchemabody.size(); i++) {
            //            System.out.println(GetdeliverySchemabody.get(i).get("dlvNumber")+"========>GetdeliverySchemabody.get(i).get(\"dlvNumber\")");
            if (CommonUtilities.getDeliveryNumber().equals(GetdeliverySchemabody.get(i).get("dlvNumber"))) {
                System.out.println("searching for delivery number");
                Map<String, String> FinalGetDeliveryBody = GetdeliverySchemabody.get(i);
                System.out.println("=================================================================");
                //  System.out.println("FinalResponseBody" + "    " + FinalGetDeliveryBody);
                // Convert the map to a JSONObject
                JSONObject jsonObject = new JSONObject(FinalGetDeliveryBody);

                // Print the JSON object
                System.out.println(jsonObject.toString());

                System.out.println("====================================================================");
                String ExpectedDeliveryNumber = FinalGetDeliveryBody.get("dlvNumber");
                String ActualDeliveryNumber = SorterCreateDelivery31Step.createdeliverySorterPojo.dlvNumber;
                System.out.println(ActualDeliveryNumber + "is  actual delivery number ");
                System.out.println(ExpectedDeliveryNumber + "is  Expected delivery number");
                //  System.out.println(ExpectedDeliveryNumber+"=====>ExpectedDeliveryNumber sucessfully verified");

                String ExpecteddstSiteId = FinalGetDeliveryBody.get("dstSiteId");
                String ExpectedsrcSiteId = FinalGetDeliveryBody.get("srcSiteId");
                //  String ExpectedtotalOriginalBoxes = FinalGetDeliveryBody.get("metrics.totalOriginalBoxes");

                //===================================================
                JsonPath jsonPath = new JsonPath(String.valueOf(jsonObject));
                // Fetch the value of totalOriginalBoxes
                totalOriginalBoxes = jsonPath.getInt("metrics.totalOriginalBoxes");
                System.out.println(totalOriginalBoxes + "=========>totalOriginalBoxes");

                //================================================
                String Expecteddock = FinalGetDeliveryBody.get("dock");

                System.out.println(ExpecteddstSiteId + "is ExpecteddstSiteId ");
                System.out.println(ExpectedsrcSiteId + "is  ExpectedsrcSiteId ");
                System.out.println(ActualDeliveryNumber + "===>" + dlvStatusValue);
                //        System.out.println(ExpectedtotalOriginalBoxes + "is  ExpectedtotalOriginalBoxes ");
                System.out.println(Expecteddock + "is  Expecteddock ");


                //     validating delivery number
                if (ExpectedDeliveryNumber.equals(ActualDeliveryNumber)) {
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("Expected delivery number is " + ExpectedDeliveryNumber + " and the actual deliveryNumber is " + ActualDeliveryNumber);
                    Assert.assertEquals(ExpectedDeliveryNumber, ActualDeliveryNumber);
                    System.out.println(ExpectedDeliveryNumber + " ===================>" + "deliveryNumber is present and validation is successful");

                } else {
                    ExtentReportManager.logFailureDetails("Failed");
                    ExtentReportManager.logInfoDetails("Expected delivery number is " + ExpectedDeliveryNumber + " and the actual deliveryNumber is " + ActualDeliveryNumber);
                    System.out.println(ExpectedDeliveryNumber + " ===================>" + "deliveryNumber is not present and validation is fail");
                }
//                flag=true;
                break;
            } else {
                flag = false;
            }

            if (i == GetdeliverySchemabody.size() - 1) {
                if (!flag) {
                    System.out.println("=======================================================================");
                    System.out.println("@delivery number is not present@.................... ");

                }
            }
        }
//validatiing total boxes
        System.out.println(ActualTotalBoxCount + "===============>ActualTotalBoxCount");
        System.out.println(totalOriginalBoxes + "==========>totalOriginalBoxes");
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

    }

    //==========================================================================================
    //get delivery by delivery number
    Response GetDeliveryresponse;
    HashMap<String, String> queryParams = new HashMap<>();

    @And("Provide delivery number {string}to get delivery Details in sorter")
    public void provideDeliveryNumberToGetDeliveryDetailsInSorter(String dlvNumber) {
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

    @When("User calls the get delivery by to get delivery details")
    public void userCallsTheGetDeliveryByToGetDeliveryDetails() {
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


    @And("verify delivery number is present or not in  after creating sorter")
    public void verifyDeliveryNumberIsPresentOrNotInAfterCreatingSorter() {

        boolean flag = true;
        ActualTotalBoxCount = SorterCreateDelivery31Step.ActualTotalBoxes;
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

    //===================================================================================================================
    //change
    @And("verify delivery number is present or not in  after creating sorter change")
    public void verifyDeliveryNumberIsPresentOrNotInAfterCreatingSorterChange() {

        boolean flag = true;
        ActualTotalBoxCount = SorterCreateDelivery31Step.ActualTotalBoxes;
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
//mrp , le qty,item wt, itemvol, storaege type
        List<Map<String, Object>> dlvItemsAL = GetDeliveryresponse.jsonPath().get("dlvItems");
        for (int i = 0; i < dlvItemsAL.size(); i++) {
            Map<String, Object> dlvItemsOnebyone = dlvItemsAL.get(i);
            Object itemseqValue = dlvItemsOnebyone.get("itemseq");
            Object dlvItemNumValue = dlvItemsOnebyone.get("dlvItemNum");

            float ActualitemWt = (float) dlvItemsOnebyone.get("itemWt");
            //System.out.println(ActualitemWt+"========================>ActualitemWt");
            float ActualitemVol = (float) dlvItemsOnebyone.get("itemVol");
            //System.out.println(ActualitemVol+"========================>ActualitemVol");
            String Actualean = (String) dlvItemsOnebyone.get("ean");
            Integer Actualcaselot = (Integer) dlvItemsOnebyone.get("caselot");


            //      originalDlvQty
            Object ActoriginalDlvQtyObj = dlvItemsOnebyone.get("originalDlvQty");
            double ActoriginalDlvQty;
            if (ActoriginalDlvQtyObj instanceof Double) {
                ActoriginalDlvQty = (Double) ActoriginalDlvQtyObj;
            } else if (ActoriginalDlvQtyObj instanceof Integer) {
                ActoriginalDlvQty = ((Integer) ActoriginalDlvQtyObj).doubleValue();
            } else {
                // Handle other types or unexpected values
                ActoriginalDlvQty = 0.0; // Default value or appropriate error handling
            }
            //   System.out.println(ActoriginalDlvQty + "========================>ActoriginalDlvQty");
//==========================================================================


            // le qty
            Object ActleQtyObj = dlvItemsOnebyone.get("leQty");
            double ActleQty;
            if (ActleQtyObj instanceof Double) {
                ActleQty = (Double) ActleQtyObj;
            } else if (ActleQtyObj instanceof Integer) {
                ActleQty = ((Integer) ActleQtyObj).doubleValue();
            } else {
                // Handle other types or unexpected values
                ActleQty = 0.0; // Default value or appropriate error handling
            }
            //   System.out.println(ActleQty + "========================>ActleQty");
//==========================================================================
            //      mrp
            Object mrpObject = dlvItemsOnebyone.get("mrp");
            double ActMrp;
            if (mrpObject instanceof Double) {
                ActMrp = (Double) mrpObject;
            } else if (mrpObject instanceof Integer) {
                ActMrp = ((Integer) mrpObject).doubleValue();
            } else {
                // Handle other types or unexpected values
                ActMrp = 0.0; // Default value or appropriate error handling
            }


            ArrayList<DlvIemsSorterMain31Pojo> ExpectedDlvIremAl = SorterCreateDelivery31Step.createdeliverySorterPojo.getDlvItems();
            for (int k = 0; k < ExpectedDlvIremAl.size(); k++) {
                DlvIemsSorterMain31Pojo ExpectedItemsOnebyone = ExpectedDlvIremAl.get(k);
                //========================================================================================

                Integer ExpectedOriginalDlvQty = Integer.valueOf(ExpectedItemsOnebyone.getProposedDlvQty());

                double Expectedmrp = Double.parseDouble(ExpectedItemsOnebyone.getMrp());
                double Expectesleqty = Double.parseDouble(ExpectedItemsOnebyone.getLeQty());
                float ExpectedItemwt = Float.parseFloat(ExpectedItemsOnebyone.getItemWt());
                float ExpectedItemvol = Float.parseFloat(ExpectedItemsOnebyone.getItemVol());
                float ExpectedCaselot = Float.parseFloat(ExpectedItemsOnebyone.getCaselot());
                int expectedCaselotInteger = (int) ExpectedCaselot;
                String Expectedean = ExpectedItemsOnebyone.getEan();
                System.out.println(Expectedmrp + "====================>Expectedmrp");

                if (ActoriginalDlvQty == ExpectedOriginalDlvQty) {
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("ExpectedOriginalDlvQty is " + ExpectedOriginalDlvQty + " and the ActualoriginalDlvQtyORPropsedDlvQty is " + ActoriginalDlvQty);
                    Assert.assertEquals((double) ActoriginalDlvQty, (double) ExpectedOriginalDlvQty);
                    System.out.println(ExpectedOriginalDlvQty + "" + " ===================>" + ExpectedOriginalDlvQty + "ExpectedOriginalDlvQty is present and validation is successful");
                }

                if (ActMrp == Expectedmrp) {
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("ActMrp is " + ActMrp + " and the Expectedmrp is " + Expectedmrp);
                    Assert.assertEquals((double) ActMrp, (double) Expectedmrp);
                    System.out.println(Expectedmrp + "" + " ===================>" + Expectedmrp + "Expectedmrp is present and validation is successful");
                }
                if (ActleQty == Expectesleqty) {
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("ActleQty is " + ActleQty + " and the Expectesleqty is " + Expectesleqty);
                    Assert.assertEquals((double) ActleQty, (double) Expectesleqty);
                    System.out.println(Expectesleqty + "" + " ===================>" + Expectesleqty + "Expectesleqty is present and validation is successful");
                }

                if (ActualitemWt == ExpectedItemwt) {
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("ActualitemWt is " + ActualitemWt + " and the ExpectedItemwt is " + ExpectedItemwt);
                    Assert.assertEquals((double) ActualitemWt, (double) ExpectedItemwt);
                    System.out.println(ActualitemWt + "" + " ===================>" + ExpectedItemwt + "ExpectedItemwt is present and validation is successful");
                }
                if (ActualitemVol == ExpectedItemvol) {
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("ActualitemVol is " + ActualitemVol + " and the ExpectedItemvol is " + ExpectedItemvol);
                    Assert.assertEquals((double) ActualitemVol, (double) ExpectedItemvol);
                    System.out.println(ActualitemVol + "" + " ===================>" + ExpectedItemvol + "ExpectedItemvol is present and validation is successful");
                }
                if (Actualcaselot == expectedCaselotInteger) {
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("Actualcaselot is " + Actualcaselot + " and the expectedCaselotInteger is " + expectedCaselotInteger);
                    Assert.assertEquals((Integer) Actualcaselot, (Integer) expectedCaselotInteger);
                    System.out.println(Actualcaselot + "" + " ===================>" + expectedCaselotInteger + "expectedCaselotInteger is present and validation is successful");
                }
                for (int j = 0; j < EanList.size(); j++) {
                    if (Actualean.equals(EanList.get(j))) {
                        ExtentReportManager.logPassDetails("Passed");
                        ExtentReportManager.logInfoDetails("Actualean is " + Actualean + " and the Expectedean is " + EanList.get(j));
                        Assert.assertEquals((String) Actualean, (String) EanList.get(j));
                        System.out.println(Actualean + "is actual Ean" + " ===================>" + EanList.get(j) + "Expectedean is present and validation is successful");
                    }
                }


//                else {
//                    ExtentReportManager.logFailureDetails("Failed");
//                    ExtentReportManager.logInfoDetails("ExpectedOriginalDlvQty " + ExpectedOriginalDlvQty + " and the ActoriginalDlvQty is " + ActoriginalDlvQty);
//                    System.out.println(ExpectedOriginalDlvQty + ""+" ===================>"+ExpectedOriginalDlvQty + "ExpectedOriginalDlvQty is not present and validation is fail");
//                }
            }
        }

    }


    //=============================================================================================================
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @And("validating JsonSchema Of object in sorter {string}")
    public void validatingJsonSchemaOfObjectInSorter(String schemaFile) {

        ExtentReportManager.logInfoDetails("verify that schema should be equal {string}");
        CommonUtilities.getResponseInstance().then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(PropertyReader.fileReaders(JSON_SCHEMA_VALIDATION_PATH, schemaFile)));
        JsonSchemaValidator schema = JsonSchemaValidator.matchesJsonSchema(PropertyReader.fileReaders(JSON_SCHEMA_VALIDATION_PATH, schemaFile));
        ExtentReportManager.logInfoDetails(" Schema is pass " + schema);
        System.out.println("===================================================================================");
        System.out.println("schema validation succesful" + "=============================>");
    }


//=======================================================
    //===========================================================================================================
/////////////////////////////////////////////////////////////////////////////////////////
//update delivey get details
//         "updated": true,
//                 "deleted": true,


    @And("verify delivery  is updated  or not in sorter")
    public void verifyDeliveryIsUpdatedOrNotInSorter() {
        String ExpectedStatus = "CREATED";
        String expectedSiteid = CommonUtilities.getDstSiteId();
        String expectedsrcSiteId = CommonUtilities.getSrcSiteId();
        String ExpecteddDlvnumber = CommonUtilities.getDeliveryNumber();
        String ActualdlvNumber = GetDeliveryresponse.jsonPath().get("dlvNumber");
        String actualdstSiteId = GetDeliveryresponse.jsonPath().get("dstSiteId");
        String actualsrcSiteId = GetDeliveryresponse.jsonPath().get("srcSiteId");
        String actualdeliveryStatus = GetDeliveryresponse.jsonPath().get("deliveryStatus");
        String actualdock = GetDeliveryresponse.jsonPath().get("dock");


        if (ExpecteddDlvnumber.equals(ActualdlvNumber)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("Expected delivery number is " + ExpecteddDlvnumber + " and the actual deliveryNumber is " + ActualdlvNumber);
            Assert.assertEquals(ExpecteddDlvnumber, ActualdlvNumber);
            System.out.println(ExpecteddDlvnumber + " ===================>" + "deliveryNumber is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("Expected delivery number is " + ExpecteddDlvnumber + " and the actual deliveryNumber is " + ActualdlvNumber);
            System.out.println(ExpecteddDlvnumber + " ===================>" + "deliveryNumber is not present and validation is fail");
        }
        if (expectedSiteid.equals(actualdstSiteId)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("expectedSiteid is " + expectedSiteid + " and the ActualdstSiteId is " + actualdstSiteId);
            Assert.assertEquals(expectedSiteid, actualdstSiteId);
            System.out.println(expectedSiteid + " ===================>" + "ActualdstSiteId is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expectedSiteid " + expectedSiteid + " and the ActualdstSiteId is " + actualdstSiteId);
            System.out.println(expectedSiteid + " ===================>" + "ActualdstSiteId is not present and validation is fail");
        }

        if (expectedsrcSiteId.equals(actualsrcSiteId)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("expectedsrcSiteId is " + expectedsrcSiteId + " and the ActualsrcSiteId is " + actualsrcSiteId);
            Assert.assertEquals(expectedsrcSiteId, actualsrcSiteId);
            System.out.println(expectedsrcSiteId + " ===================>" + "ActualsrcSiteId is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expectedSiteid " + expectedSiteid + " and the ActualsrcSiteId is " + actualsrcSiteId);
            System.out.println(expectedSiteid + " ===================>" + "ActualsrcSiteId is not present and validation is fail");
        }

        if (ExpectedStatus.equals(actualdeliveryStatus)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("ExpectedStatus is " + ExpectedStatus + " and the ActualdeliveryStatus is " + actualdeliveryStatus);
            Assert.assertEquals(ExpectedStatus, actualdeliveryStatus);
            System.out.println(ExpectedStatus + " ===================>" + "ActualdeliveryStatus is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expectedSiteid " + expectedSiteid + " and the ActualdeliveryStatus is " + actualdeliveryStatus);
            System.out.println(expectedSiteid + " ===================>" + "ActualdeliveryStatus is not present and validation is fail");
        }


        System.out.println(actualdock + "====================>actualdock");


        Object itemseqValue;
        Object dlvItemNumValue;
        // Integer  originalDlvQty;

        List<Map<String, Object>> dlvItemsAL = GetDeliveryresponse.jsonPath().get("dlvItems");
        for (int i = 0; i < dlvItemsAL.size(); i++) {
            Map<String, Object> dlvItemsOnebyone = dlvItemsAL.get(i);
            itemseqValue = dlvItemsOnebyone.get("itemseq");
            dlvItemNumValue = dlvItemsOnebyone.get("dlvItemNum");
            Integer originalDlvQty = (Integer) dlvItemsOnebyone.get("originalDlvQty");
            String Expectedean = (String) dlvItemsOnebyone.get("ean");

            HashMap<String, Object> itemMetricsObject = (HashMap<String, Object>) dlvItemsOnebyone.get("itemMetrics");

            Object ActualtotalOriginalBoxes = itemMetricsObject.get("totalOriginalBoxes");
            Object ActualAdjustedBoxes = itemMetricsObject.get("totalAdjustedBoxes");

            Boolean flag = true;

            Boolean updateFlag = (Boolean) dlvItemsOnebyone.get("updated");
            Boolean deletedFlag = (Boolean) dlvItemsOnebyone.get("deleted");

            System.out.println(itemseqValue + "=================>itemseqValue");
            System.out.println(dlvItemNumValue + "=================>dlvItemNumValue");
            //    System.out.println(originalDlvQty + "=================>originalDlvQty");

            for (int j = 0; j < proposedDlvQtyList.size(); j++) {
                // System.out.println(proposedDlvQtyList.get(j)+"================>proposedDlvQtyList.get(j)   before");
                if (proposedDlvQtyList.get(j).equals(originalDlvQty)) {
                    //  System.out.println(proposedDlvQtyList.get(j)+"================>proposedDlvQtyList.get(j)   after");
                    ExtentReportManager.logPassDetails("Passed");
                    ExtentReportManager.logInfoDetails("proposedDlvQtyList.get(j) " + originalDlvQty + " and the originalDlvQty is " + originalDlvQty);
                    Assert.assertEquals(proposedDlvQtyList.get(j), originalDlvQty);
                    System.out.println(originalDlvQty + " ===================>" + "originalDlvQty is present and updated is successful");
                    break;
                }
            }


            if (updateFlag == true) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("updateFlag is " + "true");
                System.out.println(updateFlag + " ===================>" + "updateFlag is present and validation is successful");
            }


            if (deletedFlag == true) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("deletedFlag is " + "true");
                System.out.println(deletedFlag + " ===================>" + "deletedFlag is present and validation is successful");
            }


        }
    }
//=====================================================================================================================
    //===============================================================================
    //sorter confirm get delivery

    @And("verify delivery number is present or not in  after Confirming the sorter delivery")
    public void verifyDeliveryNumberIsPresentOrNotInAfterConfirmingTheSorterDelivery() {

        String ExpectedDlvStatus = "CONFIRMED";
        String ActualDeliveryStatus = GetDeliveryresponse.jsonPath().get("deliveryStatus");
        String ExpectedDeliveryNumber = CommonUtilities.getDeliveryNumber();
        Object ActualDlvNumber = GetDeliveryresponse.jsonPath().get("dlvNumber");

        Object ActualtotalOriginalItems = GetDeliveryresponse.jsonPath().get("totalOriginalItems");
        Object ActualtotalOriginalBoxes = GetDeliveryresponse.jsonPath().get("totalOriginalBoxes");
        Object ActualtotalAdjustedItems = GetDeliveryresponse.jsonPath().get("totalAdjustedItems");
        Object ActualtotalAdjustedBoxes = GetDeliveryresponse.jsonPath().get("totalAdjustedBoxes");
        Object ActualwaveId = GetDeliveryresponse.jsonPath().get("waveId");

        if (ExpectedDeliveryNumber.equals(ActualDlvNumber)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("ExpectedDeliveryNumber is " + ExpectedDeliveryNumber + " and the ActualDlvNumber is " + ActualDlvNumber);
            Assert.assertEquals(ExpectedDeliveryNumber, ActualDlvNumber);
            System.out.println(ActualDlvNumber + " ===================>" + "ActualDlvNumber is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("ExpectedDeliveryNumber is " + ExpectedDeliveryNumber + " and the ActualDlvNumber is " + ActualDlvNumber);
            System.out.println(ActualDlvNumber + " ===================>" + "ActualDlvNumber is not present and validation is fail");
        }

        if (ExpectedDlvStatus.equals(ActualDeliveryStatus)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("ExpectedDlvStatus is " + ExpectedDlvStatus + " and the ExpectedDlvStatus is " + ExpectedDlvStatus);
            Assert.assertEquals(ExpectedDlvStatus, ActualDeliveryStatus);
            System.out.println(ActualDeliveryStatus + " ===================>" + "ActualDeliveryStatus is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("ActualDeliveryStatus is " + ActualDeliveryStatus + " and the ExpectedDlvStatus is " + ExpectedDlvStatus);
            System.out.println(ExpectedDlvStatus + " ===================>" + "ExpectedDlvStatus is not present and validation is fail");
        }

       LinkedList <HashMap<String, Object>> ConfirmdlvItemsList = GetDeliveryresponse.jsonPath().get("dlvItems");
     //   List<HashMap<String, Object>> ConfirmdlvItemsList = GetDeliveryresponse.jsonPath().get("dlvItems");
        for (int i = 0; i < ConfirmdlvItemsList.size(); i++) {
            HashMap<String, Object> ConfirmdlvItemsOneByone = ConfirmdlvItemsList.get(i);
            Object Actualitemseq = ConfirmdlvItemsOneByone.get("itemseq");
            Object ActualdlvItemNum = ConfirmdlvItemsOneByone.get("dlvItemNum");
            Object ActualleQty = ConfirmdlvItemsOneByone.get("leQty");
            Object ActualstorageType = ConfirmdlvItemsOneByone.get("storageType");

            Boolean flag = true;

            Boolean updateFlag = (Boolean) ConfirmdlvItemsOneByone.get("updated");
            Boolean DeleteFlag = (Boolean) ConfirmdlvItemsOneByone.get("deleted");

            if (updateFlag == true) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("updateFlag is " + "true");
                System.out.println(updateFlag + " ===================>" + "updateFlag is present and validation is successful");
            }
            if (DeleteFlag == true) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("DeleteFlag is " + "true");
                System.out.println(DeleteFlag + " ===================>" + "DeleteFlag is present and validation is successful");
            }

            System.out.println(Actualitemseq + "=======================>Actualitemseq");
            System.out.println(ActualdlvItemNum + "=======================>ActualdlvItemNum");

            System.out.println(ActualstorageType + "=======================>ActualstorageType");

            System.out.println(ActualleQty + "=======================>ActualleQty");


        }

    }

}
