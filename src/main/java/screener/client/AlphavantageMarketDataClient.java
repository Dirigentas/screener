package screener.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import tools.jackson.databind.JsonNode;

@Service
public class AlphavantageMarketDataClient {

    private final WebClient client;

    private final String URL = "https://www.alphavantage.co";
    private final String KEY = "A1AUVL6TFBYF1GHF";

    public AlphavantageMarketDataClient() {
        this.client = WebClient.builder()
            .baseUrl(URL)
            .build();
    }

    public JsonNode getCompanyJson(String ticker, String queryParam) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/query")
                .queryParam("function", queryParam)
                .queryParam("symbol", ticker)
                .queryParam("apikey", KEY)
                .build())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();
    }
}
