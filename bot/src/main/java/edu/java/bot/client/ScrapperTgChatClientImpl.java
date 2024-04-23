package edu.java.bot.client;

import edu.java.bot.exception.ApiErrorResponse;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
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

    @Retry(name = "removeChatById")
    @Override
    public void removeById(Long id) {
        webClient.delete()
            .uri(BASE_ENDPOINT_WITH_PATH_VAR, id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, this::handleError)
            .toBodilessEntity()
            .block();
    }

    @Retry(name = "createChat")
    @Override
    public void create(Long id) {
        webClient.post()
            .uri(BASE_ENDPOINT_WITH_PATH_VAR, id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, this::handleError)
            .toBodilessEntity()
            .block();
    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(ApiErrorResponse.class)
            .flatMap(error -> Mono.error(new Exception(error.getExceptionMessage())));
    }
}
