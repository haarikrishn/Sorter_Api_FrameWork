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
  //   features = "./src/test/resources/features/SingleSuite/PWPFlowV2_121346Boxtype.feature",

   //  tags = "@SorterCreateDeliveryAllinone1 or  @GetSorterCreateDeliveryAllinone2 or @UpdateDeliveryInSorterWithoutDeleteAllinOne3 or   @getSorterUpdateDeliveryWithoutDeleteItemAllinone4 or   @AdjustDeliveryAllinOne7  or   @ConfirmDeliveryAllinOne8 or   @GetSorterConfirmDeliveryAllinone9 ",
     //  tags = "@SorterCreateDeliveryAllinone1 or  @GetSorterCreateDeliveryAllinone2 or @UpdateDeliveryInSorterWithoutDeleteAllinOne3 or   @getSorterUpdateDeliveryWithoutDeleteItemAllinone4 ",
        tags = "@SorterCreateDeliveryExcelDT",
        //   tags=       "@SorterCreateDelivery200Items",
//                dryRun = false,
    //    tags = "@SorterCreateDeliveryAllinone1",
        glue = "com/dmartLabs/stepdefinitions",
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/html-report.html",
                "json:test-output/cucumber-reports/json-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true)

public class SorterTestRunner {

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
