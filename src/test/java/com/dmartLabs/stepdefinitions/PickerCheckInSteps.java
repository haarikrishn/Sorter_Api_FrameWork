package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.GenricUtils;
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

public class PickerCheckInSteps {

    private static Object requestPayload;
    private Map<String, String> pickerCheckInRequestPayload;
    private Map<String, String> header;
    Response response;

    @And("Get Picker's details to Check-In Picker into DC")
    public void providePickerSDetailsToCheckInPickerIntoDC() {
        ExtentReportManager.logInfoDetails("Provide Picker's details to Check-In Picker into DC");
        header = new HashMap<>();
        header = CommonUtilities.genericHeader();
        pickerCheckInRequestPayload = new HashMap<>();

        pickerCheckInRequestPayload.put("attendeeId", CommonUtilities.getClientId());
        pickerCheckInRequestPayload.put("checkinTime",
                GenricUtils.getFormattedDateTime(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "DATE_MONTH_YEAR_TIME_FORMAT")));
        pickerCheckInRequestPayload.put("deviceSerialNumber", GenricUtils.getDeviceSerialNumber());
        pickerCheckInRequestPayload.put("siteId", CommonUtilities.getSiteId());
        requestPayload = pickerCheckInRequestPayload;
        CommonUtilities.setMapRequestPayload(pickerCheckInRequestPayload);
    }

    @And("Provide Picker's details using DataTable to Check-In Picker into DC")
    public void providePickerSDetailsUsingDataTableToCheckInPickerIntoDC(DataTable dataTable) {
        ExtentReportManager.logInfoDetails("Provide Picker's details using DataTable to Check-In Picker into DC");
        header = new HashMap<>();
        header = CommonUtilities.genericHeader();
        pickerCheckInRequestPayload = dataTable.asMap(String.class, String.class);
//        pickerCheckInRequestPayload.put("checkinTime", GenricUtils.getFormattedDateTime(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "DATE_MONTH_YEAR_TIME_FORMAT")));
        CommonUtilities.setClientId(pickerCheckInRequestPayload.get("attendeeId"));
        CommonUtilities.setMapRequestPayload(pickerCheckInRequestPayload);
        requestPayload = pickerCheckInRequestPayload;
    }

    @And("Provide Picker's details to Check-In Picker into DC {string} {string} {string} {string}")
    public void providePickerSDetailsToCheckInPickerIntoDC(String attendeeId, String checkinTime, String deviceSerialNumber, String siteId) {
        ExtentReportManager.logInfoDetails("Provide Picker's details to Check-In Picker into DC {string} {string} {string} {string}");
        header = new HashMap<>();
        header = CommonUtilities.genericHeader();
        if (attendeeId.equals("null")){
            attendeeId=null;
        }
        else if (checkinTime.equals("null")){
            checkinTime = null;
        }
        else if (siteId.equals("null")){
            siteId = null;
        }

        pickerCheckInRequestPayload = new HashMap<>();

        pickerCheckInRequestPayload.put("attendeeId", attendeeId);
        pickerCheckInRequestPayload.put("checkinTime", checkinTime);
        pickerCheckInRequestPayload.put("deviceSerialNumber", GenricUtils.getDeviceSerialNumber());
        pickerCheckInRequestPayload.put("siteId", siteId);
        requestPayload = pickerCheckInRequestPayload;
        CommonUtilities.setMapRequestPayload(pickerCheckInRequestPayload);
        requestPayload = pickerCheckInRequestPayload;
        CommonUtilities.setMapRequestPayload(pickerCheckInRequestPayload);
    }

    @When("User calls the Picker Check-In's endpoint to check-in the Picker")
    public void userCallsThePickerCheckInSEndpointToCheckInThePicker() {
        ExtentReportManager.logInfoDetails("User calls the Picker Check-In's endpoint to check-in the Picker");
        RequestSpecification requestSpecification = RequestGenerator.getRequest(header, requestPayload).log().all();
        response = requestSpecification.post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "PICKER_CHECK_IN")).then().log().all().extract().response();
        ExtentReportManager.logInfoDetails("Picker Check-In status code is : "+response.getStatusCode());
        ExtentReportManager.logInfoDetails("Picker Check-In Response is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response Time is : "+response.getTimeIn(TimeUnit.MILLISECONDS)+" ms");
        CommonUtilities.setResponseInstance(response);
    }

}
