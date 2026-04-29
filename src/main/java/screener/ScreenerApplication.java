package screener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import screener.API.AlphavantageMarketDataClient;
import screener.API.FinnhubMarketDataClient;
import screener.API.interfaces.MarketDataClient;
import screener.db.CompanyService;
import screener.dto.Company;
import screener.utils.FileReader;
import screener.utils.JsonFileReader;
import screener.utils.JsonFileWriter;
import tools.jackson.databind.JsonNode;

@SpringBootApplication
public class ScreenerApplication {

    private static LocalDate dateNow = LocalDate.now();
    private static final int MILLION  = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        //---------------------------------------------------------------------------------------
        // get all API endpoints data for all tickers from tickers.txt to rawData
        // getAPIEndpointsData();
        //---------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------
        
        String jsonDate = "2026-04-27";
        ArrayList<String> tickers = FileReader.read("./rawData/tickers.txt");

        // Start Spring context once and reuse the CompanyService for all inserts
        ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
        CompanyService companyService = context.getBean(CompanyService.class);

        for (String ticker : tickers) {

            // read data from json
            String name = getCompanyName(ticker, jsonDate);
            double ev = getCompanyEV(ticker, jsonDate);
            double ebitFinn = getFinnhubEBIT(ticker, jsonDate);
            double ebitAlph = getAlphavantageEBIT(ticker, jsonDate);

            // add company data to database
            Company company = new Company();
            company.setTicker(ticker);
            company.setName(name);
            // Earnings Yield
            company.setEarningsYieldAlph((double) Math.round(ebitAlph / ev * 100 * 10) / 10);
            company.setEarningsYieldFinn((double) Math.round(ebitFinn / ev * 100 * 10) / 10);

            // Return on Capital
            // Net Working Capital = totalCurrentAssets - cashAndCashEquivalentsAtCarryingValue - totalCurrentLiabilities - shortTermDebt
            // Net Fixed Assets = totalNonCurrentAssets - intangibleAssets - goodwill

            Company addedToDBCompany = companyService.createNewCompany(company);
            System.out.println("Successfully added company with ID: " + addedToDBCompany.getTicker());
        }
        
        //---------------------------------------------------------------------------------------
    }

    private static void getAPIEndpointsData() throws InterruptedException {
        String pathForDataFiles;
        MarketDataClient dataProvider = null;
        JsonNode jsonData = null;
        MarketDataClient finnhubMarketDataClient = new FinnhubMarketDataClient();
        MarketDataClient alphavantageMarketDataClient = new AlphavantageMarketDataClient();
        ArrayList<String> tickers = FileReader.read("./rawData/tickers.txt");
        
        ArrayList<String> endpoints = new ArrayList<>();
        endpoints.add("finnhub.all");
        endpoints.add("alphavantage.OVERVIEW");
        endpoints.add("alphavantage.INCOME_STATEMENT");
        endpoints.add("alphavantage.BALANCE_SHEET");
        endpoints.add("alphavantage.CASH_FLOW");

        for (String ticker : tickers) {
            for (String endpoint : endpoints) {
                
                pathForDataFiles = "./rawData/" + ticker + "/" + endpoint + "_" + dateNow + ".json";

                if (endpoint.contains("finnhub")) {
                    dataProvider = finnhubMarketDataClient;
                } else if (endpoint.contains("alphavantage")) {
                    dataProvider = alphavantageMarketDataClient;
                }
                if (dataProvider != null) {
                    jsonData = dataProvider.getCompanyJson(ticker, endpoint.split("\\.")[1]);
                    JsonFileWriter.write(pathForDataFiles, jsonData);
                }
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }

    private static String getCompanyName(String ticker, String jsonDate) {

        String path = "rawData/" + ticker + "/alphavantage.OVERVIEW_" + jsonDate + ".json";
        JsonNode json = JsonFileReader.read(path);

        String metric = json
                .get("Name")
                .asString();

        return metric;
    }
    private static double getCompanyEV(String ticker, String jsonDate) {

        String path = "rawData/" + ticker + "/finnhub.all_" + jsonDate + ".json";
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("enterpriseValue")
                .asDouble();

        return (double) Math.round(metric);
    }
    private static double getFinnhubEBIT(String ticker, String jsonDate) {

        String path1 = "rawData/" + ticker + "/finnhub.all_" + jsonDate + ".json";
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

        String path2 = "rawData/" + ticker + "/alphavantage.OVERVIEW_" + jsonDate + ".json";
        JsonNode json2 = JsonFileReader.read(path2);

        double sharesOutstanding = json2
                .get("SharesOutstanding")
                .asDouble();

        return (double) Math.round(ebitPerShare * sharesOutstanding / MILLION);
    }

    private static double getAlphavantageEBIT(String ticker, String jsonDate) {

        String path = "rawData/" + ticker + "/alphavantage.INCOME_STATEMENT_" + jsonDate + ".json";
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
