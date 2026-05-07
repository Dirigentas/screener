package screener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "companies") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class Company {
    
    @Id // Marks this as the primary key
    // @ColumnOrder(1)
    private String ticker;

    @Column(name = "name")
    private String aname;

    @Column(name = "latest quarter date")
    private String latestQuarter;

    // private Double ev;

    @Column(name = "earnings yield %", comment = "Measures how much earnings a company generates compared to its price")
    private Double earningsYield; // Earnings Yield = EBIT / Enterprise Value
    
    // private Double ebit;
    
    // @Column(name = "working capital", comment = "Amount of money a company has tied up in its daily operations")
    // private Double workingCapital; // Net Working Capital = totalCurrentAssets - cashAndCashEquivalentsAtCarryingValue - totalCurrentLiabilities - shortTermDebt
    
    // @Column(name = "fixed assets", comment = "Value of a company’s long-term physical resources")
    // private Double fixedAssets; // Net Fixed Assets = totalNonCurrentAssets - intangibleAssets - goodwill
    
    @Column(name = "return on capital %", comment = "Shows how efficiently a company uses its capital")
    private Double returnOnCapital; // Return on Capital = EBIT / (Net Working Capital + Net Fixed Assets)
}
