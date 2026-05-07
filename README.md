## Roudmap

* give a rating for magic formula companies

* develop methods for key financial indicators:
  * all phil town methodology
  * all 'Revolution Investing' metrics
  * separate table for risk metrics from google sheets
* combine some methodologies and risk metrics for unified investable metric (just a thought)
* Explore other data providers: 
  * Massive (formerly Polygon.io)
  * Financial Modeling Prep (FMP)
  * Twelve Data

## Implemented

* Alpha Vantage API provider response was to call the API not more than once per second
* date of last quater earnings used for calculations
* delete date from file names
* all magic formula indicators
* make separate method for calling only finnhub for newest EV values
* make safe from no data for all metrics(example: "shortTermDebt" : "None")
* make that "read data from json" and finhub api would be for all tickers that have files, not just the ones I mention in a file
