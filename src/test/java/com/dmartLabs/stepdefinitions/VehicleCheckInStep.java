package com.dmartLabs.stepdefinitions;


import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.hu.Ha;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dmartLabs.config.ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH;

public class VehicleCheckInStep {

    HashMap<String,Object>MainObj=new HashMap<>();
    HashMap<String,String>SubObj=new HashMap<>();
    @And("provide all the driver and truck details")
    public void provideAllTheDriverAndTruckDetails(DataTable dataTable) {
        Map<String, String> VehicleCheckInDT = dataTable.asMap(String.class, String.class);
        String driverMobilevalue = VehicleCheckInDT.get("driverMobile");
        String driverNamevalue = VehicleCheckInDT.get("driverName");
        String goingToCityvalue = VehicleCheckInDT.get("goingToCity");
        String vehicleNumbervalue = VehicleCheckInDT.get("vehicleNumber");
        String vehicleTypevalue = VehicleCheckInDT.get("vehicleType");
        String vendorCodevalue = VehicleCheckInDT.get("vendorCode");
        String vendorNamevalue = VehicleCheckInDT.get("vendorName");
        String onContractvalue = VehicleCheckInDT.get("onContract");
        String vehicleTypeIdvalue = VehicleCheckInDT.get("vehicleTypeId");
        String vendorIdvalue = VehicleCheckInDT.get("vendorId");
        Boolean isManualEntryvalue = Boolean.valueOf(VehicleCheckInDT.get("isManualEntry"));
        String invoiceNumbervalue = VehicleCheckInDT.get("invoiceNumber");



        SubObj.put("driverMobile",driverMobilevalue);
        SubObj.put("driverName",driverNamevalue);
        MainObj.put("currentDriver",SubObj);
        MainObj.put("goingToCity",goingToCityvalue);
        MainObj.put("vehicleNumber",vehicleNumbervalue);
        MainObj.put("vehicleType",vehicleTypevalue);
        MainObj.put("vendorCode",vendorCodevalue);
        MainObj.put("vendorName",vendorNamevalue);
        MainObj.put("onContract",onContractvalue);
        MainObj.put("vehicleTypeId",vehicleTypeIdvalue);
        MainObj.put("vendorId",vendorIdvalue);
        MainObj.put("isManualEntry",isManualEntryvalue);
        MainObj.put("invoiceNumber",invoiceNumbervalue);


    }

    @When("User calls the assign vehicle's endpoint to VehicleCheckIn for a delivery")
    public void userCallsTheAssignVehicleSEndpointToVehicleCheckInForADelivery() {


        ExtentReportManager.logInfoDetails("User calls the assign vehicle's endpoint to assign vehicle for a delivery");
        RequestSpecification requestSpecification = RequestGenerator.getRequest(CommonUtilities.genericHeader(), MainObj).log().all();
        Response response = requestSpecification.post(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "VehicleCheckInSorter")).then().log().all().extract().response();
        ExtentReportManager.logInfoDetails("Assign Vehicle Response Status Code is : "+response.getStatusCode());
        ExtentReportManager.logInfoDetails("Assign Vehicle Response Payload is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response Time is : "+response.getTimeIn(TimeUnit.MILLISECONDS)+" ms");
        CommonUtilities.setResponseInstance(response);



    }

}




