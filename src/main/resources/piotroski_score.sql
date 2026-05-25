CREATE OR REPLACE VIEW "public"."piotroski_score" AS
WITH
  scored_data AS (
    SELECT
      ticker,
      -- 1. Profitability: Positive EPS?
      CASE 
        WHEN "EPS ttm" > 0 THEN 1 
        ELSE 0 
      END AS score_eps,
      
      -- 2. Profitability: Positive Operating Cash Flow?
      CASE 
        WHEN "CFO ttm M" > 0 THEN 1 
        ELSE 0 
      END AS score_cfo
      
      -- Note: You can add the other 7 Piotroski criteria here following the same pattern
    FROM
      mid_stats
  )
SELECT
  ticker,
  score_eps,
  score_cfo,
  -- The last column sums up all the previous score columns
  (score_eps + score_cfo) AS total_piotroski_score
FROM
  scored_data
ORDER BY
  total_piotroski_score DESC;