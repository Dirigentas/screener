-- purpose: to make table columns order as I want
CREATE TABLE piotroski_score (
    ticker VARCHAR(255) PRIMARY KEY,
    total_score INT,
    roa INT,
    cfo INT,
    roa_change INT,
    eps_quality INT,
    leverage_change INT,
    liquidity_change INT,
    dilution_change INT,
    gross_margin_change INT,
    asset_turnover_change INT
);

COMMENT ON COLUMN piotroski_score.ticker IS 'Unique identifier for the stock/company';
COMMENT ON COLUMN piotroski_score.total_score IS 'Overall Piotroski F-Score (sum of all 9 criteria, range 0-9)';
-- Profitability Signals
COMMENT ON COLUMN piotroski_score.roa IS 'ROA: Is the company generating a profit from its assets?';
COMMENT ON COLUMN piotroski_score.cfo IS 'CFO: Is the core business actually generating cash?';
COMMENT ON COLUMN piotroski_score.roa_change IS 'Δ ROA: Is the company profitability improving compared to last year?';
COMMENT ON COLUMN piotroski_score.eps_quality IS 'Quality of Earnings: Checks if the earnings are backed by real cash, helping you flag aggressive accounting tricks.';
-- Leverage, Liquidity, and Source of Funds Signals
          COMMENT ON COLUMN piotroski_score.leverage_change IS 'Δ LEVERAGE: Is the company reducing its debt burden?';
COMMENT ON COLUMN piotroski_score.liquidity_change IS 'Δ LIQUID: Is the company improving its ability to cover short-term obligations?';
COMMENT ON COLUMN piotroski_score.dilution_change IS 'Δ Dilution: Is the company raising emergency capital by printing more stock?';
-- Operating Efficiency Signals
COMMENT ON COLUMN piotroski_score.gross_margin_change IS 'Δ Gross MARGIN: Is the company improving its pricing power or cost of goods sold?';
COMMENT ON COLUMN piotroski_score.asset_turnover_change IS 'Δ TURNOVER: Is the company generating more sales per dollar of assets?';









