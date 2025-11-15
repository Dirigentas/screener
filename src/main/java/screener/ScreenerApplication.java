package screener;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import screener.API.AlphavantageMarketDataClient;
import screener.API.FinnhubMarketDataClient;
import screener.API.interfaces.MarketDataClient;
import screener.utils.FileReader;
import screener.utils.JsonFileWriter;

import tools.jackson.databind.JsonNode;

@SpringBootApplication
public class ScreenerApplication { // Renamed to follow Spring convention
    // @Autowired
    // private static CompanyService companyService;

    private static LocalDate date = LocalDate.now();

    public static void main(String[] args) {
        // 1. Start the Spring application context
        // ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
        // 2. Get the CompanyService bean from the context
        // CompanyService companyService = context.getBean(CompanyService.class);
        // 3. Now you can use the service
        // Company msft = companyService.createNewCompany("MSFT", "Microsoft", 99.5);
        // System.out.println("Successfully added company with ID: " + msft.getTicker());

        // FinnhubMarketDataClient finnhubRequest = new FinnhubMarketDataClient();

        // ArrayList<String> tickers = new ArrayList<>();
        // LocalDate date = LocalDate.now();
        


        // get all API endpoints data for all tickers from tickers.txt file----------------------
        String pathForDataFiles;
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
                    jsonData = finnhubMarketDataClient.getCompanyJson(ticker, endpoint.split("\\.")[1]);
                    
                } else if (endpoint.contains("alphavantage")) {
                    jsonData = alphavantageMarketDataClient.getCompanyJson(ticker, endpoint.split("\\.")[1]);
                }
                JsonFileWriter.write(pathForDataFiles, jsonData);
            }
        }
        //---------------------------------------------------------------------------------------
    }
}