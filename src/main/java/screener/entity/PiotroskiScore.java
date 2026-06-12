package screener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "piotroski_score") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class PiotroskiScore {

    @Id // Marks this as the primary key
    private String ticker;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "roa", comment = "Return on Assets")
    private Integer roa;

    @Column(name = "cfo", comment = "Cashflow from Operations")
    private Integer cfo;

    @Column(name = "roa_change", comment = "Is the company's profitability improving compared to last year?")
    private Integer roaChange;

    @Column(name = "eps_quality", comment = "Cash Flow from Operations > Net Income")
    private Integer epsQuality;

    @Column(name = "leverage_change", comment = "Leverage = Long-Term Debt / Average Total Assets")
    private Integer leverageChange;

    @Column(name = "liquidity_change", comment = "Is the company improving its ability to cover short-term obligations?")
    private Integer liquidityChange;

    @Column(name = "dilution_change", comment = "Is the company raising emergency capital by printing more stock?")
    private Integer dilusionChange;

    @Column(name = "gross_margin_change", comment = "Gross Margin = Gross Profit / Total Revenue")
    private Integer grossMarginChange;

    @Column(name = "asset_turnover_change", comment = "Is the company generating more sales per dollar of assets?")
    private Integer assetTurnoverChange;
}
