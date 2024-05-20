package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.dmartLabs.config.ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH;


public class SorterAdjustDeliveryy34Step {

    RequestGenerator requestGenerator = new RequestGenerator();


    LinkedHashMap<String, Object> mainobj = new LinkedHashMap<>();
    LinkedList<HashMap<String, Object>> TotalobjectAl = new LinkedList<>();

    @When("send AdjustDelivery End point to AdjustDelivery  data in sorter")
    public void sendAdjustDeliveryEndPointToAdjustDeliveryDataInSorter(DataTable dataTable) {
        Map<String, String> AdjustDT = dataTable.asMap(String.class, String.class);
        String dlvNumberValue = AdjustDT.get("dlvNumber");
        String DockValue = AdjustDT.get("dock");
        //   mainobj.put("dlvNumber", dlvNumberValue);
        String dlvobject = CommonUtilities.getDeliveryNumber();
        System.out.println(CommonUtilities.getDeliveryNumber() + "========>CommonUtilities.getDeliveryNumber()");
        try {
            if (dlvNumberValue.equals("")) {

                mainobj.put("dlvNumber", dlvobject);

            } else {
                mainobj.put("dlvNumber", dlvNumberValue);

            }
        } catch (NullPointerException e) {
            mainobj.put("dlvNumber", dlvobject);
        }

        mainobj.put("dock", DockValue);

    }

    Response AdjustResponse;

