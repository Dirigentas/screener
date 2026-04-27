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
    private Double enterpriseValue; // ev stands for Enterprise Value
    private Double ebitFinn; // earning bebore interest & taxes
    private Double ebitAlph; // earning bebore interest & taxes
    private Double earningsYield; // Earnings Yield = EBIT / Enterprise Value
}
