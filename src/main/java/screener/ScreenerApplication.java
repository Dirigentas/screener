package screener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import screener.entity.MagicFormula;
import screener.entity.MidStats;
import screener.entity.PiotroskiScore;
import screener.repository.MidStatsRepository;
import screener.repository.PiotroskiScoreRepository;
import screener.service.MagicFormulaService;
import screener.service.MarketDataRetievalService;
import screener.service.MetricPickerService;
import screener.utils.TxtFileReader;

@SpringBootApplication
public class ScreenerApplication {

    private static final ArrayList<String> TICKERS_TXT = TxtFileReader.read("./tickers.txt");
    private static final ArrayList<String> TICKERS_ALL = new ArrayList<>(
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
            MarketDataRetievalService.getAlphavantageApiData(TICKERS_TXT);
        }
        //---------------------------------------------------------------------------------------

        //---------------------------------------------------------------------------------------
        // get data from API endpoints  for all ticker folders in a rawDara folder (mainly for newest EV values)
        if (runFinnhubApi == 1) {
            MarketDataRetievalService.getFinnhubApiData(TICKERS_ALL);
        }
        //---------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------
        // Start Spring context once and reuse the CompanyService for all inserts
        if (runDb == 1) {
            ConfigurableApplicationContext context = SpringApplication.run(ScreenerApplication.class, args);
            MidStatsRepository midStatsRepository = context.getBean(MidStatsRepository.class);
            PiotroskiScoreRepository piotroskiScoreRepository = context.getBean(PiotroskiScoreRepository.class);
            MagicFormulaService magicFormulaService = context.getBean(MagicFormulaService.class);

            for (String ticker : TICKERS_ALL) {

                // pick from json
                String name = MetricPickerService.getCompanyName(ticker);
                double sharesCountM = MetricPickerService.getSharesCountM(ticker);
                double sharesCountPriorM = MetricPickerService.getSharesCountPriorM(ticker);
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
                double roaPriorTtm = MetricPickerService.getRoaPriorTtm(ticker);
                double earningsTtmM = MetricPickerService.getEarningsTtmM(ticker);
                double revenueTtmM = MetricPickerService.getRevenueTtmM(ticker);
                double revenuePriorTtmM = MetricPickerService.getRevenuePriorTtmM(ticker);
                double grossProfitTtmM = MetricPickerService.getGrossProfitTtmM(ticker);
                double grossProfitPriorTtmM = MetricPickerService.getGrossProfitPriorTtmM(ticker);
                double currentRatio = MetricPickerService.getCurrentRatio(ticker);
                double currentRatioPrior = MetricPickerService.getCurrentRatioPrior(ticker);
                double totalAssetsAtStartM = MetricPickerService.getTotalAssetsAtStartM(ticker);
                double totalAssetsAtStartPriorM = MetricPickerService.getTotalAssetsAtStartPriorM(ticker);
                double longTermDebtM = MetricPickerService.getLongTermDebtM(ticker);
                double longTermDebtPriorM = MetricPickerService.getLongTermDebtPriorM(ticker);
                double averageTotalAssetsM = MetricPickerService.getAverageTotalAssetsM(ticker);
                double averageTotalAssetsPriorM = MetricPickerService.getAverageTotalAssetsPriorM(ticker);
                double incomeTaxTtmM = MetricPickerService.getIncomeTaxTtmM(ticker);
                
                
                // calculations
                double averageEbitM = (double) Math.round((ebitAlphM + ebitFinnM) / 2 * 10) / 10;
                double earningsYield = (double) Math.round(averageEbitM / evM * 100 * 10) / 10;
                double returnOnCapital = (double) Math.round(averageEbitM / (workingCapitalM + fixedAssetsM)* 100 * 10) / 10;
                double grossMargin = (double) Math.round(grossProfitTtmM / revenueTtmM * 10000) / 100;
                double grossMarginPrior = (double) Math.round(grossProfitPriorTtmM / revenuePriorTtmM * 10000) / 100;
                double assetTurnover = (double) Math.round(revenueTtmM / totalAssetsAtStartM * 100) / 100;
                double assetTurnoverPrior = (double) Math.round(revenuePriorTtmM / totalAssetsAtStartPriorM * 100) / 100;
                double leverage = (double) Math.round(longTermDebtM / averageTotalAssetsM * 100) / 100;
                double leveragePrior = (double) Math.round(longTermDebtPriorM / averageTotalAssetsPriorM * 100) / 100;

                
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
                midStats.setSharesCountPriorM(sharesCountPriorM);
                midStats.setCashFlowOperationsTtmM(cashFlowOperationsTtmM);
                midStats.setRoaTtm(roaTtm);
                midStats.setRoaPriorTtm(roaPriorTtm);
                midStats.setEarningsTtmM(earningsTtmM);
                midStats.setGrossMargin(grossMargin);
                midStats.setGrossMarginPrior(grossMarginPrior);
                midStats.setRevenueTtmM(revenueTtmM);
                midStats.setRevenuePriorTtmM(revenuePriorTtmM);
                midStats.setGrossProfitPriorTtmM(grossProfitPriorTtmM);
                midStats.setCurrentRatio(currentRatio);
                midStats.setCurrentRatioPrior(currentRatioPrior);
                midStats.setAssetTurnover(assetTurnover);
                midStats.setAssetTurnoverPrior(assetTurnoverPrior);
                midStats.setLeverage(leverage);
                midStats.setLeveragePrior(leveragePrior);
                midStats.setIncomeTaxTtmM(incomeTaxTtmM);
                

                // magic_formula DB table
                MagicFormula magicFormula = new MagicFormula();
                magicFormula.setTicker(ticker);
                magicFormula.setAName(name);
                magicFormula.setZLatestQuarter(latestQuarter);
                magicFormula.setEarningsYield(earningsYield);
                magicFormula.setReturnOnCapital(returnOnCapital);

                
                // piotroski_score DB table
                int roaP = roaTtm > 0 ? 1 : 0;
                int cfoP = cashFlowOperationsTtmM > 0 ? 1 : 0;
                int roaChangeP = roaTtm > roaPriorTtm ? 1 : 0;
                int epsQualityP = cashFlowOperationsTtmM > earningsTtmM ? 1 : 0;
                int leverageChangeP = leverage >= leveragePrior ? 1 : 0;
                int liquidityChangeP = currentRatio > currentRatioPrior ? 1 : 0;
                int dilusionChange = sharesCountM <= sharesCountPriorM ? 1 : 0;
                int grossMarginChange = grossMargin > grossMarginPrior ? 1 : 0;
                int assetTurnoverChange = assetTurnover > assetTurnoverPrior ? 1 : 0;
                int totalScoreP = roaP + cfoP + roaChangeP + epsQualityP + leverageChangeP + liquidityChangeP + dilusionChange + grossMarginChange + assetTurnoverChange;

                PiotroskiScore piotroskiScore = new PiotroskiScore();
                piotroskiScore.setTicker(ticker);
                piotroskiScore.setTotalScore(totalScoreP);
                piotroskiScore.setRoa(roaP);
                piotroskiScore.setCfo(cfoP);
                piotroskiScore.setRoaChange(roaChangeP);
                piotroskiScore.setEpsQuality(epsQualityP);
                piotroskiScore.setLeverageChange(leverageChangeP);
                piotroskiScore.setLiquidityChange(liquidityChangeP);
                piotroskiScore.setDilusionChange(dilusionChange);
                piotroskiScore.setGrossMarginChange(grossMarginChange);
                piotroskiScore.setAssetTurnoverChange(assetTurnoverChange);


                // saving to DB
                midStatsRepository.save(midStats);
                piotroskiScoreRepository.save(piotroskiScore);
                magicFormulaService.createNewCompany(magicFormula);
            }
            // Shut down the Spring container
            context.close();
        }
        //---------------------------------------------------------------------------------------
    }
}
