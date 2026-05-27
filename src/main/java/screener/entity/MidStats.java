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

    @Column(name = "EPS ttm", comment = "Trailing twelve month earnings per share ")
    private Double eps;

    @Column(name = "PE", comment = "Price to Earnings")
    private Double pe;

    @Column(name = "shares count M", comment = "")
    private Double sharesCountM;

    @Column(name = "CFO ttm M", comment = "Trailing twelve months cash flow from operations in millions")
    private Double cashFlowOperationsTtmM;

    @Column(name = "ROA TTM", comment = "Trailing twelve months return on assets")
    private Double roaTtm;

    @Column(name = "ROA -1Y TTM", comment = "Previous year twelve months return on assets")
    private Double roaPreviousTtm;

    @Column(name = "Net Income TTM M", comment = "")
    private Double earningsTtmM;
}
