package screener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import screener.entity.MagicFormula;
import screener.entity.MidStats;
import screener.repository.MidStatsRepository;
import screener.service.MagicFormulaService;
import screener.service.MarketDataRetievalService;
import screener.service.MetricPickerService;
import screener.utils.TxtFileReader;

@SpringBootApplication
public class ScreenerApplication {

    private static final ArrayList<String> TICKERS = TxtFileReader.read("./tickers.txt");
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
            MagicFormulaService magicFormulaService = context.getBean(MagicFormulaService.class);
            MidStatsRepository midStatsRepository = context.getBean(MidStatsRepository.class);

            for (String ticker : ALL_TICKERS) {

                // pick from json
                String name = MetricPickerService.getCompanyName(ticker);
                double sharesCountM = MetricPickerService.getSharesCountM(ticker);
                String latestQuarter = MetricPickerService.getLatestQuarter(ticker);
                double evM = MetricPickerService.getCompanyEvM(ticker);
                double eps = MetricPickerService.getEpsTtm(ticker);
                double pe = MetricPickerService.getPe(ticker);
                double ebitAlphM = MetricPickerService.getAlphavantageEbitM(ticker);
                double ebitFinnM = MetricPickerService.getFinnhubEbitM(ticker);
                double workingCapitalM = MetricPickerService.getWorkingCapitalM(ticker);
                double fixedAssetsM = MetricPickerService.getFixedAssetsM(ticker);
                double cashFlowOperationsTtmM = MetricPickerService.getCashFlowOperationsTtmM(ticker);
                double roaTtm = MetricPickerService.getRoaTtm(ticker);
                double roaPreviousTtm = MetricPickerService.getRoaPreviousTtm(ticker);
                

                // calculations
                double averageEbitM = (double) Math.round((ebitAlphM + ebitFinnM) / 2 * 10) / 10;
                double earningsYield = (double) Math.round(averageEbitM / evM * 100 * 10) / 10;
                double returnOnCapital = (double) Math.round(averageEbitM / (workingCapitalM + fixedAssetsM)* 100 * 10) / 10;

                // mid_stats DB table
                MidStats midStats = new MidStats();
                midStats.setTicker(ticker);
                midStats.setEvM(evM);
                midStats.setEbitM(averageEbitM);
                midStats.setWorkingCapitalM(workingCapitalM);
                midStats.setFixedAssetsM(fixedAssetsM);
                midStats.setEps(eps);
                midStats.setPe(pe);
                midStats.setSharesCountM(sharesCountM);
                midStats.setCashFlowOperationsTtmM(cashFlowOperationsTtmM);
                midStats.setRoaTtm(roaTtm);
                midStats.setRoaPreviousTtm(roaPreviousTtm);
                
                // magic_formula DB table
                MagicFormula magicFormula = new MagicFormula();
                magicFormula.setTicker(ticker);
                magicFormula.setAName(name);
                magicFormula.setZLatestQuarter(latestQuarter);
                magicFormula.setEarningsYield(earningsYield);
                magicFormula.setReturnOnCapital(returnOnCapital);

                
                midStatsRepository.save(midStats);
                magicFormulaService.createNewCompany(magicFormula);
            }
            // Shut down the Spring container
            context.close();
        }
        //---------------------------------------------------------------------------------------
    }
}
