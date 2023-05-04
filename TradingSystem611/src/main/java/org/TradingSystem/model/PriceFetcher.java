package org.TradingSystem.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.*;

public class PriceFetcher {

    //returns Double price or null if failure
    public static Double fetchPrice(String ticker){
        try{
            URL url = new URL(urlBuilder(ticker));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("X-RapidAPI-Key", Config.APIKEY);
            con.setRequestProperty("X-RapidAPI-Host", Config.APIHOST);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            //should have a "content" buffer with the response
            String jsonString = content.toString();

            //have to extract the double from this
            JSONObject obj = new JSONObject(jsonString);

            //get to price array
//            JSONObject priceObject = new JSONObject(obj.getJSONObject("quoteSummary").toString());
            Double price = obj.getJSONObject("quoteSummary").getJSONArray("result").getJSONObject(0).getJSONObject("price").getJSONObject("regularMarketPrice").getDouble("raw");
            return price;
        } catch (Exception e){
            return null;
        }
    }

    private static String urlBuilder(String ticker){
        return "https://telescope-stocks-options-price-charts.p.rapidapi.com/stocks/" +
                ticker +
                "?modules=price";
    }

}
