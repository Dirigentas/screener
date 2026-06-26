package screener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "magic_formula") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class MagicFormula {
    
    @Id // Marks this as the primary key
    private String ticker;

    @Column(name = "earnings_yield", comment = "Measures how much earnings a company generates compared to its price %")
    private Double earningsYield; // Earnings Yield = EBIT / Enterprise Value
    
    @Column(name = "return_on_capital", comment = "Shows how efficiently a company uses its capital %")
    private Double returnOnCapital; // Return on Capital = EBIT / (Net Working Capital + Net Fixed Assets)

    @Column(name = "latest_quarter_date")
    private String latestQuarter;

    @Column(name = "yield_rank")
    private Integer yieldRank;

    @Column(name = "roc_rank")
    private Integer rocRank;
    
    @Column(name = "magic_formula_rank")
    private Integer magicFormulaRank;
}
