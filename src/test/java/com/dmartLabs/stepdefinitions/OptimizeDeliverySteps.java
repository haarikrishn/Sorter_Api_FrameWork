package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class OptimizeDeliverySteps {
    private Response response;
    private List<LinkedHashMap<String, Object>> requestPayload;
    private LinkedHashMap<String, Object> deliveryDetails;

    @And("Get the delivery number to optimize the delivery based on vehicle type")
    public void getTheDeliveryNumberToOptimizeTheDeliveryBasedOnVehicleType() {
        ExtentReportManager.logInfoDetails("Get the delivery number to optimize the delivery based on vehicle type");
        requestPayload = new ArrayList<>();
        deliveryDetails = new LinkedHashMap<>();
        deliveryDetails.put("dlvNumber", CommonUtilities.getDeliveryNumber());
    }

    @And("Give the dock number for the delivery to be optimized")
    public void giveTheDockNumberForTheDeliveryToBeOptimized() {
        ExtentReportManager.logInfoDetails("Give the dock number for the delivery to be optimized");
//        deliveryDetails.put("dock", CommonUtilities.getDock());
        deliveryDetails.put("dock", "A2");
    }


    @And("Provide the vehicle type {string} and vehicle registration number {string} for the delivery to be optimized")
    public void provideTheVehicleTypeAndVehicleRegistrationNumberForTheDeliveryToBeOptimized(String vehicleType, String vehicleRegNum) {
        ExtentReportManager.logInfoDetails("Provide the vehicle type and vehicle registration number for the delivery to be optimized");
        List<LinkedHashMap<String, String>> vehicles = new ArrayList<>();
        LinkedHashMap<String, String> vehicle = new LinkedHashMap<>();
        vehicle.put("vehicleType", vehicleType);
        vehicle.put("vehicleRegNum", vehicleRegNum);
        vehicles.add(vehicle);
        deliveryDetails.put("vehicles", vehicles);
        requestPayload.add(deliveryDetails);
    }

    @And("Get deliveries details and give vehicleType {string} to create a payload for optimizing delivery")
    public void getDeliveriesDetailsAndGiveVehicleTypeToCreateAPayloadForOptimizingDelivery(String vehicleType) {
        ExtentReportManager.logInfoDetails("Get deliveries details and give vehicleType to create a payload for optimizing delivery");
        List<String> deliveryList = CommonUtilities.getMultipleDlvNumbers();
        List<HashMap<String, String>> dstBasedOnDlvNumber = CommonUtilities.getDestinationBasedOnDeliveryNumbers();
        for (int i=0; i<deliveryList.size();i++){
            deliveryDetails = new LinkedHashMap<>();
            deliveryDetails.put("dlvNumber", deliveryList.get(i));
            String dstId = dstBasedOnDlvNumber.get(i).get(deliveryList.get(i));
            deliveryDetails.put("dock","");
            List<LinkedHashMap<String, String>> vehicles = new ArrayList<>();
            LinkedHashMap<String, String> vehicle = new LinkedHashMap<>();
            vehicle.put("vehicleType", vehicleType);
            vehicle.put("vehicleRegNum", "");
            vehicles.add(vehicle);
            deliveryDetails.put("vehicles", vehicles);
            requestPayload.add(deliveryDetails);
        }
    }

    @When("Requester calls the optimize delivery endpoint to optimize delivery for a wave")
    public void requesterCallsTheOptimizeDeliveryEndpointToOptimizeDeliveryForAWave() {
        ExtentReportManager.logInfoDetails("Requester calls the optimize delivery endpoint to optimize delivery for a wave");
        response = RequestGenerator.getRequest(CommonUtilities.genericHeader(), requestPayload).log().all()
                .when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "OptimizeDeliverySorter"));
        response.then().log().all();
        ExtentReportManager.logInfoDetails("Response is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + response.getStatusCode());
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(response);
    }
    
}
