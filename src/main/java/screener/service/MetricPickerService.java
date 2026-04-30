package screener.service;

import screener.utils.JsonFileReader;
import tools.jackson.databind.JsonNode;

public class MetricPickerService {

    private static final int MILLION  = 1_000_000;
    
    public static String getCompanyName(String ticker, String JSON_DATE) {

        String path = String.format("rawData/%s/alphavantage.OVERVIEW_%s.json", ticker, JSON_DATE);
        JsonNode json = JsonFileReader.read(path);

        String metric = json
                .get("Name")
                .asString();

        return metric;
    }

    public static double getCompanyEV(String ticker, String JSON_DATE) {

        String path = String.format("rawData/%s/finnhub.all_%s.json", ticker, JSON_DATE);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("enterpriseValue")
                .asDouble();

        return (double) Math.round(metric);
    }
    
    public static double getFinnhubEBIT(String ticker, String JSON_DATE) {

        String path1 = String.format("rawData/%s/finnhub.all_%s.json", ticker, JSON_DATE);
        JsonNode json1 = JsonFileReader.read(path1);

        double ebitPerShare = 0;

        for (int i = 0; i < 4; i++) {
            ebitPerShare += json1
                .get("series")
                .get("quarterly")
                .get("ebitPerShare")
                .get(i)
                .get("v")
                .asDouble();
        }

        String path2 = String.format("rawData/%s/alphavantage.OVERVIEW_%s.json", ticker, JSON_DATE);
        JsonNode json2 = JsonFileReader.read(path2);

        double sharesOutstanding = json2
                .get("SharesOutstanding")
                .asDouble();

        return (double) Math.round(ebitPerShare * sharesOutstanding / MILLION);
    }

    public static double getAlphavantageEBIT(String ticker, String JSON_DATE) {

        String path = String.format("rawData/%s/alphavantage.INCOME_STATEMENT_%s.json", ticker, JSON_DATE);
        JsonNode json = JsonFileReader.read(path);
        double metric = 0;
        
        for (int i = 0; i < 4; i++) {
            metric += json
                .get("quarterlyReports")
                .get(i)
                .get("ebit")
                .asDouble();
        }
        return (double) Math.round(metric / MILLION);
    }
}
