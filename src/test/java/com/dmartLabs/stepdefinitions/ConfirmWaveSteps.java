package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.GenricUtils;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.dmartLabs.config.ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH;

public class ConfirmWaveSteps {

    LinkedHashMap<String, Object> requestPayload;

    @And("Get the response from the optimize delivery API to confirm the wave")
    public void getTheResponseFromTheOptimizeDeliveryAPIToConfirmTheWave() {
        ExtentReportManager.logInfoDetails("Get the response from the optimize delivery API to confirm the wave");
        requestPayload = new LinkedHashMap<>();
        Response optimizedDelivery = CommonUtilities.getResponseInstance();
        List<LinkedHashMap<String, Object>> deliveries = optimizedDelivery.as(new TypeRef<List<LinkedHashMap<String, Object>>>() {
        });
        requestPayload.put("deliveries", deliveries);
    }

    @And("Give the status for confirming the wave {string}")
    public void giveTheStatusForConfirmingTheWave(String status) {
        ExtentReportManager.logInfoDetails("Give the status for confirming the wave");
        requestPayload.put("status", status);
    }

    @When("Requester calls the confirm wave endpoint to optimize delivery for a wave")
    public void requesterCallsTheConfirmWaveEndpointToOptimizeDeliveryForAWave() {
        ExtentReportManager.logInfoDetails("Requester calls the confirm wave endpoint to optimize delivery for a wave");
        RequestSpecification ConfirmDeliveryRequest = RequestGenerator.getRequest(CommonUtilities.genericHeader(), requestPayload);
        ConfirmDeliveryRequest.log().all();
        Response ConfirmDeliveryResponse = ConfirmDeliveryRequest.when().post(PropertyReader.getProperty(ENDPOINTS_PATHS_PROPERTIES_PATH, "confirmDeliverysorter"));
        // ConfirmDeliveryResponse.then().log().all();
        ExtentReportManager.logJson("Response is " + ConfirmDeliveryResponse.getBody().prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + ConfirmDeliveryResponse.getStatusCode());
        long responseTime = ConfirmDeliveryResponse.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setWaveId(ConfirmDeliveryResponse.jsonPath().getString("waveId"));
        CommonUtilities.setResponseInstance(ConfirmDeliveryResponse);
    }
}
