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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetAll_ItemsInWaveSteps {

    private Response response;
    HashMap<String, String> queryParam;
    @And("Provide the wave id to get the list of items")
    public void provideTheWaveIdToGetTheListOfItems() {
        ExtentReportManager.logInfoDetails("Provide the wave id to get the list of items");
        queryParam = new HashMap<>();
        queryParam.put("cycleId", CommonUtilities.getWaveId());
    }


    @When("Requester calls the wave-pick-planner all wave items api to get the list of items")
    public void requesterCallsTheWavePickPlannerAllWaveItemsApiToGetTheListOfItems() {
        ExtentReportManager.logInfoDetails("Requester calls the wave-pick-planner all wave items api to get the list of items");
        RequestSpecification requestSpecification = RequestGenerator.getRequestWithQueryParam(CommonUtilities.genericHeader(), queryParam).log().all();
        response = requestSpecification.get(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "WavePickPlannerAllWaveItems"));
        response.then().log().all();
        ExtentReportManager.logInfoDetails("Response is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code is " + response.getStatusCode());
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
        CommonUtilities.setResponseInstance(response);
    }

    @And("Get the items present in a wave to assign them to pickers")
    public void getTheItemsPresentInAWaveToAssignThemToPickers() {
        ExtentReportManager.logInfoDetails("Get the items present in a wave to assign them to pickers");
        List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) response.jsonPath().get("items");
        List<String> conveyableItems = new ArrayList<>();
        List<String> nonConveyableItems = new ArrayList<>();

        for (HashMap<String, Object> item:items){
            String itemNumber = (String) item.get("itemNumber");
            if ((Boolean) item.get("isConveyable"))
                conveyableItems.add(itemNumber);
            else
                nonConveyableItems.add(itemNumber);
        }

        CommonUtilities.setConveyableItems(conveyableItems);
        CommonUtilities.setNonConveyableItems(nonConveyableItems);
    }
}
