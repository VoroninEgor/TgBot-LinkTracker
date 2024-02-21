package edu.java.client.stackoverflow;

import edu.java.dto.QuestionItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class StackOverFlowClientImpl implements StackOverFlowClient {
    private final static String BASEURL = "https://api.stackexchange.com/questions/";
    private final WebClient webClient;

    public StackOverFlowClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    public StackOverFlowClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    @Override
    public QuestionItem fetchQuestion(Long id) {
        return webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .path(Long.toString(id))
                .queryParam("site", "stackoverflow.com")
                .build())
            .retrieve()
            .bodyToMono(QuestionItem.class)
            .block();
    }
}
