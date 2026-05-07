package screener.client;

import tools.jackson.databind.JsonNode;

public interface MarketDataClient {

    JsonNode getCompanyJson(String ticker, String metric);
}
