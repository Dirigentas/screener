## Roudmap
  - piotrovski score metrics (done: 2 from 9)
  - all Rule1 metrics
  - all TenCap metrics
  - all PBT metrics
  - all 'Revolution Investing' metrics
  
  - separate table for risk metrics from google sheets
* combine some methodologies and risk metrics for unified investable metric (just a thought)
* Explore other data providers: 
  - Massive (formerly Polygon.io)
  - Financial Modeling Prep (FMP)
  - Twelve Data

## Implemented

* Alpha Vantage API provider response was to call the API not more than once per second
* date of last quater earnings used for calculations
* delete date from file names
* all magic formula indicators
* make separate method for calling only finnhub for newest EV values
* make safe from no data for all metrics(example: "shortTermDebt" : "None")
* make that "read data from json" and finhub api would be for all tickers that have files
* make separate table for intermediate calculations (for debugging odd final metrics)
* give a rating for magic formula companies





## Useful

* test db connection, postgres=# for success
* psql -h localhost -p 5432 -U postgres -d postgres

## Piotroski score:

1. Profitability (4 Points)
These metrics measure the company's ability to generate funds internally and check the quality of those earnings.

Return on Assets (ROA): Is the company generating a profit from its assets?

ROA= 
Total Assets at Beginning of Year
Net Income
​
 
Score 1 if ROA>0 (positive net income), else 0.

Operating Cash Flow (CFO): Is the core business actually generating cash?

CFO= 
Total Assets at Beginning of Year
Cash Flow from Operations
​
 
Score 1 if CFO>0, else 0.

Change in ROA (ΔROA): Is the company's profitability improving compared to last year?

Score 1 if current year's ROA> prior year's ROA, else 0.

Accruals (Quality of Earnings): This checks if the earnings are backed by real cash, helping you flag aggressive accounting tricks.

Score 1 if Cash Flow from Operations > Net Income, else 0.

2. Leverage, Liquidity, and Source of Funds (3 Points)
These metrics flag companies that are taking on too much debt, struggling to pay short-term bills, or diluting their shareholders.

Change in Leverage (ΔLEVER): Is the company reducing its debt burden?

Leverage= 
Average Total Assets
Long-Term Debt
​
 
Score 1 if current year's leverage ratio is lower than the prior year's, else 0.

Change in Liquidity (ΔLIQUID): Is the company improving its ability to cover short-term obligations?

Current Ratio= 
Current Liabilities
Current Assets
​
 
Score 1 if current year's Current Ratio > prior year's Current Ratio, else 0.

Change in Shares in Issue (Dilution): Is the company raising emergency capital by printing more stock?

Score 1 if the current number of outstanding shares is ≤ the prior year's outstanding shares (meaning no dilution), else 0.

3. Operating Efficiency (2 Points)
These metrics measure how well the company is squeezing value out of its operations.

Change in Gross Margin (ΔMARGIN): Is the company improving its pricing power or cost of goods sold?

Gross Margin= 
Total Revenue
Gross Profit
​
Score 1 if current year's Gross Margin > prior year's Gross Margin, else 0.

Change in Asset Turnover (ΔTURN): Is the company generating more sales per dollar of assets?

Asset Turnover= 
Total Assets at Beginning of Year
Total Revenue
​
 
Score 1 if current year's Asset Turnover > prior year's Asset Turnover, else 0.

Tallying the Final Score
Once your screener runs these 9 calculations, it should sum the points for a final score between 0 and 9:

8 or 9: Very strong fundamentals (these are your buy candidates).

3 to 7: Average or mixed financial health.

0 to 2: Weak fundamentals and high risk of financial distress (prime candidates for shorting, according to Piotroski's original paper).