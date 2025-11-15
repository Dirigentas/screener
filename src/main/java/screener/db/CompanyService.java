package screener.db;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    // Dependency Injection (Spring's preferred way to get the repository)
    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Method to create and save a new company entry
    public Company createNewCompany(String ticker, String name, Double ev) {
        
        // 1. Create a new Company object (the row data)
        // We do NOT set the 'id' because PostgreSQL will generate it
        Company newCompany = new Company(ticker, name, ev);

        // 2. Use the JpaRepository's 'save()' method to insert the object
        // The save method handles the underlying SQL INSERT statement for you.
        Company savedCompany = companyRepository.save(newCompany);
        
        // 3. Return the saved object (which now includes the auto-generated ID)
        return savedCompany;
    }
}