package screener.API.interfaces;

import screener.utils.Company;
import tools.jackson.databind.JsonNode;

public interface MarketDataClient {

    Company getCompanyData(String ticker);

    String getRawJson(String ticker);
    
    JsonNode getCompanyJson(String ticker);
}
