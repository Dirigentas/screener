package screener.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void calculateAndSaveRanks() {
        List<MagicFormula> formulas = magicFormulaRepository.findAll();

        if (formulas.isEmpty()) {
            return;
        }

        // 1. Calculate Earnings Yield Rank (Descending)
        formulas.sort(Comparator.comparing(
                MagicFormula::getEarningsYield, 
                Comparator.nullsLast(Comparator.reverseOrder())
        ));
        for (int i = 0; i < formulas.size(); i++) {
            formulas.get(i).setYieldRank(i + 1);
        }

        // 2. Calculate Return on Capital Rank (Descending)
        formulas.sort(Comparator.comparing(
                MagicFormula::getReturnOnCapital, 
                Comparator.nullsLast(Comparator.reverseOrder())
        ));
        for (int i = 0; i < formulas.size(); i++) {
            formulas.get(i).setRocRank(i + 1);
        }

        // 3. Calculate Combined Magic Formula Rank
        for (MagicFormula formula : formulas) {
            int yieldRank = formula.getYieldRank() != null ? formula.getYieldRank() : 0;
            int rocRank = formula.getRocRank() != null ? formula.getRocRank() : 0;
            
            formula.setMagicFormulaRank(yieldRank + rocRank);
        }

        // 4. Batch Save
        magicFormulaRepository.saveAll(formulas);
    }
}
