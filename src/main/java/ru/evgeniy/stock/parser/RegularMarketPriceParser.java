package ru.evgeniy.stock.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class RegularMarketPriceParser {

    public String yahooParse(String ticker) {
        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.getForObject(
                "https://query1.finance.yahoo.com/v8/finance/chart/" + ticker, String.class);
        JSONObject object1 = new JSONObject(jsonString);
        JSONObject object2 = object1.getJSONObject("chart");
        JSONArray array = object2.getJSONArray("result");
        JSONObject object3 = array.getJSONObject(0);
        JSONObject object4 = object3.getJSONObject("meta");
        return object4.get("regularMarketPrice").toString();
    }
}
