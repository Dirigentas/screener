package screener;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import screener.API.AlphavantageMarketDataClient;
import screener.API.FinnhubMarketDataClient;
import screener.API.interfaces.MarketDataClient;
import screener.db.Company;
import screener.db.CompanyService;
import screener.utils.FileReader;
import screener.utils.JsonFileWriter;

import tools.jackson.databind.JsonNode;

@SpringBootApplication
public class ScreenerApplication {

    private static LocalDate date = LocalDate.now();

    public static void main(String[] args) {
        // get all API endpoints data for all tickers from tickers.txt file----------------------
        // getAPIEndpointsData();
        //---------------------------------------------------------------------------------------

        // add company data to database
        addDataToDB(args);
        //---------------------------------------------------------------------------------------
    }

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
                
                pathForDataFiles = "./rawData/" + ticker + "/" + endpoint + "_" + date + ".json";

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

    private static void addDataToDB(String[] args) {

        // 1. Start the Spring application context
        ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
        // 2. Get the CompanyService bean from the context
        CompanyService companyService = context.getBean(CompanyService.class);
        // 3. Now you can use the service
        Company deck = companyService.createNewCompany("DECK2", "Deckers3", 84.0);
        System.out.println("Successfully added company with ID: " + deck.getTicker());
    }
}