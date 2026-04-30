package screener.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import screener.api.AlphavantageMarketDataClient;
import screener.api.FinnhubMarketDataClient;
import screener.api.interfaces.MarketDataClient;
import screener.utils.JsonFileWriter;
import tools.jackson.databind.JsonNode;

@Service
public class MarketDataRetievalService {

    private static LocalDate DATE_NOW = LocalDate.now();
    
    public static void getApiEndpointsData(ArrayList<String> tickers) throws InterruptedException {
        String path;
        MarketDataClient dataProvider = null;
        JsonNode jsonData = null;
        MarketDataClient finnhubMarketDataClient = new FinnhubMarketDataClient();
        MarketDataClient alphavantageMarketDataClient = new AlphavantageMarketDataClient();
        
        ArrayList<String> endpoints = new ArrayList<>();
        endpoints.add("finnhub.all");
        endpoints.add("alphavantage.OVERVIEW");
        endpoints.add("alphavantage.INCOME_STATEMENT");
        endpoints.add("alphavantage.BALANCE_SHEET");
        endpoints.add("alphavantage.CASH_FLOW");

        for (String ticker : tickers) {
            for (String endpoint : endpoints) {
                
                path = String.format("./rawData/%s/%s_%s.json", ticker, endpoint, DATE_NOW);

                if (endpoint.contains("finnhub")) {
                    dataProvider = finnhubMarketDataClient;
                } else if (endpoint.contains("alphavantage")) {
                    dataProvider = alphavantageMarketDataClient;
                }
                
                if (dataProvider != null) {
                    jsonData = dataProvider.getCompanyJson(ticker, endpoint.split("\\.")[1]);
                    JsonFileWriter.write(path, jsonData);
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}
