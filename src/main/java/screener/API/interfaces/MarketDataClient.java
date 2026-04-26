package screener.API.interfaces;

import screener.dto.Company;
import tools.jackson.databind.JsonNode;

public interface MarketDataClient {

    Company getCompanyData(String ticker, String metric);

    String getRawJson(String ticker, String metric);
    
    JsonNode getCompanyJson(String ticker, String metric);
}
