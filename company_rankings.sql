CREATE VIEW magic_formula_rankings AS
WITH RankedData AS (
    SELECT 
        ticker,
        name,
        -- "latest quarter date",
        "earnings yield %",
        "return on capital %",
        -- Rank 1 for the highest yield, etc.
        RANK() OVER (ORDER BY "earnings yield %" DESC) as yield_rank,
        RANK() OVER (ORDER BY "return on capital %" DESC) as roc_rank
    FROM magic_formula
)
SELECT 
    *,
    (yield_rank + roc_rank) as total_rank_sum
FROM RankedData
ORDER BY total_rank_sum ASC;
