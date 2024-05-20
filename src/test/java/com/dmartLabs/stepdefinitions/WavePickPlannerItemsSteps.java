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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WavePickPlannerItemsSteps {

    private LinkedHashMap<String, Object> requestPayload;
    private List<String> conveyableItemsList;
    private List<String> nonConveyableItemsList;

    @And("Get the siteId for which wave is created")
    public void getTheSiteIdForWhichWaveIsCreated() {
        ExtentReportManager.logInfoDetails("Get the siteId for which wave is created");
        requestPayload = new LinkedHashMap<>();
        requestPayload.put("siteId", CommonUtilities.getSiteId());
    }

    @And("Get the current time at which items are assigned to picker")
    public void getTheCurrentTimeAtWhichItemsAreAssignedToPicker() {
        ExtentReportManager.logInfoDetails("Get the current time at which items are assigned to picker");
        requestPayload.put("assignedOn", GenricUtils.getFormattedDateTime(PropertyReader.getProperty(ConStants.TIME_FORMAT_PROPERTIES_PATH, "DATE_MONTH_YEAR_TIME_FORMAT")));
    }

    @And("Get the waveId from which items has to be assigned to picker")
    public void getTheWaveIdFromWhichItemsHasToBeAssignedToPicker() {
        ExtentReportManager.logInfoDetails("Get the waveId from which items has to be assigned to picker");
        requestPayload.put("waveId", CommonUtilities.getWaveId());
    }

    @And("Get the list of conveyable and non-conveyable items to be assigned to pickers")
    public void getTheListOfConveyableAndNonConveyableItems() {
        ExtentReportManager.logInfoDetails("Get the list of conveyable and non-conveyable items to be assigned to pickers");
        conveyableItemsList = CommonUtilities.getConveyableItems();
        nonConveyableItemsList = CommonUtilities.getNonConveyableItems();
    }

    @When("Requester calls wave pick planner endpoint to assign items to picker")
    public void requesterCallsWavePickPlannerEndpointToAssignItemsToPicker() {
        ExtentReportManager.logInfoDetails("Requester calls wave pick planner endpoint to assign items to picker");
        if (conveyableItemsList!=null && conveyableItemsList.size()>0){
            requestPayload.put("assigneeId", CommonUtilities.getForkliftPicker().get(0));
            requestPayload.put("itemNumber", conveyableItemsList);
            RequestSpecification requestSpecification = RequestGenerator.getRequest(CommonUtilities.genericHeader(), requestPayload).log().all();
            Response response = requestSpecification.post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "WavePickPlanner"));
            response.then().log().all();
            ExtentReportManager.logInfoDetails("Response after assigning conveyable items to picker is - ");
            ExtentReportManager.logJson(response.prettyPrint());
            ExtentReportManager.logInfoDetails("Response status code after assigning conveyable items to picker is " + response.getStatusCode());
            long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
            ExtentReportManager.logInfoDetails("Response Time for assigning conveyable items to picker is " + responseTime + " ms");
        }
        if (nonConveyableItemsList!=null && nonConveyableItemsList.size()>0){
            requestPayload.put("assigneeId", CommonUtilities.getBoptPicker().get(0));
            requestPayload.put("itemNumber", nonConveyableItemsList);
            RequestSpecification requestSpecification = RequestGenerator.getRequest(CommonUtilities.genericHeader(), requestPayload).log().all();
            Response response = requestSpecification.post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "WavePickPlanner"));
            response.then().log().all();
            ExtentReportManager.logInfoDetails("Response after assigning non-conveyable items to picker is - ");
            ExtentReportManager.logJson(response.prettyPrint());
            ExtentReportManager.logInfoDetails("Response status code after assigning non-conveyable items to picker is " + response.getStatusCode());
            long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
            ExtentReportManager.logInfoDetails("Response Time for assigning non-conveyable items to picker is " + responseTime + " ms");
        }
    }

}
