package screener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "mid_stats") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class MidStats {

    @Id // Marks this as the primary key
    private String ticker;
    
    @Column(name = "ev")
    private Double ev;
    
    @Column(name = "ebit")
    private Double ebit;
    
    @Column(name = "working capital", comment = "Amount of money a company has tied up in its daily operations")
    private Double workingCapital; // Net Working Capital = totalCurrentAssets - cashAndCashEquivalentsAtCarryingValue - totalCurrentLiabilities - shortTermDebt
    
    @Column(name = "fixed assets", comment = "Value of a company's long-term physical resources")
    private Double fixedAssets; // Net Fixed Assets = totalNonCurrentAssets - intangibleAssets - goodwill
}
