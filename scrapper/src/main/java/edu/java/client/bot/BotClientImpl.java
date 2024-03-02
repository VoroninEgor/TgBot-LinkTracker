package edu.java.client.bot;

import edu.java.dto.LinkUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClientImpl implements BotClient {
    private final static String BASEURL = "http://localhost:8090/";
    private final WebClient webClient;

    public BotClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    public BotClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest linkUpdate) {
        return webClient.post()
            .uri("/updates")
            .bodyValue(linkUpdate)
            .retrieve()
            .toEntity(Void.class)
            .block();
    }
}
