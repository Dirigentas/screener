package screener.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import screener.entity.MagicFormula;
import screener.entity.Rankings;
import screener.repository.MagicFormulaRepository;
import screener.repository.RankingsRepository;

@Service
public class RankingsService {

    private final RankingsRepository rankingsRepository;
    private final MagicFormulaRepository magicFormulaRepository;

    // Dependency Injection (Spring's preferred way to get the repository)
    public RankingsService(RankingsRepository rankingsRepository, MagicFormulaRepository magicFormulaRepository) {
        this.rankingsRepository = rankingsRepository;
        this.magicFormulaRepository = magicFormulaRepository;
    }

    // Method to create and save a new company entry
    // public Company createNewCompany(String ticker, String name, Double ev) {
    public Rankings createRanking(Rankings rankings) {
        
        // 1. Create a new MagicFormula object (the row data)
        // Note: The 'ticker' acts as the primary key and must be set manually.

        // 2. Use the JpaRepository's 'save()' method to insert the object
        // The save method handles the underlying SQL INSERT statement for you.
        Rankings savedRankings = rankingsRepository.save(rankings);
        
        // 3. Return the saved object
        return savedRankings;
    }

    @Transactional
    public void syncRankings() {
        List<MagicFormula> formulas = magicFormulaRepository.findAll();
        for (MagicFormula m : formulas) {
            Rankings r = rankingsRepository.findById(m.getTicker())
                .orElse(new Rankings());
            r.setTicker(m.getTicker());
            r.setMagicFormulaRank(m.getMagicFormulaRank());
            rankingsRepository.save(r);
        }
    }

    // @Transactional
    // public void SaveRanks() {
    //     List<MagicFormula> formulas = magicFormulaRepository.findAll();
    //     List<Rankings> rankings = rankingsRepository.findAll();

    //     if (rankings.isEmpty()) {
    //         return;
    //     }

    //     // 3. Calculate Combined Magic Formula Rank
    //     for (Rankings ranking : rankings) {
    //         ranking.setMagicFormulaRank(xxx);
    //     }

    //     // 4. Batch Save
    //     rankingsRepository.saveAll(rankings);
    // }
}
