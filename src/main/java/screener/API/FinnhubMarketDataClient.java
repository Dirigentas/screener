package screener.API;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import screener.API.interfaces.MarketDataClient;
import screener.utils.Company;
import tools.jackson.databind.JsonNode;

@Service
public class FinnhubMarketDataClient implements MarketDataClient {

    // @Value("${finnhub.api.token}")
    // private String apiToken;

    private final WebClient client;
    private final String token = "d42eropr01qorler208gd42eropr01qorler2090";

    public FinnhubMarketDataClient() {
        this.client = WebClient.builder()
                .baseUrl("https://finnhub.io/api/v1")
                .build();
    }

    @Override
    public Company getCompanyData(String ticker) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stock/metric")
                        .queryParam("symbol", ticker)
                        .queryParam("metric", "all")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(Company.class)
                .block(); // <--- simplest synchronous call
    }

    public String getRawJson(String ticker) {
    return client.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/stock/metric")
                    .queryParam("symbol", ticker)
                    .queryParam("metric", "all")
                    .queryParam("token", token)
                    .build())
            .retrieve()
            .bodyToMono(String.class)   // <--- get raw JSON as text
            .block();
    }

    public JsonNode getCompanyJson(String ticker) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stock/metric")
                        .queryParam("symbol", ticker)
                        .queryParam("metric", "all")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
