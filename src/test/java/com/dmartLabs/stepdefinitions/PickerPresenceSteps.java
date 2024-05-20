package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PickerPresenceSteps {

    Response response;
    private static List<String> forkliftPicker = new ArrayList<>();
    private static List<String> manualPicker = new ArrayList<>();
    private static List<String> boptPicker = new ArrayList<>();

    @When("User calls the Picker Presence endpoint to get the checked-in Pickers")
    public void userCallsThePickerPresenceEndpointToGetTheCheckedInPickers() {
        ExtentReportManager.logInfoDetails("User calls the Picker Presence endpoint to get the checked-in Pickers");
        RequestSpecification requestSpecification = RequestGenerator.getRequest(CommonUtilities.genericHeader()).log().all();
        response = requestSpecification.get(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "PICKER_PRESENCE")).then().log().all().extract().response();
        ExtentReportManager.logInfoDetails("Picker Presence status code is : "+response.getStatusCode());
        ExtentReportManager.logInfoDetails("Picker Presence Response is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response Time is : "+response.getTimeIn(TimeUnit.MILLISECONDS)+" ms");
        CommonUtilities.setResponseInstance(response);
    }

    @And("Verify the Checked-In Picker details")
    public void verifyTheCheckedInPickerDetails() {
        ExtentReportManager.logInfoDetails("Verify the Checked-In Picker's details");
        Map<String, String> pickerCheckedInRequestPayload = CommonUtilities.getMapRequestPayload();
        response = CommonUtilities.getResponseInstance();
        JSONArray jsonArrayResponse = new JSONArray(response.asString());

        for (int i=0; i<jsonArrayResponse.length(); i++){

            if (jsonArrayResponse.getJSONObject(i).getString("userID").equals(pickerCheckedInRequestPayload.get("attendeeId"))){
                ExtentReportManager.logPassDetails("userID field is Passed");
                ExtentReportManager.logInfoDetails("Expected userId is "+pickerCheckedInRequestPayload.get("attendeeId")+" and the Actual userId is "+jsonArrayResponse.getJSONObject(i).getString("userID"));
                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("userID"), pickerCheckedInRequestPayload.get("attendeeId"));

                if (jsonArrayResponse.getJSONObject(i).getString("profileType").equals("Forklift")){
                    forkliftPicker.add(jsonArrayResponse.getJSONObject(i).getString("userID"));
                    CommonUtilities.setForkliftPicker(forkliftPicker);
                } else if (jsonArrayResponse.getJSONObject(i).getString("profileType").equals("BOPT")){
                    boptPicker.add(jsonArrayResponse.getJSONObject(i).getString("userID"));
                    CommonUtilities.setBoptPicker(boptPicker);
                } else if (jsonArrayResponse.getJSONObject(i).getString("profileType").equals("MANUAL")){
                    manualPicker.add(jsonArrayResponse.getJSONObject(i).getString("userID"));
                    CommonUtilities.setManualPicker(manualPicker);
                }

                if (jsonArrayResponse.getJSONObject(i).getString("siteId").equals(pickerCheckedInRequestPayload.get("siteId"))){
                    ExtentReportManager.logPassDetails("siteId field is Passed");
                    ExtentReportManager.logInfoDetails("Expected siteId is "+pickerCheckedInRequestPayload.get("siteId")+" and the Actual siteId is "+jsonArrayResponse.getJSONObject(i).getString("siteId"));
                }
                else {
                    ExtentReportManager.logFailureDetails("siteId field is Failed");
                    ExtentReportManager.logInfoDetails("Expected siteId is "+pickerCheckedInRequestPayload.get("siteId")+" and the Actual siteId is "+jsonArrayResponse.getJSONObject(i).getString("siteId"));
                }
                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("siteId"), pickerCheckedInRequestPayload.get("siteId"));

//                if (jsonArrayResponse.getJSONObject(i).getString("checkinTime").equals(pickerCheckedInRequestPayload.get("checkinTime"))){
//                    ExtentReportManager.logPassDetails("checkinTime field is Passed");
//                    ExtentReportManager.logInfoDetails("Expected checkinTime is "+pickerCheckedInRequestPayload.get("checkinTime")+" and the Actual checkinTime is "+jsonArrayResponse.getJSONObject(i).getString("checkinTime"));
//                }
//                else {
//                    ExtentReportManager.logFailureDetails("checkinTime field is Failed");
//                    ExtentReportManager.logInfoDetails("Expected siteId is "+pickerCheckedInRequestPayload.get("checkinTime")+" and the Actual siteId is "+jsonArrayResponse.getJSONObject(i).getString("checkinTime"));
//                }
//                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("checkinTime"), pickerCheckedInRequestPayload.get("checkinTime"));



                if (jsonArrayResponse.getJSONObject(i).getString("attendeeStatus").equals("Online")){
                    ExtentReportManager.logPassDetails("attendeeStatus field is Passed");
                    ExtentReportManager.logInfoDetails("Expected attendeeStatus is Online and the Actual attendeeStatus is "+jsonArrayResponse.getJSONObject(i).getString("attendeeStatus"));
                }
                else {
                    ExtentReportManager.logFailureDetails("attendeeStatus field is Failed");
                    ExtentReportManager.logInfoDetails("Expected attendeeStatus is Online and the Actual attendeeStatus is "+jsonArrayResponse.getJSONObject(i).getString("attendeeStatus"));
                }
                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("attendeeStatus"), "Online");
                break;
            }
            else if (i==jsonArrayResponse.length()-1 && !(jsonArrayResponse.getJSONObject(i).getString("userID").equals(pickerCheckedInRequestPayload.get("attendeeId")))){
                ExtentReportManager.logFailureDetails("userID field is Failed");
                ExtentReportManager.logInfoDetails("Expected userId is "+pickerCheckedInRequestPayload.get("attendeeId")+" and the Actual userId is "+jsonArrayResponse.getJSONObject(i).getString("userID"));
                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("userID"), pickerCheckedInRequestPayload.get("attendeeId"));
            }
        }

        if (jsonArrayResponse.length()==0){
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("Picker is not Checked-In");
            Assert.fail();
        }

    }

    @And("Verify that Picker is not Checked-In to DC")
    public void verifyThatPickerIsNotCheckedInToDC() {
        ExtentReportManager.logInfoDetails("Verify that Picker is not Checked-In to DC");
//        List listResponse = response.as(List.class);
//        if (listResponse.size()==0){
//            ExtentReportManager.logPassDetails("Pass");
//            ExtentReportManager.logInfoDetails("Picker is not checked-in");
//        }
//        else{
//            ExtentReportManager.logFailureDetails("Failed");
//            ExtentReportManager.logInfoDetails("Picker is checked-in");
//        }
//        Assert.assertEquals(listResponse.size(), 0);

        Map<String, String> pickerCheckedInRequestPayload = CommonUtilities.getMapRequestPayload();
        response = CommonUtilities.getResponseInstance();
        JSONArray jsonArrayResponse = new JSONArray(response.asString());

        if (jsonArrayResponse.length()==0){
            ExtentReportManager.logPassDetails("Pass");
            ExtentReportManager.logInfoDetails("Picker is not Checked-In");
            Assert.assertEquals(jsonArrayResponse.length(), 0);
        }

        for (int i=0; i<jsonArrayResponse.length(); i++){
            if (jsonArrayResponse.getJSONObject(i).getString("userID").equals(pickerCheckedInRequestPayload.get("attendeeId"))){
                ExtentReportManager.logFailureDetails("Failed");
                ExtentReportManager.logInfoDetails("Picker is Checked-In");
                Assert.fail();
                break;
            }
            if (i==jsonArrayResponse.length()-1 && !(jsonArrayResponse.getJSONObject(i).getString("userID").equals(pickerCheckedInRequestPayload.get("attendeeId")))){
                ExtentReportManager.logPassDetails("Pass");
                ExtentReportManager.logInfoDetails("Picker is not Checked-In");
                Assert.assertEquals(response.getStatusCode(), 200);
            }
        }
    }
}
