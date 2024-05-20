package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PickerCheckOutSteps {

    Response response;
    Map<String, String> queryParam;
    Map<String, String> header;

    @And("Provide attendeeId to check-out the Picker")
    public void provideAttendeeIdToCheckOutThePicker(DataTable dataTable) {
        ExtentReportManager.logInfoDetails("Provide attendeeId to check-out the Picker");
        header = CommonUtilities.genericHeader();
        queryParam = dataTable.asMap(String.class, String.class);
        CommonUtilities.setMapRequestPayload(queryParam);
    }

    @And("Get attendeeId to check-out the Picker")
    public void getAttendeeIdToCheckOutThePicker() {
        ExtentReportManager.logInfoDetails("Get attendeeId to check-out the Picker");
        header = CommonUtilities.genericHeader();
        queryParam = new HashMap<>();
        queryParam.put("attendeeId", CommonUtilities.getClientId());
    }

    @And("Provide attendeeId to check-out the Picker {string}")
    public void provideAttendeeIdToCheckOutThePicker(String attendeeId) {
        ExtentReportManager.logInfoDetails("Provide attendeeId to check-out the Picker {string}");
        header = CommonUtilities.genericHeader();
        if (attendeeId.equals("null")){
            attendeeId=null;
        }
        queryParam = new HashMap<>();
        queryParam.put("attendeeId", attendeeId);
    }

    @When("User calls the Picker Check-Out endpoint to check-out the Picker")
    public void userCallsThePickerCheckOutEndpointToCheckOutThePicker() {
        ExtentReportManager.logInfoDetails("User calls the Picker Check-Out endpoint to check-out the Picker");
        RequestSpecification requestSpecification = RequestGenerator.getRequestWithQueryParam(header, queryParam);
        response = requestSpecification.put(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH,"PICKER_CHECK_OUT")).then().log().all().extract().response();
        ExtentReportManager.logInfoDetails("Picker Check-Out Response Payload is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response Time is : "+response.getTimeIn(TimeUnit.MILLISECONDS)+" ms");
        CommonUtilities.setResponseInstance(response);
    }

}
