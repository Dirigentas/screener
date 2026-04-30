package screener.api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import screener.api.interfaces.MarketDataClient;
import screener.dto.Company;
import tools.jackson.databind.JsonNode;

@Service
public class FinnhubMarketDataClient implements MarketDataClient {

    private final WebClient client;
    private final String token = "d42eropr01qorler208gd42eropr01qorler2090"; // 60 API calls/minute

    public FinnhubMarketDataClient() {
        this.client = WebClient.builder()
                .baseUrl("https://finnhub.io")
                .build();
    }

    @Override
    public Company getCompanyData(String ticker, String metric) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/stock/metric")
                        .queryParam("symbol", ticker)
                        .queryParam("metric", "all")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(Company.class)
                .block(); // <--- simplest synchronous call
    }

    public String getRawJson(String ticker, String metric) {
    return client.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/api/v1/stock/metric")
                    .queryParam("symbol", ticker)
                    .queryParam("metric", "all")
                    .queryParam("token", token)
                    .build())
            .retrieve()
            .bodyToMono(String.class)   // <--- get raw JSON as text
            .block();
    }

    public JsonNode getCompanyJson(String ticker, String metric) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/stock/metric")
                        .queryParam("symbol", ticker)
                        .queryParam("metric", metric)
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
