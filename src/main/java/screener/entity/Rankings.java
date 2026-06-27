package screener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "rankings") // Sets the table name
@Data // The "all-in-one" shortcut. It applies @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor in one go
public class Rankings {

    @Id // Marks this as the primary key
    private String ticker;
        
    @Column(name = "name")
    private String aName;
    
    @Column(name = "magic_formula_rank")
    private Integer magicFormulaRank;
    
    @Column(name = "piotroski_score")
    private Integer piotroskiScore;

    @Column(name = "ten_cap_fcf")
    private double tenCapFcf;

    @Column(name = "ten_cap_earnings")
    private double tenCapEarnings;
    
    @Column(name = "latest_quarter_date")
    private String latestQuarter;
}
