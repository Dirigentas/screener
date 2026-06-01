package screener.service;

import java.util.HashMap;
import java.util.Map;

import screener.utils.JsonFileReader;
import tools.jackson.databind.JsonNode;

public class MetricPickerService {

    private static final int MILLION  = 1_000_000;
    private static final String ALPHA_BALANCE  = "rawData/%s/alphavantage.BALANCE_SHEET.json";
    private static final String ALPHA_CASHFLOW  = "rawData/%s/alphavantage.CASH_FLOW.json";
    private static final String ALPHA_INCOME  = "rawData/%s/alphavantage.INCOME_STATEMENT.json";
    private static final String FINN_METRIC  = "rawData/%s/finnhub.metric.json";
    private static final String FINN_PROFILE  = "rawData/%s/finnhub.profile2.json";
    
    public static String getCompanyName(String ticker) {

        String path = String.format(FINN_PROFILE, ticker);
        JsonNode json = JsonFileReader.read(path);

        String metric = json
                .get("name")
                .asString();

        return metric;
    }

    public static double getPe(String ticker) {

        String path = String.format(FINN_METRIC, ticker);
        JsonNode json = JsonFileReader.read(path);
        JsonNode node = json
                .get("metric")
                .get("peTTM");

        double metric = (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();

        return (double) Math.round(metric * 10) / 10;
    }

    public static double getTotalAssetsAtStartM(String ticker) {

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);

        JsonNode node = json
                .get("quarterlyReports")
                .get(4)
                .get("totalAssets");

        double metric = (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();

        return (double) Math.round(metric / MILLION * 10) / 10;
    }

    public static double getTotalAssetsAtStartPreviousM(String ticker) {

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);

        JsonNode node = json
                .get("quarterlyReports")
                .get(8)
                .get("totalAssets");

        double metric = (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();

        return (double) Math.round(metric / MILLION * 10) / 10;
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

    public static double getSharesCountPreviousM(String ticker) {

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);

        JsonNode node = json
                .get("quarterlyReports")
                .get(4)
                .get("commonStockSharesOutstanding");

        double metric = (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();

        return (double) Math.round(metric / MILLION * 10) / 10;
    }

    public static double getSharesCountM(String ticker) {

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);

        JsonNode node = json
                .get("quarterlyReports")
                .get(0)
                .get("commonStockSharesOutstanding");

        double metric = (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();

        return (double) Math.round(metric / MILLION * 10) / 10;
    }

    public static double getCurrentRatio(String ticker) {

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);
        double assets = 0;
        double liabilities = 0;
        
        for (int i = 0; i < 4; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("totalCurrentAssets");

                assets += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }

        for (int i = 0; i < 4; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("totalCurrentLiabilities");

                liabilities += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(assets / liabilities * 100) / 100;
    }

    public static double getCurrentRatioPrevious(String ticker) {

        String path = String.format(ALPHA_BALANCE, ticker);
        JsonNode json = JsonFileReader.read(path);
        double assets = 0;
        double liabilities = 0;
        
        for (int i = 4; i < 8; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("totalCurrentAssets");

                assets += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }

        for (int i = 4; i < 8; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("totalCurrentLiabilities");

                liabilities += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(assets / liabilities * 100) / 100;
    }

    public static double getCompanyEvM(String ticker) {

        String path = String.format(FINN_METRIC, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("enterpriseValue")
                .asDouble();

        return (double) Math.round(metric);
    }

    public static double getEpsTtm(String ticker) {

        String path = String.format(FINN_METRIC, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("epsTTM")
                .asDouble();

        return (double) Math.round(metric * 100) / 100;
    }

    public static double getGrossMargin(String ticker) {

        String path = String.format(FINN_METRIC, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("grossMarginTTM")
                .asDouble();

        return (double) Math.round(metric * 100) / 100;
    }

    public static double getRevenuePreviousTtmM(String ticker) {

        String path = String.format(ALPHA_INCOME, ticker);
        JsonNode json = JsonFileReader.read(path);
        double metric = 0;
        
        for (int i = 4; i < 8; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("totalRevenue");

                metric += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(metric / MILLION * 100) / 100;
    }

    public static double getRevenueTtmM(String ticker) {

        String path = String.format(ALPHA_INCOME, ticker);
        JsonNode json = JsonFileReader.read(path);
        double metric = 0;
        
        for (int i = 0; i < 4; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("totalRevenue");

                metric += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(metric / MILLION * 100) / 100;
    }

    public static double getGrossProfitPreviousTtmM(String ticker) {

        String path = String.format(ALPHA_INCOME, ticker);
        JsonNode json = JsonFileReader.read(path);
        double metric = 0;
        
        for (int i = 4; i < 8; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("grossProfit");

                metric += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(metric / MILLION * 10) / 10;
    }

    public static double getRoaTtm(String ticker) {

        String path = String.format(FINN_METRIC, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("series")
                .get("quarterly")
                .get("roaTTM")
                .get(0)
                .get("v")
                .asDouble();

        return metric;
    }

    public static double getRoaPreviousTtm(String ticker) {

        String path = String.format(FINN_METRIC, ticker);
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("series")
                .get("quarterly")
                .get("roaTTM")
                .get(4)
                .get("v")
                .asDouble();

        return metric;
    }
    
    public static double getFinnhubEbitM(String ticker) {

        String path1 = String.format(FINN_METRIC, ticker);
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

        return (double) Math.round(ebitPerShare * getSharesCountM(ticker) * 10) / 10;
    }

    public static double getAlphavantageEbitM(String ticker) {

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
        return (double) Math.round(metric / MILLION * 10) / 10;
    }

    public static double getEarningsTtmM(String ticker) {

        String path = String.format(ALPHA_INCOME, ticker);
        JsonNode json = JsonFileReader.read(path);
        double metric = 0;
        
        for (int i = 0; i < 4; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("netIncome");

                metric += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(metric / MILLION * 10) / 10;
    }

    public static double getWorkingCapitalM(String ticker) {
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

        double finalMetric = metrics.get("totalCurrentAssets")
                - metrics.get("cashAndCashEquivalentsAtCarryingValue")
                - metrics.get("totalCurrentLiabilities")
                + metrics.get("shortTermDebt");

        if (finalMetric > 0) {

            return (double) Math.round(finalMetric / MILLION * 10) / 10;
            
        } else {
            return 0;
        }
    }

    public static double getFixedAssetsM(String ticker) {
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

        double finalMetric = metrics.get("totalNonCurrentAssets")
                - metrics.get("intangibleAssets")
                - metrics.get("goodwill")
                - metrics.get("longTermInvestments");

        return (double) Math.round(finalMetric / MILLION * 10) / 10;
    }

    public static double getCashFlowOperationsTtmM(String ticker) {

        String path = String.format(ALPHA_CASHFLOW, ticker);
        JsonNode json = JsonFileReader.read(path);
        double metric = 0;
        
        for (int i = 0; i < 4; i++) {
            JsonNode node = json
                .get("quarterlyReports")
                .get(i)
                .get("operatingCashflow");

                metric += (node.isNull() || node.asString().equals("None")) ? 0 : node.asDouble();
        }
        return (double) Math.round(metric / MILLION * 10) / 10;
    }
}
