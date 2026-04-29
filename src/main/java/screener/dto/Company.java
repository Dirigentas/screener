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
    
    // @Column(comment = "Shows how efficiently a company uses its capital")
    // private Double returnOnCapital; // Return on Capital = EBIT / (Net Working Capital + Net Fixed Assets)
}
