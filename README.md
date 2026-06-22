## Roudmap

  - all TenCap metrics (operatingCashflowTTM and capitalExpendituresTTM)

  - all Rule1 metrics
  - all PBT metrics
  - all 'Revolution Investing' metrics

  - make that only the first time finnhub profile2 would be called
  
  - separate table for risk metrics from google sheets
* combine some methodologies and risk metrics for unified investable metric (just a thought)
* Explore other data providers: 
  - Massive (formerly Polygon.io)
  - Financial Modeling Prep (FMP) (šitas tikrai turi metines ataskaitas)
  - Twelve Data

## Implemented

* instead of the 'view', placed piotroski score in a separate table
* Alpha Vantage API provider response was to call the API not more than once per second
* date of last quater earnings used for calculations
* delete date from file names
* all magic formula indicators
* make separate method for calling only finnhub for newest EV values
* make safe from no data for all metrics(example: "shortTermDebt" : "None")
* make that "read data from json" and finhub api would be for all tickers that have files
* make separate table for intermediate calculations (for debugging odd final metrics)
* give a rating for magic formula companies
* piotrovski score metrics (done: 9 from 9)



## Useful

* test db connection, postgres=# for success
* psql -h localhost -p 5432 -U postgres -d postgres
