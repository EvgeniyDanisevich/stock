package ru.evgeniy.stock.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsoupCompanyParser {

    private static final Logger log = LoggerFactory.getLogger(JsoupCompanyParser.class);

    public Map<String, Map<String, String>> fvParse() {
        Map<String, Map<String, String>> data = new HashMap<>();
        try {
            Document document = Jsoup.connect(
                    "https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap"
            ).get();
            Element table = document.select(".table-dark-row-cp").parents().first();
            Elements rows = table.children();
            for (Element element : rows) {
                if (element.child(0).text().equals("No.")) {
                    continue;
                }
                String ticker = element.child(1).text();
                String company = element.child(2).text();
                String sector = element.child(3).text();
                String industry = element.child(4).text();
                String lastPrice = new RegularMarketPriceParser().yahooParse(ticker);
                data.put(ticker, Map.of(
                        "ticker", ticker,
                        "company", company,
                        "sector", sector,
                        "industry", industry,
                        "lastPrice", lastPrice
                ));
            }
        } catch (IOException e) {
            log.info("JsoupCompanyParser.class IOException in fvParse() method. " + e.getMessage());
        }
        return data;
    }
}