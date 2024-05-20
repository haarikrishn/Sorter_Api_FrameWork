package com.dmartLabs.runner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(features = "./src/test/resources/features/SingleSuite",

   //      tags="@SorterCreateDeliveryAllFlowNew1 or @OptimizeDeliveryFlowNew2 or  @ConfirmDeliveryFlowNew3 or   @CreateTOForOriginalDeliveryFlowNew4 or   @getDeliveryAllFlow5",
     //  tags="@SorterCreateDeliveryAllFlowNew1 or  @AssignVehicleNew2 or @OptimizeDeliveryFlowNew3 or  @ConfirmDeliveryFlowNew4 ",
      //  tags="@SorterCreateDeliveryAllFlowNew1",
        tags="@SorterCreateDeliveryAllFlowNew11 or @OptimizeDeliveryFlowNew12 or  @ConfirmDeliveryFlowNew13  or  @AssignVehicleNew14 or  @CreateTOForOriginalDeliveryFlowNew15 ",
     //@AssignVehicleNew14
        glue = "com/dmartLabs/stepdefinitions",
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/html-report.html",
                "json:test-output/cucumber-reports/json-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true)

public class SorterTONEWTestRunner {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }

    @Test(groups = "cucumber scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleEvent, FeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        testNGCucumberRunner=new TestNGCucumberRunner(this.getClass());
        return testNGCucumberRunner.provideScenarios();
    }


}
