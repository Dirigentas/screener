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

    private static final String JSON_DATE  = "2026-04-27";
    private static final ArrayList<String> TICKERS = FileReader.read("./rawData/tickers.txt");
    
    public static void main(String[] args) throws InterruptedException {
        //---------------------------------------------------------------------------------------
        // get all API endpoints data for tickers.txt
        // MarketDataRetievalService.getApiEndpointsData(TICKERS);
        //---------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------

        // Start Spring context once and reuse the CompanyService for all inserts
        ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
        CompanyService companyService = context.getBean(CompanyService.class);

        for (String ticker : TICKERS) {

            // read data from json
            String name = MetricPickerService.getCompanyName(ticker, JSON_DATE);
            double ev = MetricPickerService.getCompanyEV(ticker, JSON_DATE);
            // EBIT
            double ebitAlph = MetricPickerService.getAlphavantageEBIT(ticker, JSON_DATE);
            double ebitFinn = MetricPickerService.getFinnhubEBIT(ticker, JSON_DATE);
            double averageEbit = (double) Math.round((ebitAlph + ebitFinn) / 2 * 10) / 10;

            double workingCapital = MetricPickerService.getWorkingCapital(ticker, JSON_DATE);
            double fixedAssets = MetricPickerService.getFixedAssets(ticker, JSON_DATE);

            // add company data to database
            Company company = new Company();
            company.setTicker(ticker);
            company.setAname(name);
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
        //---------------------------------------------------------------------------------------
    }
}
