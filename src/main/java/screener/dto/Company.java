package screener.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "companies") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class Company {
    
    @Id // Marks this as the primary key
    
    private String ticker;
    
    private String name;

    @Column(name = "earnings_yield_alph_%", comment = "Measures how much earnings a company generates compared to its price")
    private Double earningsYieldAlph; // Earnings Yield = EBIT / Enterprise Value
    
    @Column(name = "earnings_yield_finn_%", comment = "Measures how much earnings a company generates compared to its price")
    private Double earningsYieldFinn; // Earnings Yield = EBIT / Enterprise Value
    
    @Column(comment = "Amount of money a company has tied up in its daily operations")
    private Double workingCapital; // Net Working Capital = totalCurrentAssets - cashAndCashEquivalentsAtCarryingValue - totalCurrentLiabilities - shortTermDebt
    
    @Column(comment = "Value of a company’s long-term physical resources")
    private Double fixedAssets; // Net Fixed Assets = totalNonCurrentAssets - intangibleAssets - goodwill
    
    @Column(name = "return_On_Capital_%", comment = "Shows how efficiently a company uses its capital")
    private Double returnOnCapital; // Return on Capital = EBIT / (Net Working Capital + Net Fixed Assets)
}
