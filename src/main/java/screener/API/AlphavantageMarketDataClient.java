package screener.API;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import screener.API.interfaces.MarketDataClient;
import screener.dto.Company;
import tools.jackson.databind.JsonNode;

@Service
public class AlphavantageMarketDataClient implements MarketDataClient {

    private final WebClient client;
    private final String token = "A1AUVL6TFBYF1GHF"; // 25 API calls/day

    public AlphavantageMarketDataClient() {
        this.client = WebClient.builder()
                .baseUrl("https://www.alphavantage.co")
                .build();
    }

    @Override
    public Company getCompanyData(String ticker, String metric) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "OVERVIEW")
                        .queryParam("symbol", ticker)
                        .queryParam("apikey", token)
                        .build())
                .retrieve()
                .bodyToMono(Company.class)
                .block(); // <--- simplest synchronous call
    }

    public String getRawJson(String ticker, String metric) {
    return client.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/query")
                    .queryParam("function", "OVERVIEW")
                    .queryParam("symbol", ticker)
                    .queryParam("apikey", token)
                    .build())
            .retrieve()
            .bodyToMono(String.class)   // <--- get raw JSON as text
            .block();
    }

    public JsonNode getCompanyJson(String ticker, String metric) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", metric)
                        .queryParam("symbol", ticker)
                        .queryParam("apikey", token)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
