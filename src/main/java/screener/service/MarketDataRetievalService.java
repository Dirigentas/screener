package screener.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import screener.client.MarketDataClient;
import screener.client.impl.AlphavantageMarketDataClient;
import screener.client.impl.FinnhubMarketDataClient;
import screener.utils.JsonFileWriter;
import tools.jackson.databind.JsonNode;

@Service
public class MarketDataRetievalService {

    public static void getAlphavantageApiData(ArrayList<String> tickers) throws InterruptedException {
        String path;
        MarketDataClient dataProvider = null;
        JsonNode jsonData = null;
        MarketDataClient alphavantageMarketDataClient = new AlphavantageMarketDataClient();
        
        ArrayList<String> endpoints = new ArrayList<>();
        endpoints.add("alphavantage.OVERVIEW");
        endpoints.add("alphavantage.INCOME_STATEMENT");
        endpoints.add("alphavantage.BALANCE_SHEET");
        endpoints.add("alphavantage.CASH_FLOW");

        for (String ticker : tickers) {
            for (String endpoint : endpoints) {
                
                path = String.format("./rawData/%s/%s.json", ticker, endpoint);

                if (endpoint.contains("alphavantage")) {
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


    public static void getFinnhubApiData(ArrayList<String> tickers) throws InterruptedException {
        String path;
        MarketDataClient dataProvider = null;
        JsonNode jsonData = null;
        MarketDataClient finnhubMarketDataClient = new FinnhubMarketDataClient();
        
        ArrayList<String> endpoints = new ArrayList<>();
        endpoints.add("finnhub.all");

        for (String ticker : tickers) {
            for (String endpoint : endpoints) {
                
                path = String.format("./rawData/%s/%s.json", ticker, endpoint);

                if (endpoint.contains("finnhub")) {
                    dataProvider = finnhubMarketDataClient;
                }
                
                if (dataProvider != null) {
                    jsonData = dataProvider.getCompanyJson(ticker, endpoint.split("\\.")[1]);
                    JsonFileWriter.write(path, jsonData);
                }
            }
        }
    }
}
