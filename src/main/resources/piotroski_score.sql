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
