package edu.java.bot.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperTgChatClientImpl implements ScrapperTgChatClient {
    private final static String BASEURL = "http://localhost:8080/";
    private final WebClient webClient;
    private final static String BASE_ENDPOINT_WITH_PATH_VAR = "/tg-chat/{id}";

    public ScrapperTgChatClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    public ScrapperTgChatClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    @Override
    public ResponseEntity<Void> tgChatIdDelete(Long id) {
        return webClient.delete()
            .uri(BASE_ENDPOINT_WITH_PATH_VAR, id)
            .retrieve()
            .toEntity(Void.class)
            .block();
    }

    @Override
    public ResponseEntity<Void> tgChatIdPost(Long id) {
        return webClient.post()
            .uri(BASE_ENDPOINT_WITH_PATH_VAR, id)
            .retrieve()
            .toEntity(Void.class)
            .block();
    }
}