    //  | vehicleType | vehicleRegNum |
    @And("add items under vehicles in AdjustDeliveryInSorter")
    public void addItemsUnderVehiclesInAdjustDeliveryInSorter(DataTable dataTable) {
        List<Map<String, String>> vechiclesListDT = dataTable.asMaps();
        LinkedList<Map<String, String>> vehiclesList = new LinkedList<>();
        LinkedHashMap<String, String> vehicles = null;
        for (int i = 0; i < vechiclesListDT.size(); i++) {
            Map<String, String> itemOneByone = vechiclesListDT.get(i);
            String vehicleTypeValue = itemOneByone.get("vehicleType");
            String vehicleRegNumValue = itemOneByone.get("vehicleRegNum");
            vehicles = new LinkedHashMap<>();
            vehicles.put("vehicleType", vehicleTypeValue);
            vehicles.put("vehicleRegNum", vehicleRegNumValue);
            vehiclesList.add(vehicles);
        }

        mainobj.put("vehicles", vehiclesList);
        TotalobjectAl.add(mainobj);
        System.out.println("==============================================================");
        System.out.println(TotalobjectAl+"=================>TotalobjectAl");
        System.out.println("===============================================================");


        RequestSpecification AdjustRequest = requestGenerator.getRequest(CommonUtilities.genericHeader(), TotalobjectAl);
        AdjustRequest.log().all();
        AdjustResponse =  AdjustRequest  .when().post(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "OptimizeDeliverySorter"));
         // AdjustResponse.then().log().all();
        ExtentReportManager.logJson("Response is " + AdjustResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + AdjustResponse.getStatusCode());
        long responseTime = AdjustResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(AdjustResponse);

    }


    Object ActualdlvNumber;
    Object ActualdstSiteId;
    Object ActualdeliveryStatus;
    Object Actualdock;
    Object ActualtotalOriginalItems;
    Object ActualtotalOriginalBoxes;
    Object ActualtotalAdjustedItems;
    Object ActualtotalAdjustedBoxes;
    Object itemseqValue;
    Object dlvItemNumValue;
    Object originalDlvQtyValue;
    Object adjustedDlvQtyValue;

    Object ActualsrcSiteId;

    @Then("verify data  is Updated or not in AdjustDelivery sorter")
    public void verifyDataIsUpdatedOrNotInAdjustDeliverySorter() {

        String ExpectedDeliverynumber = CommonUtilities.getDeliveryNumber();
        String ExpectedDstSitid = CommonUtilities.getDstSiteId();
        String ExpectedsrcsiteId = CommonUtilities.getSrcSiteId();
        String ExpectedStatus = "CONFIRMED";
      //  List<HashMap<String, Object>> TotalResponseAl = AdjustResponse.jsonPath().get();
        List<HashMap<String, Object>> TotalResponseAl = AdjustResponse.jsonPath().get();
        for (int i = 0; i < TotalResponseAl.size(); i++) {
            HashMap<String, Object> ItemOneByone = TotalResponseAl.get(i);
            ActualdlvNumber = ItemOneByone.get("dlvNumber");
            ActualdstSiteId = ItemOneByone.get("dstSiteId");
            ActualsrcSiteId = ItemOneByone.get("srcSiteId");
            ActualdeliveryStatus = ItemOneByone.get("deliveryStatus");
            //   CONFIRMED
            Actualdock = ItemOneByone.get("dock");

            if (ExpectedDeliverynumber.equals(ActualdlvNumber)) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("ExpectedDeliverynumber is " + ExpectedDeliverynumber + " and the ActualdlvNumber is " + ActualdlvNumber);
                Assert.assertEquals(ExpectedDeliverynumber, ActualdlvNumber);
                System.out.println(ActualdlvNumber + " ===================>" + "ActualdlvNumber is present and validation is successful");

            }

            if (ExpectedDstSitid.equals(ActualdstSiteId)) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("ExpectedDstSitid is " + ExpectedDstSitid + " and the ActualdstSiteId is " + ActualdstSiteId);
                Assert.assertEquals(ExpectedDstSitid, ActualdstSiteId);
                System.out.println(ExpectedDstSitid + " ===================>" + "ExpectedDstSitid is present and validation is successful");

            }

            if (ExpectedsrcsiteId.equals(ActualsrcSiteId)) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("ExpectedsrcsiteId is " + ExpectedsrcsiteId + " and the ExpectedsrcsiteId is " + ExpectedsrcsiteId);
                Assert.assertEquals(ExpectedsrcsiteId, ActualsrcSiteId);
                System.out.println(ExpectedsrcsiteId + " ===================>" + "ExpectedsrcsiteId is present and validation is successful");

            }
            if (ExpectedStatus.equals(ActualdeliveryStatus)) {
                ExtentReportManager.logPassDetails("Passed");
                ExtentReportManager.logInfoDetails("ExpectedStatus is " + ExpectedStatus + " and the ActualdeliveryStatus is " + ActualdeliveryStatus);
                Assert.assertEquals(ExpectedStatus, ActualdeliveryStatus);
                System.out.println(ExpectedStatus + " ===================>" + "ExpectedStatus is present and validation is successful");

            }


            HashMap<String, String> metricsObject = (HashMap<String, String>) ItemOneByone.get("metrics");
            for (int k = 0; k < metricsObject.size(); k++) {
                ActualtotalOriginalItems = metricsObject.get("totalOriginalItems");
                ActualtotalOriginalBoxes = metricsObject.get("totalOriginalBoxes");
                ActualtotalAdjustedItems = metricsObject.get("totalAdjustedItems");
                ActualtotalAdjustedBoxes = metricsObject.get("totalAdjustedBoxes");

            }
            System.out.println(ActualtotalOriginalItems + "===========>ActualtotalOriginalItems");
            System.out.println(ActualtotalOriginalBoxes + "=============>ActualtotalOriginalBoxes");
            System.out.println(ActualtotalAdjustedItems + "===================>ActualtotalAdjustedItems");
            System.out.println(ActualtotalAdjustedBoxes + "============>ActualtotalAdjustedBoxes");

            System.out.println(Actualdock + "=============>Actualdock");

//            List<HashMap<String, String>> ListdlvItems = (List<HashMap<String, String>>) ItemOneByone.get("dlvItems");
//            System.out.println(ListdlvItems.size() + "================>ListOfitems.size()");
            List<HashMap<String, Object>> ListDlvItems1 = (List<HashMap<String, Object>>) ItemOneByone.get("dlvItems");
            for (int j = 0; j < ListDlvItems1.size(); j++) {
                HashMap<String, Object> dlvItemsoneByOne = (HashMap<String, Object>) ListDlvItems1.get(j);
                itemseqValue = dlvItemsoneByOne.get("itemseq");
                dlvItemNumValue = dlvItemsoneByOne.get("dlvItemNum");
                originalDlvQtyValue = dlvItemsoneByOne.get("originalDlvQty");
                adjustedDlvQtyValue = dlvItemsoneByOne.get("adjustedDlvQty");


                System.out.println(itemseqValue + "=============>itemseqValue" );
                System.out.println(dlvItemNumValue + "=============>dlvItemNumValue" );
                System.out.println(originalDlvQtyValue + "=============>originalDlvQtyValue" );
                System.out.println(adjustedDlvQtyValue + "=============>adjustedDlvQtyValue");

            }

        }
    }

    //====================================================================================================


}
