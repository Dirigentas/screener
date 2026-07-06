package screener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import screener.entity.Rankings;

/* Spring Data JPA
 * Lowest boilerplate code level
 * The modern industry standard, especially with Spring Boot.
 * It automatically generates almost all common CRUD (Create, Read, Update, Delete) methods for you simply by defining an interface.
 */

// JpaRepository<Entity_Class, Primary_Key_Type>
public interface RankingsRepository extends JpaRepository<Rankings, String> {

    // You can also define custom queries by just naming the method!
    // Spring will automatically create the query: SELECT * FROM companies WHERE name = ?
    // Company findByName(String name);

    @Modifying
    @Query(value = """
        INSERT INTO rankings (ticker, magic_formula_rank) 
        SELECT m.ticker, m.magic_formula_rank 
        FROM magic_formula m
        ON CONFLICT (ticker) 
        DO UPDATE SET magic_formula_rank = EXCLUDED.magic_formula_rank
        """, nativeQuery = true)
    void upsertFromMagicFormula();
}
