package edu.java.client.stackoverflow;

import edu.java.dto.QuestionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import static java.lang.String.format;

@Slf4j
public class StackOverFlowClientImpl implements StackOverFlowClient {
    private final static String BASEURL = "https://api.stackexchange.com/";
    private final WebClient webClient;

    public StackOverFlowClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    public StackOverFlowClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    @Override
    public QuestionResponse fetchQuestion(Long id) {
        return webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .path(format("questions/%s", id))
                .queryParam("site", "stackoverflow.com")
                .build())
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .block();
    }
}
