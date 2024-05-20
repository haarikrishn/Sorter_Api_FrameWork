package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExtentReportManager;
import com.dmartLabs.commonutils.RequestGenerator;
import com.dmartLabs.config.ConStants;
import com.dmartLabs.config.PropertyReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetDocksSteps {
    Response response;
    private HashMap<String, Object> queryParam;

    @And("Provide the DC of which docks are needed {string}")
    public void provideTheDCOfWhichDocksAreNeeded(String srcSiteId) {
        ExtentReportManager.logInfoDetails("Provide the DC of which docks are needed");
        queryParam.put("isSearch", true);
        queryParam.put("searchValue", srcSiteId);
    }

    @When("Requester calls the master dock api to get the dock for destinations")
    public void requesterCallsTheMasterDockApiToGetTheDockForDestinations() {
        ExtentReportManager.logInfoDetails("Requester calls the master dock api to get the dock for destinations");
        RequestSpecification requestSpecification = RequestGenerator.getRequestWithQueryParams(CommonUtilities.genericHeader(), queryParam).log().all();
        response = requestSpecification.get(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH,"MDM_DockSearch"));
        response.then().log().all();
        ExtentReportManager.logInfoDetails("Response for get dock is - ");
        ExtentReportManager.logJson(response.prettyPrint());
        ExtentReportManager.logInfoDetails("Response status code for get dock is " + response.getStatusCode());
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        ExtentReportManager.logInfoDetails("Response Time to get a dock is " + responseTime + " ms");
    }

    @And("Get the docks for destinations for which delivery is created")
    public void getTheDocksForDestinationsForWhichDeliveryIsCreated() {
        ExtentReportManager.logInfoDetails("Get the docks for destinations for which delivery is created");
        List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) response.jsonPath().get("data");
        List<String> multipleDlvNumber = CommonUtilities.getMultipleDlvNumbers();
        List<HashMap<String, String>> dstBasedOnDlvNumber = CommonUtilities.getDestinationBasedOnDeliveryNumbers();
        HashMap<String, String> dock = new HashMap<>();

        for (int i=0;i<multipleDlvNumber.size(); i++){
            for (int j=0; j<datas.size(); j++){
                String dstId = dstBasedOnDlvNumber.get(i).get(multipleDlvNumber.get(i));
                if (((String)datas.get(j).get("dstSiteId")).equals(dstId)){
                    String dockName = (String) datas.get(j).get("dockName");
                    dock.put(dstId, dockName);
                    break;
                } //else if (j==datas.size() && )
            }
        }

        for (HashMap<String, Object> data:datas){
            for (int i=0;i<multipleDlvNumber.size();i++){
                String dstId = dstBasedOnDlvNumber.get(i).get(multipleDlvNumber.get(i));
                if (data.get("dstSiteId").equals(dstId)){
                    String dockName = (String) data.get("dockName");
                    dock.put(dstId, dockName);
                }
            }
        }
    }

}
