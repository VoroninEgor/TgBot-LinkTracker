package edu.java.client.bot;

import edu.java.dto.link.LinkUpdateRequest;
import edu.java.kafka.service.DataSender;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClientImpl implements DataSender {
    private final static String BASEURL = "http://localhost:8090/";
    private final WebClient webClient;

    public BotClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    public BotClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    @Retry(name = "updatesPost")
    @Override
    public void send(LinkUpdateRequest linkUpdate) {
        webClient.post()
            .uri("/updates")
            .bodyValue(linkUpdate)
            .retrieve()
            .toBodilessEntity()
            .block();
    }
}
