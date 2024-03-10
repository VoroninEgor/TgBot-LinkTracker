package edu.java.bot.client;

import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperTgChatClientImpl implements ScrapperTgChatClient {
    private final static String BASEURL = "http://localhost:8080/";
    private final static String BASE_ENDPOINT_WITH_PATH_VAR = "/tg-chat/{id}";

    private final WebClient webClient;

    public ScrapperTgChatClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    public ScrapperTgChatClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    @Override
    public void tgChatIdDelete(Long id) {
        webClient.delete()
            .uri(BASE_ENDPOINT_WITH_PATH_VAR, id)
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    @Override
    public void tgChatIdPost(Long id) {
        webClient.post()
            .uri(BASE_ENDPOINT_WITH_PATH_VAR, id)
            .retrieve()
            .toBodilessEntity()
            .block();
    }
}
