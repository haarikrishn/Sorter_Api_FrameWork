package com.dmartLabs.stepdefinitions;


import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.dmartLabs.config.ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH;

public class AssignVehicleStep {

HashMap<String,Object>TotalObj=new HashMap<>();
Response response;
//
//    {
//        "dlvNumber": "0084797803",
//            "vehicles": [
//        {
//            "vehicleType":"1109",
//                "vehicleRegNum":"RG465757"
//        }
//    ],
//        "currentDriver":null
//    }

    @And("Get delivery number to which vehicle is to be assigned")
    public void getDeliveryNumberToWhichVehicleIsToBeAssigned() {

        ExtentReportManager.logInfoDetails("Provide delivery number to which vehicle is to be assigned");
//        assignVehiclePayload = new HashMap<>();
//        assignVehiclePayload.put("dlvNumber", CommonUtilities.getDeliveryNumber());
        TotalObj.put("dlvNumber", CommonUtilities.getDeliveryNumber());
    }

    @And("Provide dock vehicle type and vehicle number for a delivery to be assigned vehicle")
    public void provideDockVehicleTypeAndVehicleNumberForADeliveryToBeAssignedVehicle(DataTable dataTable) {
        ExtentReportManager.logInfoDetails("Provide dock vehicle type and vehicle number for a delivery to be assigned vehicle");
        Map<String, String> AssignDT = dataTable.asMap(String.class, String.class);
        String dlvNumberValue = AssignDT.get("dlvNumber");
        String vehicleTypeValue = AssignDT.get("vehicleType");
        String vehicleRegNumValue = AssignDT.get("vehicleRegNum");
        String currentDriverValue = AssignDT.get("currentDriver");
        String dockValue = AssignDT.get("dock");
        List<HashMap<String,String>>VehiclesAl=new ArrayList<>();
        HashMap<String,String>VehiclesObj=new HashMap<>();
        String dlvnumObject = CommonUtilities.getDeliveryNumber();
        try {
            if (dlvNumberValue.equals(null)) {
                TotalObj.put("dlvNumber", dlvnumObject);
                // Subobject.put("dlvNumber", dlvNumberValue);
            } else {
                TotalObj.put("dlvNumber", dlvNumberValue);
                // Subobject.put("dlvNumber", dlvnumObject);
            }
        } catch (NullPointerException e) {
            TotalObj.put("dlvNumber", dlvnumObject);
        }
        TotalObj.put("dock", dockValue);
        VehiclesObj.put("vehicleType",vehicleTypeValue);
        VehiclesObj.put("vehicleRegNum",vehicleRegNumValue);
        VehiclesAl.add(VehiclesObj);
        TotalObj.put("vehicles",VehiclesAl);
        TotalObj.put("currentDriver",currentDriverValue);
    }



    @When("User calls the assign vehicle's endpoint to assign vehicle for a delivery")
    public void userCallsTheAssignVehicleSEndpointToAssignVehicleForADelivery() {


        ExtentReportManager.logInfoDetails("User calls the assign vehicle's endpoint to assign vehicle for a delivery");
        RequestSpecification requestSpecification = RequestGenerator.getRequest(CommonUtilities.genericHeader(), TotalObj).log().all();
        Response response = requestSpecification.put(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "ASSIGN_VEHICLESorter")).then().log().all().extract().response();
        ExtentReportManager.logInfoDetails("Assign Vehicle Response Status Code is : "+response.getStatusCode());
        ExtentReportManager.logInfoDetails("Assign Vehicle Response Payload is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response Time is : "+response.getTimeIn(TimeUnit.MILLISECONDS)+" ms");
        CommonUtilities.setResponseInstance(response);

    }

    private LinkedHashMap<String, Object> requestPayload;

    @And("Get the delivery number to assign vehicle to the delivery")
    public void getTheDeliveryNumberToAssignVehicleToTheDelivery() {
        ExtentReportManager.logInfoDetails("Get the delivery number to assign vehicle to the delivery");
        requestPayload = new LinkedHashMap<>();
//        requestPayload.put("dlvNumber", CommonUtilities.getDeliveryNumber());
        requestPayload.put("dlvNumber", "9338589810");
    }

    @And("Get the dock number from which items will be loaded into a vehicle")
    public void getTheDockNumberFromWhichItemsWillBeLoadedIntoAVehicle() {
        ExtentReportManager.logInfoDetails("Get the dock number from which items will be loaded into a vehicle");
        requestPayload.put("dock", "A2");
    }

    @And("Provide the current driver for the vehicle")
    public void provideTheCurrentDriverForTheVehicle() {
        ExtentReportManager.logInfoDetails("Provide the current driver for the vehicle");
        requestPayload.put("currentDriver", null);
    }

    @And("Give the vehicle type {string} and vehicle registration number {string} to assign vehicle to delivery")
    public void giveTheVehicleTypeAndVehicleRegistrationNumberToAssignVehicleToDelivery(String vehicleType, String vehicleRegNum) {
        ExtentReportManager.logInfoDetails("Give the vehicle type and vehicle registration number to assign vehicle to delivery");
        List<LinkedHashMap<String, String>> vehicles = new ArrayList<>();
        LinkedHashMap<String, String> vehicle = new LinkedHashMap<>();
        vehicle.put("vehicleType", vehicleType);
        vehicle.put("vehicleRegNum", vehicleRegNum);
        vehicles.add(vehicle);
        requestPayload.put("vehicles", vehicles);
    }

    @When("Requester calls the assign vehicle endpoint to assign vehicle for a delivery")
    public void requesterCallsTheAssignVehicleEndpointToAssignVehicleForADelivery() {
        ExtentReportManager.logInfoDetails("Requester calls the assign vehicle endpoint to assign vehicle for a delivery");
        response = RequestGenerator.getRequest(CommonUtilities.genericHeader(), requestPayload).log().all()
                .when().put(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "ASSIGN_VEHICLESorter"));
        response.then().log().all();
        ExtentReportManager.logInfoDetails("Response is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + response.getStatusCode());
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(response);
    }
}




