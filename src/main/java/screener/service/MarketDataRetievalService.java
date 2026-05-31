package screener.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import screener.client.AlphavantageMarketDataClient;
import screener.client.FinnhubMarketDataClient;
import screener.utils.JsonFileWriter;
import tools.jackson.databind.JsonNode;

@Service
public class MarketDataRetievalService {

    public static void getAlphavantageApiData(ArrayList<String> tickers) throws InterruptedException {
        String path;
        JsonNode jsonData = null;
        AlphavantageMarketDataClient alphavantageMarketDataClient = new AlphavantageMarketDataClient();
        
        ArrayList<String> endpoints = new ArrayList<>();
        endpoints.add("alphavantage.INCOME_STATEMENT");
        endpoints.add("alphavantage.BALANCE_SHEET");
        endpoints.add("alphavantage.CASH_FLOW");

        // endpoints.add("alphavantage.OVERVIEW");

        for (String ticker : tickers) {
            for (String endpoint : endpoints) {
                
                path = String.format("./rawData/%s/%s.json", ticker, endpoint);

                if (alphavantageMarketDataClient != null) {
                    jsonData = alphavantageMarketDataClient.getCompanyJson(ticker, endpoint.split("\\.")[1]);
                    JsonFileWriter.write(path, jsonData);
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    public static void getFinnhubApiData(ArrayList<String> tickers) throws InterruptedException {
        String path;
        JsonNode jsonData = null;
        FinnhubMarketDataClient finnhubMarketDataClient = new FinnhubMarketDataClient();
        
        ArrayList<String> endpoints = new ArrayList<>();
        endpoints.add("finnhub.metric.all");
        // endpoints.add("finnhub.profile2");
        
        // endpoints.add("finnhub.financials-reported");
        // endpoints.add("finnhub.insider-transactions");
        // endpoints.add("finnhub.insider-sentiment");

        for (String ticker : tickers) {
            for (String endpoint : endpoints) {

                String[] endpontParts = endpoint.split("\\.");
                String queryParam = "";
                if (endpontParts.length > 2) {
                    queryParam = endpontParts[2];
                }
                
                path = String.format("./rawData/%s/%s.%s.json", ticker, endpontParts[0], endpontParts[1]);

                if (finnhubMarketDataClient != null) {
                    jsonData = finnhubMarketDataClient.getCompanyJson(ticker, endpontParts[1], queryParam);
                    JsonFileWriter.write(path, jsonData);
                }
            }
        }
    }
}
