## Piotroski score

### Profitability (4 Points)
These metrics measure the company's ability to generate funds internally and check the quality of those earnings.

1. Return on Assets (ROA): Is the company generating a profit from its assets? &check;

ROA = Total Assets at Beginning of Year / Net Income
 
Score 1 if ROA>0 (positive net income), else 0.


2. Operating Cash Flow (CFO): Is the core business actually generating cash? &check;

CFO= Total Assets at Beginning of Year / Cash Flow from Operations
​
Score 1 if CFO>0, else 0.


3. Change in ROA (ΔROA): Is the company's profitability improving compared to last year? &check;

Score 1 if current year's ROA> prior year's ROA, else 0.


4. Quality of Earnings: Checks if the earnings are backed by real cash, helping you flag aggressive accounting tricks. &check;

Score 1 if Cash Flow from Operations > Net Income, else 0. 


### Leverage, Liquidity, and Source of Funds (3 Points)
These metrics flag companies that are taking on too much debt, struggling to pay short-term bills, or diluting their shareholders.

5. Change in Leverage (ΔLEVER): Is the company reducing its debt burden?

Leverage = Average Total Assets / Long-Term Debt
 
Score 1 if current year's leverage ratio is lower than the prior year's, else 0.


6. Change in Liquidity (ΔLIQUID): Is the company improving its ability to cover short-term obligations?

Current Ratio = Current Assets / Current Liabilities
​
Score 1 if current year's Current Ratio > prior year's Current Ratio, else 0.


7. Change in Shares in Issue (Dilution): Is the company raising emergency capital by printing more stock? &check;

Score 1 if the current number of outstanding shares is ≤ the prior year's outstanding shares (meaning no dilution), else 0.


### Operating Efficiency (2 Points)
These metrics measure how well the company is squeezing value out of its operations.

8. Change in Gross Margin (ΔMARGIN): Is the company improving its pricing power or cost of goods sold? &check;

Gross Margin = Gross Profit / Total Revenue
​
Score 1 if current year's Gross Margin > prior year's Gross Margin, else 0.


9. Change in Asset Turnover (ΔTURN): Is the company generating more sales per dollar of assets?

Asset Turnover = Total Assets at Beginning of Year / Total Revenue
​
Score 1 if current year's Asset Turnover > prior year's Asset Turnover, else 0.


### Tallying the Final Score
Once your screener runs these 9 calculations, it should sum the points for a final score between 0 and 9:

8 or 9: Very strong fundamentals (these are your buy candidates).

3 to 7: Average or mixed financial health.

0 to 2: Weak fundamentals and high risk of financial distress (prime candidates for shorting, according to Piotroski's original paper).