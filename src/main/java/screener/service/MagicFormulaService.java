package screener.service;

import org.springframework.stereotype.Service;

import screener.entity.MagicFormula;
import screener.repository.MagicFormulaRepository;

@Service
public class MagicFormulaService {

    private final MagicFormulaRepository magicFormulaRepository;

    // Dependency Injection (Spring's preferred way to get the repository)
    public MagicFormulaService(MagicFormulaRepository magicFormulaRepository) {
        this.magicFormulaRepository = magicFormulaRepository;
    }

    // Method to create and save a new company entry
    // public Company createNewCompany(String ticker, String name, Double ev) {
    public MagicFormula createNewCompany(MagicFormula magicFormula) {
        
        // 1. Create a new MagicFormula object (the row data)
        // Note: The 'ticker' acts as the primary key and must be set manually.

        // 2. Use the JpaRepository's 'save()' method to insert the object
        // The save method handles the underlying SQL INSERT statement for you.
        MagicFormula savedMagicFormula = magicFormulaRepository.save(magicFormula);
        
        // 3. Return the saved object
        return savedMagicFormula;
    }
}
