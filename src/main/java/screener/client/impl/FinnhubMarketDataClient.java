package screener.client.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import screener.client.MarketDataClient;
import tools.jackson.databind.JsonNode;

@Service
public class FinnhubMarketDataClient implements MarketDataClient {

    private final WebClient client;

    private final String URL = "https://finnhub.io/api/v1";
    private final String KEY = "d42eropr01qorler208gd42eropr01qorler2090";

    public FinnhubMarketDataClient() {
        this.client = WebClient.builder()
            .baseUrl(URL)
            .build();
    }
    // /stock/metric?symbol=AAPL&metric=all
    // /stock/profile2?symbol=AAPL
    @Override
    public JsonNode getCompanyJson(String ticker, String metric) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/stock/metric")
                .queryParam("symbol", ticker)
                .queryParam("metric", metric)
                .queryParam("token", KEY)
                .build())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();
    }
}
