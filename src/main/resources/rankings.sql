-- view
CREATE OR REPLACE VIEW rankings AS
SELECT
    ms.ticker,
    mf.magic_formula_rank,
    ps.total_score AS piotroski_score
FROM mid_stats ms
LEFT JOIN magic_formula mf ON mf.ticker = ms.ticker
LEFT JOIN piotroski_score ps ON ps.ticker = ms.ticker
ORDER BY magic_formula_rank ASC;
