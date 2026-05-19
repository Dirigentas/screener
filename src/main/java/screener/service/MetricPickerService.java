package screener.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import screener.utils.JsonFileReader;
import tools.jackson.databind.JsonNode;

public class MetricPickerService {

    private static final int MILLION  = 1_000_000;
    private static final String ALPHA_OVERVIEW  = "rawData/%s/alphavantage.OVERVIEW.json";
    private static final String ALPHA_INCOME  = "rawData/%s/alphavantage.INCOME_STATEMENT.json";
    private static final String ALPHA_BALANCE  = "rawData/%s/alphavantage.BALANCE_SHEET.json";
    private static final String FINN_ALL  = "rawData/%s/finnhub.all.json";
    
    public static String getCompanyName(String ticker) {

        String path = String.format(ALPHA_OVERVIEW, ticker);
        JsonNode json = JsonFileReader.read(path);

        String metric = json
                .get("Name")
                .asString();

        return metric;
    }

    public static double getPe(String ticker) {

        String path = String.format(ALPHA_OVERVIEW, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("PERatio")
                .asDouble();

        return metric;
    }

    public static BigInteger getSharesCount(String ticker) {

        String path = String.format(ALPHA_OVERVIEW, ticker);
        JsonNode json = JsonFileReader.read(path);

        BigInteger metric = json
                .get("SharesOutstanding")
                .asBigInteger();

        return metric;
    }

    public static String getLatestQuarter(String ticker) {

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);

        String metric = json
                .get("quarterlyReports")
                .get(0)
                .get("fiscalDateEnding")
                .asString();

        return metric;
    }

    public static double getCompanyEV(String ticker) {

        String path = String.format(FINN_ALL, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("enterpriseValue")
                .asDouble();

        return (double) Math.round(metric);
    }

    public static double getEpsTtm(String ticker) {

        String path = String.format(FINN_ALL, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("epsTTM")
                .asDouble();

        return (double) Math.round(metric * 100) / 100;
    }
    
    public static double getFinnhubEBIT(String ticker) {

        String path1 = String.format(FINN_ALL, ticker);
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

        String path2 = String.format(ALPHA_OVERVIEW, ticker);
        JsonNode json2 = JsonFileReader.read(path2);

        double sharesOutstanding = json2
                .get("SharesOutstanding")
                .asDouble();

        return (double) Math.round(ebitPerShare * sharesOutstanding / MILLION);
    }

    public static double getAlphavantageEBIT(String ticker) {

        String path = String.format(ALPHA_INCOME, ticker);
        JsonNode json = JsonFileReader.read(path);
        double metric = 0;
        
        for (int i = 0; i < 4; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("ebit");

                metric += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(metric / MILLION);
    }

    public static double getWorkingCapital(String ticker) {
        // Net Working Capital = totalCurrentAssets - cashAndCashEquivalentsAtCarryingValue - totalCurrentLiabilities - shortTermDebt

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);
        Map<String, Double> metrics = new HashMap<>();

        metrics.put("totalCurrentAssets", null);
        metrics.put("cashAndCashEquivalentsAtCarryingValue", null);
        metrics.put("totalCurrentLiabilities", null);
        metrics.put("shortTermDebt", null);
        
        for (String metric : metrics.keySet()) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(0)
                .get(metric);

            double value = (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();

            metrics.putIfAbsent(metric, value);
        }

        if ((metrics.get("totalCurrentAssets")
                - metrics.get("cashAndCashEquivalentsAtCarryingValue")
                - metrics.get("totalCurrentLiabilities")
                + metrics.get("shortTermDebt")) > 0) {

            return Math.round((metrics.get("totalCurrentAssets")
                - metrics.get("cashAndCashEquivalentsAtCarryingValue")
                - metrics.get("totalCurrentLiabilities")
                + metrics.get("shortTermDebt"))
                / MILLION);
            
        } else {
            return 0;
        }
    }

    public static double getFixedAssets(String ticker) {
        // Net Fixed Assets = totalNonCurrentAssets - intangibleAssets - goodwill - longTermInvestments

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);
        Map<String, Double> metrics = new HashMap<>();

        metrics.put("totalNonCurrentAssets", null);
        metrics.put("intangibleAssets", null);
        metrics.put("goodwill", null);
        metrics.put("longTermInvestments", null);
        
        for (String metric : metrics.keySet()) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(0)
                .get(metric);

            double value = (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();

            metrics.putIfAbsent(metric, value);
        }
        return Math.round((metrics.get("totalNonCurrentAssets")
                - metrics.get("intangibleAssets")
                - metrics.get("goodwill")
                - metrics.get("longTermInvestments"))
                / MILLION);
    }
}
