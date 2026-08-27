## Roudmap
  - add sec gov endpoints infrastructure
  - create empty columns to mid_data table for missing data for inputing by hand
  

  - make that only the first time finnhub profile2 would be called
  

* combine some methodologies and risk metrics for unified investable metric (just a thought)


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
* all TenCap metrics
* all PBT calculations to rankings table



## Useful

* test db connection, postgres=# for success
* psql -h localhost -p 5432 -U postgres -d postgres



https://www.sec.gov/search-filings/edgar-application-programming-interfaces

10 requests per second per IP address.
Best Practices to Avoid Bans
Client-Side Throttling: Add a delay or bucket limiter (e.g., maximum 8 requests/sec) to stay comfortably under the threshold.

Reuse HTTP Connections: Keep connections open (Keep-Alive) rather than opening a new TCP connection on every request.

Accept Compression: Pass Accept-Encoding: gzip, deflate to reduce payload sizes and improve latency.

add header: User-Agent ArasMinelgaPersonalDev minelga.aras@gmail.com without i gen get ban for going over then limit

