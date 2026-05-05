package screener;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import screener.db.CompanyService;
import screener.dto.Company;
import screener.service.MarketDataRetievalService;
import screener.service.MetricPickerService;
import screener.utils.FileReader;

@SpringBootApplication
public class ScreenerApplication {

    private static final ArrayList<String> TICKERS = FileReader.read("./rawData/tickers.txt");
    
    public static void main(String[] args) throws InterruptedException {
        //----------------------------//
        // Control panel             //
        int runAlphavantageApi = 0; // Change to '0' to skip API fetch
        int runFinnhubApi = 1;     // Change to '0' to skip API fetch
        int runDb = 1;            // Change to '0' to skip DB patch
        //-----------------------//



        //---------------------------------------------------------------------------------------
        // get data from API endpoints  for tickers.txt
        if (runAlphavantageApi == 1) {
            MarketDataRetievalService.getAlphavantageApiData(TICKERS);
        }
        //---------------------------------------------------------------------------------------

        //---------------------------------------------------------------------------------------
        // get data from API endpoints  for tickers.txt (mainly for newest EV values)
        if (runFinnhubApi == 1) {
            MarketDataRetievalService.getFinnhubApiData(TICKERS);
        }
        //---------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------
        // Start Spring context once and reuse the CompanyService for all inserts
        if (runDb == 1) {
            ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
            CompanyService companyService = context.getBean(CompanyService.class);

            for (String ticker : TICKERS) {

                // read data from json
                String name = MetricPickerService.getCompanyName(ticker);
                String latestQuarter = MetricPickerService.getLatestQuarter(ticker);
                double ev = MetricPickerService.getCompanyEV(ticker);
                // EBIT
                double ebitAlph = MetricPickerService.getAlphavantageEBIT(ticker);
                double ebitFinn = MetricPickerService.getFinnhubEBIT(ticker);
                double averageEbit = (double) Math.round((ebitAlph + ebitFinn) / 2 * 10) / 10;

                double workingCapital = MetricPickerService.getWorkingCapital(ticker);
                double fixedAssets = MetricPickerService.getFixedAssets(ticker);

                // add company data to database
                Company company = new Company();
                company.setTicker(ticker);
                company.setAname(name);
                company.setLatestQuarter(latestQuarter);
                    // Earnings Yield
                // company.setEv(ev);
                company.setEarningsYield((double) Math.round(averageEbit / ev * 100 * 10) / 10);
                // company.setEbit(averageEbit);
                    // Return on Capital
                // company.setWorkingCapital(workingCapital);
                // company.setFixedAssets(fixedAssets);
                company.setReturnOnCapital((double) Math.round(ebitAlph / (workingCapital + fixedAssets)* 100 * 10) / 10);
                

                Company addedToDBCompany = companyService.createNewCompany(company);
                System.out.println("Successfully added company with ID: " + addedToDBCompany.getTicker());
            }
        }
        
        //---------------------------------------------------------------------------------------
    }
}
