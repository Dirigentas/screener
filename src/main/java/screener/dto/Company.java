package screener.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "companies") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class Company {
    
    @Id // Marks this as the primary key
    
    private String ticker; //varying(10)
    private String name; //varying(50)
    private Double enterpriseValue; // ev stands for Enterprise Value (double)
}
