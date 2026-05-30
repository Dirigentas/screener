package screener.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import tools.jackson.databind.JsonNode;

@Service
public class FinnhubMarketDataClient {

    private final WebClient client;

    private final String URL = "https://finnhub.io/api/v1";
    private final String KEY = "d42eropr01qorler208gd42eropr01qorler2090";

    public FinnhubMarketDataClient() {
        this.client = WebClient.builder()
            .baseUrl(URL)
            .build();
    }

    public JsonNode getCompanyJson(String ticker, String pathParam, String queryParam) {
        if (queryParam.isEmpty()) {
            return client.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/stock/" + pathParam)
                    .queryParam("symbol", ticker)
                    .queryParam("token", KEY)
                    .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        }
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/stock/" + pathParam)
                .queryParam("symbol", ticker)
                .queryParam(queryParam)
                .queryParam("token", KEY)
                .build())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();
    }
}
