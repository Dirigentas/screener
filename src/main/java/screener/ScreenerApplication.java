package screener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import screener.entity.Company;
import screener.service.CompanyService;
import screener.service.MarketDataRetievalService;
import screener.service.MetricPickerService;
import screener.utils.TxtFileReader;

@SpringBootApplication
public class ScreenerApplication {

    private static final ArrayList<String> TICKERS = TxtFileReader.read("./rawData/tickers.txt");
    private static final ArrayList<String> ALL_TICKERS = new ArrayList<>(
        Arrays.asList(new File("./rawData/").list((dir, name) -> new File(dir, name).isDirectory()))
    );
    
    public static void main(String[] args) throws InterruptedException {
        //----------------------------//
        // Controller                //
        int runAlphavantageApi = 0; // Change to '0' to skip API fetch
        int runFinnhubApi = 0;     // Change to '0' to skip API fetch
        int runDb = 1;            // Change to '0' to skip DB patch
        //-----------------------//



        //---------------------------------------------------------------------------------------
        // get data from API endpoints  for tickers.txt
        if (runAlphavantageApi == 1) {
            MarketDataRetievalService.getAlphavantageApiData(TICKERS);
        }
        //---------------------------------------------------------------------------------------

        //---------------------------------------------------------------------------------------
        // get data from API endpoints  for all ticker folders in a rawDara folder (mainly for newest EV values)
        if (runFinnhubApi == 1) {
            MarketDataRetievalService.getFinnhubApiData(ALL_TICKERS);
        }
        //---------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------
        // Start Spring context once and reuse the CompanyService for all inserts
        if (runDb == 1) {
            ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
            CompanyService companyService = context.getBean(CompanyService.class);

            for (String ticker : ALL_TICKERS) {

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
                company.setReturnOnCapital((double) Math.round(averageEbit / (workingCapital + fixedAssets)* 100 * 10) / 10);
                

                // companyRepository.save(company);

                Company addedToDBCompany = companyService.createNewCompany(company);
                System.out.println("Successfully added company with ID: " + addedToDBCompany.getTicker());
            }
        }
        
        //---------------------------------------------------------------------------------------
    }
}
