package screener.client.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import screener.client.MarketDataClient;
import tools.jackson.databind.JsonNode;

@Service
public class AlphavantageMarketDataClient implements MarketDataClient {

    private final WebClient client;

    private final String URL = "https://www.alphavantage.co";
    private final String KEY = "A1AUVL6TFBYF1GHF";

    public AlphavantageMarketDataClient() {
        this.client = WebClient.builder()
            .baseUrl(URL)
            .build();
    }

    @Override
    public JsonNode getCompanyJson(String ticker, String metric) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/query")
                .queryParam("function", metric)
                .queryParam("symbol", ticker)
                .queryParam("apikey", KEY)
                .build())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();
    }
}
