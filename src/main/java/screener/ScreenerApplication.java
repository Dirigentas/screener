package screener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import screener.API.FinnhubMarketDataClient;
import screener.db.Company;
import screener.db.CompanyService;

@SpringBootApplication
public class ScreenerApplication { // Renamed to follow Spring convention
    

    // @Autowired
    // private static CompanyService companyService;

    public static void main(String[] args) {
        // 1. Start the Spring application context
        // ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);

        // 2. Get the CompanyService bean from the context
        // CompanyService companyService = context.getBean(CompanyService.class);

        // 3. Now you can use the service
        // Company msft = companyService.createNewCompany("MSFT", "Microsoft", 99.5);
        // System.out.println("Successfully added company with ID: " + msft.getTicker());

        FinnhubMarketDataClient test = new FinnhubMarketDataClient();
        Company result = test.getCompanyData("UPWK");
        System.out.println(result);

    }
}