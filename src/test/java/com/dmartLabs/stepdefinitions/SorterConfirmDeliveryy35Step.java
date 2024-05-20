package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.dmartLabs.config.ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH;


public class SorterConfirmDeliveryy35Step {

    RequestGenerator requestGenerator = new RequestGenerator();
    Response ConfirmDeliveryResponse;
    private LinkedHashMap<String, Object> requestPayload;

    //      | siteId     | 1034 |
//            | deliveries |      |
    @When("send confirmDelivery End point to confirmDelivery  data in sorter")
    public void sendConfirmDeliveryEndPointToConfirmDeliveryDataInSorter(DataTable dataTable) throws InterruptedException {
        Map<String, Object> confirmDT = dataTable.asMap(String.class, String.class);
        Object siteDt = confirmDT.get("siteId");
        Object deliveriesDT = confirmDT.get("deliveries");
        // List<HashMap<String,Object>>AdjustDeliveryResponce=new ArrayList<>();
        List<HashMap<String, Object>> ReponseMap = new ArrayList<>();

        HashMap<String, Object> ReponseMap1 = new HashMap<>();

        List<LinkedHashMap<String, Object>> Expecteddata = CommonUtilities.getResponseInstance().as(new TypeRef<List<LinkedHashMap<String, Object>>>() {
        });

        //   List<HashMap<String,Object>> Ex =  CommonUtilities.getResponseInstance().as(new TypeRef<List<HashMap<String, Object>>>() {});
        //System.out.println(Expecteddata.prettyPrint()+"==================>Expecteddata.prettyPrint()");

        ReponseMap1.put("siteId", siteDt);
        try {
            if (deliveriesDT.equals("")) {

                ReponseMap1.put("deliveries", Expecteddata);

            } else {
                ReponseMap1.put("deliveries", deliveriesDT);

            }
        } catch (NullPointerException e) {
            ReponseMap1.put("deliveries", Expecteddata);
            //  System.out.println("ResponseMap1=====================================>");
            //    System.out.println(ReponseMap1);
            //       ReponseMap1.put("deliveries", Expecteddata.asString());
        }

      //  ReponseMap.add(ReponseMap1);
       System.out.println("=========================================================");
          System.out.println(ReponseMap1+"=============================>ReponseMap1");
       System.out.println("===========================================================");

        RequestSpecification ConfirmDeliveryRequest = requestGenerator.getRequest(CommonUtilities.genericHeader(), ReponseMap1);
        ConfirmDeliveryRequest.log().all();
        ConfirmDeliveryResponse= ConfirmDeliveryRequest. when().post(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "confirmDeliverysorter"));
         // ConfirmDeliveryResponse.then().log().all();
        ExtentReportManager.logJson("Response is " + ConfirmDeliveryResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + ConfirmDeliveryResponse.getStatusCode());
        long responseTime = ConfirmDeliveryResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(ConfirmDeliveryResponse);

Thread.sleep(5000);
    }


    @Then("verify data  is Updated or not in confirmDelivery sorter")
    public void verifyDataIsUpdatedOrNotInConfirmDeliverySorter() {


        String ExpectedStatus = "PLANNED";
        String expecteddSiteid = CommonUtilities.getDstSiteId();
        String expectedSrcSiteId = CommonUtilities.getSrcSiteId();
        String ExpecteddDlvnumber = CommonUtilities.getDeliveryNumber();
        //   String ActualdlvNumber = ConfirmDeliveryResponse.jsonPath().get("dlvNumber");
        // String actualdstSiteId = ConfirmDeliveryResponse.jsonPath().get("dstSiteId");
        String actualSiteId = ConfirmDeliveryResponse.jsonPath().get("siteId");
        String actualdeliveryStatus = ConfirmDeliveryResponse.jsonPath().get("status");
        String actualwaveId = ConfirmDeliveryResponse.jsonPath().get("waveId");

        List<HashMap<String, Object>> AllResponseList = ConfirmDeliveryResponse.jsonPath().get("deliveries");
        Object ActualdlvNumber = null;
        Object ActualdstSiteId = null;
        for (int i = 0; i < AllResponseList.size(); i++) {
            HashMap<String, Object> ItemOneByOne = AllResponseList.get(i);
            ActualdlvNumber = ItemOneByOne.get("dlvNumber");
            ActualdstSiteId = ItemOneByOne.get("dstSiteId");
            Object Actualdock = ItemOneByOne.get("dock");

        }
        if (expecteddSiteid.equals(ActualdstSiteId)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("expecteddSiteid is " + expecteddSiteid + " and the ActualdstSiteId is " + ActualdstSiteId);
            Assert.assertEquals(expecteddSiteid, ActualdstSiteId);
            System.out.println(expecteddSiteid + " ===================>" + "ActualdstSiteId is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expecteddSiteid is " + expecteddSiteid + " and the ActualdstSiteId is " + ActualdstSiteId);
            System.out.println(expecteddSiteid + " ===================>" + "expecteddSiteid is not present and validation is fail");
        }

        if (ExpecteddDlvnumber.equals(ActualdlvNumber)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("ExpecteddDlvnumber is " + ExpecteddDlvnumber + " and the ActualdlvNumber is " + ActualdlvNumber);
            Assert.assertEquals(ExpecteddDlvnumber, ActualdlvNumber);
            System.out.println(ExpecteddDlvnumber + " ===================>" + "ExpecteddDlvnumber is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("ExpecteddDlvnumber is " + ExpecteddDlvnumber + " and the ActualdlvNumber is " + ActualdlvNumber);
            System.out.println(ExpecteddDlvnumber + " ===================>" + "ExpecteddDlvnumber is not present and validation is fail");
        }

        if (ExpectedStatus.equals(actualdeliveryStatus)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("ExpectedStatus is " + ExpectedStatus + " and the actualdeliveryStatus is " + actualdeliveryStatus);
            Assert.assertEquals(ExpectedStatus, actualdeliveryStatus);
            System.out.println(ExpectedStatus + " ===================>" + "ExpectedStatus is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("ExpectedStatus is " + ExpectedStatus + " and the actualdeliveryStatus is " + actualdeliveryStatus);
            System.out.println(ExpecteddDlvnumber + " ===================>" + "actualdeliveryStatus is not present and validation is fail");
        }

        if (actualSiteId.equals(expectedSrcSiteId)) {
            ExtentReportManager.logPassDetails("Passed");
            ExtentReportManager.logInfoDetails("expectedSiteId is " + expectedSrcSiteId + " and the actualSiteId is " + actualSiteId);
            Assert.assertEquals(expectedSrcSiteId, actualSiteId);
            System.out.println(expectedSrcSiteId + " ===================>" + "expectedSiteId is present and validation is successful");

        } else {
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("expectedSiteId is " + expectedSrcSiteId + " and the actualSiteId is " + actualSiteId);
            System.out.println(expectedSrcSiteId + " ===================>" + "actualSiteId is not present and validation is fail");
        }




        System.out.println(actualwaveId + "==================>actualwaveId");



    }




    //===============================================================================================
    //sorter confirm get delivery


}
