package screener.API.interfaces;

import screener.db.Company;

public interface MarketDataClient {

    Company getCompanyData(String ticker);

}
