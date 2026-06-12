CREATE OR REPLACE VIEW rankings AS
WITH RankedData AS (
    SELECT 
        m.ticker,
        m.name,
        p.total_score AS piotroski_score,
        m."earnings yield %",
        m."return on capital %",
        RANK() OVER (ORDER BY m."earnings yield %" DESC) as yield_rank,
        RANK() OVER (ORDER BY m."return on capital %" DESC) as roc_rank
    FROM magic_formula m
    LEFT JOIN piotroski_score p ON m.ticker = p.ticker
)
SELECT
    r.ticker,
    r.name,
    (yield_rank + roc_rank) as magic_formula_rank,
    r.piotroski_score,
    r."earnings yield %",
    r."return on capital %",
    r.yield_rank,
    r.roc_rank
FROM RankedData r
ORDER BY magic_formula_rank ASC;
