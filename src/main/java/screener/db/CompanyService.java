package screener.db;

import org.springframework.stereotype.Service;

import screener.dto.Company;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    // Dependency Injection (Spring's preferred way to get the repository)
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Method to create and save a new company entry
    // public Company createNewCompany(String ticker, String name, Double ev) {
    public Company createNewCompany(Company company) {
        
        // 1. Create a new Company object (the row data)
        // Note: The 'ticker' acts as the primary key and must be set manually.

        // 2. Use the JpaRepository's 'save()' method to insert the object
        // The save method handles the underlying SQL INSERT statement for you.
        Company savedCompany = companyRepository.save(company);
        
        // 3. Return the saved object
        return savedCompany;
    }
}
