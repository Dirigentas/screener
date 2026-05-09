package screener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import screener.entity.MidStats;

/* Spring Data JPA
 * Lowest boilerplate code level
 * The modern industry standard, especially with Spring Boot.
 * It automatically generates almost all common CRUD (Create, Read, Update, Delete) methods for you simply by defining an interface.
 */

// JpaRepository<Entity_Class, Primary_Key_Type>
public interface MidStatsRepository extends JpaRepository<MidStats, String> {

    // You can also define custom queries by just naming the method!
    // Spring will automatically create the query: SELECT * FROM companies WHERE name = ?
    // Company findByName(String name);
    
}
