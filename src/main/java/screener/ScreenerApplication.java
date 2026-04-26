package screener;

import java.time.LocalDate;
import java.util.ArrayList;

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

    public static void main(String[] args) {
        // get all API endpoints data for all tickers from tickers.txt to rawData----------------
        // getAPIEndpointsData();
        //---------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------
        
        String jsonDate = "2026-04-26";
        ArrayList<String> tickers = FileReader.read("./rawData/tickers.txt");

        // Start Spring context once and reuse the CompanyService for all inserts
        ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
        CompanyService companyService = context.getBean(CompanyService.class);

        for (String ticker : tickers) {

            // read data from json
            String name = readJsonName(ticker, jsonDate);
            double ev = readJsonEV(ticker, jsonDate);

            // add company data to database
            Company company = new Company();
            company.setTicker(ticker);
            company.setName(name);
            company.setEnterpriseValue(ev);
            Company addedToDBCompany = companyService.createNewCompany(company);
            System.out.println("Successfully added company with ID: " + addedToDBCompany.getTicker());
        }
        
        //---------------------------------------------------------------------------------------
    }

    @SuppressWarnings("unused")
    private static void getAPIEndpointsData() {
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
            }
        }
    }

    private static String readJsonName(String ticker, String jsonDate) {

        String path = "rawData/" + ticker + "/alphavantage.OVERVIEW_" + jsonDate + ".json";
        JsonNode json = JsonFileReader.read(path);

        String metric = json
                .get("Name")
                .asString();

        return metric;
    }
    private static double readJsonEV(String ticker, String jsonDate) {

        String path = "rawData/" + ticker + "/finnhub.all_" + jsonDate + ".json";
        JsonNode json = JsonFileReader.read(path);

        double metric = json
                .get("metric")
                .get("enterpriseValue")
                .asDouble();

        return metric;
    }
}
