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
      END AS Δ_roa,

      -- 4. Quality of Earnings
      CASE 
        WHEN "CFO ttm M" > "Net Income TTM M" THEN 1 
        ELSE 0 
      END AS earnings_quality,

      -- 5. Change in Leverage
      CASE 
        WHEN "leverage" >= "leverage -1" THEN 1 
        ELSE 0 
      END AS Δ_leverage,

      -- 6. Change in Liquidity
      CASE 
        WHEN "Current ratio" > "Current ratio -1Y" THEN 1 
        ELSE 0 
      END AS Δ_liquidity,

      -- 7. Change in shares count
      CASE 
        WHEN "shares count M" <= "shares count -1Y M" THEN 1 
        ELSE 0 
      END AS Δ_dilusion,

      -- 8. Change in Gross Margin
      CASE 
        WHEN "Gross Margin" > "Gross Margin -1Y" THEN 1 
        ELSE 0 
      END AS Δ_gross_margin,

      -- 9. Change in Gross Margin
      CASE 
        WHEN "Asset Turnover" > "Asset Turnover -1Y" THEN 1 
        ELSE 0 
      END AS Δ_Asset_Turnover
      
    FROM
      mid_stats
  )
SELECT
  ticker,
  (score_eps + score_cfo + Δ_roa + earnings_quality + Δ_leverage + Δ_liquidity + Δ_dilusion + Δ_gross_margin + Δ_Asset_Turnover) AS total_score,
  score_eps,
  score_cfo,
  Δ_roa,
  earnings_quality,
  Δ_leverage,
  Δ_liquidity,
  Δ_dilusion,
  Δ_gross_margin,
  Δ_Asset_Turnover
  
FROM
  scored_data
ORDER BY
  total_score DESC;