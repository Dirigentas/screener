package screener;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import screener.API.FinnhubMarketDataClient;
import screener.utils.Company;
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
        

        // Data saver to a json file-------------------
        String dataProvider = "Finnhub";
        ArrayList<String> tickers = FileReader.read("./rawData/tickers.txt");

        for (String ticker : tickers) {
            String path = "./rawData/" + ticker + "_" + dataProvider + "_" + date + ".json";
            JsonNode jsonData = new FinnhubMarketDataClient().getCompanyJson(ticker);
            JsonFileWriter.write(path, jsonData);
        }
        //----------------------------------------------
    }
}