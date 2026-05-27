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
      END AS score_cfo,

      -- 3. Change in ROA
      CASE 
        WHEN "ROA TTM" > "ROA -1Y TTM" THEN 1 
        ELSE 0 
      END AS roa_change,

      -- 4. Quality of Earnings
      CASE 
        WHEN "CFO ttm M" > "Net Income TTM M" THEN 1 
        ELSE 0 
      END AS earnings_quality
      
      -- Note: You can add the other 7 Piotroski criteria here following the same pattern
    FROM
      mid_stats
  )
SELECT
  ticker,
  score_eps,
  score_cfo,
  roa_change,
  earnings_quality,
  -- The last column sums up all the previous score columns
  (score_eps + score_cfo + roa_change + earnings_quality) AS total_piotroski_score
FROM
  scored_data
ORDER BY
  total_piotroski_score DESC;