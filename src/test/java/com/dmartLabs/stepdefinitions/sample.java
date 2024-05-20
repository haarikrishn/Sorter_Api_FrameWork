package com.dmartLabs.stepdefinitions;

import org.json.JSONArray;
import org.json.JSONObject;

public class sample {


        public static void main(String[] args) {
            String jsonResponse = "[{\"dlvNumber\":\"9771156204\",\"dstSiteId\":\"4017\",\"dstSiteName\":\"Thane Garden Estate\",\"srcSiteId\":\"1034\",\"proposedDlvDate\":\"2024-03-14T00:00:00Z\",\"deliveryStatus\":\"CREATED\",\"dock\":\"B2\",\"metrics\":{\"totalOriginalItems\":2,\"totalOriginalBoxes\":2,\"totalOriginalWt\":0.020496,\"totalOriginalVol\":2.3040000000000003,\"totalAdjustedItems\":2,\"totalAdjustedBoxes\":2,\"totalAdjustedWt\":0.020496,\"totalAdjustedVol\":2.3040000000000003},\"dlvItems\":[{\"itemseq\":\"000020\",\"dlvItemNum\":\"0000209771156204\",\"stoNum\":\"5101334418\"}]}]";

            // Parse JSON array
            JSONArray jsonArray = new JSONArray(jsonResponse);

            // Iterate over the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Access dlvItems array
                JSONArray dlvItemsArray = jsonObject.getJSONArray("dlvItems");

                // Iterate over dlvItems array
                for (int j = 0; j < dlvItemsArray.length(); j++) {
                    JSONObject itemObject = dlvItemsArray.getJSONObject(j);

                    // Access and print itemseq
                    String itemseq = itemObject.getString("itemseq");
                    System.out.println("itemseq: " + itemseq);
                }
            }
        }
    }

