package screener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "mid_stats") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class MidStats {

    @Id // Marks this as the primary key
    private String ticker;
    
    @Column(name = "ev M")
    private Double evM;
    
    @Column(name = "ebit M")
    private Double ebitM;
    
    @Column(name = "working capital M", comment = "Amount of money a company has tied up in its daily operations")
    private Double workingCapitalM; // Net Working Capital = totalCurrentAssets - cashAndCashEquivalentsAtCarryingValue - totalCurrentLiabilities - shortTermDebt
    
    @Column(name = "fixed assets M", comment = "Value of a company's long-term physical resources")
    private Double fixedAssetsM; // Net Fixed Assets = totalNonCurrentAssets - intangibleAssets - goodwill

    @Column(name = "PE", comment = "Price to Earnings")
    private Double pe;

    @Column(name = "shares count M", comment = "")
    private Double sharesCountM;

    @Column(name = "shares count -1Y M", comment = "")
    private Double sharesCountPriorM;

    @Column(name = "CFO ttm M", comment = "Trailing twelve months cash flow from operations")
    private Double cashFlowOperationsTtmM;

    @Column(name = "CapEx ttm M", comment = "Cash spent on acquiring, upgrading, and maintaining physical assets")
    private Double capitalExpendituresTtmM;

    @Column(name = "ROA TTM", comment = "Trailing twelve months return on assets")
    private Double roaTtm;

    @Column(name = "ROA -1Y TTM", comment = "Prior year twelve months return on assets")
    private Double roaPriorTtm;

    @Column(name = "Net Income TTM M", comment = "")
    private Double earningsTtmM;

    @Column(name = "Gross Margin", comment = "Gross Margin = Total Revenue / Gross Profit")
    private Double grossMargin;

    @Column(name = "Gross Margin -1Y", comment = "Gross Margin = Total Revenue / Gross Profit")
    private Double grossMarginPrior;

    @Column(name = "Revenue TTM M", comment = "")
    private Double revenueTtmM;

    @Column(name = "Revenue -1Y TTM M", comment = "")
    private Double revenuePriorTtmM;

    @Column(name = "Gross Profit -1Y TTM M", comment = "")
    private Double grossProfitPriorTtmM;

    @Column(name = "Current ratio", comment = "")
    private Double currentRatio;

    @Column(name = "Current ratio -1Y", comment = "")
    private Double currentRatioPrior;

    @Column(name = "Asset Turnover", comment = "Total Revenue / Total Assets at Beginning of Year")
    private Double assetTurnover;

    @Column(name = "Asset Turnover -1Y", comment = "Prior Total Revenue / Prior Total Assets at Beginning of Year ")
    private Double assetTurnoverPrior;

    @Column(name = "leverage", comment = "Leverage = Long-Term Debt / Average Total Assets")
    private Double leverage;

    @Column(name = "leverage -1", comment = "Prior Leverage = Long-Term Debt / Average Total Assets")
    private Double leveragePrior;

    @Column(name = "income_tax_TTM_M", comment = "Prior Leverage = Long-Term Debt / Average Total Assets")
    private Double incomeTaxTtmM;
}
