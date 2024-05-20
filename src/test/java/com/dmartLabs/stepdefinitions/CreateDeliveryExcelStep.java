package com.dmartLabs.stepdefinitions;

import com.dmartLabs.commonutils.ExcelUtils;
import com.dmartLabs.commonutils.GenricUtils;
import com.dmartLabs.commonutils.RequestGenerator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import rst.pdfbox.layout.text.Constants;

import java.io.IOException;
import java.util.*;


public class CreateDeliveryExcelStep extends Constants {
    RequestGenerator requestGenerator;
    LinkedHashMap<String, Object> MapObj = new LinkedHashMap<>();
    LinkedHashMap<String,Object>SubObject=new LinkedHashMap<>();
    List<LinkedHashMap<String,Object>>SubObjAl=new ArrayList<>();

    @And("Assigning Main Mandatory fields to CreateOneDeliveryWithListOfItemsUsingDTInSorter using ExcelDataTable")
    public void assigningMainMandatoryFieldsToCreateOneDeliveryWithListOfItemsUsingDTInSorterUsingExcelDataTable(DataTable dataTable) {
        Map<String, String> ExcelDT = dataTable.asMap(String.class, String.class);
        String srcSiteIdValue = ExcelDT.get("srcSiteId");
        String dstSiteIdValue = ExcelDT.get("dstSiteId");
        String whNumberValue = ExcelDT.get("whNumber");
        String createdByValue = ExcelDT.get("createdBy");
        String totalGdsMvtStatValue = ExcelDT.get("totalGdsMvtStat");

        String dlvNumber = GenricUtils.getRandomDeliveryNumber();
        MapObj.put("dlvNumber", dlvNumber);
        CommonUtilities.setDeliveryNumber(dlvNumber);
        MapObj.put("proposedDlvDate", GenricUtils.getDate("yyyyMMdd"));
        MapObj.put("srcSiteId", srcSiteIdValue);
        CommonUtilities.setSrcSiteID(srcSiteIdValue);
        MapObj.put("dstSiteId",dstSiteIdValue );
        CommonUtilities.setDstSiteID(dstSiteIdValue);
        MapObj.put("whNumber",whNumberValue );
        MapObj.put("createdBy", createdByValue);
        MapObj.put("creationDate", GenricUtils.getDate("yyyyMMdd"));
        MapObj.put("creationTime", GenricUtils.getTime("HHmmss"));
        MapObj.put("totalGdsMvtStat",totalGdsMvtStatValue );
    }


    @And("Provide dlvItemNum to CreateOneDeliveryWithListOfItemsUsingDTInSorter using excel sheetDT")
    public void provideDlvItemNumToCreateOneDeliveryWithListOfItemsUsingDTInSorterUsingExcelSheetDT(DataTable dataTable) throws IOException {
        Map<String, String> ExcelDT = dataTable.asMap(String.class, String.class);
        String filePath = ExcelDT.get("fileName");
        String sheetName = ExcelDT.get("sheetName");
        //    List<LinkedHashMap<String, Object>> task = ThrreTaskExcel.ReadDataFromExcelRefill(filePath, sheetName);
       SubObjAl.add(MapObj);
        List<LinkedHashMap<String, Object>> DlvItemsObj = ExcelUtils.ReadDataFromExcelDT(filePath, sheetName);

        MapObj.put("dlvItems",DlvItemsObj);
       // SubObjAl.add(SubObject);

       System.out.println(SubObjAl.toString());


//        RequestSpecification CreateDeliveryResponse = requestGenerator.getRequest(GenericSteps.userCredential, SubObjAl).log().all();
//        Response CreateDeliveryResponse1 = CreateDeliveryResponse.when().post(PropertyReader.getProperty(ConStants.ENDPOINTS_PATHS_PROPERTIES_PATH, "CreateDeliverySorterEndPoint"));
//        CreateDeliveryResponse.then().log().all();
//        ExtentReportManager.logJson("Response is " + CreateDeliveryResponse1.getBody().prettyPrint());
//        ExtentReportManager.logInfoDetails("Response status code is " + CreateDeliveryResponse1.getStatusCode());
//        long responseTime = CreateDeliveryResponse1.getTimeIn(TimeUnit.MILLISECONDS);
//        ExtentReportManager.logInfoDetails("Response Time is " + responseTime + " ms");
//        CommonUtilities1.setResponseInstance(CreateDeliveryResponse1);

    }


}