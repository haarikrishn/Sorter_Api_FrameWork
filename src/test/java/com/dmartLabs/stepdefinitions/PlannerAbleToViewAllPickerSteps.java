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

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlannerAbleToViewAllPickerSteps {

    Response response;

    @When("Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers")
    public void userCallsThePickerPresenceEndpointToGetTheCheckedInPickers() {
        ExtentReportManager.logInfoDetails("Picker Planner calls the Planner able to view all picker API endpoint to get the list of checked-in Pickers");
        RequestSpecification requestSpecification = RequestGenerator.getRequest(CommonUtilities.genericHeader()).log().all();
        response = requestSpecification.get(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "GET_ALL_AVAILABLE_PICKERS")).then().log().all().extract().response();
        ExtentReportManager.logInfoDetails("Picker Presence status code is : "+response.getStatusCode());
        ExtentReportManager.logInfoDetails("Picker Presence Response is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response Time is : "+response.getTimeIn(TimeUnit.MILLISECONDS)+" ms");
        CommonUtilities.setResponseInstance(response);
    }

    @And("Verify the Checked-In Pickers details for wave type deliveries")
    public void verifyTheCheckedInPickerDetails() {
        ExtentReportManager.logInfoDetails("Verify the Checked-In Pickers details for wave type deliveries");
        Map<String, String> pickerCheckedInRequestPayload = CommonUtilities.getMapRequestPayload();
        response = CommonUtilities.getResponseInstance();
        JSONArray jsonArrayResponse = new JSONArray(response.asString());

        for (int i=0; i<jsonArrayResponse.length(); i++){

            if (jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals(pickerCheckedInRequestPayload.get("attendeeId"))){
                ExtentReportManager.logPassDetails("attendeeId field is Passed");
                ExtentReportManager.logInfoDetails("Expected attendeeId is "+pickerCheckedInRequestPayload.get("attendeeId")+" and the Actual userId is "+jsonArrayResponse.getJSONObject(i).getString("attendeeId"));
                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("attendeeId"), pickerCheckedInRequestPayload.get("attendeeId"));

//                if (jsonArrayResponse.getJSONObject(i).getString("siteId").equals(pickerCheckedInRequestPayload.get("siteId"))){
//                    ExtentReportManager.logPassDetails("siteId field is Passed");
//                    ExtentReportManager.logInfoDetails("Expected siteId is "+pickerCheckedInRequestPayload.get("siteId")+" and the Actual siteId is "+jsonArrayResponse.getJSONObject(i).getString("siteId"));
//                }
//                else {
//                    ExtentReportManager.logFailureDetails("siteId field is Failed");
//                    ExtentReportManager.logInfoDetails("Expected siteId is "+pickerCheckedInRequestPayload.get("siteId")+" and the Actual siteId is "+jsonArrayResponse.getJSONObject(i).getString("siteId"));
//                }
//                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("siteId"), pickerCheckedInRequestPayload.get("siteId"));

                if (jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals("5fbe3a3d5f8051588f3bc46b")){
                    if (jsonArrayResponse.getJSONObject(i).getString("profileType").equals("Manual")){
                        ExtentReportManager.logPassDetails("profileType field is Passed");
                        ExtentReportManager.logInfoDetails("Expected profileType is Manual and the Actual profileType is "+jsonArrayResponse.getJSONObject(i).getString("profileType"));
                    } else {
                        ExtentReportManager.logFailureDetails("profileType field is failed");
                        ExtentReportManager.logInfoDetails("Expected profileType is Manual but the Actual profileType is "+jsonArrayResponse.getJSONObject(i).getString("profileType"));
                    }
                    Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("profileType"), "Manual");
                }
                else if (jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals("6577f98130e33d33903e7876")){
                    if (jsonArrayResponse.getJSONObject(i).getString("profileType").equals("BOPT")){
                        ExtentReportManager.logPassDetails("profileType field is Passed");
                        ExtentReportManager.logInfoDetails("Expected profileType is BOPT and the Actual profileType is "+jsonArrayResponse.getJSONObject(i).getString("profileType"));
                    } else {
                        ExtentReportManager.logFailureDetails("profileType field is failed");
                        ExtentReportManager.logInfoDetails("Expected profileType is BOPT but the Actual profileType is "+jsonArrayResponse.getJSONObject(i).getString("profileType"));
                    }
                    Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("profileType"), "BOPT");
                }
                else if (jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals("659fd2585c2fd619b4032812")) {
                    if (jsonArrayResponse.getJSONObject(i).getString("profileType").equals("Forklift")) {
                        ExtentReportManager.logPassDetails("profileType field is Passed");
                        ExtentReportManager.logInfoDetails("Expected profileType is Forklift and the Actual profileType is " + jsonArrayResponse.getJSONObject(i).getString("profileType"));
                    } else {
                        ExtentReportManager.logFailureDetails("profileType field is failed");
                        ExtentReportManager.logInfoDetails("Expected profileType is Forklift but the Actual profileType is " + jsonArrayResponse.getJSONObject(i).getString("profileType"));
                    }
                    Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("profileType"), "Forklift");
                }


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
            else if (i==jsonArrayResponse.length()-1 && !(jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals(pickerCheckedInRequestPayload.get("attendeeId")))){
                ExtentReportManager.logFailureDetails("attendeeId field is Failed");
                ExtentReportManager.logInfoDetails("Expected userId is "+pickerCheckedInRequestPayload.get("attendeeId")+" but the Actual attendeeId is "+jsonArrayResponse.getJSONObject(i).getString("attendeeId"));
                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("attendeeId"), pickerCheckedInRequestPayload.get("attendeeId"));
            }

        }



        if (jsonArrayResponse.length()==0){
            ExtentReportManager.logFailureDetails("Failed");
            ExtentReportManager.logInfoDetails("Picker is not Checked-In");
            Assert.fail();
        }

    }

    @And("Verify that Picker is not present in DC to pick wave type deliveries")
    public void verifyThatPickerIsNotCheckedInToDC() {
        ExtentReportManager.logInfoDetails("Verify that Picker is not present in DC to pick wave type deliveries");
        Map<String, String> pickerCheckedInRequestPayload = CommonUtilities.getMapRequestPayload();
        response = CommonUtilities.getResponseInstance();
        JSONArray jsonArrayResponse = new JSONArray(response.asString());

        if (jsonArrayResponse.length()==0){
            ExtentReportManager.logPassDetails("Pass");
            ExtentReportManager.logInfoDetails("Picker is not Checked-In");
            Assert.assertEquals(jsonArrayResponse.length(), 0);
        }

        for (int i=0; i<jsonArrayResponse.length(); i++){
            if (jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals(pickerCheckedInRequestPayload.get("attendeeId"))){
                ExtentReportManager.logFailureDetails("Failed");
                ExtentReportManager.logInfoDetails("Picker is present in DC");
                Assert.fail();
                break;
            }
            if (i==jsonArrayResponse.length()-1 && !(jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals(pickerCheckedInRequestPayload.get("attendeeId")))){
                ExtentReportManager.logPassDetails("Pass");
                ExtentReportManager.logInfoDetails("Picker is not present in DC");
                Assert.assertEquals(response.getStatusCode(), 200);
            }
        }
    }

    @And("Verify that Pickers are available in Distribution Center to pick the Articles for wave type deliveries")
    public void verifyThatPickersAreAvailableInDistributionCenterToPickTheArticlesForWaveTypeDeliveries() {
        ExtentReportManager.logInfoDetails("Verify that Pickers are available in Distribution Center to pick the Articles for wave type deliveries");
        response = CommonUtilities.getResponseInstance();
        JSONArray jsonArrayResponse = new JSONArray(response.asString());
        if (jsonArrayResponse.length()>0){
            ExtentReportManager.logInfoDetails("Pickers are online, Pick Planner can assign the wave deliveries articles to pickers");
        } else {
            ExtentReportManager.logInfoDetails("All pickers are offline, Check-in Pickers to assign the wave deliveries articles");
        }
    }

    @And("Verify that activeSince field value for just checked-in picker is {string}")
    public void verifyThatActiveSinceFieldValueForJustCheckedInPickerIs(String expectedActiveSince) {
        ExtentReportManager.logInfoDetails("Verify that activeSince field value for just checked-in picker is"+expectedActiveSince);
        Map<String, String> pickerCheckedInRequestPayload = CommonUtilities.getMapRequestPayload();
        JSONArray jsonArrayResponse = new JSONArray(response.asString());
        for (int i=0; i<jsonArrayResponse.length(); i++) {

            if (jsonArrayResponse.getJSONObject(i).getString("attendeeId").equals(pickerCheckedInRequestPayload.get("attendeeId"))) {
                if (jsonArrayResponse.getJSONObject(i).getString("activeSince").equals(expectedActiveSince)){
                    ExtentReportManager.logPassDetails("activeSince field is Passed");
                    ExtentReportManager.logInfoDetails("Expected activeSince is "+expectedActiveSince+" and the Actual activeSince is "+jsonArrayResponse.getJSONObject(i).getString("activeSince"));
                }
                else {
                    ExtentReportManager.logFailureDetails("activeSince field is failed");
                    ExtentReportManager.logInfoDetails("Expected activeSince is "+expectedActiveSince+" but the Actual activeSince is "+jsonArrayResponse.getJSONObject(i).getString("activeSince"));
                }
                Assert.assertEquals(jsonArrayResponse.getJSONObject(i).getString("activeSince"), expectedActiveSince);
            }
        }
    }
}
